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

    ReusableFunction reusableFunction;

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
    private Label details_address;

    @FXML
    private Label details_name;

    @FXML
    private Label details_nid;

    @FXML
    private Label details_number;

    @FXML
    private Label details_email;



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

        try {

            System.out.println("Send money add click !! ");

            LocalDate currentDate = LocalDate.now();
            String receiverName = sendReceiverAccountInput.getText();
            String sendAmount = sendAmountInput.getText();
            String userPassword = sendPasswordInput.getText();
            String email = LoggedInUser.userEmail;

            con = database.connectDb();
            String que = "Select userEmail , userName from userInfo where  userPassword=? and userEmail=?";
            pst = con.prepareStatement(que);
            pst.setString(1, userPassword);
            pst.setString(2, email);
            rs = pst.executeQuery();

            if (rs.next()) {
                System.out.println("Email: " + rs.getString(1));
                System.out.println("Name: " + rs.getString(2));

                String que2 = "Select userEmail from userInfo where  accountName=? ";
                pst = con.prepareStatement(que2);
                pst.setString(1, receiverName);
                rs = pst.executeQuery();

                if (!rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Receiver account number is incorrect ");
                    alert.showAndWait();
                } else {

                    String receiverEmail = rs.getString(1);

                    con = database.connectDb();
                    String que3 = " insert into receive values (?,?,?,?) ";
                    pst = con.prepareStatement(que3);
                    pst.setString(1, email);
                    pst.setString(2, receiverEmail);
                    pst.setString(3, sendAmount);
                    pst.setString(4, currentDate.toString());

                    pst.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Send Money");
                    alert.setHeaderText(null);
                    alert.setContentText("Money send successfully!!");
                    alert.showAndWait();

                }

            } else if (!rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Password is incorrect!!");
                alert.showAndWait();

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
    void handleWithdrawMoney(ActionEvent event) {

        try {
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
    void handleShowDetailForn(ActionEvent event) {
        switchForm(event);
        System.out.println("Details form : ");
        
            
            
         
            
        

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
    
    
        @FXML
    private Button logoutBtn;
    
        @FXML
    void handleLogout(ActionEvent event) throws Exception {

        LoggedInUser.userName = null;
        LoggedInUser.userEmail = null;

        //        to  hide admin  form 
        logoutBtn.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        System.out.println("logged in user in dashboard = " + LoggedInUser.userEmail);

        // Check if the deposit form is visible starts 
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
        // Check if the deposit form is visible ends 

        // Check if the withdraw  form is visible starts 
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
        // Check if the withdraw  form is visible ends  
        
        
//        check if the details form is visible starts 

        if(AccountDetailForm.isVisible()){
            
               try{
                        con = database.connectDb();
            String que = " select * from userInfo  ";
                        pst = con.prepareStatement(que);
            rs = pst.executeQuery();
            
            if(rs.next()){
                
                
                
                details_accountNo.setText(rs.getString(1) );
                details_address.setText(rs.getString(6) );
                details_name.setText(rs.getString(2) );
                details_nid.setText(rs.getString(4) );
                details_number.setText(rs.getString(5) );
                details_email.setText(rs.getString(3) );
                
                       
//     Label details_accountNo;
//     Label details_address;
//     Label details_name;
//     Label details_nid;
//    Label details_number;
//     Label details_email;
    
    
                
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
        
        //        check if the details form is visible ends 

        System.out.println("logged in user in dashboard = " + LoggedInUser.userEmail);

    }
    
    
    
    
    
    

}
