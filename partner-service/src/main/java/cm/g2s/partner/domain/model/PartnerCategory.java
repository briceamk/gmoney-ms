package cm.g2s.partner.domain.model;

import cm.g2s.partner.domain.data.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerCategory extends BaseEntity {
    @Column(nullable = false, unique = true, length = 64)
    private String name;
    private BigDecimal creditLimit;
    private Boolean active;
}
