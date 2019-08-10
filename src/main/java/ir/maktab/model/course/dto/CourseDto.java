package ir.maktab.model.course.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ir.maktab.model.base.BaseDTO;
import ir.maktab.model.user.dto.UserDto;
import lombok.Data;

import java.util.Date;

@Data


public class CourseDto extends BaseDTO<Long> {

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date endDate;

    private UserDto teacher;
}
