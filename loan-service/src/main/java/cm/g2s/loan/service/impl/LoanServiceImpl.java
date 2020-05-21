package cm.g2s.loan.service.impl;

import cm.g2s.loan.constant.LoanConstantType;
import cm.g2s.loan.domain.model.Loan;
import cm.g2s.loan.domain.model.LoanState;
import cm.g2s.loan.infrastructure.repository.LoanRepository;
import cm.g2s.loan.security.CustomPrincipal;
import cm.g2s.loan.service.LoanManagerService;
import cm.g2s.loan.service.LoanService;
import cm.g2s.loan.service.rule.service.RuleClientService;
import cm.g2s.loan.shared.dto.LoanDto;
import cm.g2s.loan.shared.dto.LoanDtoPage;
import cm.g2s.loan.shared.exception.BadRequestException;
import cm.g2s.loan.shared.exception.ResourceNotFoundException;
import cm.g2s.loan.shared.mapper.LoanMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.script.ScriptException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service("loanService")
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final RuleClientService ruleClientService;
    private final LoanManagerService loanManagerService;


    @Override
    public LoanDto create(CustomPrincipal principal, LoanDto loanDto) throws ScriptException {
        //We set default value
        loanDto.setNumber(LoanConstantType.NEW_LOAN_NUMBER);
        loanDto.setCreationDate(LocalDateTime.now());
        loanDto.setState(LoanState.DRAFT.name());

        //We first run interest
        Map<String, BigDecimal> interest = processInterest(loanDto.getRuleDto().getId(),
                loanDto.getCreationDate(), loanDto.getIssueDate(), loanDto.getAmount());

        if(interest.get(LoanConstantType.INTEREST_KEY) == BigDecimal.ZERO) {
            log.error("We can't calculate interest of your loan. retry later");
            throw new BadRequestException("We can't determine interest of your loan. retry later");
        }
        //We set interest
        loanDto.setInterest(interest.get(LoanConstantType.INTEREST_KEY));
        return loanMapper.map(loanRepository.save(loanMapper.map(loanDto)));
    }

    @Override
    public void update(CustomPrincipal principal, LoanDto loanDto) throws ScriptException {
        loanDto.setCreationDate(LocalDateTime.now());

        //We first run interest
        Map<String, BigDecimal> interest = processInterest(loanDto.getRuleDto().getId(),
                loanDto.getCreationDate(), loanDto.getIssueDate(), loanDto.getAmount());

        if(interest.get(LoanConstantType.INTEREST_KEY) == BigDecimal.ZERO) {
            log.error("We can't determine interest of your loan. retry later");
            throw new BadRequestException("We can't determine interest of your loan. retry later");
        }

        //We set interest
        loanDto.setInterest(interest.get(LoanConstantType.INTEREST_KEY));

        loanMapper.map(loanRepository.save(loanMapper.map(loanDto)));
    }

    @Override
    public LoanDto findById(CustomPrincipal principal, String id) {
        Loan loan = loanRepository.findById(id).orElseThrow(
                () -> {
                    log.error("could not find loan with id {}", id);
                    throw new ResourceNotFoundException(String.format("could not find loan with id %s", id));
                }
        );
        return loanMapper.map(loan);
    }

    @Override
    public LoanDtoPage findAll(CustomPrincipal principal, String number,
                               String state, String partnerId, String accountId, PageRequest pageRequest) {

        Page<Loan> loanPage;

        if (!StringUtils.isEmpty(number)) {
            //search by category name
            loanPage = loanRepository.findByNumber(number, pageRequest);
        } else if (!StringUtils.isEmpty(state)) {
            //search by partnerId
            loanPage = loanRepository.findByState(LoanState.valueOf(state), pageRequest);
        } else if(!StringUtils.isEmpty(partnerId)) {
            loanPage = loanRepository.findByPartnerId(partnerId, pageRequest);
        }
        else if(!StringUtils.isEmpty(accountId)) {
            loanPage = loanRepository.findByAccountId(partnerId, pageRequest);
        }
        else{
            // search all
            loanPage = loanRepository.findAll(pageRequest);
        }

        return new LoanDtoPage(
                loanPage.getContent().stream().map(loanMapper::map).collect(Collectors.toList()),
                PageRequest.of(loanPage.getPageable().getPageNumber(),
                        loanPage.getPageable().getPageSize()),
                loanPage.getTotalElements()
        );
    }

    @Override
    public void deleteById(CustomPrincipal principal, String id) {
        Loan loan = loanRepository.findById(id).orElseThrow(
                () -> {
                    log.error("could not find loan with id {}", id);
                    throw new ResourceNotFoundException(String.format("could not find loan with id %s", id));
                }
        );
        loanRepository.delete(loan);
    }

    @Override
    public void validateLoan(CustomPrincipal principal, LoanDto loanDto) {
        if(loanDto.getState()!= null && !loanDto.getState().equals(LoanState.DRAFT.name())) {
           log.error("We cant not validate a loan with is not in Draft state");
           throw  new BadRequestException("We cant not validate a loan with is not in Draft state");
        }
        loanManagerService.validateLoan(principal, loanDto);
    }

    private Map<String, BigDecimal> processInterest(String ruleId, LocalDateTime creationDate,
                                                    LocalDateTime issueDate, BigDecimal amount) throws ScriptException {
  ;
        return ruleClientService.processInterest(ruleId, processNumberOfDays(creationDate, issueDate) , amount);
    }

    private Long processNumberOfDays(LocalDateTime creationDate, LocalDateTime issueDate) {
        return Duration.between(creationDate, issueDate).toDays() + 1;
    }
}
