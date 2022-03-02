package project;

import java.util.List;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import static project.Project.navBar;

/**
 *
 * @author HU6EM001
 */
public class MyProfile {

    static TableView<TableViewBookings> bookTable;  // create table for booking
    static TableView<TableViewMyPro> myProBookTable; // create table for my property booking
    static Session session = HibernateUtil.getSessionFactory().openSession();
    static Transaction tx = session.beginTransaction();
    //static customer customer;

    // get data for the profile page (booking)
    static public ObservableList<TableViewBookings> getBooking(customer customer) {
        ObservableList<TableViewBookings> book = FXCollections.observableArrayList();
        // create an observibale list to store the collected data in it
        // select property list 
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
        List<Order> pList = null; // property_owner list
        String query_P = "from Order";
        Query queryP = session.createQuery(query_P);
        pList = queryP.list();
        tx.commit();
        session.close();

        for (Order p : pList) {
            if (p.getCustomerID() == customer.getCustomerID()) {
                // if the custemer id in the order table match the user id, then go to the property table and take its name
                session = HibernateUtil.getSessionFactory().openSession();
                tx = session.beginTransaction();
                String query_O = "from property where propertyId =" + "\'" + p.getPropertyId() + "\'";
                Query queryO = session.createQuery(query_O);
                property proInfo = (property) queryO.uniqueResult();
                tx.commit();
                session.close();
                // create a "TableViewBookings" object with the information pulled from the data base then add the object to the observable list
                book.add(new TableViewBookings(proInfo.getPropertyName(), proInfo.getPropertyType(), p.getCheckIn(), p.getDuration(), (int) p.getPrice()));
            }
        }
        return book; // return the observable list
    }

    static public ObservableList<TableViewMyPro> getMyProBooking(owner ownerInfo) {
        ObservableList<TableViewMyPro> book = FXCollections.observableArrayList();
        // create an observibale list to store the collected data in it
        // select property list 
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
        List<Order> pList = null; // property_owner list
        String query_P = "from Order";
        Query queryP = session.createQuery(query_P);
        pList = queryP.list();
        tx.commit();
        session.close();

        for (Order p : pList) {
            if (p.getOwnerID() == ownerInfo.getOwnerID()) {
                // if the owner id in the order table match the user's owner id then take in formation from the property table and customer table
                session = HibernateUtil.getSessionFactory().openSession();
                tx = session.beginTransaction();
                String query_O = "from property where propertyId =" + "\'" + p.getPropertyId() + "\'";
                Query queryO = session.createQuery(query_O);
                property proInfo = (property) queryO.uniqueResult();

                String query_C = "from customer where customerID =" + "\'" + p.getCustomerID() + "\'";
                Query queryC = session.createQuery(query_C);
                customer custInfo = (customer) queryC.uniqueResult();

                tx.commit();
                session.close();
                // create a "TableViewMyPro" object with the information pulled from the data base then add the object to the observable list
                book.add(new TableViewMyPro(proInfo.getPropertyName(), p.getOrderDate(), p.getCheckIn(), p.getDuration(), custInfo.getFullName()));
            }
        }
        return book;
    }

