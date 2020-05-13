package cm.g2s.partner.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public  class Wallet implements Serializable {

    static final long serialVersionUID = 9186582349103994579L;

    @Id @GeneratedValue(generator = "uuid")
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
    
    @Column(unique = true, nullable = false, length = 16)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 12)
    private WalletType type;
    private Boolean isDefault;
    private Boolean active = true;
    @ManyToOne
    @JoinColumn(name = "partner_id", nullable = false)
    private Partner partner;

    
}
