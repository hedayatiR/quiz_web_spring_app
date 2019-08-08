package ir.maktab.model.user.dto;

import ir.maktab.model.base.BaseDTO;
import ir.maktab.model.role.dto.RoleDto;
import ir.maktab.model.user.UserStatusEnum;
import lombok.Data;

import java.util.Collection;

@Data

public class UserDto extends BaseDTO<Long> {
    private String username;
    private String password;
    private String repeatPassword;
//    private UserStatusEnum status;
    private boolean enabled;
    private Collection<RoleDto> roles;
}
