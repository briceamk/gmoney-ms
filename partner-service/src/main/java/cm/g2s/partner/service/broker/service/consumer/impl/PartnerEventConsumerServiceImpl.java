package cm.g2s.partner.service.broker.service.consumer.impl;

import cm.g2s.partner.domain.model.PartnerState;
import cm.g2s.partner.domain.model.PartnerType;
import cm.g2s.partner.domain.model.WalletType;
import cm.g2s.partner.service.PartnerService;
import cm.g2s.partner.service.broker.payload.CreatePartnerResponse;
import cm.g2s.partner.service.uaa.UserDto;
import cm.g2s.partner.service.broker.service.consumer.PartnerEventConsumerService;
import cm.g2s.partner.service.broker.service.producer.PartnerEventPublisherService;
import cm.g2s.partner.shared.dto.PartnerDto;
import cm.g2s.partner.shared.dto.WalletDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Service("partnerEventConsumerService")
public class PartnerEventConsumerServiceImpl implements PartnerEventConsumerService {

    private final PartnerService partnerService;
    private final PartnerEventPublisherService publisherService;

    @Override
    @StreamListener(target = "partnerCreatedChannel")
    public void observePartnerCreateRequest(UserDto userDto) {

        CreatePartnerResponse.CreatePartnerResponseBuilder builder = CreatePartnerResponse.builder();
        try{
            log.info("Receiving Create Partner Request from uaa-service");
            PartnerDto partnerDto = userDtoToPartnerDto(userDto);
            partnerDto = partnerService.create(partnerDto);
            builder.userDto(userDto)
                    .partnerDto(partnerDto)
                    .creationPartnerError(false);
            log.info("Creation Partner Request Successfully");
        } catch (Exception e) {
            log.error("Error when creating partner with data from uaa-service");
            builder.userDto(userDto)
                    .creationPartnerError(true);
        } finally {
            //Sending Response to uaa-service
            publisherService.onCreatePartnerResponseEvent(builder.build());
        }

    }

    @Override
    @StreamListener(target = "accountCreatedFailedChannel")
    public void observeAccountCreateFailed(UserDto userDto) {
        log.info("Receiving Account Creation Failed  Request from uaa-service");
        partnerService.deleteByUserId(userDto.getId());
    }

    private PartnerDto userDtoToPartnerDto(UserDto userDto) {
        return PartnerDto.builder()
                .lastName(userDto.getFullName())
                .email(userDto.getEmail())
                .city(userDto.getCity())
                .type(PartnerType.FREE.name())
                .state(PartnerState.CREATE.name())
                .userDto(userDto)
                .walletDtos(
                        Arrays.asList(
                                WalletDto.builder()
                                        .active(true)
                                        .isDefault(true)
                                        .name(userDto.getMobile())
                                        //TODO write a function to determine the number type
                                        .type(WalletType.ORANGE.name())
                                        .build()
                        )
                ).build();
    }
}
