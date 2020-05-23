package cm.g2s.loan.web;

import cm.g2s.loan.constant.LoanConstantType;
import cm.g2s.loan.domain.model.Loan;
import cm.g2s.loan.security.CurrentPrincipal;
import cm.g2s.loan.security.CustomPrincipal;
import cm.g2s.loan.service.LoanManagerService;
import cm.g2s.loan.service.LoanService;
import cm.g2s.loan.service.ValidationErrorService;
import cm.g2s.loan.web.dto.LoanDto;
import cm.g2s.loan.web.dto.LoanDtoPage;
import cm.g2s.loan.web.mapper.LoanMapper;
import cm.g2s.loan.web.payload.ResponseApi;
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
import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/loans")
@Api(value = "Loan", tags = "Loan End Points")
public class LoanController {

    private final LoanService loanService;
    private final LoanManagerService loanManagerService;
    private final LoanMapper loanMapper;
    private final ValidationErrorService validationErrorService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') and hasAuthority('CREATE_LOAN')")
    public ResponseEntity<?> create(@CurrentPrincipal CustomPrincipal principal,
                                    @Valid @RequestBody LoanDto loanDto, BindingResult result) throws ScriptException {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        Loan loan = loanService.create(principal, loanMapper.map(loanDto));
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/loans/{id}")
                .buildAndExpand(loan.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseApi(true, "Loan saved successfully!"));
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER') and hasAuthority('UPDATE_LOAN')")
    public ResponseEntity<?> update(@CurrentPrincipal CustomPrincipal principal,
                                    @Validated @RequestBody LoanDto loanDto, BindingResult result) throws ScriptException {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        loanService.update(principal, loanMapper.map(loanDto));
        return new ResponseEntity<>(new ResponseApi(true, "Loan updated successfully!"), HttpStatus.OK);
    }

    @PutMapping("/validate")
    @PreAuthorize("hasRole('ROLE_USER') and hasAuthority('UPDATE_LOAN')")
    public ResponseEntity<?> validateLoan(@CurrentPrincipal CustomPrincipal principal,
                                    @Validated @RequestBody LoanDto loanDto, BindingResult result) throws ScriptException {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        loanManagerService.validateLoan(principal, loanMapper.map(loanDto), loanDto.getAccountDto(), loanDto.getPartnerDto());
        return new ResponseEntity<>(new ResponseApi(true, "Your request is validated. we will send it in a few moments!"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') and hasAuthority('READ_LOAN')")
    public ResponseEntity<?> findById(@CurrentPrincipal CustomPrincipal principal,
                                      @PathVariable String id) {
        return new ResponseEntity<>(loanMapper.map(loanService.findById(principal, id)), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') and hasAuthority('READ_LOAN')")
    public ResponseEntity<?> findAll(@CurrentPrincipal CustomPrincipal principal,
                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                     @RequestParam(value = "number", required = false) String number,
                                     @RequestParam(value = "state", required = false) String state,
                                     @RequestParam(value = "accountId", required = false) String accountId,
                                     @RequestParam(value = "partnerId", required = false) String partnerId){

        if (pageNumber == null || pageNumber < 0){
            pageNumber = LoanConstantType.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = LoanConstantType.DEFAULT_PAGE_SIZE;
        }

        Page<Loan> loanPage =  loanService.findAll(principal, number, state, partnerId,
                accountId, PageRequest.of(pageNumber, pageSize));

        LoanDtoPage loanDtoPage = new LoanDtoPage(
                loanPage.getContent().stream().map(loanMapper::map).collect(Collectors.toList()),
                PageRequest.of(loanPage.getPageable().getPageNumber(),
                        loanPage.getPageable().getPageSize()),
                loanPage.getTotalElements()
        );

        return new ResponseEntity<>(loanDtoPage, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') and hasAuthority('DELETE_LOAN')")
    public ResponseEntity<?> deleteById(@CurrentPrincipal CustomPrincipal principal,
                                        @PathVariable String id) {
        loanService.deleteById(principal, id);
        return new ResponseEntity<>(new ResponseApi(true, "Loan deleted successfully!"), HttpStatus.OK);
    }
}
