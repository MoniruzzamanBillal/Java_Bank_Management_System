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
    private Label depositTotalBalance;

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
    private Label details_balanceMoney;

    @FXML
    private TextField sendAmountInput;

    @FXML
    private Button sendBtn;

    @FXML
    private PasswordField sendPasswordInput;

    @FXML
    private TextField sendReceiverAccountInput;

    @FXML
    private Label sendTotalBalance;

    @FXML
    private Label withdrawTotalBalance;

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

//    function for adding deposit money starts 
    @FXML
    void handleAddDeposit(ActionEvent event) {
        try {
            String email = LoggedInUser.userEmail;
            String depositAmount = depositMoneyInput.getText();
            LocalDate currentDate = LocalDate.now();

            if (depositAmount.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a deposit amount.");
                alert.showAndWait();
                return;
            }

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

            try {
                double totalBalance = getTotalBalance(LoggedInUser.userEmail);
                System.out.println("total balance in deposit form  = " + totalBalance);
                depositTotalBalance.setText(String.valueOf(totalBalance));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
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
//    function for adding deposit money ends  

    //    function for sending  money starts   
    @FXML
    void handleSendMoney(ActionEvent event) {

        try {

            LocalDate currentDate = LocalDate.now();
            String receiverName = sendReceiverAccountInput.getText();
            String sendAmount = sendAmountInput.getText();
            String userPassword = sendPasswordInput.getText();
            String email = LoggedInUser.userEmail;

            if (receiverName.isEmpty() || sendAmount.isEmpty() || userPassword.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields.");
                alert.showAndWait();
                return;
            }

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

                    sendReceiverAccountInput.setText("");
                    sendAmountInput.setText("");
                    sendPasswordInput.setText("");

                    try {
                        double totalBalance = getTotalBalance(LoggedInUser.userEmail);
                        System.out.println("total balance in send form   = " + totalBalance);
                        sendTotalBalance.setText(String.valueOf(totalBalance));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }

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
    //    function for sending  money ends  

    //    function for withdraw money starts   
    @FXML
    void handleWithdrawMoney(ActionEvent event) {

        try {
            String email = LoggedInUser.userEmail;
            String withdrawAmount = withdrawInput.getText();
            LocalDate currentDate = LocalDate.now();

            if (withdrawAmount.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a withdraw amount.");
                alert.showAndWait();
                return;
            }

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

            try {
                double totalBalance = getTotalBalance(LoggedInUser.userEmail);
                System.out.println("total balance in withdraw form   = " + totalBalance);
                withdrawTotalBalance.setText(String.valueOf(totalBalance));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
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
    //    function for withdraw money ends   

    @FXML
    void handleShowDetailForn(ActionEvent event) {
        switchForm(event);
        System.out.println("Details form fro line 321 : ");

        double totalBalance = getTotalBalance(LoggedInUser.userEmail);

        details_balanceMoney.setText(String.valueOf(totalBalance));

    }

    @FXML
    void handleShowDepositForm(ActionEvent event) {
        switchForm(event);
        System.out.println("deposit  money form  ");
        try {
            double totalBalance = getTotalBalance(LoggedInUser.userEmail);
            System.out.println("total balance in deposit form  = " + totalBalance);
            depositTotalBalance.setText(String.valueOf(totalBalance));
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

        System.out.println("send   money form  ");
        try {
            double totalBalance = getTotalBalance(LoggedInUser.userEmail);
            System.out.println("total balance in send money form form  = " + totalBalance);
            sendTotalBalance.setText(String.valueOf(totalBalance));
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
    void handleShowWithdrawForm(ActionEvent event) {
        switchForm(event);

        System.out.println("withdraw money form  ");
        try {
            double totalBalance = getTotalBalance(LoggedInUser.userEmail);
            System.out.println("total balance in send money form form  = " + totalBalance);
            withdrawTotalBalance.setText(String.valueOf(totalBalance));
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

        System.out.println("total balance in initializer call  = " + getTotalBalance(LoggedInUser.userEmail));

        // Check if the deposit form is visible starts 
        if (DepositForm.isVisible()) {

            try {
                double totalBalance = getTotalBalance(LoggedInUser.userEmail);
                System.out.println("total balance in deposit form  = " + totalBalance);
                depositTotalBalance.setText(String.valueOf(totalBalance));
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
        // Check if the deposit form is visible ends 

        // Check if the send  form is visible starts 
        if (sendMoneyForm.isVisible()) {

            try {
                double totalBalance = getTotalBalance(LoggedInUser.userEmail);
                System.out.println("total balance in withdraw form  = " + totalBalance);
                sendTotalBalance.setText(String.valueOf(totalBalance));
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
        // Check if the send  form is visible ends  

//        check if the details form is visible starts 
        if (AccountDetailForm.isVisible()) {
            System.out.println("account detail form !!! ");
            try {
                con = database.connectDb();
                String que = " select * from userInfo WHERE userEmail = ?   ";
                pst = con.prepareStatement(que);
                pst.setString(1, LoggedInUser.userEmail);
                rs = pst.executeQuery();

                double totalBalance = getTotalBalance(LoggedInUser.userEmail);
                System.out.println("total balance in detail = " + totalBalance);
                details_balanceMoney.setText(String.valueOf(totalBalance));
                if (rs.next()) {

                    details_accountNo.setText(rs.getString(1));
                    details_address.setText(rs.getString(6));
                    details_name.setText(rs.getString(2));
                    details_nid.setText(rs.getString(4));
                    details_number.setText(rs.getString(5));
                    details_email.setText(rs.getString(3));
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

        //        check if the details form is visible ends 
//        check if withdraw form is visible starts 
        if (withdrawForm.isVisible()) {
            System.out.println("Hello ");

            try {
                double totalBalance = getTotalBalance(LoggedInUser.userEmail);
                System.out.println("total balance in withdraw form  = " + totalBalance);
                withdrawTotalBalance.setText(String.valueOf(totalBalance));
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

//        check if withdraw form is visible starts 
    }

//    initializer ends 
//function for finding total balance 
    public double getTotalBalance(String email) {
        double totalDeposit = 0;
        double totalWithdrawal = 0;
        double totalReceived = 0;
        double totalSend = 0;

        try {
            con = database.connectDb();

            // Query to get total deposit amount
            String depositQuery = "SELECT SUM(CAST(depositAmount AS DECIMAL(10,2))) FROM deposit WHERE userEmail = ?";
            pst = con.prepareStatement(depositQuery);
            pst.setString(1, email);
            ResultSet dedpositRs = pst.executeQuery();

            if (dedpositRs.next()) {
                totalDeposit = dedpositRs.getDouble(1);
            }

            // Query to get total withdrawal amount
            String withdrawalQuery = "SELECT SUM(CAST(withdrawAmount AS DECIMAL(10,2))) FROM withdraw WHERE userEmail = ?";
            pst = con.prepareStatement(withdrawalQuery);
            pst.setString(1, email);
            ResultSet withdrawRs = pst.executeQuery();

            if (withdrawRs.next()) {
                totalWithdrawal = withdrawRs.getDouble(1);
            }

            // Query to get total received amount
            String receivedQuery = "SELECT SUM(CAST(receiveAmount AS DECIMAL(10,2))) FROM receive WHERE receiverEmail = ?";
            pst = con.prepareStatement(receivedQuery);
            pst.setString(1, email);
            ResultSet receivedRs = pst.executeQuery();

            if (receivedRs.next()) {
                totalReceived = receivedRs.getDouble(1);
            }

//        query to get  total send  amount
            String sendQuery = "SELECT SUM(CAST(receiveAmount AS DECIMAL(10,2))) FROM receive WHERE senderEmail = ?";
            pst = con.prepareStatement(sendQuery);
            pst.setString(1, email);
            ResultSet sendRs = pst.executeQuery();

            if (sendRs.next()) {
                totalSend = sendRs.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }

        // Calculate total balance
        double totalBalance = (totalDeposit + totalReceived) - (totalWithdrawal + totalSend);

        return totalBalance;
    }

}
