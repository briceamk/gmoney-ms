package cm.g2s.partner.domain.model;

import cm.g2s.partner.domain.data.BaseEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
public class Partner extends BaseEntity {

    static final long serialVersionUID = -6092355029785433052L;
    
    private String firstName;
    @Column(nullable = false, length = 64)
    private String lastName;
    private Date bornDate;
    @Column(unique = true)
    private String nicId;
    @Column(unique = true)
    private String email;
    private Date nicIssueDate;
    private String nicIssuePlace;
    private String city;
    private String country;
    private BigDecimal creditLimit;
    private String companyId;
    private String userId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private PartnerType type;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private PartnerState state;
    @OneToMany(mappedBy = "partner",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Wallet> wallets;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private PartnerCategory category;

    @Builder
    public Partner(String id, String firstName, String lastName, Date bornDate, String nicId, String email, Date nicIssueDate,
                   String nicIssuePlace, String city, String country, BigDecimal creditLimit, String companyId, String userId,
                   PartnerType type, PartnerState state, List<Wallet> wallets, PartnerCategory category) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.bornDate = bornDate;
        this.nicId = nicId;
        this.email = email;
        this.nicIssueDate = nicIssueDate;
        this.nicIssuePlace = nicIssuePlace;
        this.city = city;
        this.country = country;
        this.creditLimit = creditLimit;
        this.companyId = companyId;
        this.userId = userId;
        this.type = type;
        this.state = state;
        this.wallets = wallets;
        this.category = category;

    }
}
