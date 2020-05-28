package cm.g2s.notification.web.mapper;


import cm.g2s.notification.domain.model.MailServer;
import cm.g2s.notification.web.dto.MailServerDto;
import org.mapstruct.Mapper;

@Mapper
public interface MailServerMapper {

    MailServer map(MailServerDto mailServerDto);

    MailServerDto map(MailServer mailServer);
}
