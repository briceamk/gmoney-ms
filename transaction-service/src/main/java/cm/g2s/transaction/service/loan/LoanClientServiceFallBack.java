package cm.g2s.transaction.service.loan;

import cm.g2s.transaction.service.loan.dto.LoanDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoanClientServiceFallBack implements LoanClientService {

    @Override
    public LoanDto findById(String id) {
        log.error("Error when calling loan-service api from transaction-service");
        return new LoanDto();
    }
}
