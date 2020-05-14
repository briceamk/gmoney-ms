package cm.g2s.rule.repository;

import cm.g2s.rule.domain.model.RuleLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleLineRepository extends JpaRepository<RuleLine, String> {
}
