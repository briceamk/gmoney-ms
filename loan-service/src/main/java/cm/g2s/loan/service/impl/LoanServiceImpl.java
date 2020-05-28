package cm.g2s.loan.service.impl;

import cm.g2s.loan.constant.LoanConstantType;
import cm.g2s.loan.domain.model.Loan;
import cm.g2s.loan.domain.model.LoanState;
import cm.g2s.loan.infrastructure.repository.LoanRepository;
import cm.g2s.loan.security.CustomPrincipal;
import cm.g2s.loan.service.LoanService;
import cm.g2s.loan.service.rule.service.RuleClientService;
import cm.g2s.loan.exception.BadRequestException;
import cm.g2s.loan.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.script.ScriptException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service("loanService")
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final RuleClientService ruleClientService;


    @Override
    public Loan create(CustomPrincipal principal, Loan loan) throws ScriptException {
        //We first check if account is not in PENDING state. In this state, loan can't be create
        //TODO check account state before

        //We set default value
        loan.setNumber(LoanConstantType.NEW_LOAN_NUMBER);
        loan.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        loan.setState(LoanState.DRAFT);

        //We first run interest
        Map<String, BigDecimal> interest = processInterest(loan.getRuleId(),
                loan.getCreationDate().toLocalDateTime(),
                loan.getIssueDate().toLocalDateTime(), loan.getAmount());

        if(interest.get(LoanConstantType.INTEREST_KEY) == BigDecimal.ZERO) {
            log.error("We can't calculate interest of your loan. retry later");
            throw new BadRequestException("We can't determine interest of your loan. retry later");
        }
        //We set interest
        loan.setInterest(interest.get(LoanConstantType.INTEREST_KEY));
        return loanRepository.save(loan);
    }

    @Override
    public void update(CustomPrincipal principal, Loan loan) throws ScriptException {
        //if loan is not validate we update creation date
        if(loan.getState().equals(LoanState.DRAFT)) {
            loan.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
            //We first run interest
            Map<String, BigDecimal> interest = processInterest(loan.getRuleId(),
                    loan.getCreationDate().toLocalDateTime(),
                    loan.getIssueDate().toLocalDateTime(), loan.getAmount());
            if(interest.get(LoanConstantType.INTEREST_KEY) == BigDecimal.ZERO) {
                log.error("We can't determine interest of your loan. retry later");
                throw new BadRequestException("We can't determine interest of your loan. retry later");
            }
            //We set interest
            loan.setInterest(interest.get(LoanConstantType.INTEREST_KEY));
        }
        loanRepository.save(loan);
    }

    @Override
    public Loan findById(CustomPrincipal principal, String id) {
        return loanRepository.findById(id).orElseThrow(
                () -> {
                    log.error("could not find loan with id {}", id);
                    throw new ResourceNotFoundException(String.format("could not find loan with id %s", id));
                }
        );
    }

    @Override
    public Page<Loan> findAll(CustomPrincipal principal, String number,
                               String state, String partnerId, String accountId, PageRequest pageRequest) {

        Page<Loan> loanPage;

        if (!StringUtils.isEmpty(number)) {
            //search by number
            loanPage = loanRepository.findByNumber(number, pageRequest);
        } else if (!StringUtils.isEmpty(state)) {
            //search by state
            loanPage = loanRepository.findByState(LoanState.valueOf(state), pageRequest);
        } else if(!StringUtils.isEmpty(partnerId)) {
            //search by partnerId
            loanPage = loanRepository.findByPartnerId(partnerId, pageRequest);
        }
        else if(!StringUtils.isEmpty(accountId)) {
            //search by accountId
            loanPage = loanRepository.findByAccountId(accountId, pageRequest);
        }
        else{
            // search all
            loanPage = loanRepository.findAll(pageRequest);
        }

        return loanPage;
    }

    @Override
    public void deleteById(CustomPrincipal principal, String id) {
        loanRepository.delete(findById(principal, id));
    }

    @Override
    public Loan validateLoan(CustomPrincipal principal, Loan loan) {
        if(loan.getState()!= null && !loan.getState().equals(LoanState.DRAFT)) {
           log.error("We cant not validate a loan with is not in Draft state");
           throw  new BadRequestException("We cant not validate a loan with is not in Draft state");
        }
        return loan;
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
