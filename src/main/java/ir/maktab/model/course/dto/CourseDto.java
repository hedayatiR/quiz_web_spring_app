package ir.maktab.model.course.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ir.maktab.model.base.BaseDTO;
import ir.maktab.model.student.Student;
import ir.maktab.model.teacher.Teacher;
import ir.maktab.model.teacher.dto.TeacherDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data

public class CourseDto extends BaseDTO<Long> {

    private String name;
    private Long code;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date endDate;
//    private Set<Student> students;
    private TeacherDto teacher;

}
