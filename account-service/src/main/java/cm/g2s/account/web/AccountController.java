package cm.g2s.account.web;

import cm.g2s.account.constant.AccountConstantType;
import cm.g2s.account.service.AccountService;
import cm.g2s.account.shared.dto.AccountDto;
import cm.g2s.account.shared.dto.AccountDtoPage;
import cm.g2s.account.shared.payload.ResponseApi;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Api(value = "Account", tags = "Account End Points")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody AccountDto accountDto) {
        accountDto = accountService.create(accountDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/accounts/{id}")
                .buildAndExpand(accountDto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseApi(true, "Account saved successfully!"));
    }

    @PutMapping
    public ResponseEntity<?> update(@Validated @RequestBody AccountDto accountDto) {
        accountService.update(accountDto);
        return new ResponseEntity<>(new ResponseApi(true, "Account updated successfully!"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return new ResponseEntity<>(accountService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/number/{number}")
    public ResponseEntity<?> findByCode(@PathVariable String number) {
        return new ResponseEntity<>(accountService.findByNumber(number), HttpStatus.OK);
    }

    @GetMapping("/partnerId/{partnerId}")
    public ResponseEntity<?> findByPartnerId(@PathVariable String partnerId) {
        return new ResponseEntity<>(accountService.findByPartnerId(partnerId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<AccountDtoPage> findAll(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                  @RequestParam(value = "number", required = false) String number,
                                                  @RequestParam(value = "partnerId", required = false) String partnerId){


        if (pageNumber == null || pageNumber < 0){
            pageNumber = AccountConstantType.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = AccountConstantType.DEFAULT_PAGE_SIZE;
        }

        return new ResponseEntity<>(accountService.findAll(number, partnerId, PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        accountService.deleteById(id);
        return new ResponseEntity<>(new ResponseApi(true, "Account deleted successfully!"), HttpStatus.OK);
    }


}
