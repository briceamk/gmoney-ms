package cm.g2s.cron.web.mapper;

import cm.g2s.cron.domain.model.SchedulerJobInfo;
import cm.g2s.cron.web.dto.SchedulerJobInfoDto;
import org.mapstruct.Mapper;

@Mapper
public interface SchedulerJobInfoMapper {
    SchedulerJobInfo map(SchedulerJobInfoDto jobInfoDto);
    SchedulerJobInfoDto map(SchedulerJobInfo jobInfo);
}
