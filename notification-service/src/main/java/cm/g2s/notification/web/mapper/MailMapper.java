package cm.g2s.notification.web.mapper;

import cm.g2s.notification.domain.model.Mail;
import cm.g2s.notification.web.dto.MailDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = {DateTimeMapper.class})
public interface MailMapper {

    Mail map(MailDto mailDto);

    MailDto map(Mail mail);

    List<Mail> mapToList(List<MailDto> mailDtos);

    List<MailDto> mapToListDto(List<Mail> mails);
}
