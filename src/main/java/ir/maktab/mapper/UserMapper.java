package ir.maktab.mapper;

import ir.maktab.mapper.base.impl.BaseMapperImpl;
import ir.maktab.model.user.User;
import ir.maktab.model.user.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class UserMapper extends BaseMapperImpl<User, UserDto> {

    public UserMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
