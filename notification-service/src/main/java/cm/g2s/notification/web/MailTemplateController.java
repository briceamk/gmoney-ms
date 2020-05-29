package cm.g2s.notification.web;

import cm.g2s.notification.constant.NotificationConstantType;

import cm.g2s.notification.domain.model.MailTemplate;
import cm.g2s.notification.security.CurrentPrincipal;
import cm.g2s.notification.security.CustomPrincipal;
import cm.g2s.notification.service.MailTemplateService;
import cm.g2s.notification.service.ValidationErrorService;
import cm.g2s.notification.web.dto.MailTemplateDto;
import cm.g2s.notification.web.dto.MailTemplateDtoPage;
import cm.g2s.notification.web.mapper.MailTemplateMapper;
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


import javax.validation.Valid;
import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mailTemplate-templates")
@Api(value = "Mail Template", tags = "Mail Template End Points")
public class MailTemplateController {


    private final MailTemplateService mailTemplateService;
    private final MailTemplateMapper mailTemplateMapper;
    private final ValidationErrorService validationErrorService;


    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('CREATE_MAIL_TEMPLATE')")
    public ResponseEntity<?> create(@CurrentPrincipal CustomPrincipal principal,
                                    @Valid @RequestBody MailTemplateDto mailTemplateDto, BindingResult result) {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        MailTemplate mailTemplate = mailTemplateService.create(principal, mailTemplateMapper.map(mailTemplateDto));
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/mail-templates/{id}")
                .buildAndExpand(mailTemplate.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseApi(true, "Mail template saved successfully!"));
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('UPDATE_MAIL_TEMPLATE')")
    public ResponseEntity<?> update(@CurrentPrincipal CustomPrincipal principal,
                                    @Validated @RequestBody MailTemplateDto mailTemplateDto, BindingResult result)  {

        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        mailTemplateService.update(principal, mailTemplateMapper.map(mailTemplateDto));
        return new ResponseEntity<>(new ResponseApi(true, "Mail template updated successfully!"), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('READ_MAIL_TEMPLATE')")
    public ResponseEntity<?> findById(@CurrentPrincipal CustomPrincipal principal,
                                      @PathVariable String id) {
        return new ResponseEntity<>(mailTemplateMapper.map(mailTemplateService.findById(principal, id)), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('READ_MAIL_TEMPLATE')")
    public ResponseEntity<?> findByName(@CurrentPrincipal CustomPrincipal principal,
                                      @PathVariable String name) {
        return new ResponseEntity<>(mailTemplateMapper.map(mailTemplateService.findByName(principal, name)), HttpStatus.OK);
    }


    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('READ_MAIL_TEMPLATE')")
    public ResponseEntity<?> findAll(@CurrentPrincipal CustomPrincipal principal,
                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                     @RequestParam(value = "name", required = false) String name){

        if (pageNumber == null || pageNumber < 0){
            pageNumber = NotificationConstantType.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = NotificationConstantType.DEFAULT_PAGE_SIZE;
        }

        Page<MailTemplate> mailTemplatePage =  mailTemplateService.findAll(principal, name, PageRequest.of(pageNumber, pageSize));

        MailTemplateDtoPage mailTemplateDtoPage = new MailTemplateDtoPage(
                mailTemplatePage.getContent().stream().map(mailTemplateMapper::map).collect(Collectors.toList()),
                PageRequest.of(mailTemplatePage.getPageable().getPageNumber(),
                        mailTemplatePage.getPageable().getPageSize()),
                mailTemplatePage.getTotalElements()
        );

        return new ResponseEntity<>(mailTemplateDtoPage, HttpStatus.OK);
    }


}
