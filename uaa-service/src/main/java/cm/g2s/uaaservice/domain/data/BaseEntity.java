package cm.g2s.uaaservice.domain.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {

    static final long serialVersionUID = -6595313140364210752L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 64, nullable = false, updatable = false)
    protected String id;
    @Column(length = 64)
    protected String createdUid;
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    protected Timestamp createdDate;
    @UpdateTimestamp
    @Column(insertable = false)
    protected Timestamp lastModifiedDate;
    @Column(length = 64, insertable = false)
    protected String lastModifiedUid;

    public BaseEntity(String id) {
        this.id = id;
    }
}
