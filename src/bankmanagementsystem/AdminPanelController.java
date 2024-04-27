package bankmanagementsystem;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdminPanelController implements Initializable {

    //    databases configurations 
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    
        @FXML
    private Label totalUserNumber;
        
        
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

    @FXML
    private Button showUserBtn;

    @FXML
    private Button transactionHistoryBtn;

    @FXML
    private AnchorPane UsersForm;

    @FXML
    private AnchorPane transactionForm;

    @FXML
    private TableView<Transaction> transactionTableView;

    @FXML
    private TableColumn<Transaction, String> receiveAmount;

    @FXML
    private TableColumn<Transaction, String> receiveTime;

    @FXML
    private TableColumn<Transaction, String> receiverEmail;

    @FXML
    private TableColumn<Transaction, String> senderEmail;

    @FXML
    private TableView<UserAccount> userTableView;

    @FXML
    private TableColumn<UserAccount, String> userAccountDeposit;

    @FXML
    private TableColumn<UserAccount, String> userAccountEmail;

    @FXML
    private TableColumn<UserAccount, String> userAccountName;

    @FXML
    private TableColumn<UserAccount, String> userAccountReceive;

    @FXML
    private TableColumn<UserAccount, String> userAccountSend;

    @FXML
    private TableColumn<UserAccount, String> userAccountWithdraw;

    private ObservableList<UserAccount> userAccounts = FXCollections.observableArrayList();
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    @FXML
    void handleShowTransactionForm(ActionEvent event) {
        System.out.println("Click on show transaction form  !!");
        switchForm(event);
//        fetchTransactionData();

    }

    @FXML
    void handleShowUserForm(ActionEvent event) {
        System.out.println("Click  on show user form  !!");
        switchForm(event);
//        fetchUserData();
    }

    //    function for switching pages 
    public void switchForm(ActionEvent event) {
        if (event.getSource() == showUserBtn) {
            UsersForm.setVisible(true);
            transactionForm.setVisible(false);
        } else if (event.getSource() == transactionHistoryBtn) {
            UsersForm.setVisible(false);
            transactionForm.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Set up column bindings
        userAccountName.setCellValueFactory(new PropertyValueFactory<>("name"));
        userAccountEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        userAccountDeposit.setCellValueFactory(new PropertyValueFactory<>("deposit"));
        userAccountWithdraw.setCellValueFactory(new PropertyValueFactory<>("withdraw"));
        userAccountSend.setCellValueFactory(new PropertyValueFactory<>("send"));
        userAccountReceive.setCellValueFactory(new PropertyValueFactory<>("receive"));

        senderEmail.setCellValueFactory(new PropertyValueFactory<>("senderEmail"));
        receiverEmail.setCellValueFactory(new PropertyValueFactory<>("receiverEmail"));
        receiveAmount.setCellValueFactory(new PropertyValueFactory<>("receiveAmount"));
        receiveTime.setCellValueFactory(new PropertyValueFactory<>("receiveTime"));

// TODO
        fetchUserData();
        fetchTransactionData();
    }

    //function for getting user data from database starts 
    private void fetchUserData() {
        try {
            int userCount = 0 ;
            con = database.connectDb();
            String que = " select * from userInfo  ";
            pst = con.prepareStatement(que);
            rs = pst.executeQuery();
            while (rs.next()) {
                userCount ++ ;
                String name = rs.getString(1);
                String email = rs.getString(3);

//                for getting total deposit 
                String depositQuery = " select sum(cast(depositAmount AS DECIMAL(10,2)))  FROM deposit  WHERE userEmail = ? ";
                pst = con.prepareStatement(depositQuery);
                pst.setString(1, email);
                ResultSet depositRs = pst.executeQuery();
                String totalDeposit = new String();
                while (depositRs.next()) {
                    totalDeposit = depositRs.getString(1);
                }

                // for getting total withdraw
                String withdrawQuery = " select sum(cast(withdrawAmount AS DECIMAL(10,2)))  FROM withdraw  WHERE userEmail = ? ";
                pst = con.prepareStatement(withdrawQuery);
                pst.setString(1, email);
                ResultSet withdrawRs = pst.executeQuery();
                String totalWithdraw = new String();
                while (withdrawRs.next()) {
                    totalWithdraw = withdrawRs.getString(1);
                }

//                for getting total send amount 
                String sendQuery = " select sum(cast(receiveAmount AS DECIMAL(10,2)))  FROM receive  WHERE senderEmail = ? ";
                pst = con.prepareStatement(sendQuery);
                pst.setString(1, email);
                ResultSet sendRs = pst.executeQuery();
                String totalSend = new String();
                while (sendRs.next()) {
                    totalSend = sendRs.getString(1);
                }

//                for getting total receive amount 
                String receiveQuery = " select sum(cast(receiveAmount AS DECIMAL(10,2)))  FROM receive  WHERE receiverEmail = ? ";
                pst = con.prepareStatement(receiveQuery);
                pst.setString(1, email);
                ResultSet receiveRs = pst.executeQuery();
                String totalReceive = new String();
                while (receiveRs.next()) {
                    totalReceive = receiveRs.getString(1);
                }
//
//                System.out.println("Total deposit = " + totalDeposit);
//                System.out.println("Total totalWithdraw = " + totalWithdraw);
//                System.out.println("Total totalSend = " + totalSend);
//                System.out.println("Total totalReceive = " + totalReceive);
//                System.out.println("");

                userAccounts.add(new UserAccount(name, email, totalDeposit, totalWithdraw, totalSend, totalReceive));
            }
            userTableView.setItems(userAccounts);
         totalUserNumber.setText(String.valueOf(userCount));

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
    //function for getting user data from database  ends 

//    
//    
//    
    // Function to fetch transaction data starts 
    private void fetchTransactionData() {

        try {

            con = database.connectDb();
            String transactionQuery = "SELECT * FROM receive ORDER BY receiveTime DESC";
            pst = con.prepareStatement(transactionQuery);
            ResultSet transactionRs = pst.executeQuery();
            while (transactionRs.next()) {
                String senderEmail = transactionRs.getString("senderEmail");
                String receiverEmail = transactionRs.getString("receiverEmail");
                String receiveAmount = transactionRs.getString("receiveAmount");
                String receiveTime = transactionRs.getString("receiveTime");

                System.out.println("sender email = " + senderEmail);
                System.out.println("receiver email = " + receiverEmail);
                System.out.println("receive amount  email = " + receiveAmount);
                System.out.println("receive time = " + receiveTime);
                System.out.println("");

                transactions.add(new Transaction(senderEmail, receiverEmail, receiveAmount, receiveTime));

            }

            transactionTableView.setItems(transactions);

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

    // Function to fetch transaction ends 
}
