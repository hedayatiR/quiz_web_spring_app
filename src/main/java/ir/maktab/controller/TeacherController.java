package ir.maktab.controller;

import ir.maktab.controller.base.BaseRestFulService;
import ir.maktab.mapper.TeacherMapper;
import ir.maktab.model.student.Student;
import ir.maktab.model.student.dto.StudentDto;
import ir.maktab.model.teacher.Teacher;
import ir.maktab.model.teacher.dto.TeacherDto;
import ir.maktab.service.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RestController
@RequestMapping("/api/teachers")
public class TeacherController extends BaseRestFulService<Teacher, TeacherDto, Long, TeacherService, TeacherMapper> {

    @Autowired
    public TeacherController(TeacherService baseService, TeacherMapper teacherMapper) {
        super(baseService, teacherMapper);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/findByCourse/{id}")
    public ResponseEntity<Set<TeacherDto>> findAllByCourseId(@PathVariable("id") Long id) {
        Set<Teacher> teachers = service.findByCourseId(id);
        if (teachers == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(baseMapper.entityToDtoSet(teachers, TeacherDto.class));
    }


}
