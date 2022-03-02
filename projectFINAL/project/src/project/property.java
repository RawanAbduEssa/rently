package project;



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
@Table(name = "property")
public class property implements java.io.Serializable {

    @Id
    @Column(name = "propertyId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int propertyId;

    @Column(name = "propertyName")
    private String propertyName;

    @Column(name = "PropertyType")
    private String PropertyType;

    @Column(name = "unitSpace")
    private double unitSpace;

    @Column(name = "price")
    private double price;

    @Column(name = "description")
    private String description;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "outdoorSeatingCapacity")
    private String outdoorSeatingCapacity;

    @Column(name = "livingRoomCapacity")
    private String livingRoomCapacity;

    @Column(name = "roomsNum")
    private String roomsNum;

    @Column(name = "masterBedsNum")
    private String masterBedsNum;

    @Column(name = "singleBedsNum")
    private String singleBedsNum;

    @Column(name = "bedroomsNum")
    private String bedroomsNum;

    @Column(name = "bathroomsNum")
    private String bathroomsNum;

    @Column(name = "single")
    private boolean single;

    @Column(name = "families")
    private boolean families;

    @Column(name = "playground")
    private boolean playground;

    @Column(name = "pool")
    private boolean pool;

    @Column(name = "football")
    private boolean football;

    @Column(name = "garden")
    private boolean garden;
    
    @Column(name = "ownerID")
    private int ownerID;

    public property() {
    }

    public property(String propertyName, String PropertyType, double unitSpace, double price, String description, String zipcode, String outdoorSeatingCapacity, String livingRoomCapacity, String roomsNum, String masterBedsNum, String singleBedsNum, String bedroomsNum, String bathroomsNum, boolean single, boolean families, boolean playground, boolean pool, boolean football, boolean garden, int ownerID) {
        this.propertyName = propertyName;
        this.PropertyType = PropertyType;
        this.unitSpace = unitSpace;
        this.price = price;
        this.description = description;
        this.zipcode = zipcode;
        this.outdoorSeatingCapacity = outdoorSeatingCapacity;
        this.livingRoomCapacity = livingRoomCapacity;
        this.roomsNum = roomsNum;
        this.masterBedsNum = masterBedsNum;
        this.singleBedsNum = singleBedsNum;
        this.bedroomsNum = bedroomsNum;
        this.bathroomsNum = bathroomsNum;
        this.single = single;
        this.families = families;
        this.playground = playground;
        this.pool = pool;
        this.football = football;
        this.garden = garden;
        this.ownerID = ownerID;
    }

    public property(int propertyId, String propertyName, String PropertyType, double unitSpace, double price, String description, String zipcode, String outdoorSeatingCapacity, String livingRoomCapacity, String roomsNum, String masterBedsNum, String singleBedsNum, String bedroomsNum, String bathroomsNum, boolean single, boolean families, boolean playground, boolean pool, boolean football, boolean garden, int ownerID) {
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.PropertyType = PropertyType;
        this.unitSpace = unitSpace;
        this.price = price;
        this.description = description;
        this.zipcode = zipcode;
        this.outdoorSeatingCapacity = outdoorSeatingCapacity;
        this.livingRoomCapacity = livingRoomCapacity;
        this.roomsNum = roomsNum;
        this.masterBedsNum = masterBedsNum;
        this.singleBedsNum = singleBedsNum;
        this.bedroomsNum = bedroomsNum;
        this.bathroomsNum = bathroomsNum;
        this.single = single;
        this.families = families;
        this.playground = playground;
        this.pool = pool;
        this.football = football;
        this.garden = garden;
        this.ownerID = ownerID;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyType() {
        return PropertyType;
    }

    public void setPropertyType(String PropertyType) {
        this.PropertyType = PropertyType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getOutdoorSeatingCapacity() {
        return outdoorSeatingCapacity;
    }

    public void setOutdoorSeatingCapacity(String outdoorSeatingCapacity) {
        this.outdoorSeatingCapacity = outdoorSeatingCapacity;
    }

    public String getLivingRoomCapacity() {
        return livingRoomCapacity;
    }

    public void setLivingRoomCapacity(String livingRoomCapacity) {
        this.livingRoomCapacity = livingRoomCapacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getUnitSpace() {
        return unitSpace;
    }

    public void setUnitSpace(double unitSpace) {
        this.unitSpace = unitSpace;
    }

    public String getRoomsNum() {
        return roomsNum;
    }

    public void setRoomsNum(String roomsNum) {
        this.roomsNum = roomsNum;
    }

    public String getMasterBedsNum() {
        return masterBedsNum;
    }

    public void setMasterBedsNum(String masterBedsNum) {
        this.masterBedsNum = masterBedsNum;
    }

    public String getSingleBedsNum() {
        return singleBedsNum;
    }

    public void setSingleBedsNum(String singleBedsNum) {
        this.singleBedsNum = singleBedsNum;
    }

    public String getBedroomsNum() {
        return bedroomsNum;
    }

    public void setBedroomsNum(String bedroomsNum) {
        this.bedroomsNum = bedroomsNum;
    }

    public String getBathroomsNum() {
        return bathroomsNum;
    }

    public void setBathroomsNum(String bathroomsNum) {
        this.bathroomsNum = bathroomsNum;
    }

    public boolean getSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public boolean getFamilies() {
        return families;
    }

    public void setFamilies(boolean families) {
        this.families = families;
    }

    public boolean getPlayground() {
        return playground;
    }

    public void setPlayground(boolean playground) {
        this.playground = playground;
    }

    public boolean getPool() {
        return pool;
    }

    public void setPool(boolean pool) {
        this.pool = pool;
    }

    public boolean getFootball() {
        return football;
    }

    public void setFootball(boolean football) {
        this.football = football;
    }

    public boolean getGarden() {
        return garden;
    }

    public void setGarden(boolean garden) {
        this.garden = garden;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

}
