/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import util.enumeration.CustomerMembershipEnum;
import util.enumeration.GenderEnum;

/**
 *
 * @author vincentyeo
 */
@Entity
@Table(
    name = "CUSTOMERENTITY",
    uniqueConstraints =  {@UniqueConstraint(columnNames = {"CUSTIDENTITY, EMAIL, PHONENUM"})} //Defining multiple unique constraints
)
   
public class CustomerEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String firstName;
    private String lastName;
    
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String custIdentity;
    private String email;
    private String phoneNum;
    private String country;
    
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    
    @Temporal(TemporalType.DATE)
    private Date firstJoined;
    @Enumerated(EnumType.STRING)
    private CustomerMembershipEnum membershipStatus;
    private String password;
    

    

    public CustomerEntity(String firstName, String lastName, GenderEnum gender, String phoneNum, String custIdentity, String country, String email, Date birthDate, Date firstJoined) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.gender = gender;
        this.custIdentity = custIdentity;
        this.country = country;
        this.email = email;
        this.birthDate = birthDate;
        this.firstJoined = firstJoined;
        this.membershipStatus = CustomerMembershipEnum.NON_MEMBER;
    }

    //Customer make a booking, will first establish his customer profile
    public CustomerEntity(String firstName, String lastName, GenderEnum gender, String phoneNum, String custIdentity, String email, Date birthDate, Date firstJoined) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.custIdentity = custIdentity;
        this.email = email;
        this.birthDate = birthDate;
        this.firstJoined = firstJoined;
        membershipStatus = CustomerMembershipEnum.NON_MEMBER;
    }

    //When the customer first sign up as member
    public CustomerEntity(String firstName, String lastName, GenderEnum gender, String phoneNum, String email, Date birthDate, Date firstJoined, String password, String salt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.email = email;
        this.birthDate = birthDate;
        this.firstJoined = firstJoined;
        this.password = password;
        membershipStatus = CustomerMembershipEnum.UNVERIFIED_MEMBER;
        this.country = country;
    }

    //When the customer make a booking without account
    public CustomerEntity(String firstName, String lastName, GenderEnum gender, String phoneNum, String custIdentity, String country, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.custIdentity = custIdentity;
        this.country = country;
        this.email = email;
    }

    public CustomerEntity() {
        membershipStatus = CustomerMembershipEnum.NON_MEMBER;
    }

    public Long getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the firstJoined
     */
    public Date getFirstJoined() {
        return firstJoined;
    }

    /**
     * @param firstJoined the firstJoined to set
     */
    public void setFirstJoined(Date firstJoined) {
        this.firstJoined = firstJoined;
    }

    public String getCustIdentity() {
        return custIdentity;
    }

    public void setCustIdentity(String custIdentity) {
        this.custIdentity = custIdentity;
    }

    public CustomerMembershipEnum getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(CustomerMembershipEnum membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.customerId);
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
        final CustomerEntity other = (CustomerEntity) obj;
        if (!Objects.equals(this.customerId, other.customerId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CustomerEntity{" + "customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNum=" + phoneNum + ", custIdentity=" + custIdentity + ", email=" + email + ", birthDate=" + birthDate + ", firstJoined=" + firstJoined + ", membershipStatus=" + membershipStatus + ", password=" + password + '}';
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
