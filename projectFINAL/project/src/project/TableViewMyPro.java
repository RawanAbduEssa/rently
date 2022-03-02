/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author Uni
 */
public class TableViewMyPro {
    
    private String propertyName;
    private String orderDate;
    private String CheckIn;
    private int duration;
    private String CustomerFullName;

    public TableViewMyPro() {
    }

    public TableViewMyPro(String propertyName, String orderDate, String CheckIn, int duration, String CustomerFullName) {
        this.propertyName = propertyName;
        this.orderDate = orderDate;
        this.CheckIn = CheckIn;
        this.duration = duration;
        this.CustomerFullName = CustomerFullName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getCustomerFullName() {
        return CustomerFullName;
    }

    public void setCustomerFullName(String CustomerFullName) {
        this.CustomerFullName = CustomerFullName;
    }
    
    
}
