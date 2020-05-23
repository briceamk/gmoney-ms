package cm.g2s.transaction.service.loan.impl;

import cm.g2s.transaction.service.loan.LoanClientService;
import cm.g2s.transaction.service.loan.dto.LoanDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service("loanClientService")
public class LoanClientServiceImpl implements LoanClientService {

    private final LoanClientService loanClientService;

    @Override
    public LoanDto findById(String id) {
        log.info("Calling loan-service api from transaction-service");
        return loanClientService.findById(id);
    }
}
