package cm.g2s.account.web;

import cm.g2s.account.constant.AccountConstantType;
import cm.g2s.account.domain.model.Account;
import cm.g2s.account.security.CurrentPrincipal;
import cm.g2s.account.security.CustomPrincipal;
import cm.g2s.account.service.AccountService;
import cm.g2s.account.service.ValidationErrorService;
import cm.g2s.account.web.dto.AccountDto;
import cm.g2s.account.web.dto.AccountDtoPage;
import cm.g2s.account.web.mapper.AccountMapper;
import cm.g2s.account.web.payload.ResponseApi;
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

import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
@Api(value = "Account", tags = "Account End Points")
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final ValidationErrorService validationErrorService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')  and hasAuthority('CREATE_ACCOUNT')")
    public ResponseEntity<?> create(@CurrentPrincipal CustomPrincipal principal,
                                    @Validated @RequestBody AccountDto accountDto , BindingResult result) {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;

        Account account = accountService.create(principal, accountMapper.map(accountDto), accountDto.getPartnerDto());
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/accounts/{id}")
                .buildAndExpand(account.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseApi(true, "Account saved successfully!"));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')  and hasAuthority('UPDATE_ACCOUNT')")
    public ResponseEntity<?> update(@CurrentPrincipal CustomPrincipal principal,
                                    @Validated @RequestBody AccountDto accountDto, BindingResult result) {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;

        accountService.update(principal, accountMapper.map(accountDto));
        return new ResponseEntity<>(new ResponseApi(true, "Account updated successfully!"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER', 'ROLE_USER') and hasAuthority('READ_ACCOUNT')")
    public ResponseEntity<?> findById(@CurrentPrincipal CustomPrincipal principal,
                                      @PathVariable String id) {
        return new ResponseEntity<>(accountMapper.map(accountService.findById(principal, id)), HttpStatus.OK);
    }

    @GetMapping("/number/{number}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER', 'ROLE_USER') and hasAuthority('READ_ACCOUNT')")
    public ResponseEntity<?> findByCode(@CurrentPrincipal CustomPrincipal principal,
                                        @PathVariable String number) {
        return new ResponseEntity<>(accountMapper.map(accountService.findByNumber(principal, number)), HttpStatus.OK);
    }

    @GetMapping("/partnerId/{partnerId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasAnyRole('ROLE_MANAGER', 'ROLE_USER') and hasAuthority('READ_ACCOUNT'))")
    public ResponseEntity<?> findByPartnerId(@CurrentPrincipal CustomPrincipal principal,
                                             @PathVariable String partnerId) {
        return new ResponseEntity<>(accountMapper.map(accountService.findByPartnerId(principal, partnerId)), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER', 'ROLE_USER') and hasAuthority('READ_ACCOUNT')")
    public ResponseEntity<?> findAll(@CurrentPrincipal CustomPrincipal principal,
                                                  @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                  @RequestParam(value = "number", required = false) String number,
                                                  @RequestParam(value = "key", required = false) String key,
                                                  @RequestParam(value = "partnerId", required = false) String partnerId){


        if (pageNumber == null || pageNumber < 0){
            pageNumber = AccountConstantType.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = AccountConstantType.DEFAULT_PAGE_SIZE;
        }

        Page<Account>  accountPage = accountService.findAll(principal, number, key, partnerId, PageRequest.of(pageNumber, pageSize));


        AccountDtoPage accountDtoPage = new AccountDtoPage(
                accountPage.getContent().stream().map(accountMapper::map).collect(Collectors.toList()),
                PageRequest.of(accountPage.getPageable().getPageNumber(),
                        accountPage.getPageable().getPageSize()),
                accountPage.getTotalElements()
        );

        return new ResponseEntity<>( accountDtoPage, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')  and hasAuthority('DELETE_ACCOUNT')")
    public ResponseEntity<?> deleteById(@CurrentPrincipal CustomPrincipal principal,
                                        @PathVariable String id) {
        accountService.deleteById(principal, id);
        return new ResponseEntity<>(new ResponseApi(true, "Account deleted successfully!"), HttpStatus.OK);
    }


}
