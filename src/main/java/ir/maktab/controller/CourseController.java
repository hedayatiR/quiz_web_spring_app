package ir.maktab.controller;

import ir.maktab.controller.base.BaseRestFulService;
import ir.maktab.mapper.CourseMapper;
import ir.maktab.model.base.BaseDTO;
import ir.maktab.model.course.Course;
import ir.maktab.model.course.dto.CourseDto;
import ir.maktab.model.user.User;
import ir.maktab.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/courses")
public class CourseController extends BaseRestFulService<Course, CourseDto, Long, CourseService, CourseMapper> {

    @Autowired
    public CourseController(CourseService baseService, CourseMapper courseMapper) {
        super(baseService, courseMapper);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/findByTeacher/{id}")
    public ResponseEntity<Set<CourseDto>> findAllByTeacher(@PathVariable("id") Long id) {
        Set<Course> courses = service.findAllByTeacherId(id);
        if (courses == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(baseMapper.entityToDtoSet(courses, CourseDto.class));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/findByStudent/{id}")
    public ResponseEntity<Set<CourseDto>> findAllByStudent(@PathVariable("id") Long id) {
        Set<Course> courses = service.findAllByStudentId(id);
        if (courses == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(baseMapper.entityToDtoSet(courses, CourseDto.class));
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/findByTeacherUsername/{username}")
    public ResponseEntity<Set<CourseDto>> findAllByTeacherUsername(@PathVariable("username") String username) {
        Set<Course> courses = service.findAllByTeacherUsername(username);
        if (courses == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(baseMapper.entityToDtoSet(courses, CourseDto.class));
    }



    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/{courseId}/setTeacher")
    public ResponseEntity<CourseDto> setTeacher(@RequestBody BaseDTO<Long> teacherId, @PathVariable("courseId") Long courseId) {
        Course course = service.setTeacher(courseId, teacherId.getId());
        return ResponseEntity.ok(baseMapper.entityToDto(course, CourseDto.class));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/{courseId}/addStudents")
    public ResponseEntity<CourseDto> addStudents(@RequestBody Set<Long> studentsId, @PathVariable("courseId") Long id) {

        Course course = service.addStudents(id, studentsId);
        return ResponseEntity.ok(baseMapper.entityToDto(course, CourseDto.class));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/{courseId}/removeStudents")
    public ResponseEntity<CourseDto> removeStudents(@RequestBody Set<Long> studentsId, @PathVariable("courseId") Long id) {
        Course course = service.removeStudents(id, studentsId);
        return ResponseEntity.ok(baseMapper.entityToDto(course, CourseDto.class));
    }



}
