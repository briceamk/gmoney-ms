package cm.g2s.transaction.service.momo.impl;

import cm.g2s.transaction.service.momo.MomoClientService;
import cm.g2s.transaction.service.momo.dto.TransferRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Service("momoClientService")
public class MomoClientServiceImpl implements MomoClientService {

    private final MomoClientService momoClientService;

    @Override
    public Object makeTransfer(@Valid TransferRequestDto transferRequest, String resourceId) {
        log.info("Calling momo-service api from transaction-service");
        return momoClientService.makeTransfer(transferRequest, resourceId);
    }

    @Override
    public Object findTransferInfo(String resourceId) {
        log.info("Calling momo-service api from transaction-service");
        return momoClientService.findTransferInfo(resourceId);
    }
}
