package cm.g2s.cron.service.impl;

import cm.g2s.cron.domain.model.JonInfoState;
import cm.g2s.cron.domain.model.SchedulerJobInfo;
import cm.g2s.cron.exception.BadRequestException;
import cm.g2s.cron.exception.ConflictException;
import cm.g2s.cron.exception.ResourceNotFoundException;
import cm.g2s.cron.infratructure.repository.SchedulerJobInfoRepository;
import cm.g2s.cron.security.CustomPrincipal;
import cm.g2s.cron.service.SchedulerJobInfoService;
import cm.g2s.cron.util.JobScheduleCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service("schedulerJobInfoService")
public class SchedulerJobInfoServiceImpl implements SchedulerJobInfoService {

    private final SchedulerFactoryBean schedulerFactoryBean;
    private final SchedulerJobInfoRepository schedulerJobInfoRepository;
    private final ApplicationContext context;
    private final JobScheduleCreator scheduleCreator;

    @Override
    public SchedulerJobInfo create(CustomPrincipal principal, SchedulerJobInfo jobInfo) {
        if(schedulerJobInfoRepository.existsByJobName(jobInfo.getJobName())) {
            log.error("A job with name {} already taken!", jobInfo.getJobName());
            throw new ConflictException(String.format("A job with name %s already taken!", jobInfo.getJobName()));
        }
        if(schedulerJobInfoRepository.existsByJobClass(jobInfo.getJobClass())) {
            log.error("A job with class name {} already taken!", jobInfo.getJobName());
            throw new ConflictException(String.format("A job with class name %s already taken!", jobInfo.getJobName()));
        }

        if(jobInfo.getCronExpression() != null && !jobInfo.getCronExpression().isEmpty()) {
            if( CronExpression.isValidExpression(jobInfo.getCronExpression())) {
                jobInfo.setCronJob(true);
            } else {
                log.error("Invalid cron expression {}", jobInfo.getCronExpression());
                throw new BadRequestException(String.format("Invalid cron expression %s", jobInfo.getCronExpression()));
            }
        }

        jobInfo.setState(JonInfoState.DRAFT);
        return schedulerJobInfoRepository.save(jobInfo);
    }

    @Override
    public void update(CustomPrincipal principal, SchedulerJobInfo jobInfo) {
        //TODO validate unique field
        if(jobInfo.getCronExpression() != null && !jobInfo.getCronExpression().isEmpty()) {
            if( CronExpression.isValidExpression(jobInfo.getCronExpression())) {
                jobInfo.setCronJob(true);
            } else {
                log.error("Invalid cron expression {}", jobInfo.getCronExpression());
                throw new BadRequestException(String.format("Invalid cron expression %s", jobInfo.getCronExpression()));
            }
        }
        schedulerJobInfoRepository.save(jobInfo);
    }

