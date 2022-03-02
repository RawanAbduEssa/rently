package project;

import java.io.FileOutputStream;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
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
public class HomePage {

    public static Scene homePage(Stage primaryStage) {

        //parent node
        BorderPane mainPane = new BorderPane();

        //page header
        Text home = new Text("Home");
        home.setFont(Font.font(60));
        home.getStyleClass().add("header");
        BorderPane.setAlignment(home, Pos.CENTER);
        BorderPane.setMargin(home, new Insets(15, 0, 12, 0));

        mainPane.setTop(home);

        //center of parent BorderPane => the pane that contains the properties
        VBox deals = new VBox(20);

        //get all the properties from database
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String hqlSelect = "from property";
        Query querySelect = session.createQuery(hqlSelect);
        List<property> propertiesList = querySelect.list();

        transaction.commit();
        session.close();

        //if no properties in the system: display an image with animation
        if (propertiesList.isEmpty()) {
            ImageView noProperties = new ImageView(new Image("no-properties.png"));
            noProperties.setPreserveRatio(true);
            noProperties.setFitWidth(150);

            //Animation
            TranslateTransition t = new TranslateTransition();
            t.setDuration(Duration.seconds(3));
            t.setToX(0);
            t.setToY(10);
            t.setCycleCount(Animation.INDEFINITE);
            t.setAutoReverse(true);
            t.setNode(noProperties);
            t.play();

            Label noPropertiesLabel = new Label("No properties to display", noProperties);
            noPropertiesLabel.setContentDisplay(ContentDisplay.BOTTOM);
            noPropertiesLabel.setFont(Font.font(20));
            noPropertiesLabel.setStyle("-fx-text-fill:#BE8779;");
            VBox.setMargin(noPropertiesLabel, new Insets(90, 0, 0, 0));

            deals.setAlignment(Pos.CENTER);
            deals.getChildren().add(noPropertiesLabel);

        } else {
            //in case if there was no properties in the same run remove children
            deals.getChildren().removeAll(deals.getChildren());
            deals.setAlignment(Pos.TOP_CENTER);

            //for each property in the database
            for (property property : propertiesList) {

                //create a pane
                BorderPane deal = new BorderPane();
                deal.setPadding(new Insets(30, 0, 30, 0));
                BorderPane.setMargin(deal, new Insets(0,10,0,10));

                //top of the property pane: photo of property
                session = HibernateUtil.getSessionFactory().openSession();
                transaction = session.beginTransaction();
                hqlSelect = "from imagep where propertyID=" + property.getPropertyId();
                querySelect = session.createQuery(hqlSelect);

                //get the first photo
                imagep mainImage = (imagep) querySelect.list().get(0);
                transaction.commit();
                session.close();

                //write the photo into an image file usig FileOutputStream
                try {
                    FileOutputStream fos = new FileOutputStream("output.jpg");
                    fos.write(mainImage.getImages());
                    fos.close();
                } catch (Exception e) {

                }

                //place the image in a rectangle
                Rectangle propertyImage = new Rectangle(260, 175);
                ImagePattern image = new ImagePattern(new Image("file:output.jpg"));
                propertyImage.setFill(image);
                propertyImage.setArcHeight(30);
                propertyImage.setArcWidth(30);

                //under the image: property name and price
                VBox center = new VBox(15);
                center.setPadding(new Insets(15, 0, 0, 15));

                Label propertyName = new Label(property.getPropertyName());
                propertyName.setFont(Font.font(20));

                Line br = new Line(0, 0, 190, 0);
                br.setStroke(Color.rgb(150, 107, 97, 0.2));
                VBox.setMargin(br, new Insets(0, 0, 0, 20));

                Label propertyPrice = new Label(Double.toString(property.getPrice()));
                propertyPrice.setFont(Font.font(18));

                VBox.setMargin(propertyPrice, new Insets(0, 0, 0, 90));
                center.getChildren().addAll(propertyName, br, propertyPrice);

                //set the property pane
                deal.setTop(propertyImage);
                BorderPane.setAlignment(propertyImage, Pos.TOP_CENTER);
                deal.setCenter(center);
                //-fx-background-color: #BE8779;
                deal.setStyle("-fx-background-color: #D8E4E0;"
                        + "-fx-background-radius:30;");

                //hover effect
                deal.setOnMouseMoved(e -> {
                    deal.setStyle("-fx-background-color: #C0D1CC;"
                            + "-fx-background-radius:30;");
                    br.setStroke(Color.rgb(150, 114, 97, 0.2));
                });
                deal.setOnMouseExited(e -> {
                    deal.setStyle("-fx-background-color: #D8E4E0;"
                            + "-fx-background-radius:30;");
                    br.setStroke(Color.rgb(150, 107, 97, 0.2));
                });

                //go to the property information page
                deal.setOnMouseClicked(e -> {
                    primaryStage.setScene(PropertyInformation.properyInformation(primaryStage, property.getPropertyId()));
                });

                //add children to PROPERTIES pane
                deals.getChildren().add(deal);
            }
        }

        deals.setPadding(new Insets(20, 50, propertiesList.size() * 50, 59));
        deals.setId("deals");

        ScrollPane scroll = new ScrollPane(deals);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainPane.setCenter(scroll);

        //add a navigation bar to the bottom
        mainPane.setBottom(Project.navBar(primaryStage));

        //add background
        mainPane.getStyleClass().add("page");

        Scene homePage = new Scene(mainPane, 375, 612);
        homePage.getStylesheets().add("style.css");

        return homePage;
    }
}
