package cm.g2s.loan.web.mapper;

import cm.g2s.loan.domain.model.Loan;
import cm.g2s.loan.service.account.model.AccountDto;
import cm.g2s.loan.service.account.service.AccountClientService;
import cm.g2s.loan.service.partner.model.PartnerDto;
import cm.g2s.loan.service.partner.service.PartnerClientService;
import cm.g2s.loan.web.dto.LoanDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class LoanMapperDecorator implements LoanMapper{

    private PartnerClientService partnerClientService;
    private AccountClientService accountClientService;
    private LoanMapper loanMapper;

    @Autowired
    public void setPartnerClientService(PartnerClientService partnerClientService) {
        this.partnerClientService = partnerClientService;
    }

    @Autowired
    public void setAccountClientService(AccountClientService accountClientService) {
        this.accountClientService = accountClientService;
    }

    @Autowired
    public void setLoanMapper(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    @Override
    public Loan map(LoanDto loanDto) {
        Loan loan = loanMapper.map(loanDto);
        if(loanDto.getAccountDto() != null)
            loan.setAccountId(loanDto.getAccountDto().getId());
        if(loanDto.getCompanyDto() != null)
            loan.setCompanyId(loanDto.getCompanyDto().getId());
        if(loanDto.getPartnerDto() != null)
            loan.setPartnerId(loanDto.getPartnerDto().getId());
        if(loanDto.getRuleDto() != null)
            loan.setRuleId(loanDto.getRuleDto().getId());
        if(loanDto.getUserDto() != null)
            loan.setUserId(loanDto.getUserDto().getId());
        return loan;
    }

    @Override
    public LoanDto map(Loan loan) {
        LoanDto loanDto = loanMapper.map(loan);
        if(loan.getPartnerId() != null) {
            PartnerDto partnerDto = partnerClientService.findById(loan.getPartnerId());
            if(partnerDto != null) {
                loanDto.setUserDto(partnerDto.getUserDto());
                loanDto.setPartnerDto(partnerDto);

                if(partnerDto.getCompanyDto() != null) {
                    loanDto.setCompanyDto(partnerDto.getCompanyDto());
                }

                if(partnerDto.getRuleDto() != null) {
                    loanDto.setRuleDto(partnerDto.getRuleDto());
                } else if(partnerDto.getCategoryDto() != null && partnerDto.getCategoryDto().getRuleDto() != null) {
                    loanDto.setRuleDto(partnerDto.getCategoryDto().getRuleDto());
                }
            }
        }
        if(loan.getAccountId() != null) {
            AccountDto accountDto = accountClientService.findById(loan.getAccountId());
            if(accountDto != null) {
                loanDto.setAccountDto(accountDto);
            }
        }
        return loanDto;
    }
}
