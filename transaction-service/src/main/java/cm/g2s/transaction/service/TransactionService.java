package cm.g2s.transaction.service;

import cm.g2s.transaction.domain.model.Transaction;
import cm.g2s.transaction.domain.model.TransactionState;
import cm.g2s.transaction.security.CustomPrincipal;
import org.codehaus.jettison.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface TransactionService {
    Transaction create(CustomPrincipal principal, Transaction transaction);
    void update(CustomPrincipal principal, Transaction transaction);
    Transaction findById(CustomPrincipal principal, String id);
    Page<Transaction> findAll(CustomPrincipal principal, String number,
                 String type, String mode, String origin, String state,
                 String partnerId, String accountId, PageRequest pageRequest);
    void deleteById(CustomPrincipal principal, String id);

    List<Transaction> findReadyToSend(CustomPrincipal principal, TransactionState toSend);

    Boolean sendMoney(CustomPrincipal principal, Transaction transaction) throws JSONException;
}
