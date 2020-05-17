package cm.g2s.uaa.domain.model;

import cm.g2s.uaa.domain.data.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
public class Permission extends BaseEntity {
    @Column(nullable = false, unique = true, length = 64)
    private String name;

    @Builder
    public Permission(String id, String name) {
        super(id);
        this.name = name;
    }
}
