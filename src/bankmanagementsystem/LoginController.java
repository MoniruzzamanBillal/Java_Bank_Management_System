package bankmanagementsystem;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class LoginController implements Initializable {

    //    databases configurations 
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField userEmail;

    @FXML

    private PasswordField userPassword;

    @FXML
    void handleLogin(ActionEvent event) {

        String userEmail, userPassword;

        userEmail = this.userEmail.getText();
        userPassword = this.userPassword.getText();

        try {

            con = database.connectDb();
            String que = "Select  * from userInfo where userEmail=? and userPassword=?";
            pst = con.prepareStatement(que);
            pst.setString(1, userEmail);
            pst.setString(2, userPassword);

            rs = pst.executeQuery();

            System.out.println(rs.next());

            if (rs.next() == false) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("User name or password is incorrect!!");
                alert.showAndWait();
                this.userEmail.setText("");
                this.userPassword.setText("");

            }

            while (rs.next()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Login!");
                alert.showAndWait();

                System.out.println(rs.getString("userName"));
                System.out.println(rs.getString("userEmail"));
                System.out.println(rs.getString("userPassword"));
                System.out.println(rs.getString("userAddress"));

//                 tot hide login  form 
//                    loginBtn.getScene().getWindow().hide();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
