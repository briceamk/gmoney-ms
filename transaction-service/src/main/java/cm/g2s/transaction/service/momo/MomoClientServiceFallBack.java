package cm.g2s.transaction.service.momo;

import cm.g2s.transaction.service.momo.dto.TransferRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Slf4j
//@Component
public class MomoClientServiceFallBack {}/*implements MomoClientService {


    @Override
    public Object makeTransfer(@Valid TransferRequestDto transferRequest, String resourceId) {
        log.error("Error when calling momo-service api from transaction-service");
        return null;
    }

    @Override
    public Object findTransferInfo(String resourceId) {
        log.error("Error when calling momo-service api from transaction-service");
        return null;
    }
}*/
