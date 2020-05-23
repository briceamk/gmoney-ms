package cm.g2s.transaction.web.mapper;

import cm.g2s.transaction.domain.model.Transaction;
import cm.g2s.transaction.service.loan.LoanClientService;
import cm.g2s.transaction.service.loan.dto.LoanDto;
import cm.g2s.transaction.web.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class TransactionMapperDecorator implements TransactionMapper{

    private TransactionMapper transactionMapper;
    private LoanClientService loanClientService;

    @Autowired
    public void setTransactionMapper(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    @Autowired
    public void setLoanClientService(LoanClientService loanClientService) {
        this.loanClientService = loanClientService;
    }

    @Override
    public TransactionDto map(Transaction transaction) {
        TransactionDto transactionDto = transactionMapper.map(transaction);

        if(transaction.getLoanId() != null && !transaction.getLoanId().isEmpty()) {
            LoanDto loanDto = loanClientService.findById(transaction.getLoanId());
            if(loanDto != null && !loanDto.getId().isEmpty()) {
                transactionDto.setPartnerDto(loanDto.getPartnerDto());
                transactionDto.setLoanDto(loanDto);
                transactionDto.setUserDto(loanDto.getUserDto());
                transactionDto.setAccountDto(loanDto.getAccountDto());
            }
        }
        return transactionDto;
    }

    @Override
    public Transaction map(TransactionDto transactionDto) {
        Transaction transaction = transactionMapper.map(transactionDto);
        if(transactionDto.getPartnerDto() != null && !transactionDto.getPartnerDto().getId().isEmpty())
            transaction.setPartnerId(transactionDto.getPartnerDto().getId());
        if(transactionDto.getAccountDto() != null && !transactionDto.getAccountDto().getId().isEmpty())
            transaction.setAccountId(transactionDto.getAccountDto().getId());
        if(transactionDto.getUserDto() != null && !transactionDto.getUserDto().getId().isEmpty())
            transaction.setUserId(transactionDto.getUserDto().getId());
        if(transactionDto.getUserDto() != null && !transactionDto.getUserDto().getId().isEmpty())
            transaction.setUserId(transactionDto.getUserDto().getId());
        return transaction;
    }
}
