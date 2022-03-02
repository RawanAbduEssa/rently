/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.regex.Pattern;
import javafx.scene.control.Alert;

/**
 *
 * @author HU6EM001
 */
public class Validation {

    public static boolean vaildation(String password) {
        //check for length
        boolean isUpper = Character.isUpperCase(password.charAt(0));
        if (password.length() >= 4 && password.length() <= 12 && isUpper) {
            //a pattren that the password should follow
            Pattern p = Pattern.compile("[a-z0-9]");
            //match the password to the pattren
            //find() method returns true if the pattern was found in the string
            return p.matcher(password).find();
        } else {
            return false;
        }
    }

    public static boolean vaildationZipcode(String zipCode) {
        //check for length
        if (zipCode.length() == 5) {
            //a pattren that the password should follow
            Pattern p = Pattern.compile("[0-9]");
            //match the password to the pattren
            //find() method returns true if the pattern was found in the string
            return p.matcher(zipCode).find();
        } else {
            return false;
        }
    }

    public static boolean vaildationAccountNumber(String AccountNum) {
        //check for length
        if (AccountNum.length() == 16) {
            //a pattren that the Account Number should follow
            Pattern p = Pattern.compile("[0-9]");
            //match the Account Number to the pattren
            //find() method returns true if the pattern was found in the string
            return p.matcher(AccountNum).find();
        } else {
            // if the Account number length is less or mor than 16 --> show this alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Account Number Must Be 16 Numbers Only");
            alert.show();
            return false;
        }
    }

    public static boolean vaildationCvc(String cvc) {
        //check for length
        if (cvc.length() >= 3 && cvc.length() <= 4) {
            //a pattren that the Account Number should follow
            Pattern p = Pattern.compile("[0-9]");
            //match the Account Number to the pattren
            //find() method returns true if the pattern was found in the string
            return p.matcher(cvc).find();
        } else {
            // if the Account number length is less or mor than 16 --> show this alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("CVC must be three or four-digit number");
            alert.show();
            return false;
        }
    }

    public static boolean vaildationCardExpiry(String cardExpiary) {
        // (0 followed by 1-9, or 1 followed by 0-2)    (followed by "/")   (followed by 0-9, twice)

        if (cardExpiary.matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
            return true;
        } else {
            // if the card Expairy Date is not in the correct formate
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Expiry Date Must Be In [MM/YY] Format");
            alert.show();
            return false;
        }
    }
}
