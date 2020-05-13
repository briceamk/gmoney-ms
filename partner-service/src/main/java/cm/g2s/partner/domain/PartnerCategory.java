package cm.g2s.partner.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PartnerCategory implements Serializable {

    static final long serialVersionUID = -6901293121815381283L;

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
    
    @Column(nullable = false, unique = true, length = 64)
    private String name;
    private BigDecimal creditLimit;
    private Boolean active;
}
