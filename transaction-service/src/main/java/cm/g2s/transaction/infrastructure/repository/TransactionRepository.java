package cm.g2s.transaction.infrastructure.repository;

import cm.g2s.transaction.domain.model.Transaction;
import cm.g2s.transaction.domain.model.TransactionMode;
import cm.g2s.transaction.domain.model.TransactionState;
import cm.g2s.transaction.domain.model.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    Page<Transaction> findByNumber(String number, Pageable pageable);

    Page<Transaction> findByState(TransactionState valueOf, Pageable pageable);

    Page<Transaction> findByType(TransactionType valueOf, Pageable pageable);

    Page<Transaction> findByMode(TransactionMode valueOf, Pageable pageable);

    Page<Transaction> findByPartnerId(String partnerId, Pageable pageable);

    Page<Transaction> findByAccountId(String accountId, Pageable pageable);
}
