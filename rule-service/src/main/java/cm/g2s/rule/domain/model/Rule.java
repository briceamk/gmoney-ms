package cm.g2s.rule.domain.model;

import cm.g2s.rule.domain.data.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Rule extends BaseEntity {

    static final long serialVersionUID = -6638452146641073877L;

    @Column(length = 8, nullable = false, unique = true)
    private String code;
    @Column(length = 64, nullable = false)
    private String name;
    private Boolean active;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "rule")
    private List<RuleLine> ruleLines;

    @Builder
    public Rule(String id, String code, String name, List<RuleLine> ruleLines) {
        super(id);
        this.code = code;
        this.name = name;
        this.ruleLines = ruleLines;
    }
}
