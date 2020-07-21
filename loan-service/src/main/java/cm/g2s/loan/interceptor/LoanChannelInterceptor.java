package cm.g2s.loan.interceptor;

import cm.g2s.loan.constant.LoanConstantType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

@Slf4j
//@Component
//@GlobalChannelInterceptor(order = -1)
public class LoanChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        OAuth2AuthenticationDetails details = authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails ?
                (OAuth2AuthenticationDetails) authentication.getDetails() : null;
        if (details == null)
            return message;
        MessageBuilder builder = MessageBuilder.withPayload(message.getPayload());
        message.getHeaders().entrySet().forEach(header -> {
            if(!header.getKey().equals("id") && !header.getKey().equals("timestamp"))
                builder.setHeader(header.getKey(), header.getValue());
        });
        builder.setHeader(LoanConstantType.ACCESS_TOKEN, details.getTokenValue());
        return builder.build();
    }
}
