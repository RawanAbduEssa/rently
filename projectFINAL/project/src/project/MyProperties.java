package project;

import java.io.FileOutputStream;
import java.sql.Blob;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author HU6EM001
 */
public class MyProperties {

    public static Scene myProperties(Stage primaryStage) {
        BorderPane myPropertiesBorderPanePane = new BorderPane();

        Text myPropertiesText = new Text("My Properties");
        myPropertiesText.setFont(Font.font(50));
        myPropertiesText.getStyleClass().add("header");
        BorderPane.setAlignment(myPropertiesText, Pos.CENTER);
        BorderPane.setMargin(myPropertiesText, new Insets(15, 0, 27.5, 0));

        myPropertiesBorderPanePane.setTop(myPropertiesText);

        VBox myPropertiesCenter = new VBox(20);

        Session session = HibernateUtil.getSessionFactory().openSession();
        String hqlSelect = "from owner where customerId =" + Project.customer.getCustomerID();
        Query querySelect = session.createQuery(hqlSelect);
        owner owner = (owner) querySelect.uniqueResult();
        session.close();

        Button createNewProperty = new Button("Create new property");
        createNewProperty.setOnAction(e -> {
            primaryStage.setScene(RentAProperty.rentAProperty(primaryStage));
        });

        if (owner == null) {

            ImageView notFound = new ImageView(new Image("no-properties2.png"));
            notFound.setPreserveRatio(true);
            notFound.setFitWidth(190);

            //Animation
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.seconds(3));
            t.setToX(20);
            t.setToY(20);
            t.setCycleCount(Animation.INDEFINITE);
            t.setAutoReverse(true);
            t.setNode(notFound);
            t.play();

            Label noProperty = new Label("No registered properties", notFound);
            noProperty.setContentDisplay(ContentDisplay.BOTTOM);
            noProperty.setFont(Font.font(20));
            noProperty.setStyle("-fx-text-fill:#BE8779;");

            myPropertiesCenter.setAlignment(Pos.CENTER);
            myPropertiesCenter.setPadding(new Insets(110, 10, 50, 10));
            myPropertiesCenter.getChildren().addAll(noProperty, createNewProperty);

        } else {
            myPropertiesCenter.getChildren().removeAll(myPropertiesCenter.getChildren());

            myPropertiesCenter.setAlignment(Pos.TOP_CENTER);

            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            String query = "from property where ownerId =" + owner.getOwnerID();
            Query queryP = session.createQuery(query);
            List<property> myPropertiesList = queryP.list();
            tx.commit();
            session.close();

            for (property property : myPropertiesList) {

                HBox propertyHBox = new HBox(10);
                propertyHBox.setPadding(new Insets(10));
                propertyHBox.setId("my-property");
                VBox propertyVBox = new VBox(10);

                session = HibernateUtil.getSessionFactory().openSession();
                tx = session.beginTransaction();
                hqlSelect = "from imagep where propertyID=" + property.getPropertyId();
                querySelect = session.createQuery(hqlSelect);
                List<imagep> images = querySelect.list();
                tx.commit();
                session.close();
                FileOutputStream fos;
                for (int j = 0; j < images.size(); j++) { //number of records 
                    try {
                        fos = new FileOutputStream("output" + j + ".jpg");
                        fos.write(images.get(j).getImages());
                        fos.close();
                    } catch (Exception e) {

                    }
                }

                Rectangle imageContainer = new Rectangle(130, 100);
                ImagePattern myPropertyImagePattern = new ImagePattern(new Image("file:output0.jpg"));
                imageContainer.setFill(myPropertyImagePattern);
                imageContainer.setArcHeight(30);
                imageContainer.setArcWidth(30);

                Label myPropertyName = new Label(property.getPropertyName());
                myPropertyName.setFont(Font.font(18));
                myPropertyName.setWrapText(true);
                Label myPropertyPrice = new Label(property.getPrice() + " SR");
                myPropertyPrice.setFont(Font.font(13));

                ImageView editIcon = new ImageView(new Image("edit.png"));
                editIcon.setPreserveRatio(true);
                editIcon.setFitHeight(20);

                Button edit = new Button("Edit", editIcon);
                edit.setId("edit-btn");

                edit.setOnAction(e -> {
                    primaryStage.setScene(EditAProperty.editAProperty(primaryStage, property.getPropertyId()));
                });

                propertyVBox.getChildren().addAll(myPropertyName, myPropertyPrice, edit);
                propertyHBox.getChildren().addAll(imageContainer, propertyVBox);

                myPropertiesCenter.getChildren().add(propertyHBox);
            }
            myPropertiesCenter.getChildren().add(createNewProperty);
            myPropertiesCenter.setPadding(new Insets(20, 10, myPropertiesList.size() * 20, 10));
        }
        ScrollPane myPropertiesScrollPane = new ScrollPane(myPropertiesCenter);
        myPropertiesScrollPane.setFitToWidth(true);
        myPropertiesScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        myPropertiesScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        myPropertiesBorderPanePane.setCenter(myPropertiesScrollPane);
        myPropertiesBorderPanePane.setBottom(Project.navBar(primaryStage));
        myPropertiesBorderPanePane.getStyleClass().add("page");

        Scene rentScene1 = new Scene(myPropertiesBorderPanePane, 375, 612);

        rentScene1.getStylesheets().add("style.css");

        return rentScene1;

    }

}
