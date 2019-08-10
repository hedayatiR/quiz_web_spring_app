package ir.maktab.model.user.dto;

import ir.maktab.model.base.BaseDTO;
import ir.maktab.model.account.dto.AccountDto;
import lombok.Data;

@Data

public class UserDto extends BaseDTO<Long> {
    private String firstName;
    private String lastName;
    private AccountDto Account;

}