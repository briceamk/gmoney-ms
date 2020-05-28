package cm.g2s.notification.web;

import cm.g2s.notification.constant.NotificationConstantType;
import cm.g2s.notification.domain.model.MailServer;
import cm.g2s.notification.security.CurrentPrincipal;
import cm.g2s.notification.security.CustomPrincipal;
import cm.g2s.notification.service.MailServerService;
import cm.g2s.notification.service.ValidationErrorService;
import cm.g2s.notification.web.dto.MailServerDto;
import cm.g2s.notification.web.dto.MailServerDtoPage;
import cm.g2s.notification.web.mapper.MailServerMapper;
import cm.g2s.notification.web.payload.ResponseApi;
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
@RequestMapping("/api/v1/mail-servers")
@Api(value = "Mail Server", tags = "Mail Server End Points")
public class MailServerController {


    private final MailServerService mailServerService;
    private final MailServerMapper mailServerMapper;
    private final ValidationErrorService validationErrorService;


    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('CREATE_MAIL_SERVER')")
    public ResponseEntity<?> create(@CurrentPrincipal CustomPrincipal principal,
                                    @Valid @RequestBody MailServerDto mailServerDto, BindingResult result)  {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        MailServer mailServer = mailServerService.create(principal, mailServerMapper.map(mailServerDto));
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/mail-servers/{id}")
                .buildAndExpand(mailServer.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseApi(true, "Mail server saved successfully!"));
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('UPDATE_MAIL_SERVER')")
    public ResponseEntity<?> update(@CurrentPrincipal CustomPrincipal principal,
                                    @Validated @RequestBody MailServerDto mailServerDto, BindingResult result) {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        mailServerService.update(principal, mailServerMapper.map(mailServerDto));
        return new ResponseEntity<>(new ResponseApi(true, "Mail server updated successfully!"), HttpStatus.OK);
    }

    @PutMapping("/test")
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('TEST_MAIL_SERVER')")
    public ResponseEntity<?> testServer(@CurrentPrincipal CustomPrincipal principal,
                                    @Validated @RequestBody MailServerDto mailServerDto, BindingResult result) throws ScriptException {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        mailServerService.testServer(principal, mailServerMapper.map(mailServerDto));
        return new ResponseEntity<>(new ResponseApi(true, "Server is connected successfully!"), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('READ_MAIL_SERVER')")
    public ResponseEntity<?> findById(@CurrentPrincipal CustomPrincipal principal,
                                      @PathVariable String id) {
        return new ResponseEntity<>(mailServerMapper.map(mailServerService.findById(principal, id)), HttpStatus.OK);
    }


    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('READ_MAIL_SERVER')")
    public ResponseEntity<?> findAll(@CurrentPrincipal CustomPrincipal principal,
                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                     @RequestParam(value = "hostname", required = false) String hostname){

        if (pageNumber == null || pageNumber < 0){
            pageNumber = NotificationConstantType.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = NotificationConstantType.DEFAULT_PAGE_SIZE;
        }

        Page<MailServer> mailServerPage =  mailServerService.findAll(principal, hostname, PageRequest.of(pageNumber, pageSize));

        MailServerDtoPage mailServerDtoPage = new MailServerDtoPage(
                mailServerPage.getContent().stream().map(mailServerMapper::map).collect(Collectors.toList()),
                PageRequest.of(mailServerPage.getPageable().getPageNumber(),
                        mailServerPage.getPageable().getPageSize()),
                mailServerPage.getTotalElements()
        );

        return new ResponseEntity<>(mailServerDtoPage, HttpStatus.OK);
    }


}
