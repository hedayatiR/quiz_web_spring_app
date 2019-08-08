package ir.maktab.controller;

import ir.maktab.controller.base.BaseRestFulService;
import ir.maktab.mapper.StudentMapper;
import ir.maktab.model.course.Course;
import ir.maktab.model.course.dto.CourseDto;
import ir.maktab.model.student.Student;
import ir.maktab.model.student.dto.StudentDto;
import ir.maktab.model.teacher.Teacher;
import ir.maktab.model.teacher.dto.TeacherDto;
import ir.maktab.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/api/students")
public class StudentController extends BaseRestFulService<Student, StudentDto, Long, StudentService, StudentMapper> {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public StudentController(StudentService baseService, StudentMapper studentMapper) {
        super(baseService, studentMapper);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/findByCourse/{id}")
    public ResponseEntity<Set<StudentDto>> findAllByCourseId(@PathVariable("id") Long id) {
        Set<Student> students = service.findAllByCourseId(id);
        if (students == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(baseMapper.entityToDtoSet(students, StudentDto.class));
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/activated")
    public ResponseEntity<Set<StudentDto>> findActivatedStudents() {
        Set<Student> students = service.findActivatedStudents();

        if (students == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(baseMapper.entityToDtoSet(students, StudentDto.class));
    }


    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/{id}/changeState")
    public ResponseEntity changeState(@PathVariable("id") Long id) {

        if (service.changeStatus(id) == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity(HttpStatus.OK);
    }

}
