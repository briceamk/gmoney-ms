package cm.g2s.loan.infrastructure.repository;

import cm.g2s.loan.domain.model.Loan;
import cm.g2s.loan.domain.model.LoanState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, String> {
    Page<Loan> findByNumber(String number, Pageable pageable);

    Page<Loan> findByState(LoanState valueOf, Pageable pageable);

    Page<Loan> findByPartnerId(String partnerId, Pageable pageable);

    Page<Loan> findByAccountId(String partnerId, Pageable pageable);
}
