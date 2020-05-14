package cm.g2s.uaaservice.domain.model;

import cm.g2s.uaaservice.domain.data.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends BaseEntity {
    @Column(nullable = false, length = 128)
    private String fullName;
    @Column(nullable = false, unique = true, length = 32)
    private String username;
    @Column(nullable = false, unique = true, length = 128)
    private String email;
    @Column(nullable = false, unique = true, length = 12)
    private String mobile;
    @Column(nullable = false)
    private String password;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private Boolean enabled;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Role> roles;

    @Builder
    public User(String id, String fullName, String username, String email, String password, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled, Set<Role> roles) {
        super(id);
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.roles = roles;
    }
}