    @Override
    public SchedulerJobInfo findById(CustomPrincipal principal, String id) {
        return schedulerJobInfoRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Job info with id {} not found", id);
                    throw new ResourceNotFoundException(String.format("Job info with id %s not found", id));
                }
        );
    }

    @Override
    public Page<SchedulerJobInfo> findAll(CustomPrincipal principal, String jobName,
                              String jobGroup, String jobClass, String cronExpression,
                                          Long repeatTime, PageRequest pageRequest) {

        Page<SchedulerJobInfo> schedulerJobInfoPage;

        if (!StringUtils.isEmpty(jobName)) {
            //search by job name
            schedulerJobInfoPage = schedulerJobInfoRepository.findByJobNameContains(jobName, pageRequest);
        } else if (!StringUtils.isEmpty(jobGroup)) {
            //search by job group
            schedulerJobInfoPage = schedulerJobInfoRepository.findByJobGroupContains(jobGroup, pageRequest);
        } else if (!StringUtils.isEmpty(jobClass)) {
            //search by job class
            schedulerJobInfoPage = schedulerJobInfoRepository.findByJobClassContains(jobClass, pageRequest);
        } else if (!StringUtils.isEmpty(cronExpression)) {
            //search by job cron expression
            schedulerJobInfoPage = schedulerJobInfoRepository.findByCronExpressionContains(cronExpression, pageRequest);
        }
        else if (repeatTime != null) {
            //search by job repeat time
            schedulerJobInfoPage = schedulerJobInfoRepository.findByRepeatTime(repeatTime, pageRequest);
        }
        else{
            // search all
            schedulerJobInfoPage = schedulerJobInfoRepository.findAll(pageRequest);
        }

        return schedulerJobInfoPage;

    }

    @Override
    public void deleteById(CustomPrincipal principal, String id) {
        schedulerJobInfoRepository.delete(findById(principal, id));
    }

    @Override
    public void startAllSchedulers(CustomPrincipal principal) {
        List<SchedulerJobInfo> jobInfoList = schedulerJobInfoRepository.findAll();

        if(jobInfoList != null) {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            jobInfoList.forEach(jobInfo -> {
                try {
                    JobDetail jobDetail = JobBuilder.newJob((Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()))
                            .withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup()).build();
                    if(!scheduler.checkExists(jobDetail.getKey())) {
                        Trigger trigger;
                        jobDetail = scheduleCreator.createJob((Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()),
                                false, context, jobInfo.getJobName(), jobInfo.getJobGroup());

                        if(jobInfo.getCronJob() && CronExpression.isValidExpression(jobInfo.getCronExpression())) {
                            trigger = scheduleCreator.createCronTrigger(jobInfo.getJobName(), new Date(),
                                    jobInfo.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
                        } else {
                            trigger = scheduleCreator.createSimpleTrigger(jobInfo.getJobName(), new Date(),
                                    jobInfo.getRepeatTime(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
                        }
                        scheduler.scheduleJob(jobDetail, trigger);
                        jobInfo.setState(JonInfoState.SCHEDULED);
                        update(principal, jobInfo);
                    }
                } catch (ClassNotFoundException e) {
                    log.error("Class not found - {}", jobInfo.getJobClass(), e);
                } catch (SchedulerException e) {
                    log.error("Unable to schedule job {}", jobInfo.getJobName(), e);
                }
            });
        }
    }

    @Override
    public void scheduleNewJob(CustomPrincipal principal, SchedulerJobInfo jobInfo) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            JobDetail jobDetail = JobBuilder.newJob((Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()))
                    .withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup()).build();

            if(!scheduler.checkExists(jobDetail.getKey())) {
                Trigger trigger;
                jobDetail = scheduleCreator.createJob((Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()),
                        false, context, jobInfo.getJobName(), jobInfo.getJobGroup());
                if(jobInfo.getCronJob() && CronExpression.isValidExpression(jobInfo.getCronExpression())) {
                    trigger = scheduleCreator.createCronTrigger(jobInfo.getJobName(), new Date(),
                            jobInfo.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
                } else {
                    trigger = scheduleCreator.createSimpleTrigger(jobInfo.getJobName(), new Date(),
                            jobInfo.getRepeatTime(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
                }
                scheduler.scheduleJob(jobDetail, trigger);
                jobInfo.setState(JonInfoState.SCHEDULED);
                update(principal, jobInfo);
                startScheduleJobNow(principal, jobInfo);
            } else {
                log.error("scheduleNewJobRequest.jobAlreadyExist");
            }
        } catch (ClassNotFoundException e) {
            log.error("Class not found - {}", jobInfo.getJobClass(), e);
        } catch (SchedulerException e) {
            log.error("Unable to schedule job {}", jobInfo.getJobName(), e);
        }
    }

    @Override
    public void updateScheduleJob(CustomPrincipal principal, SchedulerJobInfo jobInfo) {
        Trigger trigger;
        if(jobInfo.getCronJob()) {
            trigger = scheduleCreator.createCronTrigger(jobInfo.getJobName(), new Date(),
                    jobInfo.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        } else {
            trigger = scheduleCreator.createSimpleTrigger(jobInfo.getJobName(), new Date(),
                    jobInfo.getRepeatTime(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        }

        try {
            schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(jobInfo.getJobName()), trigger);
            jobInfo.setState(JonInfoState.SCHEDULED);
            update(principal, jobInfo);
        } catch (SchedulerException e) {
            log.error("Unable to schedule job {}", jobInfo.getJobName(), e);
        }
    }

    @Override
    public boolean unScheduleJob(CustomPrincipal principal, String jobName) {
        try{
            boolean isUnscheduled =  schedulerFactoryBean.getScheduler().unscheduleJob(new TriggerKey(jobName));
            if(isUnscheduled) {
                SchedulerJobInfo jobInfo = schedulerJobInfoRepository.findByJobName(jobName).orElseThrow(
                        () -> {
                            log.error("Job name {} not found", jobName);
                            throw  new ResourceNotFoundException(String.format("Job name {} not found", jobName));
                        }
                );
                jobInfo.setState(JonInfoState.UN_SCHEDULED);
                update(principal, jobInfo);
            }
            return isUnscheduled;
        } catch (SchedulerException e) {
            log.error("Unable to un-schedule job {}", jobName, e);
            throw  new ResourceNotFoundException(String.format("Unable to un-schedule job %s", jobName));
        }
    }

    @Override
    public boolean deleteScheduleJob(CustomPrincipal principal, SchedulerJobInfo jobInfo) {
        try{
            boolean isDeleted = schedulerFactoryBean.getScheduler().deleteJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
            if(isDeleted) {
                jobInfo.setState(JonInfoState.DRAFT);
                update(principal, jobInfo);
            }
            return isDeleted;
        } catch (SchedulerException e) {
            log.error("Unable to delete job {}", jobInfo.getJobName(), e);
            throw  new ResourceNotFoundException(String.format("Unable to delete job %s", jobInfo.getJobName()));
        }
    }

    @Override
    public boolean pauseScheduleJob(CustomPrincipal principal, SchedulerJobInfo jobInfo) {
        try{
            schedulerFactoryBean.getScheduler().pauseJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
            jobInfo.setState(JonInfoState.PAUSED);
            update(principal, jobInfo);
            return true;
        } catch (SchedulerException e) {
            log.error("Unable to pause job {}", jobInfo.getJobName(), e);
            throw  new ResourceNotFoundException(String.format("Unable to pause job %s", jobInfo.getJobName()));
        }
    }

    @Override
    public boolean resumeScheduleJob(CustomPrincipal principal, SchedulerJobInfo jobInfo) {
        try {
            schedulerFactoryBean.getScheduler().resumeJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
            jobInfo.setState(JonInfoState.SCHEDULED);
            update(principal, jobInfo);
            return true;
        } catch (SchedulerException e) {
            log.error("Unable to resume job {}", jobInfo.getJobName(), e);
            throw  new ResourceNotFoundException(String.format("Unable to resume job %s", jobInfo.getJobName()));
        }
    }

    @Override
    public boolean startScheduleJobNow(CustomPrincipal principal, SchedulerJobInfo jobInfo) {
        try {
            schedulerFactoryBean.getScheduler().triggerJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
            jobInfo.setState(JonInfoState.STARTED);
            update(principal, jobInfo);
            return true;
        } catch (SchedulerException e) {
            log.error("Unable to start job {}", jobInfo.getJobName(), e);
            throw  new ResourceNotFoundException(String.format("Unable to start job %s", jobInfo.getJobName()));
        }
    }
}
