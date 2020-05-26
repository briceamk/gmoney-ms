package cm.g2s.cron.domain.model;

import cm.g2s.cron.domain.data.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Data
@Entity
@NoArgsConstructor
public class SchedulerJobInfo extends BaseEntity {

    static final long serialVersionUID = 5597058793100819223L;
    @Column(nullable = false, unique = true)
    private String jobName;
    @Column(nullable = false)
    private String jobGroup;
    @Column(nullable = false, unique = true)
    private String jobClass;
    private String cronExpression;
    private Long repeatTime;
    private Boolean cronJob;
    @Enumerated(EnumType.STRING)
    private JonInfoState state;

    @Builder
    public SchedulerJobInfo(String id, String jobName, String jobGroup, String jobClass,
                            String cronExpression, Long repeatTime, Boolean cronJob, JonInfoState state) {
        super(id);
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.jobClass = jobClass;
        this.cronExpression = cronExpression;
        this.repeatTime = repeatTime;
        this.cronJob = cronJob;
        this.state = state;
    }
}
