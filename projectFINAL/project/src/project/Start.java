package project;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author ropan
 */
public class Start {

    public static Scene start(Stage primaryStage) {
        /////////////////////////
        ///// start sceren //////
        /////////////////////////

        VBox root = new VBox(10);
        VBox buttons = new VBox(20);

        Image img = new Image("test1.png");
        ImageView iv = new ImageView(img);

        Image img2 = new Image("hand2.png");
        ImageView iv2 = new ImageView(img2);

        iv.setFitHeight(90);
        iv.setPreserveRatio(true);

        iv2.setFitHeight(125);
        iv2.setPreserveRatio(true);

        //Animation
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(3));
        transition.setToX(10);
        transition.setToY(0);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setAutoReverse(true);
        transition.setNode(iv);
        transition.play();

        //Animation
        TranslateTransition transition1 = new TranslateTransition();
        transition1.setDuration(Duration.seconds(3));
        transition1.setToX(-10);
        transition1.setToY(0);
        transition1.setCycleCount(Animation.INDEFINITE);
        transition1.setAutoReverse(true);
        transition1.setNode(iv2);
        transition1.play();

        Text rently = new Text("Rently");
        rently.setFont(new Font(90));
        rently.setId("app-name");

        Button signIn = new Button("Sign in");
        signIn.setMaxSize(180, 90);
        signIn.setOnAction(e -> {
            primaryStage.setScene(SignIn.signIn(primaryStage));
        });

        Button signUp = new Button("Sign up");
        signUp.setMaxSize(180, 90);
        signUp.setOnAction(e -> {
            primaryStage.setScene(CreateAccount.createAccount(primaryStage));
        });

        HBox images = new HBox(10);
        images.getChildren().addAll(iv2, iv);
        images.setAlignment(Pos.CENTER);

        root.setAlignment(Pos.TOP_CENTER);
        buttons.getChildren().addAll(signIn, signUp);
        root.setPadding(new Insets(125, 0, 0, 0));

        buttons.setAlignment(Pos.TOP_CENTER);
        buttons.setPadding(new Insets(25, 0, 0, 0));
        BackgroundSize Size = new BackgroundSize(375, 612, false, false, false, false);
        root.setBackground(new Background(new BackgroundImage(new Image("2.jpg"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                Size)));
        root.getChildren().addAll(images, rently, buttons);
        Scene start = new Scene(root, 375, 612);
        start.getStylesheets().add("style.css");
        return (start);
    }

}
