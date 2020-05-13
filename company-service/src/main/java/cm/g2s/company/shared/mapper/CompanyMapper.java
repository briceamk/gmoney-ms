package cm.g2s.company.shared.mapper;

import cm.g2s.company.domain.model.Company;
import cm.g2s.company.shared.dto.CompanyDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateTimeMapper.class, DateMapper.class})
public interface CompanyMapper {
    Company map(CompanyDto companyDto);
    CompanyDto map(Company company);
}
