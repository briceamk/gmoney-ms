package cm.g2s.cron.web;

import cm.g2s.cron.constant.CronConstantType;
import cm.g2s.cron.domain.model.SchedulerJobInfo;
import cm.g2s.cron.security.CurrentPrincipal;
import cm.g2s.cron.security.CustomPrincipal;
import cm.g2s.cron.service.SchedulerJobInfoService;
import cm.g2s.cron.service.ValidationErrorService;
import cm.g2s.cron.web.dto.SchedulerJobInfoDto;
import cm.g2s.cron.web.dto.SchedulerJobInfoDtoPage;
import cm.g2s.cron.web.mapper.SchedulerJobInfoMapper;
import cm.g2s.cron.web.payload.ResponseApi;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/crons")
@Api(value = "Cron", tags = "Cron End Points")
public class SchedulerJobInfoController {

    private final SchedulerJobInfoService schedulerJobInfoService;
    private final SchedulerJobInfoMapper  schedulerJobInfoMapper;
    private final ValidationErrorService validationErrorService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('CREATE_JOB_INFO')")
    public ResponseEntity<?> create(@CurrentPrincipal CustomPrincipal principal,
                                            @Valid @RequestBody SchedulerJobInfoDto jobInfoDto, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        SchedulerJobInfo jobInfo = schedulerJobInfoService.create(principal, schedulerJobInfoMapper.map(jobInfoDto));
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/crons/{id}")
                .buildAndExpand(jobInfo.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseApi(true, "New job has create successfully!"));
    }


    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('UPDATE_JOB_INFO')")
    public ResponseEntity<?> update(@CurrentPrincipal CustomPrincipal principal,
                                    @Valid @RequestBody SchedulerJobInfoDto jobInfoDto, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        schedulerJobInfoService.update(principal, schedulerJobInfoMapper.map(jobInfoDto));

        return ResponseEntity.ok(new ResponseApi(true, "New job has updated successfully!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('READ_JOB_INFO')")
    public ResponseEntity<?> findById(@CurrentPrincipal CustomPrincipal principal,
                                    @PathVariable String id) {

        return ResponseEntity.ok(schedulerJobInfoMapper.map(schedulerJobInfoService.findById(principal, id)));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('READ_JOB_INFO')")
    public ResponseEntity<?> findAll(@CurrentPrincipal CustomPrincipal principal,
                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                     @RequestParam(value = "jobName", required = false) String jobName,
                                     @RequestParam(value = "jobGroup", required = false) String jobGroup,
                                     @RequestParam(value = "jobClass", required = false) String jobClass,
                                     @RequestParam(value = "cronExpression", required = false) String cronExpression,
                                     @RequestParam(value = "repeatTime", required = false) Long repeatTime ){

        if (pageNumber == null || pageNumber < 0){
            pageNumber = CronConstantType.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = CronConstantType.DEFAULT_PAGE_SIZE;
        }

        Page<SchedulerJobInfo> schedulerJobInfoPage = schedulerJobInfoService.findAll(principal, jobName, jobGroup, jobClass,
                cronExpression, repeatTime, PageRequest.of(pageNumber, pageSize));

        SchedulerJobInfoDtoPage jobInfoDtoPage = new SchedulerJobInfoDtoPage(
                schedulerJobInfoPage.getContent().stream().map(schedulerJobInfoMapper::map).collect(Collectors.toList()),
                PageRequest.of(schedulerJobInfoPage.getPageable().getPageNumber(),
                        schedulerJobInfoPage.getPageable().getPageSize()),
                schedulerJobInfoPage.getTotalElements()
        );

        return ResponseEntity.ok(jobInfoDtoPage);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('DELETE_JOB_INFO')")
    public ResponseEntity<?> deleteById(@CurrentPrincipal CustomPrincipal principal,
                                      @PathVariable String id) {
        schedulerJobInfoService.deleteById(principal, id);
        return ResponseEntity.ok(new ResponseApi(true, "New job has deleted successfully!"));
    }

    @GetMapping("/scheduler/start-all")
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('START_ALL_SCHEDULER')")
    public ResponseEntity<?> startAllSchedulers(@CurrentPrincipal CustomPrincipal principal) {
        schedulerJobInfoService.startAllSchedulers(principal);
        return ResponseEntity.ok(new ResponseApi(true, "All jobs has scheduled successfully!"));
    }


    @PutMapping("/scheduler/new")
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('NEW_SCHEDULER')")
    public ResponseEntity<?> scheduleNewJob(@CurrentPrincipal CustomPrincipal principal,
                                            @Valid @RequestBody SchedulerJobInfoDto jobInfoDto, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        schedulerJobInfoService.scheduleNewJob(principal, schedulerJobInfoMapper.map(jobInfoDto));
        return ResponseEntity.ok(new ResponseApi(true, "New job has scheduled successfully!"));
    }


    @PutMapping("/scheduler/re")
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('RE_SCHEDULER')")
    public ResponseEntity<?> reScheduleJob(@CurrentPrincipal CustomPrincipal principal,
                                            @Valid @RequestBody SchedulerJobInfoDto jobInfoDto, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        schedulerJobInfoService.updateScheduleJob(principal, schedulerJobInfoMapper.map(jobInfoDto));
        return ResponseEntity.ok(new ResponseApi(true, "job has re-schedule successfully!"));
    }


    @GetMapping("/scheduler/un/{jobName}")
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('UN_SCHEDULER')")
    public ResponseEntity<?> unScheduleJob(@CurrentPrincipal CustomPrincipal principal,
                                               @PathVariable String jobName) {
        schedulerJobInfoService.unScheduleJob(principal, jobName);
        return ResponseEntity.ok(new ResponseApi(true,  "job has un-schedule successfully!"));
    }


    @PutMapping("/scheduler/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('DELETE_SCHEDULER')")
    public ResponseEntity<?> deleteScheduleJob(@CurrentPrincipal CustomPrincipal principal,
                                           @Valid @RequestBody SchedulerJobInfoDto jobInfoDto, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        schedulerJobInfoService.deleteScheduleJob(principal, schedulerJobInfoMapper.map(jobInfoDto));
        return ResponseEntity.ok(new ResponseApi(true,   "job schedule delete successfully!"));
    }


    @PutMapping("/scheduler/pause")
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('PAUSE_SCHEDULER')")
    public ResponseEntity<?> pauseScheduleJob(@CurrentPrincipal CustomPrincipal principal,
                                               @Valid @RequestBody SchedulerJobInfoDto jobInfoDto, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        schedulerJobInfoService.pauseScheduleJob(principal, schedulerJobInfoMapper.map(jobInfoDto));
        return ResponseEntity.ok(new ResponseApi(true,   "job schedule pause successfully!"));
    }


    @PutMapping("/scheduler/resume")
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('RESUME_SCHEDULER')")
    public ResponseEntity<?> resumeScheduleJob(@CurrentPrincipal CustomPrincipal principal,
                                              @Valid @RequestBody SchedulerJobInfoDto jobInfoDto, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        schedulerJobInfoService.resumeScheduleJob(principal, schedulerJobInfoMapper.map(jobInfoDto));
        return ResponseEntity.ok(new ResponseApi(true,    "job schedule resumed successfully!"));
    }


    @PutMapping("/scheduler/start")
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('START_SCHEDULER')")
    public ResponseEntity<?> startScheduleJobNow(@CurrentPrincipal CustomPrincipal principal,
                                               @Valid @RequestBody SchedulerJobInfoDto jobInfoDto, BindingResult result) {
        ResponseEntity<?> errors = validationErrorService.process(result);
        if(errors != null)
            return errors;
        schedulerJobInfoService.startScheduleJobNow(principal, schedulerJobInfoMapper.map(jobInfoDto));
        return ResponseEntity.ok(new ResponseApi(true,    "job started successfully!"));
    }


}
