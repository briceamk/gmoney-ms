package cm.g2s.loan.web.mapper;

import cm.g2s.loan.domain.model.Loan;
import cm.g2s.loan.web.dto.LoanDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateTimeMapper.class})
@DecoratedWith(LoanMapperDecorator.class)
public interface LoanMapper {

    Loan map(LoanDto loanDto);
    LoanDto map(Loan loan);
}
