package cm.g2s.transaction.domain.model;

public enum TransactionState {
    TO_SEND, WAITING_SEND_RESPONSE, SEND_PENDING, SEND, SEND_EXCEPTION
}
