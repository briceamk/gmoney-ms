package cm.g2s.uaa.domain.model;

import cm.g2s.uaa.domain.data.BaseEntity;
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
    @Column(length = 64)
    private String firstName;
    @Column(nullable = false, length = 64)
    private String lastName;
    @Column(nullable = false, unique = true, length = 32, updatable = false)
    private String username;
    @Column(nullable = false, unique = true, length = 128)
    private String email;
    @Column(nullable = false, unique = true, length = 12)
    private String mobile;
    //TODO remove city after test complete
    private String city;
    private String partnerId;
    private String companyId;
    @Column(nullable = false)
    private String password;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private Boolean enabled;
    @Enumerated(EnumType.STRING)
    private UserState state;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Role> roles;

    @Builder
    public User(String id, String firstName,String lastName, String username, String email, String mobile,
                String city, String partnerId, String companyId, String password, boolean accountNonExpired,
                boolean accountNonLocked, boolean credentialsNonExpired, UserState state,
                boolean enabled, Set<Role> roles) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        //TODO remove city after test complete
        this.city = city;
        this.companyId = companyId;
        this.partnerId = partnerId;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.state = state;
        this.enabled = enabled;
        this.roles = roles;
    }
}
