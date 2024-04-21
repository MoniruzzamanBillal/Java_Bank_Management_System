package bankmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.*;
import java.time.LocalDate;
import java.sql.*;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class DashboardController implements Initializable {

    //    databases configurations 
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    @FXML
    private AnchorPane AccountDetailForm;

    @FXML
    private AnchorPane DepositForm;

    @FXML
    private AnchorPane withdrawForm;

    @FXML
    private AnchorPane sendMoneyForm;

    @FXML
    private Button dashboardDepositBtn;

    @FXML
    private Button dashboardDetailsBtn;

    @FXML
    private Button dashboardSendBtn;

    @FXML
    private Button dashboardWithdrawBtn;

    @FXML
    private Label withdraw_totalWithdraw;
    

    @FXML
    private Button depositBtn;

    @FXML
    private TextField depositMoneyInput;

    @FXML
    private Label deposit_TotalDEpositAmount;

    @FXML
    private Label details_accountNo;

    @FXML
    private Label details_balance;

    @FXML
    private Label details_email;

    @FXML
    private Label details_name;

    @FXML
    private TextField sendAmountInput;

    @FXML
    private Button sendBtn;

    @FXML
    private PasswordField sendPasswordInput;

    @FXML
    private TextField sendReceiverAccountInput;

    @FXML
    private TextField withdrawInput;

//    function for switching pages 
    public void switchForm(ActionEvent event) {
        if (event.getSource() == dashboardDetailsBtn) {
            AccountDetailForm.setVisible(true);
            DepositForm.setVisible(false);
            withdrawForm.setVisible(false);
            sendMoneyForm.setVisible(false);

        } else if (event.getSource() == dashboardDepositBtn) {
            AccountDetailForm.setVisible(false);
            DepositForm.setVisible(true);
            withdrawForm.setVisible(false);
            sendMoneyForm.setVisible(false);

        } else if (event.getSource() == dashboardWithdrawBtn) {
            AccountDetailForm.setVisible(false);
            DepositForm.setVisible(false);
            withdrawForm.setVisible(true);
            sendMoneyForm.setVisible(false);

        } else if (event.getSource() == dashboardSendBtn) {
            AccountDetailForm.setVisible(false);
            DepositForm.setVisible(false);
            withdrawForm.setVisible(false);
            sendMoneyForm.setVisible(true);

        }
    }

    @FXML
    void handleAddDeposit(ActionEvent event) {
        try {
            String email = LoggedInUser.userEmail;
            String depositAmount = depositMoneyInput.getText();
            LocalDate currentDate = LocalDate.now();

            con = database.connectDb();
            String que = " insert into deposit values (  ? , ? , ? ) ";
            pst = con.prepareStatement(que);
            pst.setString(1, email);
            pst.setString(2, depositAmount);
            pst.setString(3, currentDate.toString());

            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deposit Money");
            alert.setHeaderText(null);
            alert.setContentText("Money deposit successfully!!");
            alert.showAndWait();

            depositMoneyInput.setText("");
            
            String que2 = " SELECT SUM(CAST(depositAmount AS DECIMAL(10,2)))  FROM deposit WHERE userEmail = ?   ";
            pst = con.prepareStatement(que2);
            pst.setString(1, email);
            rs = pst.executeQuery();
            if (!rs.next()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("No user found for this email - " + email);
                alert.showAndWait();
            } else {
                String totalDepositAmount = rs.getString(1);
                System.out.println("Total deposit = " + totalDepositAmount);
                deposit_TotalDEpositAmount.setText(totalDepositAmount);
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

    }

    @FXML
    void handleSendMoney(ActionEvent event) {

    }

    @FXML
    void handleWithdrawMoney(ActionEvent event) {
        
        try{
              String email = LoggedInUser.userEmail;
            String withdrawAmount = withdrawInput.getText();
            LocalDate currentDate = LocalDate.now();
            
                        con = database.connectDb();
            String que = " insert into withdraw values (  ? , ? , ? ) ";
            pst = con.prepareStatement(que);
            pst.setString(1, email);
            pst.setString(2, withdrawAmount);
            pst.setString(3, currentDate.toString());

            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Withdraw Money");
            alert.setHeaderText(null);
            alert.setContentText("Money withdraw successfully!!");
            alert.showAndWait();

            withdrawInput.setText("");
            
            
            String que2 = " SELECT SUM(CAST(withdrawAmount AS DECIMAL(10,2)))  FROM withdraw WHERE userEmail = ?   ";
            
            pst = con.prepareStatement(que2);
            pst.setString(1, email);

            rs = pst.executeQuery();

            if (!rs.next()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("No user found for this email - " + email);
                alert.showAndWait();

            } else {

                String totalDepositAmount = rs.getString(1);
                System.out.println("Total deposit = " + totalDepositAmount);

                withdraw_totalWithdraw.setText(totalDepositAmount);

            }
            
            
            
            
        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    void handleShowDetailForn(ActionEvent event) {
        switchForm(event);

    }

    @FXML
    void handleShowDepositForm(ActionEvent event) {
        switchForm(event);

        try {
            String email = LoggedInUser.userEmail;
            con = database.connectDb();
            String que2 = " SELECT SUM(CAST(depositAmount AS DECIMAL(10,2)))  FROM deposit WHERE userEmail = ?   ";

            pst = con.prepareStatement(que2);
            pst.setString(1, email);

            rs = pst.executeQuery();

            if (!rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("No user found for this email - " + email);
                alert.showAndWait();

            } else {

                String totalDepositAmount = rs.getString(1);
                System.out.println("Total deposit = " + totalDepositAmount);

                deposit_TotalDEpositAmount.setText(totalDepositAmount);

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

    }

    @FXML
    void handleShowSendForm(ActionEvent event) {
        switchForm(event);
    }

    @FXML
    void handleShowWithdrawForm(ActionEvent event) {
        switchForm(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        System.out.println("logged in user in dashboard = " + LoggedInUser.userEmail);

        // Check if the deposit form is visible
        if (DepositForm.isVisible()) {
            // If yes, fetch and display the total deposit amount
            try {
                String email = LoggedInUser.userEmail;

                con = database.connectDb();
                String que2 = "SELECT SUM(CAST(depositAmount AS DECIMAL(10,2))) FROM deposit WHERE userEmail = ?";
                pst = con.prepareStatement(que2);
                pst.setString(1, email);
                rs = pst.executeQuery();

                if (rs.next()) {
                    String totalDepositAmount = rs.getString(1);
                    deposit_TotalDEpositAmount.setText(totalDepositAmount);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Error fetching total deposit amount: " + e.getMessage());
                alert.showAndWait();
            }
        }

        System.out.println("logged in user in dashboard = " + LoggedInUser.userEmail);

    }

}
