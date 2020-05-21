package cm.g2s.partner.service.impl;

import cm.g2s.partner.domain.model.Partner;
import cm.g2s.partner.domain.model.PartnerCategory;
import cm.g2s.partner.domain.model.PartnerState;
import cm.g2s.partner.domain.model.PartnerType;
import cm.g2s.partner.infrastructure.repository.PartnerRepository;
import cm.g2s.partner.infrastructure.repository.WalletRepository;
import cm.g2s.partner.service.PartnerCategoryService;
import cm.g2s.partner.service.PartnerService;
import cm.g2s.partner.shared.dto.PartnerCategoryDto;
import cm.g2s.partner.shared.dto.PartnerDto;
import cm.g2s.partner.shared.dto.PartnerDtoPage;
import cm.g2s.partner.shared.exception.BadRequestException;
import cm.g2s.partner.shared.exception.ResourceNotFoundException;
import cm.g2s.partner.shared.mapper.PartnerCategoryMapper;
import cm.g2s.partner.shared.mapper.PartnerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service("partnerService")
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;
    private final PartnerMapper partnerMapper;
    private final WalletRepository walletRepository;
    private final PartnerCategoryService categoryService;
    private final PartnerCategoryMapper categoryMapper;

    @Override
    public PartnerDto create(PartnerDto partnerDto) {
        //we verify if database contains same nic  provided in payload
        if(partnerDto.getNicId() != null && partnerRepository.existsByNicId(partnerDto.getNicId())) {
            log.error("provided national identity card {} is already taken!", partnerDto.getNicId());
            throw new BadRequestException(String.format("provided national identity card %s is already taken!", partnerDto.getNicId()));
        }
        //We verify if email is unique
        if(partnerRepository.existsByEmail(partnerDto.getEmail())) {
            log.error("provided email {} is already taken!", partnerDto.getEmail());
            throw new BadRequestException(String.format("provided email %s is already taken!", partnerDto.getEmail()));
        }
        Partner partner = partnerMapper.map(partnerDto);
        // we set partner default state
        partner.setState(PartnerState.CREATE);

        // we add partner to each provided wallet before saving
        partner.getWallets().forEach(wallet -> {
            if(walletRepository.existsByName(wallet.getName())){
                log.error("Wallet with number {} already exist!", wallet.getName());
                throw new BadRequestException(String.format("Wallet with number %s already exist!", wallet.getName()));
            }
            wallet.setPartner(partner);
        });

        // We set the type. ENTERPRISE if user has provided a valid company Code
        if(partner.getCompanyId() != null && !partner.getCompanyId().isEmpty())
            partner.setType(PartnerType.ENTERPRISE);
        partner.setType(PartnerType.FREE);


        return partnerMapper.map(partnerRepository.save(partner));
    }

    @Override
    public void update(PartnerDto partnerDto) {
        // TODO validate unique field
        Partner partner = partnerMapper.map(partnerDto);
        partner.getWallets().forEach(wallet ->{
            /*if(walletRepository.existsByName(wallet.getName())){
                log.error("Wallet with number {} already exist!", wallet.getName());
                throw new BadRequestException(String.format("Wallet with number %s already exist!", wallet.getName()));
            }*/
            wallet.setPartner(partner);
        });
       partnerRepository.save(partner);
    }

    @Override
    public PartnerDto findById(String id) {
        Partner partner = partnerRepository.findById(id).orElseThrow(
                () -> {
                    log.error("partner with provided id not found!");
                    throw new ResourceNotFoundException("partner with provided id not found!");
                }
        );
        return partnerMapper.map(partner);
    }

    @Override
    public PartnerDtoPage findAll(String firstName, String lastName, String email, String nicId,
                                  String nicIssuePlace, String city, String type,
                                  String state, PageRequest pageRequest) {

        Page<Partner> partnerPage;
        if(!StringUtils.isEmpty(firstName))
            partnerPage = partnerRepository.findByFirstNameContainsIgnoreCase(firstName, pageRequest);
        else if(!StringUtils.isEmpty(lastName))
            partnerPage = partnerRepository.findByLastNameContainsIgnoreCase(lastName, pageRequest);
        else if(!StringUtils.isEmpty(email))
            partnerPage = partnerRepository.findByEmailContainsIgnoreCase(email, pageRequest);
        else if(!StringUtils.isEmpty(nicId))
            partnerPage = partnerRepository.findByNicIdIgnoreCase(nicId, pageRequest);
        else if(!StringUtils.isEmpty(nicIssuePlace))
            partnerPage = partnerRepository.findByNicIssuePlaceIgnoreCase(nicIssuePlace, pageRequest);
        else if(!StringUtils.isEmpty(city))
            partnerPage = partnerRepository.findByCityIgnoreCase(city, pageRequest);
        else if(!StringUtils.isEmpty(type))
            partnerPage = partnerRepository.findByType(PartnerType.valueOf(type), pageRequest);
        else if(!StringUtils.isEmpty(state))
            partnerPage = partnerRepository.findByState(PartnerState.valueOf(state), pageRequest);
        else
            partnerPage = partnerRepository.findAll(pageRequest);

        return new PartnerDtoPage(
                partnerPage.getContent().stream().map(partnerMapper::map).collect(Collectors.toList()),
                PageRequest.of(partnerPage.getPageable().getPageNumber(),
                        partnerPage.getPageable().getPageSize()),
                partnerPage.getTotalElements()
        );
    }

    @Override
    public void deleteById(String id) {
        Partner partner = partnerRepository.findById(id).orElseThrow(
                () -> {
                    log.error("partner with id {} not found!", id);
                    throw new ResourceNotFoundException(String.format("partner with id %s not found!", id));
                }
        );
        partnerRepository.delete(partner);
    }

    @Override
    public void deleteByUserId(String userId) {
        Partner partner = partnerRepository.findByUserId(userId).orElseThrow(
                () -> {
                    log.error("Partner with user id {} not found", userId);
                    throw  new ResourceNotFoundException("Partner with id %s not found");
                }
        );

        partnerRepository.delete(partner);
    }
}
