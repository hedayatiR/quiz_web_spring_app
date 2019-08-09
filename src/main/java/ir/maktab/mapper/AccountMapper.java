package ir.maktab.mapper;

import ir.maktab.mapper.base.impl.BaseMapperImpl;
import ir.maktab.model.account.Account;
import ir.maktab.model.account.dto.AccountDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class AccountMapper extends BaseMapperImpl<Account, AccountDto> {

    public AccountMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
