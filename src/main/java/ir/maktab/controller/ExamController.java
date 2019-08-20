package ir.maktab.controller;

import ir.maktab.controller.base.BaseRestFulService;
import ir.maktab.mapper.ExamMapper;
import ir.maktab.model.exam.Exam;
import ir.maktab.model.exam.dto.ExamDto;
import ir.maktab.service.exam.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/exams")
public class ExamController extends BaseRestFulService<Exam, ExamDto, Long, ExamService, ExamMapper> {


    @Autowired
    public ExamController(ExamService service) {
        super(service);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/findByCourseId/{id}")
    public ResponseEntity<Collection<ExamDto>> findByCourseId(@PathVariable("id") Long courseId) {
        Collection<ExamDto> examDtos = service.findByCourseId(courseId);
        if (examDtos == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(examDtos);
    }

}
