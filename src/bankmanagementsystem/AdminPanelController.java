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
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminPanelController implements Initializable {

    //    databases configurations 
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    @FXML
    private AnchorPane UsersForm;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        
        
           // Set up column bindings
    userAccountName.setCellValueFactory(new PropertyValueFactory<>("name"));
    userAccountEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    userAccountDeposit.setCellValueFactory(new PropertyValueFactory<>("deposit"));
    userAccountWithdraw.setCellValueFactory(new PropertyValueFactory<>("withdraw"));
    userAccountSend.setCellValueFactory(new PropertyValueFactory<>("send"));
    userAccountReceive.setCellValueFactory(new PropertyValueFactory<>("receive"));
        

// TODO
        fetchUserData();
    }

    //function for getting user data from database 
    private void fetchUserData() {

        try {

            System.out.println("Get data from user ");

            con = database.connectDb();
            String que = " select * from userInfo ";
            pst = con.prepareStatement(que);
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println("result = ");
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
                System.out.println(rs.getString(4));
                System.out.println(rs.getString(5));
                System.out.println(rs.getString(6));
                System.out.println(rs.getString(7));

                String name = rs.getString(1);
                String email = rs.getString(2);
                String deposit = rs.getString(3);
                String withdraw = rs.getString(4);
                String send = rs.getString(5);
                String receive = rs.getString(6);

                userAccounts.add(new UserAccount(name, email, deposit, withdraw, send, receive));
                
                
          

            }
          userTableView.setItems(userAccounts);

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

}
