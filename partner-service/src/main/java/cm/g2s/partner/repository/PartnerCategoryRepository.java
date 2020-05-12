package cm.g2s.partner.repository;

import cm.g2s.partner.domain.model.PartnerCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerCategoryRepository extends JpaRepository<PartnerCategory, String> {
    Boolean existsByNameIgnoreCase(String name);
}
