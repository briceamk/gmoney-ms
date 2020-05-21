package cm.g2s.partner.service.rule.service;

import cm.g2s.partner.service.rule.RuleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service("ruleClientService")
public class RuleClientServiceImpl implements RuleClientService {

    private final RuleClientService ruleClientService;

    @Override
    public RuleDto findById(String id) {
        log.info("Calling rule-service api from partner-service.....");
        return ruleClientService.findById(id);
    }
}
