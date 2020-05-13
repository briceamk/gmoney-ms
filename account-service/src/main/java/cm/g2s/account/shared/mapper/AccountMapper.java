package cm.g2s.account.shared.mapper;

import cm.g2s.account.domain.model.Account;
import cm.g2s.account.shared.dto.AccountDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateTimeMapper.class, DateMapper.class})
@DecoratedWith(AccountMapperDecorator.class)
public interface AccountMapper {
    Account map(AccountDto accountDto);
    AccountDto map(Account account);
}
