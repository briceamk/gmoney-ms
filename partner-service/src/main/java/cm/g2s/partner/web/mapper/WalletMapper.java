package cm.g2s.partner.web.mapper;

import cm.g2s.partner.domain.model.Wallet;
import cm.g2s.partner.web.dto.WalletDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = {DateTimeMapper.class})
public interface WalletMapper {
    Wallet map(WalletDto walletDto);
    WalletDto map(Wallet wallet);
    List<Wallet> mapListDto(List<WalletDto> walletDtos);
    List<WalletDto> mapListEntity(List<Wallet> wallets);
}
