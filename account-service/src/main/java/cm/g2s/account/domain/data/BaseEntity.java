package cm.g2s.account.domain.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    static final long serialVersionUID = 3364429759851257412L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 64, nullable = false, updatable = false)
    protected String id;
    @CreatedBy
    @Column(length = 64,  updatable = false)
    protected String createdUid;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    protected Timestamp createdDate;
    @LastModifiedDate
    @Column(insertable = false)
    protected Timestamp lastModifiedDate;
    @LastModifiedBy
    @Column(length = 64, insertable = false)
    protected String lastModifiedUid;

    public BaseEntity(String id) {
        this.id = id;
    }
}
