package ir.maktab.mapper;

import ir.maktab.mapper.base.impl.BaseMapperImpl;
import ir.maktab.model.teacher.Teacher;
import ir.maktab.model.teacher.dto.TeacherDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class TeacherMapper extends BaseMapperImpl<Teacher, TeacherDto> {

    public TeacherMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
