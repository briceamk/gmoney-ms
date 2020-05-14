package cm.g2s.rule.domain.model;

public enum Operator {
  LOWER_OR_EQUAL("<="),
  HIGHER_OR_EQUAL(">="),
  EQUAL("=");
  private String operator;

    Operator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
