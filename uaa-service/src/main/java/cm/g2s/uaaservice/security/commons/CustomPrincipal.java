package cm.g2s.uaaservice.security.commons;

import cm.g2s.uaaservice.domain.model.Authority;
import cm.g2s.uaaservice.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomPrincipal implements UserDetails {

    protected String id;
    protected String fullName;
    protected String username;
    protected String email;
    protected String mobile;
    protected String password;
    protected boolean accountNonExpired;
    protected boolean accountNonLocked;
    protected boolean credentialsNonExpired;
    protected boolean enabled;
    protected Collection<? extends GrantedAuthority> authorities;

    public static CustomPrincipal create(User user){

        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new Authority(role.getName()));
            role.getPermissions().forEach(permission -> {
                authorities.add(new Authority(permission.getName()));
            });

        });
        return new CustomPrincipal(
                user.getId(),
                user.getFullName(),
                user.getUsername(),
                user.getEmail(),
                user.getMobile(),
                user.getPassword(),
                user.getAccountNonExpired(),
                user.getAccountNonLocked(),
                user.getCredentialsNonExpired(),
                user.getEnabled(),
                authorities
        );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }


    public String getMobile() {
        return mobile;
    }


}
