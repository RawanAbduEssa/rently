package project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import static project.Project.navBar;

public class OwnerPaymentInformation {

    public static Scene ownerPaymentInformation(Stage primaryStage, property propertyToRent, property_address address) {

        Label IBANlbl = new Label("IBAN:");
        IBANlbl.setFont(Font.font(17));

        Label NameOnCardlbl = new Label("Name On Card:");
        NameOnCardlbl.setFont(Font.font(17));

        Label CardNumberlbl = new Label("Card Number:");
        CardNumberlbl.setFont(Font.font(17));

        Label Banklbl = new Label("Bank:");
        Banklbl.setFont(Font.font(17));

        Label header = new Label("Almost Done!");
        header.setFont(Font.font(55));

        //Text Fields
        TextField IBAN = new TextField();
        TextField numOnCard = new TextField();
        TextField cardNum = new TextField();
        TextField BANK = new TextField();

        //buttons
        Button submit = new Button("Submit");
        Alert alert = new Alert(Alert.AlertType.NONE);

        submit.setOnAction(e -> {
            boolean tfiban = IBAN.getText().isEmpty();
            boolean tfnumOnCard = numOnCard.getText().isEmpty();
            boolean tfcardNum = cardNum.getText().isEmpty();
            boolean tfBank = BANK.getText().isEmpty();

            if (tfiban || tfnumOnCard || tfcardNum || tfBank) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Fill in all the Fields please");
                alert.show();
            } else {
                if (Validation.vaildationAccountNumber(cardNum.getText())) {
                    try {
                        owner_payment ownerPayment = new owner_payment();
                        ownerPayment.setIBAN(IBAN.getText());
                        ownerPayment.setAccountName(numOnCard.getText());

                        ownerPayment.setAccountNum(cardNum.getText());
                        ownerPayment.setBank(BANK.getText());

                        owner owner = new owner();
                        owner.setCustomerId(Project.customer.getCustomerID());
                        owner.setIBAN(ownerPayment.getIBAN());

                        Session session1 = HibernateUtil.getSessionFactory().openSession();
                        Transaction tx = null;
                        tx = session1.beginTransaction();
                        session1.save(ownerPayment);
                        session1.save(owner);
                        session1.save(address);

                        propertyToRent.setOwnerID(owner.getOwnerID());

                        session1.save(propertyToRent);
                        tx.commit();
                        session1.close();

                        RentAProperty.storeImages(propertyToRent);

                        primaryStage.setScene(MyProperties.myProperties(primaryStage));
                    } catch (Exception ex) {
                        Alert alertOwner = new Alert(Alert.AlertType.ERROR);
                        alertOwner.setContentText("Please Enter The Correct Informationg");
                        alertOwner.show();
                    }
                }
            }
        });

        //grid 
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(50);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(IBANlbl, 0, 0);
        gridPane.add(IBAN, 1, 0);
        gridPane.add(NameOnCardlbl, 0, 1);
        gridPane.add(numOnCard, 1, 1);
        gridPane.add(CardNumberlbl, 0, 2);
        gridPane.add(cardNum, 1, 2);
        gridPane.add(Banklbl, 0, 3);
        gridPane.add(BANK, 1, 3);

        //VBox
        VBox ADVBox = new VBox(50);

        ADVBox.getChildren().addAll(gridPane, submit);

        ADVBox.setAlignment(Pos.CENTER);

        //Border pane
        BorderPane mainBorder = new BorderPane();

        mainBorder.setTop(header);
        BorderPane.setAlignment(header, Pos.CENTER);

        mainBorder.setCenter(ADVBox);

        mainBorder.setBottom(navBar(primaryStage));
        mainBorder.getStyleClass().add("page");
        Scene ownerPayment = new Scene(mainBorder, 375, 612);
        ownerPayment.getStylesheets().add("style.css");

        return ownerPayment;
    }
}
