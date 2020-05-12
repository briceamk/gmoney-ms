package cm.g2s.partner.service;

import cm.g2s.partner.repository.PartnerRepository;
import cm.g2s.partner.shared.dto.PartnerDto;
import cm.g2s.partner.shared.mapper.PartnerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("partnerService")
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;
    private final PartnerMapper partnerMapper;

    @Override
    public PartnerDto createPartner(PartnerDto partnerDto) {
        return null;
    }
}
