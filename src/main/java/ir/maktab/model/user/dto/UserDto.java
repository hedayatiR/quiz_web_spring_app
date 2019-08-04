package ir.maktab.model.user.dto;

import ir.maktab.model.base.BaseDTO;
import ir.maktab.model.user.UserStatusEnum;
import lombok.Data;

@Data

public class UserDto extends BaseDTO<Long> {
    private String userName;
    private String password;
    private UserStatusEnum status;
}
