package util.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author vincentyeo
 */
@Entity
public class HotelEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;

    private String name;
    private String address;
    private String email;
    private String country;
    private String telephoneNumber;
    @Column(columnDefinition = "VARCHAR(512)")
    private String description;
    private String hotelURL;
    @Column(columnDefinition = "VARCHAR(1000)")
    private String policies;
    private String services;
    @OneToOne
    private PictureEntity picture;
          
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.getHotelId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HotelEntity other = (HotelEntity) obj;
        if (!Objects.equals(this.hotelId, other.hotelId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HotelEntity{" + "hotelId=" + getHotelId() + '}';
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHotelURL() {
        return hotelURL;
    }

    public void setHotelURL(String hotelURL) {
        this.hotelURL = hotelURL;
    }

    public String getPolicies() {
        return policies;
    }

    public void setPolicies(String policies) {
        this.policies = policies;
    }
   
    /**
     * @return the telephoneNumber
     */
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    /**
     * @param telephoneNumber the telephoneNumber to set
     */
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    /**
     * @return the services
     */
    public String getServices() {
        return services;
    }

    /**
     * @param services the services to set
     */
    public void setServices(String services) {
        this.services = services;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public void setPicture(PictureEntity picture) {
        this.picture = picture;
    }

    
}

