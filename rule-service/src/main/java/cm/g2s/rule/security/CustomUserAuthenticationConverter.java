package cm.g2s.rule.security;


import cm.g2s.rule.constant.RuleConstantType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class CustomUserAuthenticationConverter implements UserAuthenticationConverter {

    private Collection<? extends GrantedAuthority> defaultAuthorities;

    public void setDefaultAuthorities(String[] defaultAuthorities) {
        this.defaultAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(
                StringUtils.arrayToCommaDelimitedString(defaultAuthorities));
    }

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<>();
        if(authentication.getName() != null && !authentication.getName().isEmpty())
            response.put(USERNAME, authentication.getName());
        if(authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty())
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        return response;
    }

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        CustomPrincipal principal = new CustomPrincipal();

        if(map.containsKey(USERNAME)) {
            principal.setUsername(map.get(USERNAME).toString());
            if(map.containsKey(RuleConstantType.JTI) && map.get(RuleConstantType.JTI) != null)
                principal.setId(map.get(RuleConstantType.JTI).toString());
            if(map.containsKey(RuleConstantType.FULL_NAME) && map.get(RuleConstantType.FULL_NAME) != null)
                principal.setFullName(map.get(RuleConstantType.FULL_NAME).toString());
            if(map.containsKey(RuleConstantType.MOBILE) && map.get(RuleConstantType.MOBILE) != null)
                principal.setMobile(map.get(RuleConstantType.MOBILE).toString());
            if(map.containsKey(RuleConstantType.EMAIL) && map.get(RuleConstantType.EMAIL) != null)
                principal.setEmail(map.get(RuleConstantType.EMAIL).toString());
            if(map.containsKey(AUTHORITIES) && map.get(AUTHORITIES) != null)
                principal.setAuthorities(getAuthorities(map));
            return new UsernamePasswordAuthenticationToken(principal, "N/A", principal.getAuthorities());
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Map<String,?> map) {
        if(!map.containsKey(AUTHORITIES))
            return defaultAuthorities;
        Object authorities = map.get(AUTHORITIES);

        if(authorities instanceof String)
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);

        if(authorities instanceof Collection)
            return AuthorityUtils.commaSeparatedStringToAuthorityList(
                    StringUtils.collectionToCommaDelimitedString((Collection<?>) authorities));
        throw new IllegalArgumentException("Authorities must either a string or collection");
    }
}
