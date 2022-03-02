package project;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import static project.SignIn.CheckUser;

public class CreateAccount {

    //Method to convert the bytes to hex
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

    public static Scene createAccount(Stage primaryStage) {
        /////////////////////////
        ///// create account ////
        /////////////////////////
        BorderPane mainPane = new BorderPane();

        VBox vBox = new VBox(15);
        vBox.setAlignment(Pos.TOP_CENTER);

        Text text = new Text("Create Account");
        text.getStyleClass().add("header");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font(45));
        mainPane.setTop(text);
        BorderPane.setAlignment(text, Pos.CENTER);
        BorderPane.setMargin(text, new Insets(22, 0, 50, 0));
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(0, 10, 10, 10));
        gridPane.setHgap(5);
        gridPane.setVgap(30);
        gridPane.setAlignment(Pos.CENTER);

        TextField name = new TextField(); // 1 
        name.setStyle("-fx-min-height: 30;");
        Label fullName = new Label("Full Name: ");
        fullName.setFont(Font.font("", FontWeight.BOLD, 16));

        TextField em = new TextField(); //2
        em.setStyle("-fx-min-height: 30;");
        Label email = new Label("Email: ");
        email.setFont(Font.font("", FontWeight.BOLD, 16));

        TextField ph = new TextField();//3
        ph.setStyle("-fx-min-height: 30;");
        Label phone = new Label("Phone Number: ");
        phone.setFont(Font.font("", FontWeight.BOLD, 16));

        PasswordField pass = new PasswordField();//4
        pass.setStyle("-fx-min-height: 30;");
        Label password = new Label("Password: ");
        password.setFont(Font.font("", FontWeight.BOLD, 16));

        PasswordField conpass = new PasswordField();//5
        conpass.setStyle("-fx-min-height: 30;");
        Label confirmPassword = new Label("Confirm Password: ");
        confirmPassword.setFont(Font.font("", FontWeight.BOLD, 16));

        gridPane.add(fullName, 0, 0);
        gridPane.add(name, 1, 0);
        gridPane.add(email, 0, 1);
        gridPane.add(em, 1, 1);
        gridPane.add(phone, 0, 2);
        gridPane.add(ph, 1, 2);
        gridPane.add(password, 0, 3);
        gridPane.add(pass, 1, 3);
        gridPane.add(confirmPassword, 0, 4);
        gridPane.add(conpass, 1, 4);

        mainPane.setCenter(gridPane);

        Button button = new Button("Sign up");
        button.setMaxSize(180, 90);

        Alert b = new Alert(Alert.AlertType.NONE);
        Alert a = new Alert(Alert.AlertType.NONE);

        button.setOnAction(e -> {
            String userNameString = name.getText();
            String emString = em.getText();
            String phString = ph.getText();
            String passString = pass.getText();
            String conpassString = conpass.getText();

            boolean un = name.getText().isEmpty();
            boolean ems = em.getText().isEmpty();
            boolean phs = ph.getText().isEmpty();
            boolean pass1 = pass.getText().isEmpty();
            boolean pass2 = conpass.getText().isEmpty();

            if (un || ems || phs || pass1 || pass2) {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Fill in all the Fields please");
                a.show();
            } else {
                if (passString.equals(conpassString)) { //matching passwords
                    passString = pass.getText();
                    boolean valid = Validation.vaildation(passString);
                    System.out.println(passString);
                    System.out.println(valid);
                    if (valid) {
                        try {
                            //To Hash Password
                            MessageDigest digest = MessageDigest.getInstance("SHA-256");//For the hashing
                            byte[] hashbytes = null;
                            hashbytes = digest.digest(passString.getBytes(StandardCharsets.UTF_8));
                            String sha3Hex = bytesToHex(hashbytes);

                            customer c1 = new customer();
                            c1.setFullName(userNameString);
                            c1.setPassword(sha3Hex);//Set encrypted password 
                            c1.setMobile(phString);
                            c1.setEmail(emString);
                            Session session = HibernateUtil.getSessionFactory().openSession();
                            Transaction tx;
                            tx = session.beginTransaction();
                            session.save(c1);
                            tx.commit();
                            session.close();

                        } catch (NoSuchAlgorithmException ex) {
                            Logger.getLogger(CreateAccount.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        b.setAlertType(Alert.AlertType.CONFIRMATION);
                        b.setContentText("Account Created Successfully!\nmoving on to login");
                        b.show();

                        primaryStage.setScene(SignIn.signIn(primaryStage));
                    } else {
                        b.setAlertType(Alert.AlertType.ERROR);
                        b.setContentText("Your password Should begin with a capital letter and between 4-8 characters");
                        b.show();
                    }

                } else {
                    b.setAlertType(Alert.AlertType.ERROR);
                    b.setContentText("your passwords are not matched");
                    b.show();
                }
            }
        });

        Label goSignInlbl = new Label("Already Have An Account?");
        Text goSignIn = new Text("Sign In");
        goSignIn.setFill(Color.RED);
        goSignIn.setOnMouseClicked((mouseEvent) -> {
            primaryStage.setScene(SignIn.signIn(primaryStage));
        });

        HBox labels = new HBox(5);
        labels.getChildren().addAll(goSignInlbl, goSignIn);
        labels.setAlignment(Pos.CENTER);

        VBox btns = new VBox(10);
        btns.getChildren().addAll(button, labels);
        btns.setAlignment(Pos.CENTER);
        btns.setPadding(new Insets(10, 0, 0, 0));

        Image create = new Image("phone.png");
        ImageView ivCreate = new ImageView(create);
        ivCreate.setPreserveRatio(true);
        ivCreate.setFitHeight(150);

        //Animation
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(3));
        transition.setToY(-10);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setAutoReverse(true);
        transition.setNode(ivCreate);
        transition.play();
        vBox.getChildren().addAll(btns, ivCreate);

        mainPane.setBottom(vBox);
        mainPane.getStyleClass().add("page");
        Scene createAcc = new Scene(mainPane, 375, 612);
        createAcc.getStylesheets().add("style.css");

        return (createAcc);
    }

}