    public static Scene myProfile(Stage primaryStage) { //Project.customer
        // SEARCH FOR THE USER INFORMATION
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
        String query_O = "from customer where email =" + "\'" + Project.customer.getEmail() + "\'";
        Query queryO = session.createQuery(query_O);
        Project.customer = (customer) queryO.uniqueResult();
        tx.commit();
        session.close();
        // CHECK IF THE USER IS ALSO AN "OWNER"
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
        String q = "from owner where customerId =" + "\'" + Project.customer.getCustomerID() + "\'";
        Query qo = session.createQuery(q);
        owner ownerInfo = (owner) qo.uniqueResult();
        tx.commit();
        session.close();

        BorderPane pane = new BorderPane();

        Label labelMyProfile = new Label("My Profile");
        labelMyProfile.getStyleClass().add("header");
        Font font = Font.font(45);
        labelMyProfile.setFont(font);
        labelMyProfile.setTextFill(Color.BLACK);

        Image imageExit = new Image("exit.png");
        ImageView imgViewExit = new ImageView(imageExit);
        imgViewExit.setFitHeight(25);
        imgViewExit.setFitWidth(25);
        imgViewExit.setPreserveRatio(true);

        Button buttonExit = new Button();
        buttonExit.setTranslateY(15);
        buttonExit.setGraphic(imgViewExit);
        buttonExit.setId("exit");
        //--- Action on the buttons ---//
        // --> exit button Action 
        buttonExit.setOnAction(e -> {
            primaryStage.setScene(Start.start(primaryStage));
        });

        Image imgDocument = new Image("Generic-Document-icon.png");
        ImageView imgViewDocument = new ImageView(imgDocument);
        imgViewDocument.setFitHeight(20);
        imgViewDocument.setFitWidth(20);
        imgViewDocument.setPreserveRatio(true);

        //Creating a Label
        Label labeUserName = new Label(Project.customer.getFullName());
        Font font2 = Font.font(20);
        labeUserName.setFont(font2);
        labeUserName.getStyleClass().add("header");
//        labeUserName.setGraphic(imgViewDocument);

        Image imageFile = new Image("dd.png");
        ImageView imageViewFile = new ImageView(imageFile);
        imageViewFile.setFitHeight(20);
        imageViewFile.setFitWidth(20);
        imageViewFile.setPreserveRatio(true);

        Label labelBookings = new Label("Bookings:");
        Font font3 = Font.font("", FontWeight.BOLD, 20);
        labelBookings.setFont(font3);
        labelBookings.setTextFill(Color.BLACK);
        labelBookings.setGraphic(imageViewFile);

        VBox vBoxL = new VBox();
//        vBoxL.getChildren().add(labeUserName);
//        VBox.setMargin(labeUserName, new Insets(10, 5, 50, 50));

        vBoxL.getChildren().add(labelBookings);
        VBox.setMargin(labelBookings, new Insets(0, 5, 5, 0));

        // table view (bookings) //////////////////////////////////////////////////////
        // crate columns 
        TableColumn<TableViewBookings, String> proNameColumn = new TableColumn<>("Name"); // property name column
        proNameColumn.setMinWidth(3); // set the width of the column
        proNameColumn.setStyle("-fx-alignment: CENTER;"); // center text in the column
        proNameColumn.setCellValueFactory(new PropertyValueFactory<>("proprtyName")); // set the connected variable name form the class

        TableColumn<TableViewBookings, String> proTypeColumn = new TableColumn<>("Type"); // property type column
        proTypeColumn.setMinWidth(3);
        proTypeColumn.setCellValueFactory(new PropertyValueFactory<>("PropertyType"));

        TableColumn<TableViewBookings, String> checkColumn = new TableColumn<>("CheckIN"); // order check in column
        checkColumn.setMinWidth(50);
        checkColumn.setCellValueFactory(new PropertyValueFactory<>("CheckIn"));

        TableColumn<TableViewBookings, String> durColumn = new TableColumn<>("DUR."); // order duration column
        durColumn.setMinWidth(30);
        durColumn.setStyle("-fx-alignment: CENTER;");
        durColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        TableColumn<TableViewBookings, Integer> priceColumn = new TableColumn<>("price"); // order price column
        priceColumn.setMinWidth(25); // set the width of the column
        priceColumn.setStyle("-fx-alignment: CENTER;");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        if (getBooking(Project.customer).isEmpty()) { // if the returnd list is empty, then user doesn't have bookings
            Text LableIfEmpty = new Text("YOU DON'T HAVE PROPERTY ADDED");
            LableIfEmpty.setFont(Font.font("", FontWeight.NORMAL, 15));
            LableIfEmpty.setFill(Color.ROSYBROWN);
            vBoxL.getChildren().add(LableIfEmpty);

        } else { // if their is objects in the list, then the user hase bookins
            bookTable = new TableView<>(); // create table view
            bookTable.setItems(getBooking(Project.customer)); // call the method 'getBooking' bring the user property
            bookTable.getColumns().addAll(proNameColumn, proTypeColumn, checkColumn, durColumn, priceColumn); // add the columns to the table view
            bookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // remove any Extra column
            vBoxL.getChildren().add(bookTable); // booking
        }

        Image imageHome = new Image("home-icon.png");
        ImageView imageViewHome = new ImageView(imageHome);
        imageViewHome.setFitHeight(25);
        imageViewHome.setFitWidth(25);
        imageViewHome.setPreserveRatio(true);

        //Creating a Label
        Label labeMyProBook = new Label("My Property Bookings:");
        Font font4 = Font.font("", FontWeight.BOLD, 20);
        labeMyProBook.setFont(font4);
        labeMyProBook.setGraphic(imageViewHome);

        VBox vBox2 = new VBox();
        vBox2.getChildren().add(labeMyProBook);
        VBox.setMargin(labeMyProBook, new Insets(0, 5, 5, 0));

        // table view (my prperty bookings) //////////////////////////////////////////////////////$$$$$$$$$$$$$$$$$$$
        // crate columns 
        TableColumn<TableViewMyPro, Integer> proNameColumn2 = new TableColumn<>("Name"); // property name column
        proNameColumn2.setMinWidth(3); // set the width of the column
        proNameColumn2.setStyle("-fx-alignment: CENTER;"); // ceter text in column
        proNameColumn2.setCellValueFactory(new PropertyValueFactory<>("propertyName")); // set the connected variable name form the class

        TableColumn<TableViewMyPro, String> oDateColumn2 = new TableColumn<>("Order Date"); // order date column
        oDateColumn2.setMinWidth(50);
        oDateColumn2.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        TableColumn<TableViewMyPro, String> checkColumn2 = new TableColumn<>("CheckIN"); // order check in column
        checkColumn2.setMinWidth(50);
        checkColumn2.setCellValueFactory(new PropertyValueFactory<>("CheckIn"));

        TableColumn<TableViewMyPro, String> durColumn2 = new TableColumn<>("DUR."); // order duration column
        durColumn2.setMinWidth(3);
        durColumn2.setStyle("-fx-alignment: CENTER;");
        durColumn2.setCellValueFactory(new PropertyValueFactory<>("duration"));

        TableColumn<TableViewMyPro, Integer> custNameColumn2 = new TableColumn<>("Customer"); // customer name column
        custNameColumn2.setMinWidth(30); // set the width of the column
        custNameColumn2.setStyle("-fx-alignment: CENTER;");
        custNameColumn2.setCellValueFactory(new PropertyValueFactory<>("CustomerFullName"));

        if (ownerInfo == null || getMyProBooking(ownerInfo).isEmpty()) { // if ownerifo object is empty then the user is not owner ,, or if the list is empty then the user doesn't have any orders
            Text LableIfEmpty = new Text("YOU DON'T HAVE PROPERTY ADDED");
            LableIfEmpty.setFont(Font.font("", FontWeight.NORMAL, 15));
            LableIfEmpty.setFill(Color.ROSYBROWN);
            vBox2.getChildren().add(LableIfEmpty);

        } else {
            myProBookTable = new TableView<>(); // create table view 
            myProBookTable.setItems(getMyProBooking(ownerInfo)); // call the method 'getBooking' bring the user property
            myProBookTable.getColumns().addAll(proNameColumn2, oDateColumn2, checkColumn2, durColumn2, custNameColumn2); // add columns to table view
            myProBookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // remove any Extra column
            vBox2.getChildren().add(myProBookTable); // booking
        }

        
        BorderPane topRen = new BorderPane();
        topRen.setCenter(labelMyProfile);
        topRen.setRight(buttonExit);
        topRen.setBottom(labeUserName);
        topRen.setPadding(new Insets(0,10,0,20));

        VBox mainBox = new VBox(30);
        mainBox.setPadding(new Insets(50,20,0,20));
        mainBox.getChildren().addAll(vBoxL, vBox2);
        mainBox.requestFocus();
        ScrollPane myProfileScrollPane = new ScrollPane(mainBox); // add scroll pane for main box that holds the table view
        myProfileScrollPane.setFitToWidth(true);
        myProfileScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        myProfileScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // set the panes postion the border pane
        pane.setTop(topRen);
        BorderPane.setMargin(topRen, new Insets(20, 0, 10, 0));
        pane.setCenter(myProfileScrollPane);
        pane.setBottom(navBar(primaryStage));
        pane.getStyleClass().add("page");

        //Setting the stage
        Scene myprofile = new Scene(pane, 375, 612);
        myprofile.getStylesheets().add("style.css");
        return myprofile;
    }

}
