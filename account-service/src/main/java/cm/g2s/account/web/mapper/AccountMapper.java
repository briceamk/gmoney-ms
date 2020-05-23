package cm.g2s.account.web.mapper;

import cm.g2s.account.domain.model.Account;
import cm.g2s.account.web.dto.AccountDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateTimeMapper.class, DateMapper.class})
@DecoratedWith(AccountMapperDecorator.class)
public interface AccountMapper {
    Account map(AccountDto accountDto);
    AccountDto map(Account account);
}
