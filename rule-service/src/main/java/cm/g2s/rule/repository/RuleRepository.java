package cm.g2s.rule.repository;

import cm.g2s.rule.domain.model.Rule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<Rule, String> {

    Boolean existsByCode(String code);

    Page<Rule> findByCode(String code, Pageable pageable);

    Page<Rule> findByName(String name, Pageable pageable);
}
