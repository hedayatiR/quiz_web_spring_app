package ir.maktab.mapper;

import ir.maktab.mapper.base.impl.BaseMapperImpl;
import ir.maktab.model.exam.Exam;
import ir.maktab.model.exam.dto.ExamDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ExamMapper extends BaseMapperImpl<Exam, ExamDto> {

    @Autowired
    public ExamMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
