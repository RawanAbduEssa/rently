package project;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_payment")
public class order_payment implements Serializable {
    
    @Id
    @Column(name = "cardNumber")
    private String cardNumber;

    @Column(name = "CVC")
    private int CVC;

    @Column(name = "expDate")
    private String expDate;

    @Column(name = "ownerName")
    private String ownerName;

    public order_payment() {
    }

    public order_payment(String cardNumber, int CVC, String expDate, String ownerName) {
        this.cardNumber = cardNumber;
        this.CVC = CVC;
        this.expDate = expDate;
        this.ownerName = ownerName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCVC() {
        return CVC;
    }

    public void setCVC(int CVC) {
        this.CVC = CVC;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

}
