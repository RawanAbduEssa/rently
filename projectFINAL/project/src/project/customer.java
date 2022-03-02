package project;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="customer")
public class customer implements Serializable {
    @Id
    @Column (name="customerID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerID;
    
    @Column(name = "fullName")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "mobile")
    private String mobile; //mobile 
    
    public customer() {
    }

    public customer(String fullName, String email, String password, String mobile) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
    }

    public customer(int customerID, String fullName, String email, String password, String mobile) {
        this.customerID = customerID;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
    }
    
    
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}