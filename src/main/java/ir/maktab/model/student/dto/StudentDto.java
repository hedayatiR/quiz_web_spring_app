package ir.maktab.model.student.dto;

import ir.maktab.model.base.BaseDTO;
import ir.maktab.model.course.dto.CourseDto;
import ir.maktab.model.account.dto.AccountDto;
import lombok.Data;

import java.util.Set;

@Data

public class StudentDto extends BaseDTO<Long> {
    private String firstName;
    private String lastName;
    private AccountDto Account;

    private Set<CourseDto> courses;
}