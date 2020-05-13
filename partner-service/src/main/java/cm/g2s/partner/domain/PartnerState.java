package cm.g2s.partner.domain;

/**
 * CREATE: When partner is add in app
 * VALID: When his information in app is complete. In this state he's able to make a loan.
 * ACTIVE: When partner make frequently loan
 * INACTIVE: When partner more then two month without make a loan
 */
public enum PartnerState {
    CREATE, VALID, ACTIVE, INACTIVE
}
