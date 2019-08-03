package ir.maktab.model.course.dto;

import ir.maktab.model.base.BaseDTO;
import ir.maktab.model.student.Student;
import ir.maktab.model.teacher.Teacher;
import ir.maktab.model.teacher.dto.TeacherDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data

public class CourseDto extends BaseDTO<Long> {

    private String name;
    private Long code;
    private LocalDate startDate;
    private LocalDate endDate;
//    private Set<Student> students;
    private TeacherDto teacher;

}
