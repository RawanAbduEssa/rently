package project;

import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author HU6EM001
 */
public class Project extends Application {

    static Scene start, createAcc, signInScene, myprofile;
    public static customer customer;

    public static Node navBar(Stage primaryStage) {
        //bottom bar
        HBox bar = new HBox(80);
        bar.setAlignment(Pos.CENTER);
        bar.setPadding(new Insets(10, 0, 10, 0));
        bar.setId("navbar");

        Image user = new Image("user.png");
        ImageView uservw = new ImageView(user);
        uservw.setFitHeight(40);
        uservw.setPreserveRatio(true);

        Button userbtn = new Button("", uservw);
        userbtn.getStyleClass().add("navbar-icon");
        userbtn.setOnAction(e -> {
            primaryStage.setScene(MyProfile.myProfile(primaryStage));
        });

        Image rent = new Image("rent.png");
        ImageView rentvw = new ImageView(rent);
        rentvw.setFitHeight(40);
        rentvw.setPreserveRatio(true);

        Button rentbtn = new Button("", rentvw);
        rentbtn.getStyleClass().add("navbar-icon");
        rentbtn.setOnAction(e -> {
            primaryStage.setScene(MyProperties.myProperties(primaryStage));
        });

        Image search = new Image("search.png");
        ImageView searchvw = new ImageView(search);
        searchvw.setFitHeight(37);
        searchvw.setPreserveRatio(true);

        Button searchbtn = new Button("", searchvw);
        searchbtn.getStyleClass().add("navbar-icon");
        searchbtn.setOnAction(e -> {
            primaryStage.setScene(HomePage.homePage(primaryStage));
        });

        bar.getChildren().addAll(userbtn, rentbtn, searchbtn);
        return bar;
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setScene(Start.start(primaryStage));
        primaryStage.setTitle("Rently");
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);
    }

}
