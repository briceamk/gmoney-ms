package cm.g2s.partner.service;

import cm.g2s.partner.shared.dto.PartnerDto;
import cm.g2s.partner.shared.dto.PartnerDtoPage;
import org.springframework.data.domain.PageRequest;

public interface PartnerService {
    PartnerDto create(PartnerDto partnerDto);

    void update(PartnerDto partnerDto);

    PartnerDto findById(String id);

    PartnerDtoPage findAll(String firstName, String lastName, String email,
                           String nicId, String nicIssuePlace, String city,
                           String type, String state, PageRequest of);

    void deleteById(String id);

    void deleteByUserId(String userId);
}
