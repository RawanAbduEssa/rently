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
public class TableViewBookings {
    
    private String proprtyName; 
    private String PropertyType;
    private String CheckIn;
    private int duration;
    private double price;

    public TableViewBookings() {
    }

    public TableViewBookings(String proprtyName, String PropertyType, String CheckIn, int duration, double price) {
        this.proprtyName = proprtyName;
        this.PropertyType = PropertyType;
        this.CheckIn = CheckIn;
        this.duration = duration;
        this.price = price;
    }

    public String getProprtyName() {
        return proprtyName;
    }

    public void setProprtyName(String proprtyName) {
        this.proprtyName = proprtyName;
    }

    public String getPropertyType() {
        return PropertyType;
    }

    public void setPropertyType(String PropertyType) {
        this.PropertyType = PropertyType;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    
}
