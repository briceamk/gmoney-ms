package cm.g2s.partner.service.impl;

import cm.g2s.partner.domain.model.Partner;
import cm.g2s.partner.domain.model.PartnerState;
import cm.g2s.partner.domain.model.PartnerType;
import cm.g2s.partner.exception.BadRequestException;
import cm.g2s.partner.exception.ResourceNotFoundException;
import cm.g2s.partner.infrastructure.repository.PartnerRepository;
import cm.g2s.partner.infrastructure.repository.WalletRepository;
import cm.g2s.partner.security.CustomPrincipal;
import cm.g2s.partner.service.PartnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Service("partnerService")
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;
    private final WalletRepository walletRepository;

    @Override
    public Partner create(CustomPrincipal principal, Partner partner) {
        //we verify if database contains same nic  provided in payload
        if(partner.getNicId() != null && partnerRepository.existsByNicId(partner.getNicId())) {
            log.error("provided national identity card {} is already taken!", partner.getNicId());
            throw new BadRequestException(String.format("provided national identity card %s is already taken!", partner.getNicId()));
        }
        //We verify if email is unique
        if(partnerRepository.existsByEmail(partner.getEmail())) {
            log.error("provided email {} is already taken!", partner.getEmail());
            throw new BadRequestException(String.format("provided email %s is already taken!", partner.getEmail()));
        }

        // we set partner default state
        partner.setState(PartnerState.CREATE);

        // we add partner to each provided wallet before saving
        partner.getWallets().forEach(wallet -> {
            if(walletRepository.existsByName(wallet.getName())){
                log.error("Wallet with number {} already exist!", wallet.getName());
                throw new BadRequestException(String.format("Wallet with number %s already exist!", wallet.getName()));
            }
            wallet.setPartner(partner);
            wallet.setActive(true);
        });

        // We set the type. ENTERPRISE if user has provided a valid company Code
        if(partner.getCompanyId() != null && !partner.getCompanyId().isEmpty())
            partner.setType(PartnerType.ENTERPRISE);
        partner.setType(PartnerType.FREE);


        return partnerRepository.save(partner);
    }

    @Override
    public void update(CustomPrincipal principal, Partner partner) {
        // TODO validate unique field
        partner.getWallets().forEach(wallet ->{
            wallet.setPartner(partner);
        });
       partnerRepository.save(partner);
    }

    @Override
    public Partner findById(CustomPrincipal principal, String id) {
        return partnerRepository.findById(id).orElseThrow(
                () -> {
                    log.error("partner with provided id not found!");
                    throw new ResourceNotFoundException("partner with provided id not found!");
                }
        );
    }

    @Override
    public Page<Partner> findAll(CustomPrincipal principal, String firstName, String lastName, String email, String nicId,
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

        return partnerPage;
    }

    @Override
    public void deleteById(CustomPrincipal principal, String id) {
        partnerRepository.delete(findById(principal, id));
    }

    @Override
    public void deleteByUserId(CustomPrincipal principal, String userId) {
        Partner partner = partnerRepository.findByUserId(userId).orElseThrow(
                () -> {
                    log.error("Partner with user id {} not found", userId);
                    throw  new ResourceNotFoundException("Partner with id %s not found");
                }
        );

        partnerRepository.delete(partner);
    }
}
