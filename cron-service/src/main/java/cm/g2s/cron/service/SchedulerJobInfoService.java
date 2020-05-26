package cm.g2s.cron.service;

import cm.g2s.cron.domain.model.SchedulerJobInfo;
import cm.g2s.cron.security.CustomPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface SchedulerJobInfoService {


    SchedulerJobInfo create(CustomPrincipal principal, SchedulerJobInfo jobInfo);

    void update(CustomPrincipal principal, SchedulerJobInfo schedulerJobInfo);

    SchedulerJobInfo findById(CustomPrincipal principal, String id);

    Page<SchedulerJobInfo> findAll(CustomPrincipal principal, String jobName, String jobGroup,
                       String jobClass, String cronExpression, Long repeatTime, PageRequest pageRequest);

    void deleteById(CustomPrincipal principal, String id);

    void startAllSchedulers(CustomPrincipal principal);

    void scheduleNewJob(CustomPrincipal principal, SchedulerJobInfo jobInfo);

    void updateScheduleJob(CustomPrincipal principal, SchedulerJobInfo jobInfo);

    boolean unScheduleJob(CustomPrincipal principal, String jobName);

    boolean deleteScheduleJob(CustomPrincipal principal, SchedulerJobInfo jobInfo);

    boolean pauseScheduleJob(CustomPrincipal principal, SchedulerJobInfo jobInfo);

    boolean resumeScheduleJob(CustomPrincipal principal, SchedulerJobInfo jobInfo);

    boolean startScheduleJobNow(CustomPrincipal principal, SchedulerJobInfo jobInfo);
}
