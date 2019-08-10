package ir.maktab.model.account.dto;

import ir.maktab.model.base.BaseDTO;
import ir.maktab.model.role.dto.RoleDto;
import lombok.Data;

@Data

public class AccountDto extends BaseDTO<Long> {
    private String username;
    private String password;
    private String repeatPassword;
    private boolean enabled;
    private RoleDto role;
}
