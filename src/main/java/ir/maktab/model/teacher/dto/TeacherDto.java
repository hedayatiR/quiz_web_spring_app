package ir.maktab.model.teacher.dto;

import ir.maktab.model.base.BaseDTO;
import ir.maktab.model.account.dto.AccountDto;
import lombok.Data;

@Data

public class TeacherDto extends BaseDTO<Long> {
    private String firstName;
    private String lastName;
    private AccountDto Account;
}