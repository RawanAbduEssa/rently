package project;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import static project.Project.navBar;

/**
 *
 *
 */
public class EditAProperty {
    
    private static double price, SpaceValue;
    private static List<File> imageList; //List of images to select
    private static Session session1;
    private static CheckBox pool;
    private static CheckBox garden;
    private static CheckBox playground;
    private static CheckBox footballCourt;
    private static ImageView imageViewFile;
    private static FileInputStream stream;
    private static final FileChooser fileChooser = new FileChooser();
    private static byte[] convert;//Array to convert to bytes

    private static property getPropertyByID(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        
        List<property> propertyList = null;
        String query = "from property";
        Query queryProperty = session.createQuery(query);
        propertyList = queryProperty.list();
        session.close();
        
        property returnedProperty = new property();
        
        for (property p : propertyList) {
            if (p.getPropertyId() == id) {
                returnedProperty.setPropertyId(p.getPropertyId());
                returnedProperty.setPropertyName(p.getPropertyName());
                returnedProperty.setPropertyType(p.getPropertyType());
                returnedProperty.setDescription(p.getDescription());
                returnedProperty.setSingle(p.getSingle());
                returnedProperty.setFamilies(p.getFamilies());
                returnedProperty.setPlayground(p.getPlayground());
                returnedProperty.setPool(p.getPool());
                returnedProperty.setGarden(p.getGarden());
                returnedProperty.setFootball(p.getFootball());
                returnedProperty.setLivingRoomCapacity(p.getLivingRoomCapacity());
                returnedProperty.setOutdoorSeatingCapacity(p.getOutdoorSeatingCapacity());
                returnedProperty.setBedroomsNum(p.getBedroomsNum());
                returnedProperty.setSingleBedsNum(p.getSingleBedsNum());
                returnedProperty.setMasterBedsNum(p.getMasterBedsNum());
                returnedProperty.setRoomsNum(p.getRoomsNum());
                returnedProperty.setBathroomsNum(p.getBathroomsNum());
                returnedProperty.setZipcode(p.getZipcode());
                returnedProperty.setPrice(p.getPrice());
                returnedProperty.setUnitSpace(p.getUnitSpace());
                returnedProperty.setOwnerID(p.getOwnerID());
            }
        }
        return returnedProperty;
    }
    
    private static property_address getPropertAddressByID(String zipcode) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        
        List<property_address> propertyAddressList = null;
        String query = "from property_address";
        Query queryAddress = session.createQuery(query);
        propertyAddressList = queryAddress.list();
        session.close();
        
        property_address address = new property_address();
        
