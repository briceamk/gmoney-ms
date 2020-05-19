package cm.g2s.partner.infrastructure.repository;

import cm.g2s.partner.domain.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {
    Boolean existsByName(String name);
}
