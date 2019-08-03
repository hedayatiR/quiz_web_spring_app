package ir.maktab.model.role.dto;

import ir.maktab.model.base.BaseDTO;
import lombok.Data;

@Data

public class RoleDto extends BaseDTO<Long> {
    private String name;
}
