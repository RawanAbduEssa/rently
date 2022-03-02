package project;

import java.time.LocalDate;
import java.util.Date;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.LocalDateStringConverter;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Payment {

    public static Scene payment(Stage primaryStage, int propertyID, property p, LocalDate checkIn, String duration, String price) {
        BorderPane payPane = new BorderPane();

        VBox mainV_pay = new VBox(3);
        mainV_pay.setPadding(new Insets(-8, 10, 0, 10));
        mainV_pay.setAlignment(Pos.TOP_CENTER);

        // Back image
        HBox topH_pay = new HBox(20);
        topH_pay.setAlignment(Pos.CENTER);

        ImageView backArrowPay = new ImageView(new Image("back-arrow2.png"));
        backArrowPay.setPreserveRatio(true);
        backArrowPay.setFitHeight(30);
        Button backPay = new Button("", backArrowPay);
        backPay.setTextAlignment(TextAlignment.CENTER);
        backPay.getStyleClass().add("back-button");

        // button to go to the information page
        backPay.setOnAction((e) -> {
            primaryStage.setScene(PropertyInformation.properyInformation(primaryStage, propertyID));
        });

        // create the main title of the page using a text
        Text pageTitle_pay = new Text("Complete\n Payment");
        pageTitle_pay.getStyleClass().add("header");
        pageTitle_pay.setFont(Font.font(40));
        topH_pay.getChildren().addAll(pageTitle_pay, backPay);

        payPane.setTop(topH_pay);
        topH_pay.setPadding(new Insets(0, 0, 0, 30));
        BorderPane.setAlignment(topH_pay, Pos.CENTER);
        BorderPane.setMargin(topH_pay, new Insets(6, 0, 0, 0));

        // hand card image 
        ImageView ivHandCard_pay = new ImageView(new Image("HandsCard.png"));
        ivHandCard_pay.setFitHeight(150);
        ivHandCard_pay.setPreserveRatio(true);

        // text field and labels to enter the payment information vbox
        VBox inputV_pay = new VBox(20);
        inputV_pay.setPadding(new Insets(0, 10, 30, 10));

        VBox inputCard = new VBox(5);

        // Text Warning if the textfiled is empty
        Text Warning = new Text("");
        Warning.setFill(Color.RED);
        Warning.setFont(Font.font("", FontWeight.BOLD, 16));
        inputCard.getChildren().add(Warning);

        // card number
        Label card_number = new Label("Card Number :");
        card_number.setFont(Font.font("", FontWeight.BOLD, 16));
        TextField cardNumber_tf = new TextField();
        cardNumber_tf.setMaxWidth(300.0);
        inputCard.getChildren().addAll(card_number, cardNumber_tf);

        // card holder name
        VBox inputCardName = new VBox(5);
        Label card_name = new Label("Card holder Name :");
        card_name.setFont(Font.font("", FontWeight.BOLD, 16));
        TextField cardName_tf = new TextField();
        cardName_tf.setMaxWidth(300.0);
        inputCardName.getChildren().addAll(card_name, cardName_tf);

        // CVC
        VBox inputCVC = new VBox(5);
        Label cvc = new Label("CVC :");
        cvc.setFont(Font.font("", FontWeight.BOLD, 16));
        TextField cvc_tf = new TextField();
        cvc_tf.setMaxWidth(100.0);
        inputCVC.getChildren().addAll(cvc, cvc_tf);

        // Exp date
        VBox inputExp = new VBox(5);
        Label expDate = new Label("Expiry Date :");
        expDate.setFont(Font.font("", FontWeight.BOLD, 16));
        TextField expDate_tf = new TextField();
        expDate_tf.setMaxWidth(100.0);
        expDate_tf.setPromptText("MM/YY");
        inputExp.getChildren().addAll(expDate, expDate_tf);

        // hbox to hold the cvc and Exp
        HBox inputH_CandE = new HBox(20);
        inputH_CandE.getChildren().addAll(inputCVC, inputExp);

        // add all elements in the input vbox
        inputV_pay.getChildren().addAll(inputCard, inputCardName, inputH_CandE);

        // button
        Button button_pay = new Button("pay");
        button_pay.setMaxSize(170, 90);
        button_pay.setFont(Font.font(30));
        //Animation
        TranslateTransition t = new TranslateTransition();
        t.setDuration(Duration.seconds(3));
        t.setToY(10);
        t.setCycleCount(Animation.INDEFINITE);
        t.setAutoReverse(true);
        t.setNode(ivHandCard_pay);
        t.play();

        // local date for the time of place order
        LocalDate now = java.time.LocalDate.now();

        button_pay.setOnAction((e) -> { // pay button action 
            if (cardNumber_tf.getText().isEmpty() | cardName_tf.getText().isEmpty() | cvc_tf.getText().isEmpty() | expDate_tf.getText().isEmpty()) {
                // if any filed is empty
                Warning.setText("Fill The Blank");
            } else {
                try {
                    // insert the [order] or [order_payment] 
                    if (Validation.vaildationAccountNumber(cardNumber_tf.getText()) && Validation.vaildationCvc(cvc_tf.getText()) && Validation.vaildationCardExpiry(expDate_tf.getText())) {
                        order_payment op = new order_payment();
                        op.setCardNumber(cardNumber_tf.getText());
                        op.setOwnerName(cardName_tf.getText());
                        op.setCVC(Integer.valueOf(cvc_tf.getText()));
                        op.setExpDate(expDate_tf.getText());

                        Order order = new Order();
                        order.setOrderDate(now.toString());
                        order.setCheckIn(checkIn.toString());
                        order.setCardNumber(cardNumber_tf.getText());
                        order.setPropertyId(propertyID);
                        order.setCustomerID(Project.customer.getCustomerID());
                        order.setOwnerID(p.getOwnerID());
                        order.setPrice(Double.valueOf(price));
                        order.setDuration(Integer.valueOf(duration));

                        Session session1 = HibernateUtil.getSessionFactory().openSession();
                        Transaction tx = null;
                        tx = session1.beginTransaction();

                        session1.save(op);
                        session1.save(order);

                        tx.commit();
                        session1.close();
                        primaryStage.setScene(MyProfile.myProfile(primaryStage));
                    }
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please Enter The Correct Informationg");
                    alert.show();
                }
            }
        });

        mainV_pay.getChildren().addAll(ivHandCard_pay, inputV_pay, button_pay);

        payPane.setCenter(mainV_pay);
        BorderPane.setAlignment(payPane, Pos.TOP_CENTER);
        payPane.getStyleClass().add("page");
        Scene payScene = new Scene(payPane, 375, 612);
        payScene.getStylesheets().add("style.css");
        return payScene;
    }
}
