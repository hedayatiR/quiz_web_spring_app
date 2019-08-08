package ir.maktab.model.role.dto;

import ir.maktab.model.base.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class RoleDto extends BaseDTO<Long> {
    private String name;

    public RoleDto(String name) {
        this.name = name;
    }
}
