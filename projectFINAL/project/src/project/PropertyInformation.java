package project;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import static project.Project.navBar;

/**
 *
 * @author Tasneem
 */
public class PropertyInformation {

    private static Image cottage;
    private static ImagePattern pt;
    private static ImageView ivCottage;
    private static int tempSize = 1;
    private static owner CurrentUser;

    private static property getPropertyByID(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String query = "from property where propertyId =" + id;
        Query queryP = session.createQuery(query);
        property p = (property) queryP.uniqueResult();
        tx.commit();
        session.close();

        return p;
    }

    private static property_address getPropertAddressByID(String zipcode) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String query = "from property_address where zipcode =" + "\'" + zipcode + "\'";
        Query querySelect = session.createQuery(query);
        property_address address = (property_address) querySelect.uniqueResult();

        query = "from owner where customerId=" + Project.customer.getCustomerID();
        querySelect = session.createQuery(query);
        CurrentUser = (owner) querySelect.uniqueResult();

        tx.commit();
        session.close();

        return address;
    }

    public static Scene properyInformation(Stage primaryStage, int propertyId) {

        property p = getPropertyByID(propertyId);
        String zipCodeString = p.getZipcode();
        property_address address = getPropertAddressByID(zipCodeString);

        Label pName = new Label(); //property name 
        pName.setText(p.getPropertyName());
        pName.setWrapText(true);
        pName.setTextAlignment(TextAlignment.CENTER);
        pName.setFont(Font.font(30));
        pName.setPrefHeight(80);
        pName.getStyleClass().add("header");

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String hqlSelect = "from imagep where propertyID=" + p.getPropertyId();
        Query querySelect = session.createQuery(hqlSelect);
        List<imagep> images = querySelect.list();
        tx.commit();
        session.close();

        int size = images.size();

        FileOutputStream fos;
        for (int j = 0; j < size; j++) { //number of records 
            try {
                fos = new FileOutputStream("output" + j + ".jpg");
                fos.write(images.get(j).getImages());
                fos.close();
            } catch (Exception e) {

            }
        }

        VBox PropertyPage = new VBox(20);
        PropertyPage.setPadding(new Insets(0, 0, 15, 0));

        Rectangle rounded = new Rectangle();
        rounded.setWidth(300);
        rounded.setHeight(190);
        rounded.setArcHeight(30);
        rounded.setArcWidth(30);

        cottage = new Image("file:output0.jpg");
        ivCottage = new ImageView(cottage);
        pt = new ImagePattern(cottage);
        rounded.setFill(pt);
        ivCottage.setFitHeight(190);
        ivCottage.setPreserveRatio(true);

        ImageView backArrow2 = new ImageView(new Image("back-arrow2.png"));
        backArrow2.setPreserveRatio(true);
        backArrow2.setFitHeight(30);
        Button nextimg = new Button("", backArrow2);
        backArrow2.getStyleClass().add("back-button");

        int tempSize1 = size - 1;
        nextimg.setOnAction(e -> {

            if (tempSize >= tempSize1) {
                tempSize = 0;
            } else {
                tempSize = tempSize + 1;
            }
            cottage = new Image("file:output" + tempSize + ".jpg");
            pt = new ImagePattern(cottage);
            rounded.setFill(pt);

        });

        ImageView backArrow3 = new ImageView(new Image("back-arrow.png"));
        backArrow3.setPreserveRatio(true);
        backArrow3.setFitHeight(30);
        Button previmg = new Button("", backArrow3);
        backArrow3.getStyleClass().add("back-button");

        previmg.setOnAction(e -> {

            if (tempSize == tempSize1) {
                tempSize = tempSize - 1;
            } else if (tempSize == 0 || tempSize == -1) {
                tempSize = tempSize1;
            }

            cottage = new Image("file:output" + tempSize + ".jpg");
            pt = new ImagePattern(cottage);
            rounded.setFill(pt);

        });

        Label addresslbl = new Label("City, Neighborhood: ");
        addresslbl.setFont(Font.font(16));

        String cityy = address.getCity();
        String nb = address.getNeighborhood();
        Label address2 = new Label(cityy + ", " + nb); //1

        Label propertyTypeLbl = new Label("Property Type: ");
        propertyTypeLbl.setFont(Font.font(16));

        String propertyTypeString = p.getPropertyType();
        Label propertyTypee = new Label(propertyTypeString);

        String unitSpaceDouble = Double.toString(p.getUnitSpace());

        Label unitSpacelbl = new Label("Unit Space:");
        Label uslbl = new Label(unitSpaceDouble);

        Label featuresLbl = new Label("Features: ");
        featuresLbl.setFont(Font.font(16));

        String featuresString = "";
        if (p.getPool()) {
            featuresString += "Pool\t";
        }
        if (p.getPlayground()) {
            featuresString += "Playground\n";
        }
        if (p.getGarden()) {
            featuresString += "Garden\t";
        }
        if (p.getFootball()) {
            featuresString += "Football court\n";
        }

        Label feat = new Label(featuresString);
        feat.setFont(Font.font(16));

        Label descriptionLbl = new Label("Description:");
        descriptionLbl.setFont(Font.font(16));

        String desString = p.getDescription();

        TextArea description = new TextArea();
        description.setFont(Font.font(14));
        description.setWrapText(true);
        description.setEditable(false);
        description.setText(desString);

        description.setPrefHeight(150);  //sets height of the TextArea to 400 pixels 
        description.setPrefWidth(315);

        //order HERE 
        Label checkInlbl = new Label("Check In: ");
        checkInlbl.setFont(Font.font(16));
        DatePicker checkIn = new DatePicker();
        checkIn.setValue(LocalDate.now());

        Label Durationlbl = new Label("Duration: ");
        Durationlbl.setFont(Font.font(16));
        ComboBox<String> Duration = new ComboBox();
        Duration.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        Duration.getSelectionModel().selectFirst();

        Label categorylbl = new Label("Category: ");
        categorylbl.setFont(Font.font(16));

        String categoryString = " ";
        if (p.getSingle()) {
            categoryString += "Singles\n";
        }
        if (p.getFamilies()) {
            categoryString += "Families\n";
        }
        Label categoryy = new Label(categoryString);

        Label capacitylbl = new Label("Living Room Capacity:");
        capacitylbl.setFont(Font.font(16));

        String capacityString = p.getLivingRoomCapacity();
        Label capacity = new Label(capacityString);
        capacity.setFont(Font.font(16));

        Label outCapacitylbl = new Label("Out Door Capacity:");
        outCapacitylbl.setFont(Font.font(16));

        String outString = p.getOutdoorSeatingCapacity();
        Label outCapacity = new Label(outString);
        outCapacity.setFont(Font.font(16));

        Label bedRoomlbl = new Label("Number Of Bedrooms: ");
        bedRoomlbl.setFont(Font.font(16));

        String bedString = p.getBedroomsNum();
        Label bedRoom = new Label(bedString);
        bedRoom.setFont(Font.font(16));

        Label singleBedslbl = new Label("Number Of Single Beds: ");
        singleBedslbl.setFont(Font.font(16));

        String singleBedsString = p.getSingleBedsNum();
        Label singleBeds2 = new Label(singleBedsString);
        singleBeds2.setFont(Font.font(16));

        Label mastersBedslbl = new Label("Number Of Master Beds: ");
        mastersBedslbl.setFont(Font.font(16));

        String masterString = p.getMasterBedsNum();
        Label mastersBeds = new Label(masterString);
        mastersBeds.setFont(Font.font(16));

        Label numOfRoomslbl = new Label("Number Of Rooms: ");
        numOfRoomslbl.setFont(Font.font(16));

        String roomsString = p.getRoomsNum();
        Label numOfRooms = new Label(roomsString);
        numOfRooms.setFont(Font.font(16));

        Label bathroomslbl = new Label("Number Of Bathrooms: ");
        bathroomslbl.setFont(Font.font(16));

        String bathString = p.getBathroomsNum();
        Label bathrooms2 = new Label(bathString);
        bathrooms2.setFont(Font.font(16));

        //calculated HERE
        Label pricelbl = new Label("Price : ");
        pricelbl.setFont(Font.font(16));

        Label price2 = new Label(Integer.toString((int) p.getPrice()));
        price2.setFont(Font.font(16));
        Duration.setOnAction(e -> {
            int temp = Integer.parseInt(Duration.getValue());
            double calculatePrice = (temp) * (p.getPrice());
            price2.setText(Integer.toString((int) calculatePrice));
        });

        GridPane propertyPanej = new GridPane();
        propertyPanej.setVgap(30);

        propertyPanej.add(addresslbl, 0, 0); //label
        propertyPanej.add(address2, 1, 0);
        propertyPanej.add(propertyTypeLbl, 0, 1); //label
        propertyPanej.add(propertyTypee, 1, 1);
        propertyPanej.add(unitSpacelbl, 0, 2);// Label HERE
        propertyPanej.add(uslbl, 1, 2);
        propertyPanej.add(featuresLbl, 0, 3);// Label
        propertyPanej.add(feat, 1, 3);
        propertyPanej.setPadding(new Insets(0, 5, 0, 35));

        GridPane propertyPane2 = new GridPane();
        propertyPane2.setPadding(new Insets(0, 5, 0, 35));
        propertyPane2.setVgap(10);
        //new grid pane for description 
        //decription text area is big comparing to the rest of elements 
        //and will need extra width for the rest of elements to appear 
        propertyPane2.add(descriptionLbl, 0, 0);//Label
        propertyPane2.add(description, 0, 1);

        GridPane propertyPane3 = new GridPane();
        propertyPane3.setVgap(30);
        propertyPane3.setPadding(new Insets(0, 5, 15, 35));
        propertyPane3.getColumnConstraints().add(new ColumnConstraints(200));
        propertyPane3.add(checkInlbl, 0, 0);//Label
        propertyPane3.add(checkIn, 1, 0);
        propertyPane3.add(Durationlbl, 0, 1);//Label
        propertyPane3.add(Duration, 1, 1);
        propertyPane3.add(categorylbl, 0, 2);
        propertyPane3.add(categoryy, 1, 2);
        propertyPane3.add(capacitylbl, 0, 3);//Label
        propertyPane3.add(capacity, 1, 3);
        propertyPane3.add(outCapacitylbl, 0, 4);//Label
        propertyPane3.add(outCapacity, 1, 4);
        propertyPane3.add(bedRoomlbl, 0, 5);//Label
        propertyPane3.add(bedRoom, 1, 5);
        propertyPane3.add(singleBedslbl, 0, 6);//Label
        propertyPane3.add(singleBeds2, 1, 6);
        propertyPane3.add(mastersBedslbl, 0, 7);//Label
        propertyPane3.add(mastersBeds, 1, 7);
        propertyPane3.add(numOfRoomslbl, 0, 8);//Label 
        propertyPane3.add(numOfRooms, 1, 8);
        propertyPane3.add(bathroomslbl, 0, 9);//Label
        propertyPane3.add(bathrooms2, 1, 9);
        propertyPane3.add(pricelbl, 0, 10);//Label
        propertyPane3.add(price2, 1, 10);

        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
        String hqlQuery = "From Order where customerID=" + Project.customer.getCustomerID();
        Query query = session.createQuery(hqlQuery);
        Order preOrder = (Order) query.setMaxResults(1).uniqueResult();
        tx.commit();
        session.close();

        Alert alert = new Alert(Alert.AlertType.NONE);
        Button pay = new Button("Proceed To Pay");
        pay.setPrefHeight(40);
        pay.setPrefWidth(200);
        pay.setOnAction((e) -> {
            if (preOrder == null) { // if this is customer's first order in the app
                boolean reserved = false, invalid = false;

                Session session5 = HibernateUtil.getSessionFactory().openSession();
                Transaction transaction5 = session5.beginTransaction();
                Query query1 = session5.createQuery("from Order where propertyId=" + p.getPropertyId());
                List<Order> orders = query1.list();
                transaction5.commit();
                session5.close();

                //check if it is preserved
                for (Order order : orders) {

                    LocalDate preservedDate = LocalDate.parse(order.getCheckIn());
                    if (order.getCheckIn().equals(checkIn.getValue().toString())
                            | preservedDate.plus(order.getDuration(), ChronoUnit.DAYS).isAfter(checkIn.getValue())) {
                        reserved = true;
                        break;
                    }
                }
                //check if it the date is valid
                for (Order order : orders) {
                    if (checkIn.getValue().isBefore(LocalDate.now())) {
                        invalid = true;
                        break;
                    }
                }

                //if there is nothing wrong
                if (reserved == false && invalid == false) {
                    primaryStage.setScene(Payment.payment(primaryStage, propertyId, p, checkIn.getValue(), Duration.getValue(), price2.getText()));
                } else if (reserved == true) {
                    String alertText = "The property " + p.getPropertyName() + " is preserved on the choosen date";
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText(alertText);
                    alert.show();
                } else {//if the date is invalid
                    String alertText = "Invalid date";
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText(alertText);
                    alert.show();
                }
            } else {
                try {
                    Session session5 = HibernateUtil.getSessionFactory().openSession();
                    Transaction transaction5 = session5.beginTransaction();
                    Query query1 = session5.createQuery("from Order where propertyId=" + p.getPropertyId());
                    List<Order> orders = query1.list();
                    transaction5.commit();
                    session5.close();
                    boolean reserved = false, invalid = false;

                    //check if it is preserved
                    for (Order order : orders) {

                        LocalDate preservedDate = LocalDate.parse(order.getCheckIn());
                        if (order.getCheckIn().equals(checkIn.getValue().toString())
                                | preservedDate.plus(order.getDuration(), ChronoUnit.DAYS).isAfter(checkIn.getValue())) {
                            reserved = true;
                            break;
                        }
                    }
                    //check if it the date is valid
                    for (Order order : orders) {
                        if (checkIn.getValue().isBefore(LocalDate.now())) {
                            invalid = true;
                            break;
                        }
                    }

                    //if there is nothing wrong
                    if (reserved == false && invalid == false) {
                        LocalDate now = java.time.LocalDate.now();
                        Order order = new Order();
                        order.setOrderDate(now.toString());
                        order.setCheckIn(checkIn.getValue().toString());
                        order.setCardNumber(preOrder.getCardNumber());
                        order.setPropertyId(propertyId);
                        order.setCustomerID(Project.customer.getCustomerID());
                        order.setOwnerID(p.getOwnerID());
                        order.setPrice(Double.valueOf(price2.getText()));
                        order.setDuration(Integer.valueOf(Duration.getValue()));

                        Session session1 = HibernateUtil.getSessionFactory().openSession();
                        Transaction Trans = null;
                        Trans = session1.beginTransaction();
                        session1.save(order);
                        Trans.commit();
                        session1.close();

                        String alertText = "Thank You for Booking " + p.getPropertyName();
                        alert.setAlertType(Alert.AlertType.CONFIRMATION);
                        alert.setContentText(alertText);
                        alert.show();

                        primaryStage.setScene(MyProfile.myProfile(primaryStage));

                    } else if (reserved == true) {
                        String alertText = "The property " + p.getPropertyName() + " is preserved on the choosen date";
                        alert.setAlertType(Alert.AlertType.WARNING);
                        alert.setContentText(alertText);
                        alert.show();
                    } else {//if the date is invalid
                        String alertText = "Invalid date";
                        alert.setAlertType(Alert.AlertType.WARNING);
                        alert.setContentText(alertText);
                        alert.show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Something Went Wrong");
                    alert.show();
                }
            }
        });

        if (CurrentUser != null && p.getOwnerID() == CurrentUser.getOwnerID()) {
            pay.setDisable(true);
        }

        ImageView backArrow = new ImageView(new Image("back-arrow.png"));
        backArrow.setPreserveRatio(true);
        backArrow.setFitHeight(30);
        Button back = new Button("", backArrow);
        back.setTextAlignment(TextAlignment.CENTER);
        back.getStyleClass().add("back-button");
        back.setOnAction((e) -> {
            primaryStage.setScene(HomePage.homePage(primaryStage));
        });

        HBox imageArrows = new HBox(30);
        imageArrows.getChildren().addAll(previmg, nextimg);
        imageArrows.setAlignment(Pos.CENTER);

        ScrollPane sp = new ScrollPane(PropertyPage);
        sp.setFitToWidth(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        PropertyPage.getChildren().addAll(rounded, imageArrows, propertyPanej, propertyPane2, propertyPane3, pay);
        PropertyPage.setAlignment(Pos.CENTER);

        VBox navBar = new VBox();
        navBar.getChildren().addAll(navBar(primaryStage));

        BorderPane top = new BorderPane();
        top.setCenter(pName);
        top.setLeft(back);
        pName.setWrapText(true);
        BorderPane.setAlignment(pName, Pos.CENTER);
        BorderPane.setMargin(pName, new Insets(0, 15, 0, 5));
        BorderPane.setMargin(back, new Insets(0, 0, 0, 10));
        BorderPane.setAlignment(back, Pos.CENTER);
        BorderPane.setMargin(top, new Insets(25, 0, 0, 0));

        BorderPane paneProperty = new BorderPane();
        paneProperty.setTop(top);
        paneProperty.setCenter(sp);
        paneProperty.setBottom(navBar);

        paneProperty.getStyleClass().add("page");

        Scene propertyScene = new Scene(paneProperty, 375, 612);
        propertyScene.getStylesheets().add("style.css");

        return propertyScene;
    }

}
