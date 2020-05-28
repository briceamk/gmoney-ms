package cm.g2s.notification.web;

import cm.g2s.notification.constant.NotificationConstantType;
import cm.g2s.notification.domain.model.Mail;
import cm.g2s.notification.security.CurrentPrincipal;
import cm.g2s.notification.security.CustomPrincipal;
import cm.g2s.notification.service.MailService;
import cm.g2s.notification.service.ValidationErrorService;
import cm.g2s.notification.web.dto.MailDto;
import cm.g2s.notification.web.dto.MailDtoPage;
import cm.g2s.notification.web.mapper.MailMapper;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mails")
@Api(value = "Mail", tags = "Mail End Points")
public class MailController {


    private final MailService mailService;
    private final MailMapper mailMapper;
    private final ValidationErrorService validationErrorService;


    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER') and hasAuthority('CREATE_MAIL')")
    public ResponseEntity<?> create(@CurrentPrincipal CustomPrincipal principal,
                                    @Valid @RequestBody MailDto mailDto, BindingResult result) {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        Mail mail = mailService.create(principal, mailMapper.map(mailDto));
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/mails/{id}")
                .buildAndExpand(mail.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseApi(true, "Mail saved successfully!"));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER') and hasAuthority('UPDATE_MAIL')")
    public ResponseEntity<?> update(@CurrentPrincipal CustomPrincipal principal,
                                    @Validated @RequestBody MailDto mailDto, BindingResult result)  {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        mailService.update(principal, mailMapper.map(mailDto));
        return new ResponseEntity<>(new ResponseApi(true, "Mail updated successfully!"), HttpStatus.OK);
    }

    @PutMapping("/send")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER') and hasAuthority('SEND_MAIL')")
    public ResponseEntity<?> send(@CurrentPrincipal CustomPrincipal principal,
                                    @Validated @RequestBody MailDto mailDto, BindingResult result) throws ScriptException {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        mailService.send(principal, mailMapper.map(mailDto));
        return new ResponseEntity<>(new ResponseApi(true, "Mail send successfully!"), HttpStatus.OK);
    }

    @PutMapping("/send/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER') and hasAuthority('SEND_ALL_MAIL')")
    public ResponseEntity<?> sendAll(@CurrentPrincipal CustomPrincipal principal,
                                      BindingResult result) throws ScriptException {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        mailService.sendAll(principal);
        return new ResponseEntity<>(new ResponseApi(true, "Mails send successfully!"), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER') and hasAuthority('READ_MAIL')")
    public ResponseEntity<?> findById(@CurrentPrincipal CustomPrincipal principal,
                                      @PathVariable String id) {
        return new ResponseEntity<>(mailMapper.map(mailService.findById(principal, id)), HttpStatus.OK);
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER') and hasAuthority('READ_MAIL')")
    public ResponseEntity<?> findAll(@CurrentPrincipal CustomPrincipal principal,
                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                     @RequestParam(value = "reference", required = false) String reference,
                                     @RequestParam(value = "state", required = false) String state){

        if (pageNumber == null || pageNumber < 0){
            pageNumber = NotificationConstantType.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = NotificationConstantType.DEFAULT_PAGE_SIZE;
        }

        Page<Mail> mailPage =  mailService.findAll(principal, reference, state, PageRequest.of(pageNumber, pageSize));

        MailDtoPage mailDtoPage = new MailDtoPage(
                mailPage.getContent().stream().map(mailMapper::map).collect(Collectors.toList()),
                PageRequest.of(mailPage.getPageable().getPageNumber(),
                        mailPage.getPageable().getPageSize()),
                mailPage.getTotalElements()
        );

        return new ResponseEntity<>(mailDtoPage, HttpStatus.OK);
    }


}
