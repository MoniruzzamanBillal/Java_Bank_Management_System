package bankmanagementsystem;

import java.sql.*;

public class database {

    public static Connection connectDb() {
        try {

            Class.forName("com.mysql.jdbc.Driver");

            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/bankmanagement", "root", "admin");
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
