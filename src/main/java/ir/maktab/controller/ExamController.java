package ir.maktab.controller;

import ir.maktab.controller.base.BaseRestFulService;
import ir.maktab.mapper.ExamMapper;
import ir.maktab.model.exam.Exam;
import ir.maktab.model.exam.dto.ExamDto;
import ir.maktab.service.exam.ExamService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/exams")
public class ExamController extends BaseRestFulService<Exam, ExamDto, Long, ExamService, ExamMapper> {

    public ExamController(ExamService service, ExamMapper baseMapper) {
        super(service, baseMapper);
    }
}
