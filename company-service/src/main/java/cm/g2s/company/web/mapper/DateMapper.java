package cm.g2s.company.web.mapper;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class DateMapper {
    public LocalDate map(Date date) {
        if(date != null)
            return date.toLocalDate();
        else
            return null;
    }

    public Date map(LocalDate  ld) {
        if(ld != null)
            return Date.valueOf(ld);
        else
            return null;
    }
}
