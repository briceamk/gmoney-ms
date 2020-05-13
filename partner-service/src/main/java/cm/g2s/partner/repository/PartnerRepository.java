package cm.g2s.partner.repository;

import cm.g2s.partner.domain.Partner;
import cm.g2s.partner.domain.PartnerState;
import cm.g2s.partner.domain.PartnerType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, String> {
    Boolean existsByNicId(String nicId);

    Boolean existsByEmail(String email);

    Page<Partner> findByFirstNameContainsIgnoreCase(String firstName, Pageable pageable);

    Page<Partner> findByLastNameContainsIgnoreCase(String lastName, Pageable pageable);

    Page<Partner> findByEmailContainsIgnoreCase(String email, Pageable pageable);

    Page<Partner> findByNicIdIgnoreCase(String nicId, Pageable pageable);

    Page<Partner> findByNicIssuePlaceIgnoreCase(String nicIssuePlace, Pageable pageable);

    Page<Partner> findByCityIgnoreCase(String city, Pageable pageable);

    Page<Partner> findByType(PartnerType valueOf, Pageable pageable);

    Page<Partner> findByState(PartnerState valueOf, Pageable pageable);
}
