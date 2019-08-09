package ir.maktab.model.exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ir.maktab.model.base.BaseDTO;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class ExamDto extends BaseDTO<Long> {
    private String title;
    private String description;
    private int duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date takeDate;

}
