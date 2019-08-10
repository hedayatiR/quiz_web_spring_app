package ir.maktab.controller;

import ir.maktab.controller.base.BaseRestFulService;
import ir.maktab.mapper.UserMapper;
import ir.maktab.model.user.User;
import ir.maktab.model.user.dto.UserDto;
import ir.maktab.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController extends BaseRestFulService<User, UserDto, Long, UserService, UserMapper> {


    @Autowired
    public UserController(UserService baseService, UserMapper userMapper) {
        super(baseService, userMapper);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/findStudentsByCourse/{id}")
    public ResponseEntity<Set<UserDto>> findAllByCourseId(@PathVariable("id") Long id) {
        Set<User> students = service.findAllStudentsByCourseId(id);
        if (students == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(baseMapper.entityToDtoSet(students, UserDto.class));
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/students/activated")
    public ResponseEntity<Set<UserDto>> findActivatedStudents() {
        Set<User> users = service.findActivatedUsersByRole("STUDENT");

        if (users == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(baseMapper.entityToDtoSet(users, UserDto.class));
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/teachers/activated")
    public ResponseEntity<Set<UserDto>> findActivatedTeachers() {
        Set<User> users = service.findActivatedUsersByRole("TEACHER");

        if (users == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(baseMapper.entityToDtoSet(users, UserDto.class));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/allActivated")
    public ResponseEntity<Set<UserDto>> findAllActivatedUsers() {
        Set<User> users = service.findAllActivatedUsers();

        if (users == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(baseMapper.entityToDtoSet(users, UserDto.class));
    }


    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/{id}/changeState")
    public ResponseEntity changeState(@PathVariable("id") Long id) {

        if (service.changeStatus(id) == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity(HttpStatus.OK);
    }

}
