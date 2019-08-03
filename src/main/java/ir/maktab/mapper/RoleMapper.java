package ir.maktab.mapper;

import ir.maktab.mapper.base.impl.BaseMapperImpl;
import ir.maktab.model.role.Role;
import ir.maktab.model.role.dto.RoleDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class RoleMapper extends BaseMapperImpl<Role, RoleDto> {

    public RoleMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
