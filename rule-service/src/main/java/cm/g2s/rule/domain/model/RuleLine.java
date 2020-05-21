package cm.g2s.rule.domain.model;

import cm.g2s.rule.domain.data.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class RuleLine extends BaseEntity {

    static final long serialVersionUID = -3237812315210462653L;

    private String name;
    @Enumerated(EnumType.STRING)
    private Operator operator;
    @Enumerated(EnumType.STRING)
    private RuleLineType type;
    @Column(nullable = false)
    private Long input;
    private Double amount;
    private Double factor;
    private String formula;
    @ManyToOne
    @JoinColumn(name = "rule_id", nullable = false)
    private Rule rule;

    @Builder
    public RuleLine(String id, String name, Operator operator, RuleLineType type,
                    Long input, Double amount, Double factor, String formula, Rule rule) {
        super(id);
        this.name = name;
        this.operator = operator;
        this.type = type;
        this.input = input;
        this.amount = amount;
        this.factor = factor;
        this.formula = formula;
        this.rule = rule;
    }

}
