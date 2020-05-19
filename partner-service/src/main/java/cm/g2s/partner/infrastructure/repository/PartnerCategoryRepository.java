package cm.g2s.partner.infrastructure.repository;

import cm.g2s.partner.domain.model.PartnerCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerCategoryRepository extends JpaRepository<PartnerCategory, String> {
    Boolean existsByNameIgnoreCase(String name);

    Optional<PartnerCategory> findByDefaultCategory(Boolean defaultCategory);

    Page<PartnerCategory> findByNameContainsIgnoreCase(String name, Pageable pageable);
}
