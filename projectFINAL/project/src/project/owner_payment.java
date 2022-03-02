
package project;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Uni
 */

@Entity
@Table(name = "owner_payment_p")
public class owner_payment implements Serializable{

    @Id
    @Column(name = "IBAN")
    private String IBAN;

    @Column(name = "bank")
    private String bank;

    @Column(name = "AccountName")
    private String accountName;

    @Column(name = "AccountNum")
    String accountNum;

    public owner_payment() {
    }

    public owner_payment(String IBAN, String bank, String accountName, String accountNum) {
        this.IBAN = IBAN;
        this.bank = bank;
        this.accountName = accountName;
        this.accountNum = accountNum;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

}