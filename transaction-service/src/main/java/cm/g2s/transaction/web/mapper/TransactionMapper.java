package cm.g2s.transaction.web.mapper;

import cm.g2s.transaction.domain.model.Transaction;
import cm.g2s.transaction.web.dto.TransactionDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateTimeMapper.class})
@DecoratedWith(TransactionMapperDecorator.class)
public interface TransactionMapper {
    TransactionDto map(Transaction transaction);
    Transaction    map(TransactionDto transactionDto);
}
