package cm.g2s.company.web.mapper;

import cm.g2s.company.domain.model.Company;
import cm.g2s.company.web.dto.CompanyDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateTimeMapper.class, DateMapper.class})
public interface CompanyMapper {
    Company map(CompanyDto companyDto);
    CompanyDto map(Company company);
}
