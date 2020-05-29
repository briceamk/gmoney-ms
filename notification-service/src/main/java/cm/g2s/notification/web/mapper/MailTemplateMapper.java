package cm.g2s.notification.web.mapper;


import cm.g2s.notification.domain.model.MailTemplate;
import cm.g2s.notification.web.dto.MailTemplateDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = {DateTimeMapper.class})
public interface MailTemplateMapper {

    MailTemplate map(MailTemplateDto mailTemplateDto);

    MailTemplateDto map(MailTemplate mailTemplate);

    List<MailTemplate> mapToList(List<MailTemplateDto> mailTemplateDtos);

    List<MailTemplateDto> mapToListDto(List<MailTemplate> mailTemplates);
}
