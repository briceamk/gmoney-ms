package cm.g2s.partner.domain.data;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor

public class BaseEntity implements Serializable {

    @Id @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(length = 64, nullable = false, updatable = false)
    private String id;
    @Version
    private Integer version;
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

}
