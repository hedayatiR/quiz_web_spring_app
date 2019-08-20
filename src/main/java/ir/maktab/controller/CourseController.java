package ir.maktab.controller;

import ir.maktab.controller.base.BaseRestFulService;
import ir.maktab.mapper.CourseMapper;
import ir.maktab.model.base.BaseDTO;
import ir.maktab.model.course.Course;
import ir.maktab.model.course.dto.CourseDto;
import ir.maktab.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/courses")
public class CourseController extends BaseRestFulService<Course, CourseDto, Long, CourseService, CourseMapper> {


    @Autowired
    public CourseController(CourseService service) {
        super(service);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/findByTeacher/{id}")
    public ResponseEntity<Collection<CourseDto>> findAllByTeacher(@PathVariable("id") Long id) {
        Collection<CourseDto> courseDtos = service.findAllByTeacherId(id);
        if (courseDtos == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(courseDtos);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/findByStudent/{id}")
    public ResponseEntity<Collection<CourseDto>> findAllByStudent(@PathVariable("id") Long id) {
        Collection<CourseDto> courseDtos = service.findAllByStudentId(id);
        if (courseDtos == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(courseDtos);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/findByTeacherUsername/{username}")
    public ResponseEntity<Collection<CourseDto>> findAllByTeacherUsername(@PathVariable("username") String username) {
        Collection<CourseDto> courseDtos = service.findAllByTeacherUsername(username);
        if (courseDtos == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(courseDtos);
    }



    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/{courseId}/setTeacher")
    public ResponseEntity<CourseDto> setTeacher(@RequestBody BaseDTO<Long> teacherId, @PathVariable("courseId") Long courseId) {
        CourseDto courseDto = service.setTeacher(courseId, teacherId.getId());
        return ResponseEntity.ok(courseDto);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/{courseId}/addStudents")
    public ResponseEntity<CourseDto> addStudents(@RequestBody Set<Long> studentsId, @PathVariable("courseId") Long id) {

        CourseDto courseDto = service.addStudents(id, studentsId);
        return ResponseEntity.ok(courseDto);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/{courseId}/removeStudents")
    public ResponseEntity<CourseDto> removeStudents(@RequestBody Set<Long> studentsId, @PathVariable("courseId") Long id) {
        CourseDto courseDto = service.removeStudents(id, studentsId);
        return ResponseEntity.ok(courseDto);
    }

}
