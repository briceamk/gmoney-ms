package cm.g2s.company.domain.model;

import cm.g2s.company.domain.data.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Data
@Entity
@NoArgsConstructor
public class Company extends BaseEntity {

    @Column(nullable = false, length = 12)
    private String code;
    @Column(nullable = false, length = 64)
    private String name;
    private String phoneNumber;
    private String mobileNumber;
    private String email;
    private String city;
    private String street;
    private String country;
    private String vat;
    private String trn;
    private String logoFileName;
    private Boolean active;
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] logoImage;
    private String logoImageType;

    @Builder
    public Company(String id,  String code, String name, String phoneNumber, String mobileNumber, String email, String city, String street,
                   String country, String vat, String trn, String logoFileName, Boolean active, byte[] logoImage, String logoImageType) {
        super(id);
        this.code = code;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.city = city;
        this.street = street;
        this.country = country;
        this.vat = vat;
        this.trn = trn;
        this.logoFileName = logoFileName;
        this.active = active;
        this.logoImage = logoImage;
        this.logoImageType = logoImageType;
    }
}
