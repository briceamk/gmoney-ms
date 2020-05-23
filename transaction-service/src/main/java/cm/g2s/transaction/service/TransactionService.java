package cm.g2s.transaction.service;

import cm.g2s.transaction.domain.model.Transaction;
import cm.g2s.transaction.security.CurrentPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface TransactionService {
    Transaction create(CurrentPrincipal currentPrincipal, Transaction transaction);
    void update(CurrentPrincipal currentPrincipal, Transaction transaction);
    Transaction findById(CurrentPrincipal currentPrincipal, String id);
    Page<Transaction> findAll(CurrentPrincipal currentPrincipal, String number,
                 String type, String mode, String origin, String state,
                 String partnerId, String accountId, PageRequest pageRequest);
    void deleteById(CurrentPrincipal currentPrincipal, String id);

}
