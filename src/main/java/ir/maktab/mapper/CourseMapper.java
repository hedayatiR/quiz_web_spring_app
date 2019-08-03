package ir.maktab.mapper;

import ir.maktab.mapper.base.impl.BaseMapperImpl;
import ir.maktab.model.course.Course;
import ir.maktab.model.course.dto.CourseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper extends BaseMapperImpl<Course, CourseDto> {

    @Autowired
    public CourseMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
