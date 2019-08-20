package ir.maktab.controller;

import ir.maktab.model.base.BaseDTO;
import ir.maktab.model.question.dto.QuestionFullDto;
import ir.maktab.service.base.BaseService;
import ir.maktab.service.question.baseQuestion.BaseQuestionService;
import ir.maktab.service.question.factory.QuestionServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    QuestionServiceFactory questionServiceFactory;

    @Autowired
    BaseQuestionService baseQuestionService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<QuestionFullDto>> getAll() {
        Collection<QuestionFullDto> dtoCollection = baseQuestionService.findAll();
        return ResponseEntity.ok(dtoCollection);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO> create(@RequestBody QuestionFullDto questionFullDto) {
        if (questionFullDto.getId() != null) {
            return ResponseEntity
                    .badRequest()
                    .header("error", "A new entity cannot already have an ID")
                    .build();
        }
        BaseService baseService = questionServiceFactory.getBaseService(questionFullDto.getType());
        BaseDTO dto = baseService.save(questionFullDto);
        return ResponseEntity.ok(dto);
    }


    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseDTO> update(@RequestBody QuestionFullDto questionFullDto) {

        if (questionFullDto.getId() == null) {
            return ResponseEntity
                    .badRequest()
                    .header("id is null", "entity must have an ID to update")
                    .build();
        }

        BaseService baseService = questionServiceFactory.getBaseService(questionFullDto.getType());
        BaseDTO updatedQuestionFullDto = baseService.update(questionFullDto);
        return ResponseEntity.ok(updatedQuestionFullDto);
    }


    @DeleteMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        baseQuestionService.delete(id);
        return ResponseEntity.ok().header("deleted", "successful").build();
    }



    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/getByExamId/{examId}")
    public ResponseEntity<Collection<QuestionFullDto>> getQuestionsByExamId(@PathVariable("examId") Long examId) {
        Collection<QuestionFullDto> questions = baseQuestionService.findQuestionsByExamId(examId);
        if (questions == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(questions);
    }



}
