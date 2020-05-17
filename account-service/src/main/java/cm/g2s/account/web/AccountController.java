package cm.g2s.account.web;

import cm.g2s.account.constant.AccountConstantType;
import cm.g2s.account.security.CurrentPrincipal;
import cm.g2s.account.security.CustomPrincipal;
import cm.g2s.account.service.AccountService;
import cm.g2s.account.shared.dto.AccountDto;
import cm.g2s.account.shared.dto.AccountDtoPage;
import cm.g2s.account.shared.payload.ResponseApi;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
@Api(value = "Account", tags = "Account End Points")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasAuthority('CREATE_ACCOUNT'))")
    public ResponseEntity<?> create(@CurrentPrincipal CustomPrincipal principal,
                                    @Validated @RequestBody AccountDto accountDto) {
        accountDto = accountService.create(principal, accountDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/accounts/{id}")
                .buildAndExpand(accountDto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseApi(true, "Account saved successfully!"));
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasAuthority('UPDATE_ACCOUNT'))")
    public ResponseEntity<?> update(@CurrentPrincipal CustomPrincipal principal,
                                    @Validated @RequestBody AccountDto accountDto) {
        accountService.update(principal, accountDto);
        return new ResponseEntity<>(new ResponseApi(true, "Account updated successfully!"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasAuthority('READ_ACCOUNT'))")
    public ResponseEntity<?> findById(@CurrentPrincipal CustomPrincipal principal,
                                      @PathVariable String id) {
        return new ResponseEntity<>(accountService.findById(principal, id), HttpStatus.OK);
    }

    @GetMapping("/number/{number}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasAuthority('READ_ACCOUNT'))")
    public ResponseEntity<?> findByCode(@CurrentPrincipal CustomPrincipal principal,
                                        @PathVariable String number) {
        return new ResponseEntity<>(accountService.findByNumber(principal, number), HttpStatus.OK);
    }

    @GetMapping("/partnerId/{partnerId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasAuthority('READ_ACCOUNT'))")
    public ResponseEntity<?> findByPartnerId(@CurrentPrincipal CustomPrincipal principal,
                                             @PathVariable String partnerId) {
        return new ResponseEntity<>(accountService.findByPartnerId(principal, partnerId), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasAuthority('READ_ACCOUNT'))")
    public ResponseEntity<AccountDtoPage> findAll(@CurrentPrincipal CustomPrincipal principal,
                                                  @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                  @RequestParam(value = "number", required = false) String number,
                                                  @RequestParam(value = "partnerId", required = false) String partnerId){


        if (pageNumber == null || pageNumber < 0){
            pageNumber = AccountConstantType.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = AccountConstantType.DEFAULT_PAGE_SIZE;
        }

        return new ResponseEntity<>(accountService.findAll(principal, number, partnerId, PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_MANAGER') and hasAuthority('DELETE_ACCOUNT'))")
    public ResponseEntity<?> deleteById(@CurrentPrincipal CustomPrincipal principal,
                                        @PathVariable String id) {
        accountService.deleteById(principal, id);
        return new ResponseEntity<>(new ResponseApi(true, "Account deleted successfully!"), HttpStatus.OK);
    }


}
