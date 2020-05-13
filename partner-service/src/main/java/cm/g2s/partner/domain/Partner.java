package cm.g2s.partner.domain;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Partner implements Serializable {

    static final long serialVersionUID = -6092355029785433052L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 64, nullable = false, updatable = false)
    private String id;
    @Column(length = 64)
    private String createdUid;
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Timestamp createdDate;
    @UpdateTimestamp
    @Column(insertable = false)
    private Timestamp lastModifiedDate;
    @Column(length = 64, insertable = false)
    private String lastUpdatedUid;
    
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
    
}
