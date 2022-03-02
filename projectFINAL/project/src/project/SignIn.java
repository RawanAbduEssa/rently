package project;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import static project.Project.customer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.charset.StandardCharsets;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class SignIn {

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static Boolean CheckUser(Stage primaryStage, String email, String password) {
        // this method checks if the user is registered in the database

        // Database session //
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        // search for the user(customer) email in the [custemer Table]
        String query_O = "from customer where email =" + "\'" + email + "\'";
        Query queryO = session.createQuery(query_O);
        Project.customer = (customer) queryO.uniqueResult();
        tx.commit();
        session.close();

        try {
            //To decrypt password
            MessageDigest digest = MessageDigest.getInstance("SHA-256");//For the hashing
            final byte[] hashbytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String sha3Hex = bytesToHex(hashbytes);

            if (Project.customer != null && Project.customer.getPassword().equals(sha3Hex)) {
                // email and password is correct, then go to my profile page
                primaryStage.setScene(MyProfile.myProfile(primaryStage));
                return true;
            } else {
                // email or password is not correct 
                // Alert //
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("The Email Or Passwor Is Not Correct");
                alert.show();
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CreateAccount.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public static Scene signIn(Stage primaryStage) {
        // main vbox
        BorderPane signInMainPane = new BorderPane();
        VBox main = new VBox();
        main.setPadding(new Insets(30, 5, 20, 5));

        // create the main title of the page using a text
        Text pageTitle = new Text("Sign In");
        pageTitle.getStyleClass().add("header");
        pageTitle.setTextAlignment(TextAlignment.CENTER);
        pageTitle.setFont(Font.font(45));

        //Text warning massege if the fileds is empty
        Text warning = new Text("");
        warning.setFill(Color.RED);
        warning.setFont(Font.font("Miriam", FontWeight.EXTRA_BOLD, 17));
        HBox warningBox = new HBox();
        warningBox.getChildren().add(warning);
        warningBox.setAlignment(Pos.CENTER);
        warningBox.setPadding(new Insets(85, 0, 5, 0));

        // enter info pane (email & password)
        GridPane input = new GridPane();
        input.setHgap(15);
        input.setVgap(25);

        //email (label & textField)
        TextField email_tf = new TextField();
        Label email2 = new Label("Email :");
        email2.setFont(Font.font("Miriam", FontWeight.BOLD, 16));

        //pasword (label & textField)
        PasswordField password_pf = new PasswordField();
        Label password2 = new Label("Password :");
        password2.setFont(Font.font("Miriam", FontWeight.BOLD, 16));

        // position elements
        input.add(email2, 0, 0);
        input.add(email_tf, 1, 0);
        input.add(password2, 0, 1);
        input.add(password_pf, 1, 1);
        input.setAlignment(Pos.CENTER);
        input.setPadding(new Insets(0, 0, 50, 0));

        // try and catch alert //
        Alert alert = new Alert(Alert.AlertType.ERROR);

        // sighn in button
        Button signIn2 = new Button("Sign In");
        signIn2.setMaxSize(170, 90);
        signIn2.setOnAction(e -> { // Button Action 'go to My profile page'
            try {
                if (email_tf.getText().isEmpty() | password_pf.getText().isEmpty()) { // if the textfiled is empty
                    warning.setText("Fill the Blank");
                } else { // if the user filled all the inputs 
                    CheckUser(primaryStage, email_tf.getText(), password_pf.getText());
                }
            } catch (Exception ex) {
                alert.setContentText("Something Went Wrong");
                alert.show();
            }
        });

        // text for create account 
        Text newOne = new Text("Create New One");
        newOne.setFill(Color.RED);
        //attach text to the lable 
        Label goCreate = new Label("Don't Have An Account?", newOne);
        goCreate.setContentDisplay(ContentDisplay.RIGHT);
        goCreate.setPadding(new Insets(10, 0, 0, 0));
        newOne.setOnMouseClicked((mouseEvent) -> { // mouse event to move to Create Account page
            primaryStage.setScene(CreateAccount.createAccount(primaryStage));
        });

        //image
        ImageView signInImageView = new ImageView(new Image("sign-in.png"));
        signInImageView.setFitHeight(150);
        signInImageView.setPreserveRatio(true);

        //Animation
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(3));
        transition.setToX(0);
        transition.setToY(10);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setAutoReverse(true);
        transition.setNode(signInImageView);
        transition.play();

        // add elemednt to the pane
        main.getChildren().addAll(warningBox, input, signIn2, goCreate);
        signInMainPane.setTop(pageTitle);
        signInMainPane.setCenter(main);
        signInMainPane.setBottom(signInImageView);
        BorderPane.setAlignment(signInImageView, Pos.CENTER);
        BorderPane.setAlignment(pageTitle, Pos.CENTER);
        BorderPane.setMargin(pageTitle, new Insets(15, 0, 12, 0));

        main.setAlignment(Pos.TOP_CENTER);
        signInMainPane.getStyleClass().add("page");
        Scene signInScene = new Scene(signInMainPane, 375, 612);
        signInScene.getStylesheets().add("style.css");

        signInScene.setOnKeyPressed(e -> { // key event to move to My Profile page if (enter) is pressed
            if (e.getCode() == KeyCode.ENTER) {
                try {
                    if (email_tf.getText().isEmpty() | password_pf.getText().isEmpty()) {
                        warning.setText("Fill the Blank");
                    } else { // if the user filled all the inputs 
                        CheckUser(primaryStage, email_tf.getText(), password_pf.getText());
                    }
                } catch (Exception ex) {
                    alert.setContentText("Something Went Wrong");
                    alert.show();
                }
            }
        });
//        email_tf.requestFocus();
//        password_pf.requestFocus();

        return (signInScene);
    }

}
