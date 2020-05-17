package cm.g2s.uaa.security;


import cm.g2s.uaa.constant.UaaConstantType;
import cm.g2s.uaa.security.commons.CustomPrincipal;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();

        Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());
        info.put(UaaConstantType.FULL_NAME, principal.getFullName());
        info.put(UaaConstantType.USERNAME, principal.getUsername());
        info.put(UaaConstantType.EMAIL, principal.getEmail());
        info.put(UaaConstantType.MOBILE, principal.getMobile());
        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(info);
        return super.enhance(customAccessToken, authentication);
    }


}
