package cm.g2s.partner.domain.model;

import cm.g2s.partner.domain.data.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Partner extends BaseEntity {
    private String firstName;
    @Column(nullable = false, length = 64)
    private String lastName;
    private Date bornDate;
    @Column(unique = true)
    private String nicId;
    private Date nicIssueDate;
    private String nicIssuePlace;
    private String city;
    private String country;
    private BigDecimal creditLimit;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private PartnerType type;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private PartnerState active;
    @OneToMany(mappedBy = "partner")
    private List<Wallet> wallets;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private PartnerCategory category;


}
