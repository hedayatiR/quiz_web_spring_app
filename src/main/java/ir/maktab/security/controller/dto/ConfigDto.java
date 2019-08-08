package ir.maktab.security.controller.dto;

import ir.maktab.model.role.dto.RoleDto;
import ir.maktab.model.user.UserStatusEnum;
import lombok.Data;

import java.util.Collection;

@Data

public class ConfigDto {
    private String username;
    private Collection<RoleDto> roles;

    public ConfigDto(String username) {
        this.username = username;
    }
}
