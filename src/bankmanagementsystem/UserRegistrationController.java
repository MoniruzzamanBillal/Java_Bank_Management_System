package bankmanagementsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.*;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class UserRegistrationController implements Initializable {

    //    databases configurations 
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    @FXML
    private TextField accountName;

    @FXML
    private Button signupBtn;

    @FXML
    private TextField userAddress;

    @FXML
    private TextField userEmail;

    @FXML
    private TextField userName;

    @FXML
    private TextField userNid;

    @FXML
    private PasswordField userPAssword;

    @FXML
    private TextField userPhone;

    @FXML
    void handleSignUp(ActionEvent event) {

        String accountName, userName, userEmail, userNid, userPhone, userAddress, userPassword;
        accountName = this.accountName.getText();
        userName = this.userName.getText();
        userEmail = this.userEmail.getText();
        userNid = this.userNid.getText();
        userPhone = this.userPhone.getText();
        userAddress = this.userAddress.getText();
        userPassword = this.userPAssword.getText();

        try {
            con = database.connectDb();
            String que = "insert into userInfo values(? ,? , ? , ? , ? , ? , ?)";
            pst = con.prepareStatement(que);
            pst.setString(1, accountName);
            pst.setString(2, userName);
            pst.setString(3, userEmail);
            pst.setString(4, userNid);
            pst.setString(5, userPhone);
            pst.setString(6, userAddress);
            pst.setString(7, userPassword);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Registation");
            alert.setHeaderText(null);
            alert.setContentText("user Addedddd!");
            alert.showAndWait();
            
            //        to  hide sign up form 
        signupBtn.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        
        

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
    void gotoLoginPage(ActionEvent event) throws IOException {

//        to  hide sign up form 
        signupBtn.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();





    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
