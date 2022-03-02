package project;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Uni
 */
@Entity
@Table(name = "ownerp")
public class owner implements Serializable {

    // ""ownerp"" Table in Mysql
    @Id
    @Column(name = "ownerID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ownerID;

 @Column(name = "customerId")
    private int customerId;

    @Column(name = "IBAN")
    private String IBAN;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public owner(int customerId, String IBAN) {
        this.customerId = customerId;
        this.IBAN = IBAN;
    }

    public owner() {
    }


    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }



    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

}