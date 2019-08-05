package ir.maktab.mapper;

import ir.maktab.mapper.base.impl.BaseMapperImpl;
import ir.maktab.model.student.Student;
import ir.maktab.model.student.dto.StudentDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;


@Component
public class StudentMapper extends BaseMapperImpl<Student, StudentDto> {

    public StudentMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
