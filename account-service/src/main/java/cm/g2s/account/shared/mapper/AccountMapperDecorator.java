package cm.g2s.account.shared.mapper;

import cm.g2s.account.domain.model.Account;
import cm.g2s.account.service.partner.model.PartnerDto;
import cm.g2s.account.service.partner.service.PartnerClientService;
import cm.g2s.account.service.user.model.UserDto;
import cm.g2s.account.service.user.service.UserClientService;
import cm.g2s.account.shared.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract  class AccountMapperDecorator implements AccountMapper{

    private  AccountMapper accountMapper;
    private PartnerClientService partnerClientService;
    private UserClientService userClientService;

    @Autowired
    public void setAccountMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Autowired
    public void setPartnerClientService(PartnerClientService partnerClientService) {
        this.partnerClientService = partnerClientService;
    }

    @Autowired
    public void setUserClientService(UserClientService userClientService) {
        this.userClientService = userClientService;
    }

    @Override
    public Account map(AccountDto accountDto) {
        Account account = accountMapper.map(accountDto);
        if(account == null )
            return null;
        if(accountDto.getPartnerDto() != null)
            account.setPartnerId(accountDto.getPartnerDto().getId());
        if(accountDto.getUserDto() != null)
            account.setUserId(accountDto.getUserDto().getId());
        return account;
    }

    @Override
    public AccountDto map(Account account) {
        AccountDto accountDto = accountMapper.map(account);
        if (accountDto == null)
            return null;
        if(account.getPartnerId() != null){
            PartnerDto partnerDto = partnerClientService.findById(account.getPartnerId());
            if(partnerDto != null)
                accountDto.setPartnerDto(partnerDto);
        }
        if(account.getUserId() != null) {
            UserDto userDto = userClientService.findById(account.getUserId());
            if (userDto != null)
                accountDto.setUserDto(userDto);
        }
        return accountDto;
    }
}
