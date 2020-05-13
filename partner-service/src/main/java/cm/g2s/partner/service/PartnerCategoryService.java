package cm.g2s.partner.service;

import cm.g2s.partner.shared.dto.PartnerCategoryDto;
import cm.g2s.partner.shared.dto.PartnerCategoryDtoPage;
import org.springframework.data.domain.PageRequest;

public interface PartnerCategoryService {
    PartnerCategoryDto create(PartnerCategoryDto categoryDto);

    void update(PartnerCategoryDto categoryDto);

    PartnerCategoryDto findById(String id);

    void deleteById(String id);

    PartnerCategoryDtoPage findAll(String name, PageRequest of);
}
