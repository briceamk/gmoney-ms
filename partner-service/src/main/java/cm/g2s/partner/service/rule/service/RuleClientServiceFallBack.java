package cm.g2s.partner.service.rule.service;

import cm.g2s.partner.service.rule.model.RuleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RuleClientServiceFallBack implements RuleClientService{
    @Override
    public RuleDto findById(String id) {
        log.error("Error when calling rule-service api from partner-service");
        return null;
    }
}
