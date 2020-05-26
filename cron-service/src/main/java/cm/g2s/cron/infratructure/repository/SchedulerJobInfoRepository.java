package cm.g2s.cron.infratructure.repository;

import cm.g2s.cron.domain.model.SchedulerJobInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchedulerJobInfoRepository extends JpaRepository<SchedulerJobInfo, String> {
    Boolean existsByJobName(String jobName);

    Boolean existsByJobClass(String jobClass);

    Page<SchedulerJobInfo> findByJobNameContains(String jobName, Pageable pageable);

    Page<SchedulerJobInfo> findByJobGroupContains(String jobGroup, Pageable pageable);

    Page<SchedulerJobInfo> findByJobClassContains(String jobClass, Pageable pageable);

    Page<SchedulerJobInfo> findByCronExpressionContains(String cronExpression, Pageable pageable);

    Page<SchedulerJobInfo> findByRepeatTime(Long repeatTime, Pageable pageable);

    Optional<SchedulerJobInfo> findByJobName(String jobName);
}
