package cm.g2s.uaa.web.mapper;

import cm.g2s.uaa.domain.model.User;
import cm.g2s.uaa.domain.model.UserState;
import cm.g2s.uaa.service.partner.dto.PartnerDto;
import cm.g2s.uaa.service.company.model.CompanyDto;
import cm.g2s.uaa.service.company.service.CompanyClientService;
import cm.g2s.uaa.service.partner.service.PartnerClientService;
import cm.g2s.uaa.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class UserMapperDecorator implements UserMapper{

    private  UserMapper userMapper;
    private  RoleMapper roleMapper;
    private CompanyClientService companyClientService;
    private PartnerClientService partnerClientService;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Autowired
    public void setCompanyClientService(CompanyClientService companyClientService) {
        this.companyClientService = companyClientService;
    }

    @Autowired
    public void setPartnerClientService(PartnerClientService partnerClientService) {
        this.partnerClientService = partnerClientService;
    }

    @Override
    public User map(UserDto userDto) {
        User user = userMapper.map(userDto);
        if(userDto.getRoleDtos() != null)
            user.setRoles(roleMapper.mapToListEntity(userDto.getRoleDtos()));
        if(userDto.getState() != null && !userDto.getState().isEmpty())
            user.setState(UserState.valueOf(userDto.getState()));
        if(userDto.getCompanyDto() != null)
            user.setCompanyId(userDto.getCompanyDto().getId());
        if(userDto.getPartnerDto() != null)
            user.setPartnerId(userDto.getPartnerDto().getId());
        return user;
    }

    @Override
    public UserDto map(User user) {
        UserDto userDto = userMapper.map(user);
        if(user.getRoles() != null )
            userDto.setRoleDtos(roleMapper.mapToListDto(user.getRoles()));
        if(user.getState() != null)
            userDto.setState(user.getState().name());
        if(user.getCompanyId() != null && !user.getCompanyId().isEmpty()) {
            CompanyDto companyDto = companyClientService.findById(user.getCompanyId());
            if(companyDto != null)
                userDto.setCompanyDto(companyDto);
        }
        if(user.getPartnerId() != null && !user.getPartnerId().isEmpty()) {
            PartnerDto partnerDto = partnerClientService.findById(user.getPartnerId());
            if(partnerDto != null)
                userDto.setPartnerDto(partnerDto);
        }
        return userDto;
    }
}
