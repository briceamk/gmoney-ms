package cm.g2s.partner.shared.mapper;

import cm.g2s.partner.domain.model.Partner;
import cm.g2s.partner.service.company.model.CompanyDto;
import cm.g2s.partner.service.company.service.CompanyClientService;
import cm.g2s.partner.service.uaa.UserDto;
import cm.g2s.partner.service.uaa.service.UaaClientService;
import cm.g2s.partner.shared.dto.PartnerDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class PartnerMapperDecorator implements PartnerMapper{

    private  PartnerMapper partnerMapper;
    private  PartnerCategoryMapper partnerCategoryMapper;
    private  WalletMapper walletMapper;
    private  CompanyClientService companyClientService;
    private UaaClientService uaaClientService;

    @Autowired
    public void setPartnerMapper(PartnerMapper partnerMapper) {
        this.partnerMapper = partnerMapper;
    }

    @Autowired
    public void setPartnerCategoryMapper(PartnerCategoryMapper partnerCategoryMapper) {
        this.partnerCategoryMapper = partnerCategoryMapper;
    }

    @Autowired
    public void setWalletMapper(WalletMapper walletMapper) {
        this.walletMapper = walletMapper;
    }

    @Autowired
    public void setCompanyClientService(CompanyClientService companyClientService) {
        this.companyClientService = companyClientService;
    }

    @Autowired
    public void setUaaClientService(UaaClientService uaaClientService) {
        this.uaaClientService = uaaClientService;
    }

    @Override
    public Partner map(PartnerDto partnerDto) {
        Partner partner = partnerMapper.map(partnerDto);
        if(partner == null )
            return null;
        if(partnerDto.getCompanyDto() != null)
            partner.setCompanyId(partnerDto.getCompanyDto().getId());
        if(partnerDto.getUserDto() != null)
            partner.setUserId(partnerDto.getUserDto().getId());
        partner.setCategory(partnerCategoryMapper.map(partnerDto.getCategoryDto()));
        partner.setWallets(walletMapper.mapListDto(partnerDto.getWalletDtos()));
        return partner;
    }

    @Override
    public PartnerDto map(Partner partner) {
        PartnerDto partnerDto = partnerMapper.map(partner);
        if (partnerDto == null)
            return null;
        if(partner.getCompanyId() != null) {
            CompanyDto companyDto = companyClientService.findById(partner.getCompanyId());
            if (companyDto != null)
                partnerDto.setCompanyDto(companyDto);
        }
        if(partner.getUserId() != null) {
            UserDto userDto = uaaClientService.findById(partner.getUserId());
            if (userDto != null)
                partnerDto.setUserDto(userDto);
        }
        partnerDto.setCategoryDto(partnerCategoryMapper.map(partner.getCategory()));
        partnerDto.setWalletDtos(walletMapper.mapListEntity(partner.getWallets()));
        return partnerDto;
    }
}
