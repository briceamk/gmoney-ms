package cm.g2s.partner.shared.mapper;

import cm.g2s.partner.domain.model.Wallet;
import cm.g2s.partner.shared.dto.WalletDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateTimeMapper.class})
public interface WalletMapper {
    Wallet map(WalletDto walletDto);
    WalletDto map(Wallet wallet);
}
