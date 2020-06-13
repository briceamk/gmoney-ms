package cm.g2s.partner.service.impl;

import cm.g2s.partner.domain.model.PartnerCategory;
import cm.g2s.partner.exception.BadRequestException;
import cm.g2s.partner.infrastructure.repository.PartnerCategoryRepository;
import cm.g2s.partner.security.CustomPrincipal;
import cm.g2s.partner.service.PartnerCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Service("categoryService")
public class PartnerCategoryServiceImpl implements PartnerCategoryService {

    private final PartnerCategoryRepository categoryRepository;


    @Override
    public PartnerCategory create(CustomPrincipal principal, PartnerCategory category) {
        //we verify if database contains a category with same name provided in payload
        if(categoryRepository.existsByNameIgnoreCase(category.getName())){
            log.error("category name is already used! please provide another!");
            throw new BadRequestException("category name is already used! please provide another!");
        }
        if(categoryRepository.count() == 0) {
            category.setDefaultCategory(true);
        } else {
            if(category.getDefaultCategory() != null && category.getDefaultCategory()) {
                PartnerCategory savedCategory = findByDefaultCategory(principal, true);
                if(savedCategory != null && !savedCategory.getId().equals(category.getId())) {
                    savedCategory.setDefaultCategory(false);
                    categoryRepository.save(savedCategory);
                }
            }
        }
        //We activate category
        category.setActive(true);

        return categoryRepository.save(category);
    }

    @Override
    public void update(CustomPrincipal principal, PartnerCategory category) {
        //TODO manage unique fields
        //We check if default Category has change
        if(category.getDefaultCategory() != null && category.getDefaultCategory()) {
            PartnerCategory savedCategory = findByDefaultCategory(principal, true);
            if(savedCategory != null && !savedCategory.getId().equals(category.getId())) {
                savedCategory.setDefaultCategory(false);
                categoryRepository.save(savedCategory);
            }
        }
        categoryRepository.save(category);
    }

    @Override
    public PartnerCategory findById(CustomPrincipal principal, String id) {
        return categoryRepository.findById(id).orElseThrow(
                () ->  {
                    log.info("category with id {} not found!", id);
                    throw new BadRequestException(String.format("category with id %s not found!", id));
                }
        );

    }

    @Override
    public PartnerCategory findByDefaultCategory(CustomPrincipal principal, Boolean defaultCategory) {
        return categoryRepository.findByDefaultCategory(defaultCategory).orElse(null);
    }

    @Override
    public Page<PartnerCategory> findAll(CustomPrincipal principal, String name, PageRequest pageRequest) {

        Page<PartnerCategory> categoryPage;

        if (!StringUtils.isEmpty(name)) {
            //search by category name
            categoryPage = categoryRepository.findByNameContainsIgnoreCase(name, pageRequest);
        } else {
            // search all
            categoryPage = categoryRepository.findAll(pageRequest);
        }

        return categoryPage;


    }

    @Override
    public void deleteById(CustomPrincipal principal, String id) {
        PartnerCategory category = categoryRepository.findById(id).orElseThrow(
                () ->  new BadRequestException("category with provided id not found!")
        );
        categoryRepository.delete(category);
    }
    
}
