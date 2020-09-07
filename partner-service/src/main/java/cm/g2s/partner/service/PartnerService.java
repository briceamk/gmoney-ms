package cm.g2s.partner.service;

import cm.g2s.partner.domain.model.Partner;
import cm.g2s.partner.security.CustomPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PartnerService {
    
    Partner create(CustomPrincipal principal, Partner partner);

    void update(CustomPrincipal principal, Partner partner);

    Partner findById(CustomPrincipal principal, String id);

    Partner findByUserId(CustomPrincipal principal, String userId);

    Page<Partner> findAll(CustomPrincipal principal, String firstName, String lastName, String email,
                 String nicId, String nicIssuePlace, String city,
                 String type, String state, PageRequest of);

    void deleteById(CustomPrincipal principal, String id);

    void deleteByUserId(CustomPrincipal principal, String userId);


}
