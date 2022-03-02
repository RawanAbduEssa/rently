/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "imagep")
public class imagep implements Serializable {

    @Id
    @Column(name = "imageID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageID;

    @Column(name = "image")
    private byte[] images;
    
    @Column(name = "propertyID")
    private int propertyID;

    public imagep() {
    }

    public imagep(byte[] images, int propertyID) {
        this.images = images;
        this.propertyID = propertyID;
    }

    public imagep(int imageID, byte[] images, int propertyID) {
        this.imageID = imageID;
        this.images = images;
        this.propertyID = propertyID;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public byte[] getImages() {
        return images;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }

    public int getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(int propertyID) {
        this.propertyID = propertyID;
    }
    
    
}
