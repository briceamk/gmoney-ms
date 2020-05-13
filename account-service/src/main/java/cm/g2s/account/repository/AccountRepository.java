package cm.g2s.account.repository;

import cm.g2s.account.domain.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Boolean existsByNumberAndKey(String number, String key);

    Optional<Account> findByPartnerId(String partnerId);

    Optional<Account> findByNumber(String number);

    Page<Account> findByNumber(String number, Pageable pageable);

    Page<Account> findByPartnerId(String partnerId, Pageable pageable);

    Boolean existsByPartnerId(String id);
}
