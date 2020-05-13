package cm.g2s.partner.shared.mapper;

import cm.g2s.partner.domain.Partner;
import cm.g2s.partner.shared.dto.PartnerDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class PartnerMapperDecorator implements PartnerMapper{

    private  PartnerMapper partnerMapper;
    private  PartnerCategoryMapper categoryMapper;
    private  WalletMapper walletMapper;

    @Autowired
    public void setPartnerMapper(PartnerMapper partnerMapper) {
        this.partnerMapper = partnerMapper;
    }

    @Autowired
    public void setCategoryMapper(PartnerCategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Autowired
    public void setWalletMapper(WalletMapper walletMapper) {
        this.walletMapper = walletMapper;
    }

    @Override
    public Partner map(PartnerDto partnerDto) {
        Partner partner = partnerMapper.map(partnerDto);
        if(partner == null )
            return null;
        partner.setCategory(categoryMapper.map(partnerDto.getCategoryDto()));
        partner.setWallets(walletMapper.mapListDto(partnerDto.getWalletDtos()));
        return partner;
    }

    @Override
    public PartnerDto map(Partner partner) {
        PartnerDto partnerDto = partnerMapper.map(partner);
        if (partnerDto == null)
            return null;
        partnerDto.setCategoryDto(categoryMapper.map(partner.getCategory()));
        partnerDto.setWalletDtos(walletMapper.mapListEntity(partner.getWallets()));
        return partnerDto;
    }
}
