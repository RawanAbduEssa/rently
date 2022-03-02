package project;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author HU6EM001
 */
@Entity
@Table(name = "orderp")
public class Order implements Serializable {

    @Id
    @Column(name = "orderId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Column(name = "orderDate")
    private String orderDate;

    @Column(name = "checkIn")
    private String CheckIn;

    @Column(name = "cardNumber")
    private String cardNumber;

    @Column(name = "propertyId")
    private int propertyId;

    @Column(name = "customerID")
    private int customerID;

    @Column(name = "ownerID")
    private int ownerID;

    @Column(name = "price")
    private double price;

    @Column(name = "duration")
    private int duration;

    public Order(String orderDate, String CheckIn, String cardNumber, int propertyId, int customerID, int ownerID, double price, int duration) {
        this.orderDate = orderDate;
        this.CheckIn = CheckIn;
        this.cardNumber = cardNumber;
        this.propertyId = propertyId;
        this.customerID = customerID;
        this.ownerID = ownerID;
        this.price = price;
        this.duration = duration;
    }

    public Order() {
    }

    public Order(int orderId, String orderDate, String CheckIn, String cardNumber, int propertyId, int customerID, int ownerID, double price, int duration) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.CheckIn = CheckIn;
        this.cardNumber = cardNumber;
        this.propertyId = propertyId;
        this.customerID = customerID;
        this.ownerID = ownerID;
        this.price = price;
        this.duration = duration;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCheckIn() {
        return CheckIn;
    }

    public void setCheckIn(String CheckIn) {
        this.CheckIn = CheckIn;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
