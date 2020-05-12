package cm.g2s.partner.service.impl;

import cm.g2s.partner.repository.PartnerCategoryRepository;
import cm.g2s.partner.service.PartnerCategoryService;
import cm.g2s.partner.shared.dto.PartnerCategoryDto;
import cm.g2s.partner.shared.exception.BadRequestException;
import cm.g2s.partner.shared.mapper.PartnerCategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("categoryService")
@RequiredArgsConstructor
public class PartnerCategoryServiceImpl implements PartnerCategoryService {

    private final PartnerCategoryRepository categoryRepository;
    private final PartnerCategoryMapper categoryMapper;

    @Override
    public PartnerCategoryDto createPartnerCategory(PartnerCategoryDto categoryDto) {
        //we verify if database contains a category with provided name
        if(categoryRepository.existsByNameIgnoreCase(categoryDto.getName())){
            log.error("category name is already used! please provide another!");
            throw new BadRequestException("category name is already used! please provide another!");
        }
        return categoryMapper.map(categoryRepository.save(categoryMapper.map(categoryDto)));
    }
}