        for (property_address a : propertyAddressList) {
            if (a.getZipcode().equals(zipcode)) {
                address.setCity(a.getCity());
                address.setNeighborhood(a.getNeighborhood());
                address.setStreet(a.getStreet());
                address.setLocatioLink(a.getLocatioLink());
            }
        }
        return address;
    }
    
    public static Scene editAProperty(Stage primaryStage, int propertyID) {
        
        GridPane rentPane = new GridPane();
        rentPane.setPadding(new Insets(5, 5, 5, 15));
        rentPane.setHgap(5);
        rentPane.setVgap(5);
        rentPane.setAlignment(Pos.CENTER_LEFT);
        
        Label city = new Label("City: ");
        city.setFont(Font.font(17));
        ComboBox<String> cities = new ComboBox();
        cities.getItems().addAll("Makkah", "Jeddah", "Riyadh");
        cities.setValue("Makkah");
        
        Label neighborhood = new Label("Neighborhood: ");
        neighborhood.setFont(Font.font(17));
        TextField neighborField = new TextField();
        
        Label propertyNameRent = new Label("Property Name: ");
        propertyNameRent.setFont(Font.font(17));
        TextField properytNameField = new TextField();
        
        Label propertyType = new Label("Property Type: ");
        propertyType.setFont(Font.font("Century Gothic", 17));
        ComboBox<String> types = new ComboBox();
        types.getItems().addAll("Apartments", "Chalets", "Villas");
        types.setValue("Apartments");
        
        Label location = new Label("Location Link: ");
        location.setFont(Font.font(17));
        TextField locationField = new TextField();
        
        Label zipCode = new Label("Zip Code: ");
        zipCode.setFont(Font.font(17));
        TextField zipCodeField = new TextField();
        
        Label unitSpace = new Label("Unit Space (m^2): ");
        unitSpace.setFont(Font.font(15));
        TextField unitSpaceFiled = new TextField();
        
        Label street = new Label("Street: ");
        street.setFont(Font.font(17));
        TextField streetField = new TextField();

        //Slider for the price 
        Slider priceSlider = new Slider(0, 2000, 800);
        priceSlider.setShowTickLabels(true);
        priceSlider.setShowTickMarks(true);
        priceSlider.setMajorTickUnit(100);
        priceSlider.setMinorTickCount(5);
        priceSlider.setBlockIncrement(10);
        
        Label propertyPrice = new Label("Price:     ");
        propertyPrice.setFont(Font.font(17));
        
        priceSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldNumber, Number newNumber) {
                
                price = priceSlider.getValue();
                propertyPrice.setText("Price: " + Integer.toString((int) price) + "SR");
            }
        });
        
        Label propertyDescription = new Label("Description: ");
        propertyDescription.setFont(Font.font(17));
        TextArea descriptionArea = new TextArea();
        descriptionArea.setMaxSize(300, 80);
        
        Label category = new Label("Category: ");
        category.setFont(Font.font(16));
        CheckBox singles = new CheckBox("Singles");
        CheckBox families = new CheckBox("Families");
        
        Label features = new Label("Features: ");
        features.setFont(Font.font(17));
        pool = new CheckBox("Pool");
        garden = new CheckBox("Garden");
        playground = new CheckBox("Playground");
        footballCourt = new CheckBox("Football Court");
        
        Label livingRoom = new Label("Living Room Capacity: ");
        livingRoom.setFont(Font.font(17));
        ComboBox<String> livingRoomComboBox = new ComboBox();
        livingRoomComboBox.getItems().addAll("0", "1", "2", "3", "4", "5", "6", "7", "8", "+9");
        livingRoomComboBox.setValue("1");
        
        Label outdoor = new Label("Outdoor seating Capacity: ");
        outdoor.setFont(Font.font(17));
        ComboBox<String> outdoorComboBox = new ComboBox();
        outdoorComboBox.getItems().addAll("0", "1", "2", "3", "4", "5", "6", "7", "8", "+9");
        outdoorComboBox.setValue("1");
        
        Label bedroom = new Label("Bedrooms: ");
        bedroom.setFont(Font.font(17));
        ComboBox<String> bedroomComboBox = new ComboBox();
        bedroomComboBox.getItems().addAll("0", "1", "2", "3", "4", "5", "6", "7", "8", "+9");
        bedroomComboBox.setValue("1");
        
        Label singleBeds = new Label("Single Beds: ");
        singleBeds.setFont(Font.font(17));
        ComboBox<String> singleBedsComboBox = new ComboBox();
        singleBedsComboBox.getItems().addAll("0", "1", "2", "3", "4", "5", "6", "7", "8", "+9");
        singleBedsComboBox.setValue("1");
        
        Label masterBeds = new Label("Master Beds: ");
        masterBeds.setFont(Font.font(17));
        ComboBox<String> masterBedsComboBox = new ComboBox();
        masterBedsComboBox.getItems().addAll("0", "1", "2", "3", "4", "5", "6", "7", "8", "+9");
        masterBedsComboBox.setValue("1");
        
        Label rooms = new Label("Rooms: ");
        rooms.setFont(Font.font(17));
        ComboBox<String> roomsComboBox = new ComboBox();
        roomsComboBox.getItems().addAll("0", "1", "2", "3", "4", "5", "6", "7", "8", "+9");
        roomsComboBox.setValue("1");
        
        Label bathrooms = new Label("Bathrooms: ");
        bathrooms.setFont(Font.font(17));
        ComboBox<String> bathroomsComboBox = new ComboBox();
        bathroomsComboBox.getItems().addAll("0", "1", "2", "3", "4", "5", "6", "7", "8", "+9");
        bathroomsComboBox.setValue("1");
        
        Label uplode = new Label("Upload image: ");
        uplode.setFont(Font.font(17));
        
        Button imageButton = new Button("Upload Image");//Button to upload images
        imageButton.setId("uploadButton");
        
        VBox show = new VBox(); //To show selected images in page

        //Uplaod Image Action
        imageButton.setOnAction(e -> {
            
            fileChooser.setTitle("Upload An Image");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))); //Goes to pictures folder directly
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")); //Only Image
            imageList = fileChooser.showOpenMultipleDialog(primaryStage); //List for selected images
            if (imageList != null) {
                for (int i = 0; i < imageList.size(); i++) {
                    //Image view for the selected images
                    imageViewFile = new ImageView();
                    imageViewFile.setImage(new Image(imageList.get(i).toURI().toString()));//Set on scene
                    imageViewFile.setFitHeight(150);
                    imageViewFile.setFitWidth(160);
                    imageViewFile.setPreserveRatio(true);
                    show.getChildren().add(imageViewFile);
                }
            } else {
                System.out.println("file is Invalid");
            }
        });
        
        Button edit = new Button("Done");
        Button delete = new Button("Delete");
        HBox submitBox = new HBox(15);
        submitBox.getChildren().addAll(edit, delete);
        submitBox.setAlignment(Pos.CENTER);
        submitBox.setPadding(new Insets(5, 5, 5, 5));
        
        GridPane propertyPane = new GridPane();
        propertyPane.setPadding(new Insets(15, 5, 5, 15));
        propertyPane.setHgap(5);
        propertyPane.setVgap(5);
        propertyPane.setAlignment(Pos.TOP_LEFT);
        
        propertyPane.add(city, 0, 0);
        propertyPane.add(cities, 1, 0);
        propertyPane.add(neighborhood, 0, 1);
        propertyPane.add(neighborField, 1, 1);
        propertyPane.add(propertyNameRent, 0, 2);
        propertyPane.add(properytNameField, 1, 2);
        propertyPane.add(propertyType, 0, 3);
        propertyPane.add(types, 1, 3);
        propertyPane.add(zipCode, 0, 4);
        propertyPane.add(zipCodeField, 1, 4);
        propertyPane.add(street, 0, 5);
        propertyPane.add(streetField, 1, 5);
        propertyPane.add(location, 0, 6);
        propertyPane.add(locationField, 1, 6);
        propertyPane.add(unitSpace, 0, 7);
        propertyPane.add(unitSpaceFiled, 1, 7);
        
        GridPane propertyPane1 = new GridPane();
        propertyPane1.setPadding(new Insets(5, 5, 5, 5));
        propertyPane1.setHgap(5);
        propertyPane1.setVgap(15);
        propertyPane1.setAlignment(Pos.CENTER);
        
        propertyPane1.add(category, 0, 1);
        propertyPane1.add(singles, 0, 2);
        propertyPane1.add(families, 1, 2);
        propertyPane1.add(features, 0, 3);
        propertyPane1.add(pool, 0, 4);
        propertyPane1.add(garden, 1, 4);
        propertyPane1.add(playground, 0, 5);
        propertyPane1.add(footballCourt, 1, 5);
        propertyPane1.add(livingRoom, 0, 6);
        propertyPane1.add(livingRoomComboBox, 1, 6);
        propertyPane1.add(outdoor, 0, 7);
        propertyPane1.add(outdoorComboBox, 1, 7);
        propertyPane1.add(rooms, 0, 8);
        propertyPane1.add(roomsComboBox, 1, 8);
        propertyPane1.add(bedroom, 0, 9);
        propertyPane1.add(bedroomComboBox, 1, 9);
        propertyPane1.add(singleBeds, 0, 10);
        propertyPane1.add(singleBedsComboBox, 1, 10);
        propertyPane1.add(masterBeds, 0, 11);
        propertyPane1.add(masterBedsComboBox, 1, 11);
        propertyPane1.add(bathrooms, 0, 12);
        propertyPane1.add(bathroomsComboBox, 1, 12);
        propertyPane1.add(uplode, 0, 13);
        propertyPane1.add(imageButton, 1, 13);
        
        rentPane.add(propertyPrice, 0, 1);
        rentPane.add(priceSlider, 0, 2);
        rentPane.add(propertyDescription, 0, 3);
        rentPane.add(descriptionArea, 0, 4);
        
        VBox rentBox = new VBox(3);
        rentBox.getChildren().addAll(propertyPane, rentPane, propertyPane1, show, submitBox);
        rentBox.setAlignment(Pos.CENTER);
        rentBox.setPadding(new Insets(0, 0, 0, 0));
        
        VBox centerj = new VBox(20);
        centerj.setAlignment(Pos.TOP_CENTER);
        centerj.getChildren().add(rentBox);
        centerj.setPrefHeight(1100);
        
        ScrollPane scrollPaneRent = new ScrollPane();
        scrollPaneRent.setPadding(new Insets(0, 0, 0, 10));
        scrollPaneRent.setContent(centerj);
        scrollPaneRent.pannableProperty().set(true);
        scrollPaneRent.fitToWidthProperty().set(true);
        
        Text rentPropertyText = new Text("Edit");
        rentPropertyText.setFont(Font.font(48));
        rentPropertyText.getStyleClass().add("header");
        
        System.out.println(propertyID);
        property p = getPropertyByID(propertyID);
        System.out.println(p.getPropertyId());
        String zipCodeString = p.getZipcode();
        property_address address = getPropertAddressByID(zipCodeString);
        
        cities.setValue(address.getCity());
        neighborField.setText(address.getNeighborhood());
        streetField.setText(address.getStreet());
        locationField.setText(address.getLocatioLink());
        zipCodeField.setText(zipCodeString);
        
        unitSpaceFiled.setText(Double.toString(p.getUnitSpace()));
        
        properytNameField.setText(p.getPropertyName());
        types.setValue(p.getPropertyType());
        descriptionArea.setText(p.getDescription());
        outdoorComboBox.setValue(p.getOutdoorSeatingCapacity());
        livingRoomComboBox.setValue(p.getLivingRoomCapacity());
        
        roomsComboBox.setValue(p.getRoomsNum());
        masterBedsComboBox.setValue(p.getMasterBedsNum());
        singleBedsComboBox.setValue(p.getSingleBedsNum());
        bedroomComboBox.setValue(p.getBedroomsNum());
        bathroomsComboBox.setValue(p.getBathroomsNum());
        
        if (p.getSingle()) {
            singles.setSelected(true);
        }
        if (p.getFamilies()) {
            families.setSelected(true);
        }
        if (p.getPool()) {
            pool.setSelected(true);
        }
        if (p.getPlayground()) {
            playground.setSelected(true);
        }
        if (p.getFootball()) {
            footballCourt.setSelected(true);
        }
        if (p.getGarden()) {
            garden.setSelected(true);
        }
        
        priceSlider.setValue(p.getPrice());
        propertyPrice.setText("Price: " + Integer.toString((int) p.getPrice()) + "SR");

        //////////Action on edit a property page contollers\\\\\\\\\\\\
        edit.setOnAction(e -> {
            
            address.setCity(cities.getValue());
            address.setNeighborhood(neighborField.getText());
            address.setStreet(streetField.getText());
            address.setLocatioLink(locationField.getText());
            address.setZipcode(zipCodeField.getText());
            
            SpaceValue = Double.parseDouble(unitSpaceFiled.getText());
            
            p.setPropertyName(properytNameField.getText());
            p.setPropertyType(types.getValue());
            p.setDescription(descriptionArea.getText());
            p.setZipcode(zipCodeField.getText());
            p.setOutdoorSeatingCapacity(outdoorComboBox.getValue());
            p.setLivingRoomCapacity(livingRoomComboBox.getValue());
            p.setPrice(price);
            p.setUnitSpace(SpaceValue);
            p.setRoomsNum(roomsComboBox.getValue());
            p.setMasterBedsNum(masterBedsComboBox.getValue());
            p.setSingleBedsNum(singleBedsComboBox.getValue());
            p.setBedroomsNum(bedroomComboBox.getValue());
            p.setBathroomsNum(bathroomsComboBox.getValue());
            p.setSingle(singles.isSelected());
            p.setFamilies(families.isSelected());
            p.setPool(pool.isSelected());
            p.setPlayground(playground.isSelected());
            p.setGarden(garden.isSelected());
            p.setFootball(footballCourt.isSelected());
            
            Transaction tx = null;
            session1 = HibernateUtil.getSessionFactory().openSession();
            tx = session1.beginTransaction();
            session1.update(address);
            session1.update(p);
            tx.commit();
            session1.close();
            
            imagep imagep;
            
            if (imageList != null) {
                try {
                    for (int i = 0; i < imageList.size(); i++) {
                        imagep = new imagep();
                        System.out.println("**" + imageList.get(i)); //Just to make sure
                        stream = new FileInputStream(imageList.get(i));
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] buf = new byte[(int) imageList.get(i).length()];
                        stream.read(buf);
                        for (int readNum; (readNum = stream.read(buf)) != -1;) {
                            bos.write(buf, 0, readNum);
                        }
                        convert = bos.toByteArray();
                        imagep.setPropertyID(p.getPropertyId());
                        imagep.setImages(buf);
                        try {
                            session1 = HibernateUtil.getSessionFactory().openSession();
                            tx = session1.beginTransaction();
                            session1.save(imagep);
                            tx.commit();
                        } catch (HibernateException exception) {
                            if (tx != null) {
                                tx.rollback();
                            } else {
                            }
                            throw exception;
                        } finally {
                            session1.close();
                        }
                        stream.close();
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("No file");
            }
            primaryStage.setScene(MyProperties.myProperties(primaryStage));
        }
        );
        
        delete.setOnAction(e -> {
            address.setCity(cities.getValue());
            address.setNeighborhood(neighborField.getText());
            address.setStreet(streetField.getText());
            address.setLocatioLink(locationField.getText());
            address.setZipcode(zipCodeField.getText());
            
            SpaceValue = Double.parseDouble(unitSpaceFiled.getText());
            
            p.setPropertyName(properytNameField.getText());
            p.setPropertyType(types.getValue());
            p.setDescription(descriptionArea.getText());
            p.setZipcode(zipCodeField.getText());
            p.setOutdoorSeatingCapacity(outdoorComboBox.getValue());
            p.setLivingRoomCapacity(livingRoomComboBox.getValue());
            p.setPrice(price);
            p.setUnitSpace(SpaceValue);
            p.setRoomsNum(roomsComboBox.getValue());
            p.setMasterBedsNum(masterBedsComboBox.getValue());
            p.setSingleBedsNum(singleBedsComboBox.getValue());
            p.setBedroomsNum(bedroomComboBox.getValue());
            p.setBathroomsNum(bathroomsComboBox.getValue());
            p.setSingle(singles.isSelected());
            p.setFamilies(families.isSelected());
            p.setPool(pool.isSelected());
            p.setPlayground(playground.isSelected());
            p.setGarden(garden.isSelected());
            p.setFootball(footballCourt.isSelected());
            
            imagep imagep = new imagep();
            if (imageList != null) {
                System.out.println("No images in delete!");
            } else {
                System.out.println("No file");
            }
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            String hqlQuery = "From property where ownerID=" + p.getOwnerID();
            Query query = session.createQuery(hqlQuery);
            List<property> pro = query.list();
            transaction.commit();
            session.close();
            int size = pro.size();
            if (size == 1) {
                
                Session sessionOwner = HibernateUtil.getSessionFactory().openSession();
                Transaction transactionOwner = sessionOwner.beginTransaction();
                String q = "From owner where ownerID=" + "\'" + p.getOwnerID() + "\'";
                Query query1 = sessionOwner.createQuery(q);
                owner owner = (owner) query1.uniqueResult();
                transactionOwner.commit();
                sessionOwner.close();
                
                Session sessionPayment = HibernateUtil.getSessionFactory().openSession();
                Transaction transactionPayment = sessionPayment.beginTransaction();
                String qPayment = "From owner_payment where IBAN=" + "\'" + owner.getIBAN() + "\'";
                Query queryPayment = sessionPayment.createQuery(qPayment);
                owner_payment payment = (owner_payment) queryPayment.uniqueResult();
                transactionPayment.commit();
                sessionPayment.close();
                
                imagep.setPropertyID(p.getPropertyId());
                session1 = HibernateUtil.getSessionFactory().openSession();
                Transaction tx = null;
                tx = session1.beginTransaction();
                session1.delete(imagep);
                session1.delete(p);
                session1.delete(address);
                session1.delete(owner);
                session1.delete(payment);
                tx.commit();
                session1.close();
            } else {
                imagep.setPropertyID(p.getPropertyId());
                session1 = HibernateUtil.getSessionFactory().openSession();
                Transaction tx = null;
                tx = session1.beginTransaction();
                session1.delete(imagep);
                session1.delete(p);
                session1.delete(address);
                tx.commit();
                session1.close();
            }
            primaryStage.setScene(MyProperties.myProperties(primaryStage));
        }
        );
        
        BorderPane rentPropertyBorderPane = new BorderPane();
        BorderPane.setAlignment(rentPropertyText, Pos.CENTER);
        BorderPane.setMargin(rentPropertyText, new Insets(15, 0, 40, 0));
        
        rentPropertyBorderPane.setTop(rentPropertyText);
        rentPropertyBorderPane.setCenter(scrollPaneRent);
        rentPropertyBorderPane.setBottom(navBar(primaryStage));
        
        rentPropertyBorderPane.getStyleClass().add("page");
        rentPropertyBorderPane.setId("rentPropertyBorderPane");
        
        Scene rentScene = new Scene(rentPropertyBorderPane, 375, 612);
        rentScene.getStylesheets().add("style.css");
        
        return rentScene;
        
    }
}
