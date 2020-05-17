package cm.g2s.rule.web;

import cm.g2s.rule.constant.RuleConstantType;
import cm.g2s.rule.security.CurrentPrincipal;
import cm.g2s.rule.security.CustomPrincipal;
import cm.g2s.rule.service.RuleService;
import cm.g2s.rule.shared.dto.RuleDto;
import cm.g2s.rule.shared.payload.ResponseApi;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.script.ScriptException;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rules")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Api(value = "Rule", tags = "Rule End Points")
public class RuleController {

    private final RuleService ruleService;

    @PostMapping
    public ResponseEntity<?> create(@CurrentPrincipal CustomPrincipal principal,
                                    @Valid @RequestBody RuleDto ruleDto) {
        ruleDto = ruleService.create(principal, ruleDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/rules/{id}")
                .buildAndExpand(ruleDto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseApi(true, "Rule saved successfully!"));
    }

    @PutMapping
    public ResponseEntity<?> update(@CurrentPrincipal CustomPrincipal principal,
                                    @Validated @RequestBody RuleDto ruleDto) {
        ruleService.update(principal, ruleDto);
        return new ResponseEntity<>(new ResponseApi(true, "Rule updated successfully!"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@CurrentPrincipal CustomPrincipal principal,
                                      @PathVariable String id) {
        return new ResponseEntity<>(ruleService.findById(principal, id), HttpStatus.OK);
    }

    @GetMapping("/interest/amount/{amount}/numberOfDays/{numberOfDays}/id/{id}")
    public ResponseEntity<?> processInterest(@CurrentPrincipal CustomPrincipal principal,
                                             @PathVariable  String id, @PathVariable Integer numberOfDays, @PathVariable BigDecimal amount) throws ScriptException {
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

        return new ResponseEntity<>(ruleService.findAll(principal, code, name, PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@CurrentPrincipal CustomPrincipal principal,
                                        @PathVariable String id) {
        ruleService.deleteById(principal, id);
        return new ResponseEntity<>(new ResponseApi(true, "Rule deleted successfully!"), HttpStatus.OK);
    }

}
