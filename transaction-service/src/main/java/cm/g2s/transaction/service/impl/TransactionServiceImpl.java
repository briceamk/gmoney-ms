package cm.g2s.transaction.service.impl;

import cm.g2s.transaction.constant.TransactionConstantType;
import cm.g2s.transaction.domain.model.*;
import cm.g2s.transaction.infrastructure.repository.TransactionRepository;
import cm.g2s.transaction.security.CustomPrincipal;
import cm.g2s.transaction.service.TransactionService;
import cm.g2s.transaction.exception.BadRequestException;
import cm.g2s.transaction.service.momo.MomoClientService;
import cm.g2s.transaction.service.momo.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final MomoClientService momoClientService;

    @Override
    public Transaction create(CustomPrincipal principal, Transaction transaction) {

        //TODO implement default values for order Type of transactions
        transaction.setState(TransactionState.TO_SEND);
        transaction.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));

        LocalDateTime currentTime = LocalDateTime.now();
        String number = String.valueOf(currentTime.getYear()) + String.valueOf(currentTime.getMonthValue()) +
                String.valueOf(currentTime.getDayOfMonth()) + String.valueOf(currentTime.getHour()) +
                String.valueOf(currentTime.getMinute()) +  String.valueOf(currentTime.getSecond());

        if(transaction.getLoanId() != null && !transaction.getLoanId().isEmpty()) {
            transaction.setType(TransactionType.DEBIT);
            transaction.setOrigin(TransactionOrigin.LOAN);
            if(!transaction.getLoanId().isEmpty()) {
                transaction.setNumber("L" + number);
            }//TODO implement default prefix (P: for penalty, R: Refund) for order Type of transactions
        }

        return transactionRepository.save(transaction);
    }

    @Override
    public void update(CustomPrincipal principal, Transaction transaction) {
        //TODO implement
        transactionRepository.save(transaction);
    }

    @Override
    public Transaction findById(CustomPrincipal principal, String id) {
        return transactionRepository.findById(id).orElseThrow(
                () -> {
                    log.info("Transaction with id {} not found!", id);
                    throw  new BadRequestException(String.format("Transaction with id %s not found!", id));
                }
        );

    }

    @Override
    public Page<Transaction> findAll(CustomPrincipal principal, String number, String type,
                                      String mode, String origin, String state,
                                      String partnerId, String accountId, PageRequest pageRequest) {
        Page<Transaction> transactionPage;

        if (!StringUtils.isEmpty(number)) {
            //search by category name
            transactionPage = transactionRepository.findByNumber(number, pageRequest);
        } else if (!StringUtils.isEmpty(state)) {
            //search by partnerId
            transactionPage = transactionRepository.findByState(TransactionState.valueOf(state), pageRequest);
        } else if (!StringUtils.isEmpty(type)) {
            //search by partnerId
            transactionPage = transactionRepository.findByType(TransactionType.valueOf(type), pageRequest);
        } else if (!StringUtils.isEmpty(mode)) {
            //search by partnerId
            transactionPage = transactionRepository.findByMode(TransactionMode.valueOf(mode), pageRequest);
        } else if (!StringUtils.isEmpty(origin)) {
            //search by partnerId
            transactionPage = transactionRepository.findByMode(TransactionMode.valueOf(origin), pageRequest);
        } else if(!StringUtils.isEmpty(partnerId)) {
            //search by partnerId
            transactionPage = transactionRepository.findByPartnerId(partnerId, pageRequest);
        } else if(!StringUtils.isEmpty(accountId)) {
            //search by accountId
            transactionPage = transactionRepository.findByAccountId(accountId, pageRequest);
        } else{
            // search all
            transactionPage = transactionRepository.findAll(pageRequest);
        }

        return transactionPage;
    }

    @Override
    public void deleteById(CustomPrincipal principal, String id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(
                () -> {
                    log.info("Transaction with id {} not found!", id);
                    throw  new BadRequestException(String.format("Transaction with id %s not found!", id));
                }
        );

        transactionRepository.delete(transaction);
    }

    @Override
    public List<Transaction> findReadyToSend(CustomPrincipal principal, TransactionState toSend) {
        return transactionRepository.findByState(toSend);
    }

    @Override
    public Boolean sendMoney(CustomPrincipal principal, Transaction transaction) {
        TransferRequestDto transferRequestDto = transform(transaction);
        Object result = momoClientService.makeTransfer(transferRequestDto, transaction.getId());
        if(result instanceof Boolean && (Boolean)result) {
            transaction.setState(TransactionState.WAITING_SEND_RESPONSE);
            update(principal, transaction);
            transaction = findById(principal, transaction.getId());
            Object info = momoClientService.findTransferInfo(transaction.getId());
            if(info instanceof TransferResponseDto) {
                TransferResponseDto transferResponseDto = (TransferResponseDto) info;
                if(transferResponseDto.getStatus().equals(TransferState.SUCCESSFUL.name())) {
                    transaction.setState(TransactionState.SEND);
                    transaction.setTransferState(TransferState.SUCCESSFUL);
                    transaction.setFinancialTransactionId(transferResponseDto.getFinancialTransactionId());
                    update(principal, transaction);
                    return true;
                } else if(transferResponseDto.getStatus().equals(TransferState.PENDING.name())){
                    transaction.setState(TransactionState.SEND_PENDING);
                    transaction.setTransferState(TransferState.PENDING);
                    transaction.setFinancialTransactionId(transferResponseDto.getFinancialTransactionId());
                    update(principal, transaction);
                    return false;
                } else {
                    transaction.setState(TransactionState.SEND_EXCEPTION);
                    transaction.setTransferState(TransferState.FAILED);
                    transaction.setErrorCode(transferResponseDto.getReason().getCode());
                    transaction.setErrorMessage(transferResponseDto.getReason().getMessage());
                    update(principal, transaction);
                    return false;
                }
            } else {
                log.error("Unable to call momo-service");
                return false;
            }

        } else if(result instanceof ReasonDto) {
            ReasonDto reasonDto = (ReasonDto) result;
            transaction.setTransferState(TransferState.FAILED);
            transaction.setErrorCode(reasonDto.getCode());
            transaction.setErrorMessage(reasonDto.getMessage());
            update(principal, transaction);
            return false;
        }
        else {
            log.error("Unable to call momo-service");
            return false;
        }
    }

    private TransferRequestDto transform(Transaction transaction) {
        return TransferRequestDto.builder()
                .amount(transaction.getAmount().setScale(0).toPlainString())
                .currency(TransactionConstantType.DEFAULT_CURRENCY_CODE)
                .externalId(transaction.getNumber())
                .payee(
                        PayeeDto.builder()
                                .partyId(transaction.getMobile())
                                .partyIdType(PartyType.MSISDN.name())
                                .build()
                )
                .payerMessage(TransactionConstantType.PAYER_MESSAGE)
                .payeeNote(TransactionConstantType.PAYER_MESSAGE)
                .build();
    }
}
