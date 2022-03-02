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
import javafx.scene.control.Alert;
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
 * @author HU6EM001
 */
public class RentAProperty {

    public static void storeImages(property propertyToRent) {
        Transaction tx = null;
        try {
            for (int i = 0; i < imageList.size(); i++) {
                imagep imagep = new imagep();
                System.out.println("****" + imageList.get(i));
                stream = new FileInputStream(imageList.get(i));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[(int) imageList.get(i).length()];

                stream.read(buf);

                for (int readNum; (readNum = stream.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum);
                }
                convert = bos.toByteArray();
                imagep.setPropertyID(propertyToRent.getPropertyId());
                imagep.setImages(buf);
                imagep.setPropertyID(propertyToRent.getPropertyId());
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

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(RentAProperty.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

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

    public static Scene rentAProperty(Stage primaryStage) {

        GridPane rentPane = new GridPane();
        rentPane.setPadding(new Insets(5, 5, 5, 15));
        rentPane.setHgap(5);
        rentPane.setVgap(15);
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
        TextField unitSpaceField = new TextField();

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
        /*//Image view for the selected images
        imageViewFile = new ImageView();
        imageViewFile.setFitHeight(90);
        imageViewFile.setPreserveRatio(true);*/

        VBox show = new VBox(); //To show selected images in page
        //Uplaod Image Action
        imageButton.setOnAction(e -> {

            fileChooser.setTitle("Upload An Image");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")/*+ "\\Pictures\\"*/)); //Goes to pictures folder directly
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")); //Only Image
            imageList = fileChooser.showOpenMultipleDialog(primaryStage); //List for selected images
            if (imageList != null) {
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
            } else {
                System.out.println("file is Invalid");
            }
        });

        Button continueButton = new Button("Continue");

        VBox submitBox = new VBox();
        submitBox.getChildren().add(continueButton);
        submitBox.setAlignment(Pos.CENTER);
        submitBox.setPadding(new Insets(5, 0, 5, 0));

        GridPane propertyPane = new GridPane();
        propertyPane.setPadding(new Insets(15, 5, 5, 15));
        propertyPane.setHgap(5);
        propertyPane.setVgap(10);
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
        propertyPane.add(unitSpaceField, 1, 7);

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
        centerj.setPrefHeight(1200);
        centerj.setAlignment(Pos.TOP_CENTER);
        centerj.getChildren().add(rentBox);

        ScrollPane scrollPaneRent = new ScrollPane();
        scrollPaneRent.setFitToWidth(true);
        scrollPaneRent.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPaneRent.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollPaneRent.setPadding(new Insets(0, 0, 0, 10));
        scrollPaneRent.setContent(centerj);
        scrollPaneRent.pannableProperty().set(true);
        scrollPaneRent.fitToWidthProperty().set(true);

        Text rentPropertyText = new Text("Rent A Property");
        rentPropertyText.setFont(Font.font(48));
        rentPropertyText.getStyleClass().add("header");

        //////////Action on rent a property page contollers\\\\\\\\\\\\
        Alert alert = new Alert(Alert.AlertType.NONE);
        //////////Action on rent a property page contollers\\\\\\\\\\\\
        continueButton.setOnAction(e -> {
            boolean neighbortf = neighborField.getText().isEmpty();
            boolean streettf = streetField.getText().isEmpty();
            boolean locationtf = locationField.getText().isEmpty();
            boolean zctf = zipCodeField.getText().isEmpty();
            boolean propertytf = properytNameField.getText().isEmpty();
            boolean descriptiontf = descriptionArea.getText().isEmpty();

            if (neighbortf || streettf || locationtf || zctf || propertytf || descriptiontf || imageList == null) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Fill in all the Fields please");
                alert.show();
            } else {
                if (Validation.vaildationZipcode(zipCodeField.getText())) {
                    try {
                        property_address address = new property_address();
                        address.setCity(cities.getValue());
                        address.setNeighborhood(neighborField.getText());
                        address.setStreet(streetField.getText());
                        address.setLocatioLink(locationField.getText());
                        address.setZipcode(zipCodeField.getText());

                        SpaceValue = Double.parseDouble(unitSpaceField.getText());

                        property propertyToRent = new property();
                        propertyToRent.setPropertyName(properytNameField.getText());
                        propertyToRent.setPropertyType(types.getValue());
                        propertyToRent.setDescription(descriptionArea.getText());
                        propertyToRent.setZipcode(zipCodeField.getText());
                        propertyToRent.setOutdoorSeatingCapacity(outdoorComboBox.getValue());
                        propertyToRent.setLivingRoomCapacity(livingRoomComboBox.getValue());
                        propertyToRent.setPrice(price);
                        propertyToRent.setUnitSpace(SpaceValue);
                        propertyToRent.setRoomsNum(roomsComboBox.getValue());
                        propertyToRent.setMasterBedsNum(masterBedsComboBox.getValue());
                        propertyToRent.setSingleBedsNum(singleBedsComboBox.getValue());
                        propertyToRent.setBedroomsNum(bedroomComboBox.getValue());
                        propertyToRent.setBathroomsNum(bathroomsComboBox.getValue());
                        propertyToRent.setSingle(singles.isSelected());
                        propertyToRent.setFamilies(families.isSelected());
                        propertyToRent.setPool(pool.isSelected());
                        propertyToRent.setPlayground(playground.isSelected());
                        propertyToRent.setGarden(garden.isSelected());
                        propertyToRent.setFootball(footballCourt.isSelected());

                        Session session = HibernateUtil.getSessionFactory().openSession();
                        Transaction transaction = session.beginTransaction();
                        String hqlQuery = "From owner where customerId=" + Project.customer.getCustomerID();
                        Query query = session.createQuery(hqlQuery);
                        owner currentOwner = (owner) query.uniqueResult();
                        transaction.commit();
                        session.close();

                        if (currentOwner != null) {

                            session = HibernateUtil.getSessionFactory().openSession();
                            transaction = session.beginTransaction();

                            session.save(address);

                            propertyToRent.setOwnerID(currentOwner.getOwnerID());

                            session.save(propertyToRent);

                            transaction.commit();
                            session.close();

                            RentAProperty.storeImages(propertyToRent);

                            primaryStage.setScene(MyProperties.myProperties(primaryStage));
                        } else {
                            primaryStage.setScene(OwnerPaymentInformation.ownerPaymentInformation(primaryStage, propertyToRent, address));
                        }

                    } catch (Exception ex) {
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setContentText("Please Enter The Correct Information");
                        alert.show();
                    }
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Zip Code Must Be In 5 Numbers Only");
                    alert.show();
                }
            }
        });

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
