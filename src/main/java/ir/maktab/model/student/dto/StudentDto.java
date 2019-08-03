package ir.maktab.model.student.dto;

import ir.maktab.model.base.BaseDTO;
import ir.maktab.model.role.Role;
import ir.maktab.model.role.dto.RoleDto;
import ir.maktab.model.user.UserStatusEnum;
import ir.maktab.model.user.dto.UserDto;
import lombok.Data;

@Data

public class StudentDto extends BaseDTO<Long> {
    private String firstName;
    private String lastName;
    private UserStatusEnum status;
    private RoleDto role;
    private UserDto user;
}