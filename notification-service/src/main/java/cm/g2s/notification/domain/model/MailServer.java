package cm.g2s.notification.domain.model;

import cm.g2s.notification.domain.data.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Entity
@NoArgsConstructor
public class MailServer extends BaseEntity {

    static final long serialVersionUID = -1417063325310358804L;
    @Column(nullable = false)
    private String hostname;
    @Column(nullable = false)
    private String port;
    private String username;
    private String password;
    private Boolean enableSSL;
    private Boolean enableAuth;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MailServerType type;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MailServerState state;
    private Boolean defaultServer;

    @Builder
    public MailServer(String id,String hostname, String port, String username, String password, Boolean enableSSL,
                      Boolean enableAuth, MailServerType type,  MailServerState state, Boolean defaultServer) {
        super(id);
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.password = password;
        this.enableSSL = enableSSL;
        this.enableAuth = enableAuth;
        this.type = type;
        this.state = state;
        this.defaultServer = defaultServer;
    }
}
