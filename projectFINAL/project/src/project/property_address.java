/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author asus
 */
@Entity
@Table(name = "property_address")
public class property_address implements Serializable {
    
    @Id
    @Column(name = "zipcode")
    private String zipcode;
    @Column(name = "city")
    private String city;
    @Column(name = "Street")
    private String Street;
    @Column(name = "neighborhood")
    private String neighborhood;
    @Column (name = "locatioLink")
    private String locatioLink;
    
    public property_address() {
    }

    public property_address(String zipcode, String city, String Street, String neighborhood, String locatioLink) {
        this.zipcode = zipcode;
        this.city = city;
        this.Street = Street;
        this.neighborhood = neighborhood;
        this.locatioLink = locatioLink;
    }

    public String getLocatioLink() {
        return locatioLink;
    }

    public void setLocatioLink(String locatioLink) {
        this.locatioLink = locatioLink;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String Street) {
        this.Street = Street;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }
    
}
