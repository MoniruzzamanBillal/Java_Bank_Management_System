package bankmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.control.Alert;

public class ReusableFunction {

    //    databases configurations 
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    String email = LoggedInUser.userEmail;

    public  String getDeposit(String email) {
        String totalDepositAmount = new String();
        
        System.out.println("Email in resuable function body = " + email);

        try {

            String que = " SELECT SUM(CAST(depositAmount AS DECIMAL(10,2)))  FROM deposit WHERE userEmail = ?   ";
            pst = con.prepareStatement(que);
            pst.setString(1, email);

            rs = pst.executeQuery();

            if (!rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("No user found for this email - " + email);
                alert.showAndWait();
            } else {
                 totalDepositAmount = rs.getString(1);
                System.out.println("Total deposit = " + totalDepositAmount);

                return totalDepositAmount;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        
             return totalDepositAmount;

    }

}
