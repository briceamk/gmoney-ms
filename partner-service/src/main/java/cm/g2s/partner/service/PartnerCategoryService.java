package cm.g2s.partner.service;



import cm.g2s.partner.domain.model.PartnerCategory;
import cm.g2s.partner.security.CustomPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public interface PartnerCategoryService {
    PartnerCategory create(CustomPrincipal principal, PartnerCategory category);

    void update(CustomPrincipal principal, PartnerCategory category);

    PartnerCategory findById(CustomPrincipal principal, String id);

    PartnerCategory findByDefaultCategory(CustomPrincipal principal, Boolean defaultCategory);

    void deleteById(CustomPrincipal principal, String id);

    Page<PartnerCategory> findAll(CustomPrincipal principal, String name, PageRequest pageRequest);
}
