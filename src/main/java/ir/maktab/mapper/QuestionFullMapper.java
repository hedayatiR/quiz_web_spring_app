package ir.maktab.mapper;

import ir.maktab.mapper.base.impl.BaseMapperImpl;
import ir.maktab.model.course.Course;
import ir.maktab.model.course.dto.CourseDto;
import ir.maktab.model.question.BaseQuestion;
import ir.maktab.model.question.dto.QuestionFullDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionFullMapper extends BaseMapperImpl<BaseQuestion, QuestionFullDto> {

    @Autowired
    public QuestionFullMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
