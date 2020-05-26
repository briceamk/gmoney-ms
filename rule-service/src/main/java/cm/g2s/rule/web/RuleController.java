package cm.g2s.rule.web;

import cm.g2s.rule.constant.RuleConstantType;
import cm.g2s.rule.domain.model.Rule;
import cm.g2s.rule.security.CurrentPrincipal;
import cm.g2s.rule.security.CustomPrincipal;
import cm.g2s.rule.service.RuleService;
import cm.g2s.rule.service.ValidationErrorService;
import cm.g2s.rule.web.dto.RuleDto;
import cm.g2s.rule.web.dto.RuleDtoPage;
import cm.g2s.rule.web.mapper.RuleMapper;
import cm.g2s.rule.web.payload.ResponseApi;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.script.ScriptException;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rules")
@Api(value = "Rule", tags = "Rule End Points")
public class RuleController {

    private final RuleService ruleService;
    private final RuleMapper ruleMapper;
    private final ValidationErrorService validationErrorService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('CREATE_RULE')")
    public ResponseEntity<?> create(@CurrentPrincipal CustomPrincipal principal,
                                    @Valid @RequestBody RuleDto ruleDto, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        Rule rule = ruleService.create(principal, ruleMapper.map(ruleDto));
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/rules/{id}")
                .buildAndExpand(rule.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseApi(true, "Rule saved successfully!"));
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('UPDATE_RULE')")
    public ResponseEntity<?> update(@CurrentPrincipal CustomPrincipal principal,
                                    @Validated @RequestBody RuleDto ruleDto, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        ruleService.update(principal, ruleMapper.map(ruleDto));
        return new ResponseEntity<>(new ResponseApi(true, "Rule updated successfully!"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("(hasAnyRole('ROLE_ADMIN','ROLE,MANAGER','ROLE_USER') and hasAuthority('READ_RULE'))")
    public ResponseEntity<?> findById(@CurrentPrincipal CustomPrincipal principal,
                                      @PathVariable String id) {
        return new ResponseEntity<>(ruleMapper.map(ruleService.findById(principal, id)), HttpStatus.OK);
    }

    @GetMapping("/interest/amount/{amount}/numberOfDays/{numberOfDays}/id/{id}")
    @PreAuthorize("(hasAnyRole('ROLE_ADMIN','ROLE,MANAGER','ROLE_USER') and hasAuthority('READ_RULE'))")
    public ResponseEntity<?> processInterest(@CurrentPrincipal CustomPrincipal principal,
                                             @PathVariable  String id, @PathVariable Long numberOfDays, @PathVariable BigDecimal amount) throws ScriptException {
        return new ResponseEntity<>(ruleService.processInterest(principal, id, numberOfDays, amount), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@CurrentPrincipal CustomPrincipal principal,
                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                     @RequestParam(value = "code", required = false) String code,
                                     @RequestParam(value = "name", required = false) String name){

        if (pageNumber == null || pageNumber < 0){
            pageNumber = RuleConstantType.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = RuleConstantType.DEFAULT_PAGE_SIZE;
        }
        Page<Rule> rulePage = ruleService.findAll(principal, code, name, PageRequest.of(pageNumber, pageSize));
        RuleDtoPage ruleDtoPage = new RuleDtoPage(
                rulePage.getContent().stream().map(ruleMapper::map).collect(Collectors.toList()),
                PageRequest.of(rulePage.getPageable().getPageNumber(),
                        rulePage.getPageable().getPageSize()),
                rulePage.getTotalElements()
        );
        return new ResponseEntity<>(ruleDtoPage, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("(hasRole('ROLE_ADMIN') and hasAuthority('DELETE_RULE'))")
    public ResponseEntity<?> deleteById(@CurrentPrincipal CustomPrincipal principal,
                                        @PathVariable String id) {
        ruleService.deleteById(principal, id);
        return new ResponseEntity<>(new ResponseApi(true, "Rule deleted successfully!"), HttpStatus.OK);
    }

}
