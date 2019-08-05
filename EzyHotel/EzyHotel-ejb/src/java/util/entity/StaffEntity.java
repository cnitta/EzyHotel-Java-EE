/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import util.enumeration.DepartmentEnum;
import util.enumeration.GenderEnum;
import util.enumeration.JobPositionEnum;
import util.enumeration.JobTypeEnum;
import util.enumeration.StaffStatusEnum;

/**
 *
 * @author berni
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "D_Type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value="StaffEntity")
@Table(
    name = "STAFFENTITY",
    uniqueConstraints =  {@UniqueConstraint(columnNames = {"USERNAME, EMAIL, IC_NUM"})} //Defining multiple unique constraints
)
   
public class StaffEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffId;    
    private String name;
    private String ic_num;
    @Temporal(TemporalType.DATE)    
    private Date dateOfBirth;
    private String nationality;
    private String phoneNum;
    private String homeNum;
    private int leaveQuota;
    private double salary;
    private double bonus;
    private String jobTitle;
    private String email;
   
    @Enumerated(EnumType.STRING)
    private GenderEnum gender; 
    
    @Enumerated(EnumType.STRING)
    private DepartmentEnum department;
    
    @Enumerated(EnumType.STRING)
    private JobTypeEnum jobType;
    
    @Enumerated(EnumType.STRING)
    private JobPositionEnum jobPosition;
    
    @Enumerated(EnumType.STRING)
    private StaffStatusEnum staffStatus;
    
    private String username;
    private String password;  
    
    //TODO: Please determine if you want to keep CascadeType.Persist
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<LeaveEntity> leaves;
    
    @ManyToOne
    private HotelEntity hotel; 
    
//    @OneToOne
//    private PictureEntity picture;
    
    public StaffEntity() {}
    
    public StaffEntity(String name, String ic_num, Date dateOfBirth, String nationality, String phoneNum, String homeNum, 
            int leaveQuota, Double salary, Double bonus, String jobTitle,GenderEnum gender, DepartmentEnum department, JobTypeEnum jobType, 
            JobPositionEnum jobPosition, StaffStatusEnum staffStatus, String username, String password) {
        this.name = name;
        this.ic_num = ic_num;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.phoneNum = phoneNum;
        this.homeNum = homeNum;
        this.leaveQuota = leaveQuota;
        this.salary = salary;
        this.bonus = bonus;
        this.jobTitle = jobTitle;
        this.department = department;
        this.jobType = jobType;
        this.gender= gender; 
        this.jobPosition = jobPosition;
        this.staffStatus = staffStatus;
        this.username = username;
        this.password = password;
        this.leaves = leaves = new ArrayList<>();
    }

    public StaffEntity(String name, String ic_num, Date dateOfBirth, String nationality, String phoneNum, String homeNum, int leaveQuota, double salary, 
            double bonus, String jobTitle, GenderEnum gender, DepartmentEnum department, JobTypeEnum jobType, JobPositionEnum jobPosition, StaffStatusEnum staffStatus, 
            String username, String password, List<LeaveEntity> leaves, HotelEntity hotel) {
        this.name = name;
        this.ic_num = ic_num;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.phoneNum = phoneNum;
        this.homeNum = homeNum;
        this.leaveQuota = leaveQuota;
        this.salary = salary;
        this.bonus = bonus;
        this.jobTitle = jobTitle;
        this.gender = gender;
        this.department = department;
        this.jobType = jobType;
        this.jobPosition = jobPosition;
        this.staffStatus = staffStatus;
        this.username = username;
        this.password = password;
        this.leaves = leaves;
        this.hotel = hotel;
    }
    
    public StaffEntity(String name, String jobTitle, JobPositionEnum jobPosition, DepartmentEnum department, String username, String password) {
        this.name = name;
        this.jobTitle = jobTitle;
        this.jobPosition = jobPosition;
        this.department = department;
        this.username = username;
        this.password = password;
        
    }
    
    public StaffEntity(String name, String jobTitle, JobPositionEnum jobPosition, DepartmentEnum department) {
        this.name = name;
        this.jobTitle = jobTitle;
        this.jobPosition = jobPosition;
        this.department = department;
    }
    
    public Long getStaffId() {
        return staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIc_num() {
        return ic_num;
    }

    public void setIc_num(String ic_num) {
        this.ic_num = ic_num;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getHomeNum() {
        return homeNum;
    }

    public void setHomeNum(String homeNum) {
        this.homeNum = homeNum;
    }

    public int getLeaveQuota() {
        return leaveQuota;
    }

    public void setLeaveQuota(int leaveQuota) {
        this.leaveQuota = leaveQuota;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public DepartmentEnum getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEnum department) {
        this.department = department;
    }

    public JobTypeEnum getJobType() {
        return jobType;
    }

    public void setJobType(JobTypeEnum jobType) {
        this.jobType = jobType;
    }

    public JobPositionEnum getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(JobPositionEnum jobPosition) {
        this.jobPosition = jobPosition;
    }

    public StaffStatusEnum getStaffStatus() {
        return staffStatus;
    }

    public void setStaffStatus(StaffStatusEnum staffStatus) {
        this.staffStatus = staffStatus;
    }


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.staffId);
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
        final StaffEntity other = (StaffEntity) obj;
        if (!Objects.equals(this.staffId, other.staffId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StaffEntity{" + "staffId=" + staffId + '}';
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param staffId the staffId to set
     */
    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    /**
     * @return the gender
     */
    public GenderEnum getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    /**
     * @return the salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * @param salary the salary to set
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * @return the bonus
     */
    public double getBonus() {
        return bonus;
    }

    /**
     * @param bonus the bonus to set
     */
    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    /**
     * @return the hotel
     */
    public HotelEntity getHotel() {
        return hotel;
    }

    /**
     * @param hotel the hotel to set
     */
    public void setHotel(HotelEntity hotel) {
        this.hotel = hotel;
    }

    /**
     * @return the leaves
     */
    public List<LeaveEntity> getLeaves() {
        return this.leaves;
    }

    /**
     * @param leaves the leaves to set
     */
    public void setLeaves(List<LeaveEntity> leaves) {
        this.leaves = leaves;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public PictureEntity getPicture() {
//        return picture;
//    }
//
//    public void setPicture(PictureEntity picture) {
//        this.picture = picture;
//    }

}
