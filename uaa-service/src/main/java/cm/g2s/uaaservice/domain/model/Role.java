package cm.g2s.uaaservice.domain.model;

import cm.g2s.uaaservice.domain.data.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Role extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")}
    )
    private Set<Permission> permissions;

    @Builder
    public Role(String id, String name, Set<Permission> permissions) {
        super(id);
        this.name = name;
        this.permissions = permissions;
    }
}
