package bank.management.system;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    Login() {

        setTitle("Bank Management System");

        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label = new JLabel(i3);
        label.setBounds(70, 10, 100, 100);
        add(label);

//        header starts 
        JLabel header = new JLabel("Welcome to Rich Bank ");
        header.setBounds(200, 40, 600, 40);
        header.setFont(new Font("osward", Font.BOLD, 40));
        add(header);

//          header ends 
//card no starts 
        JLabel cardNo = new JLabel("Card No :  ");
        cardNo.setBounds(100, 160, 600, 40);
        cardNo.setFont(new Font("Raleway", Font.BOLD, 26));
        add(cardNo);
//card no ends  

//pin no starts 
        JLabel pinNo = new JLabel("Pin No :  ");
        pinNo.setBounds(100, 220, 600, 40);
        pinNo.setFont(new Font("Raleway", Font.BOLD, 26));
        add(pinNo);
//pin no ends 

        setSize(800, 400);
        setVisible(true);
        setLocation(390, 230);
    }

    public static void main(String[] args) {
        new Login();
    }
}
