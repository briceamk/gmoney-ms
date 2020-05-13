package cm.g2s.account.shared.mapper;

import cm.g2s.account.domain.model.Account;
import cm.g2s.account.service.partner.service.PartnerClientService;
import cm.g2s.account.shared.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract  class AccountMapperDecorator implements AccountMapper{

    private  AccountMapper accountMapper;
    private PartnerClientService partnerClientService;

    @Autowired
    public void setAccountMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Autowired
    public void setPartnerClientService(PartnerClientService partnerClientService) {
        this.partnerClientService = partnerClientService;
    }

    @Override
    public Account map(AccountDto accountDto) {
        Account account = accountMapper.map(accountDto);
        if(account == null )
            return null;
        account.setPartnerId(accountDto.getPartnerDto().getId());
        return account;
    }

    @Override
    public AccountDto map(Account account) {
        AccountDto accountDto = accountMapper.map(account);
        if (accountDto == null)
            return null;
        accountDto.setPartnerDto(partnerClientService.findById(account.getPartnerId()));
        return accountDto;
    }
}
