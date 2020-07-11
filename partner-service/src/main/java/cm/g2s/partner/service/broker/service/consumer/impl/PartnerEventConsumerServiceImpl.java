package cm.g2s.partner.service.broker.service.consumer.impl;

import cm.g2s.partner.domain.model.*;
import cm.g2s.partner.exception.BadRequestException;
import cm.g2s.partner.infrastructure.repository.PartnerCategoryRepository;
import cm.g2s.partner.service.PartnerService;
import cm.g2s.partner.service.broker.payload.CreatePartnerRequest;
import cm.g2s.partner.service.broker.payload.CreatePartnerResponse;
import cm.g2s.partner.service.broker.payload.RemovePartnerRequest;
import cm.g2s.partner.service.broker.service.consumer.PartnerEventConsumerService;
import cm.g2s.partner.service.broker.service.producer.PartnerEventPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Service("partnerEventConsumerService")
public class PartnerEventConsumerServiceImpl implements PartnerEventConsumerService {

    private final PartnerService partnerService;
    private final PartnerCategoryRepository categoryRepository;
    private final PartnerEventPublisherService publisherService;

    @Override
    @StreamListener(target = "uaaChannel", condition = "headers['uaa'] == 'createPartner'")
    //@StreamListener(target = "uaaChannel")
    public void observePartnerCreateRequest(@Payload CreatePartnerRequest createPartnerRequest) {

        CreatePartnerResponse.CreatePartnerResponseBuilder builder = CreatePartnerResponse.builder();
        try{
            log.info("Receiving Create Partner Request from uaa-service");
            Partner partner = transform(createPartnerRequest);
            partner = partnerService.create(null, partner);
            builder.userId(partner.getUserId())
                    .partnerId(partner.getId())
                    .creationPartnerError(false);
            log.info("Creation Partner Request Successfully");
        } catch (Exception e) {
            log.error("Error when creating partner with data from uaa-service. {} ", e.getMessage());
            builder.userId(createPartnerRequest.getUserId())
                    .creationPartnerError(true);
        } finally {
            //Sending Response to uaa-service
            publisherService.onCreatePartnerResponseEvent(builder.build());
        }

    }

    @Override
    @StreamListener(target = "uaaChannel", condition = "headers['uaa'] == 'removePartner'")
    //@StreamListener(target = "uaaChannel")
    public void observePartnerRemoveRequest(@Payload RemovePartnerRequest removePartnerRequest) {
        log.info("Receiving Account Creation Failed  Request from uaa-service");
        partnerService.deleteByUserId(null, removePartnerRequest.getUserId());
    }

    private Partner transform(CreatePartnerRequest partnerRequest) {

        PartnerCategory category = categoryRepository.findByDefaultCategory(true).orElseThrow(
                () -> {
                    log.error("Unable to find default category ofr partner!");
                    throw new BadRequestException("Unable to find default category ofr partner!");
                }
        );


        Partner partner = Partner.builder()
                .firstName(partnerRequest.getFirstName()!= null? partnerRequest.getFirstName(): "")
                .lastName(partnerRequest.getLastName())
                .email(partnerRequest.getEmail())
                .city(partnerRequest.getCity())
                .ruleId(category.getRuleId())
                .creditLimit(category.getCreditLimit())
                .state(PartnerState.CREATE)
                .userId(partnerRequest.getUserId())
                .category(PartnerCategory.builder().id(category.getId()).build())
                .companyId(partnerRequest.getCompanyId()!= null? partnerRequest.getCompanyId(): "")
                .wallets(
                        Arrays.asList(
                                Wallet.builder()
                                        .active(true)
                                        .isDefault(true)
                                        .name(partnerRequest.getMobile())
                                        //TODO write a function to determine the number type
                                        .type(WalletType.ORANGE)
                                        .build()
                        )
                ).build();
        return partner;
    }
}
