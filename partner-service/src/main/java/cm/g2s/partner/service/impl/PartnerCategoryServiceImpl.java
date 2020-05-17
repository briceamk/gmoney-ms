package cm.g2s.partner.service.impl;

import cm.g2s.partner.domain.model.PartnerCategory;
import cm.g2s.partner.repository.PartnerCategoryRepository;
import cm.g2s.partner.service.PartnerCategoryService;
import cm.g2s.partner.shared.dto.PartnerCategoryDto;
import cm.g2s.partner.shared.dto.PartnerCategoryDtoPage;
import cm.g2s.partner.shared.exception.BadRequestException;
import cm.g2s.partner.shared.mapper.PartnerCategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service("categoryService")
public class PartnerCategoryServiceImpl implements PartnerCategoryService {

    private final PartnerCategoryRepository categoryRepository;
    private final PartnerCategoryMapper categoryMapper;


    @Override
    public PartnerCategoryDto create(PartnerCategoryDto categoryDto) {
        //we verify if database contains a category with same name provided in dto
        if(categoryRepository.existsByNameIgnoreCase(categoryDto.getName())){
            log.error("category name is already used! please provide another!");
            throw new BadRequestException("category name is already used! please provide another!");
        }
        return categoryMapper.map(categoryRepository.save(categoryMapper.map(categoryDto)));
    }

    @Override
    public void update(PartnerCategoryDto categoryDto) {
        //TODO manage unique fields
        categoryRepository.save(categoryMapper.map(categoryDto));
    }

    @Override
    public PartnerCategoryDto findById(String id) {
        PartnerCategory category = categoryRepository.findById(id).orElseThrow(
                () ->  new BadRequestException("category with provided id not found!")
        );
        return categoryMapper.map(category);
    }

    @Override
    public void deleteById(String id) {
        PartnerCategory category = categoryRepository.findById(id).orElseThrow(
                () ->  new BadRequestException("category with provided id not found!")
        );
        categoryRepository.delete(category);
    }

    @Override
    public PartnerCategoryDtoPage findAll(String name, PageRequest pageRequest) {

        Page<PartnerCategory> categoryPage;

            if (!StringUtils.isEmpty(name)) {
                //search by category name
                categoryPage = categoryRepository.findByNameContainsIgnoreCase(name, pageRequest);
            } else {
                // search all
                categoryPage = categoryRepository.findAll(pageRequest);
            }

            return new PartnerCategoryDtoPage(
                    categoryPage.getContent().stream().map(categoryMapper::map).collect(Collectors.toList()),
                    PageRequest.of(categoryPage.getPageable().getPageNumber(),
                            categoryPage.getPageable().getPageSize()),
                    categoryPage.getTotalElements()
            );


    }
}
