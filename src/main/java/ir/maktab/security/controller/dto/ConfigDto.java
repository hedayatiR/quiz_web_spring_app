package ir.maktab.security.controller.dto;

import ir.maktab.model.role.dto.RoleDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor

public class ConfigDto {
    private String username;
    private RoleDto role;


    public ConfigDto(String username, RoleDto role) {
        this.username = username;
        this.role = role;
    }
}
