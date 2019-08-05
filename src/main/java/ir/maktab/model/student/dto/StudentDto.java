package ir.maktab.model.student.dto;

import ir.maktab.model.base.BaseDTO;
import ir.maktab.model.course.Course;
import ir.maktab.model.course.dto.CourseDto;
import ir.maktab.model.role.Role;
import ir.maktab.model.role.dto.RoleDto;
import ir.maktab.model.user.UserStatusEnum;
import ir.maktab.model.user.dto.UserDto;
import lombok.Data;

import java.util.Set;

@Data

public class StudentDto extends BaseDTO<Long> {
    private String firstName;
    private String lastName;
    private RoleDto role;
    private UserDto user;

    private Set<CourseDto> courses;
}