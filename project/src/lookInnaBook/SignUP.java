package lookInnaBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SignUP extends JFrame{
    private JLabel email_Label;
    private JTextField email_Field;
    private JLabel password_Label;
    private JPasswordField password_Field;
    private JLabel confPass_Label;
    private JPasswordField confPass_Field;
    private JButton signup_Button;
    private JPanel panel1;
    private JLabel f_name;
    private JLabel l_name;
    private JLabel phone_no;
    private JTextField f_name_field;
    private JTextField l_name_field;
    private JTextField phone_field;
    private JLabel billingCat;
    private JLabel personalCat;
    private JLabel cardNumLabel;
    private JTextField cardNumField;
    private JLabel cvvLabel;
    private JPasswordField cvvField;
    private JLabel addressLabel;
    private JLabel streetLabel;
    private JLabel countryLabel;
    private JLabel provinceLabel;
    private JLabel cityLabel;
    private JLabel zipcodeLabel;
    private JTextField streetField;
    private JTextField countryField;
    private JTextField provinceField;
    private JTextField cityField;
    private JTextField zipcodeField;
    private JComboBox<String> userType;
    private JButton backButton;

    private final String[] types = {"User", "Seller"};

    Connection con;
    Statement state;
    ResultSet rset;
    ResultSet rset2;
    ResultSet rset3;
    ResultSet rset4;

    PreparedStatement pset;
    PreparedStatement pset2;
    PreparedStatement pset3;
    PreparedStatement pset4;


//    public static void main(String[] args) {
////        EventQueue.invokeLater(new Runnable() {
////            @Override
////            public void run() {
////                try {
////                    SignUP signUp = new SignUP();
//////                    signUp.setVisible(true);
////                }
////                catch (Exception e) {
////                    e.printStackTrace();
////                }
////            }
////        });
////    }

    public SignUP() {
        try{
            con = Connect.ConnectDB();
            state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Sign Up");
        frame.setPreferredSize(new Dimension(600,1000));
        frame.setSize(600,400);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setVisible(true);

        for (String type : types) {
            userType.addItem(type);
        }


        signup_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String last_id = null;
                String last_bill_id = null;
                String last_address_id = null;
                String last_s_id = null;

                try {

                    rset = state.executeQuery("select u_id from users ORDER BY u_id DESC LIMIT 1;");
                    while(rset.next()){
                        last_id = rset.getString(1);
                    }

                    rset2 = state.executeQuery("select bill_id from billing ORDER BY u_id DESC LIMIT 1;");
                    while(rset2.next()){
                        last_bill_id = rset2.getString(1);
                    }

                    rset3 = state.executeQuery("select a_id from address ORDER BY u_id DESC LIMIT 1;");
                    while(rset3.next()){
                        last_address_id = rset3.getString(1);
                    }

                    rset4 = state.executeQuery("select s_id from seller ORDER BY s_id DESC LIMIT 1;");
                    while(rset4.next()){
                        last_s_id = rset4.getString(1);
                    }
                    assert last_id != null;
                    last_id = Integer.toString(Integer.parseInt(last_id) + 1);
                    assert last_bill_id != null;
                    last_bill_id = Integer.toString(Integer.parseInt(last_bill_id) + 1);
                    assert last_address_id != null;
                    last_address_id = Integer.toString(Integer.parseInt(last_address_id) + 1);
                    assert last_s_id != null;
                    last_s_id = Integer.toString(Integer.parseInt(last_s_id) + 1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                // personal info
                String email = email_Field.getText();
                String password = password_Field.getText();
                String confPass = confPass_Field.getText();
                String fName    = f_name_field.getText();
                String lName    = l_name_field.getText();
                int phone   = Integer.parseInt(phone_field.getText());



                // address info
                String street = streetField.getText();
                String country = countryField.getText();
                String province = provinceField.getText();
                String city = cityField.getText();
                String zipCode = zipcodeField.getText();

                String sql = "insert into users values (?,?,?,?,?,?)";
                String sql2 = "insert into billing values (?,?,?,?)";
                String sql3 = "insert into address values (?,?,?,?,?,?,?)";
                String sql4 = "insert into seller (s_id, f_name, l_name, email, phone_no, password) values (?,?,?,?,?,?)";

                if(password.equals(confPass)) {
                    if (userType.getSelectedIndex() == 0) {

                        // billing info
                        int cardNum = Integer.parseInt(cardNumField.getText());
                        int cvv = Integer.parseInt(cvvField.getText());
                        try {

                            // personal info
                            pset = con.prepareStatement(sql);
                            pset.setString(1,last_id);
                            pset.setString(2,fName);
                            pset.setString(3,lName);
                            pset.setString(4,email);
                            pset.setInt(5,phone);
                            pset.setString(6,password);
                            int x = pset.executeUpdate();

                            // billing info
                            pset2 = con.prepareStatement(sql2);
                            pset2.setString(1, last_bill_id);
                            pset2.setString(2, last_id);
                            pset2.setInt(3,cardNum);
                            pset2.setInt(4,cvv);
                            int x2 = pset2.executeUpdate();

                            // address info
                            pset3 = con.prepareStatement(sql3);
                            pset3.setString(1,last_address_id);
                            pset3.setString(2,last_id);
                            pset3.setString(3,street);
                            pset3.setString(4,country);
                            pset3.setString(5,city);
                            pset3.setString(6,province);
                            pset3.setString(7,zipCode);
                            int x3 = pset3.executeUpdate();



                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                    }else {
                        try {
                            pset4 = con.prepareStatement(sql4);
                            pset4.setString(1, last_s_id);
                            pset4.setString(2,fName);
                            pset4.setString(3,lName);
                            pset4.setString(4,email);
                            pset4.setInt(5,phone);
                            pset4.setString(6,password);
                            pset4.executeUpdate();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                    }

                    JOptionPane.showMessageDialog(panel1, "Account is created. Try logging in again.");
                    frame.dispose();
                    Login logIn = new Login();

                }else{
                    JOptionPane.showMessageDialog(panel1, "Passwords are not the same.");
                }
                
                int colNum = 0;
                
                try {
                    rset = state.executeQuery("select * from users;");
                    ResultSetMetaData rsmd = rset.getMetaData();
                    colNum = rsmd.getColumnCount();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
//                while (true){
//                    try {
//                        if (!rset.next()) break;
//                    } catch (SQLException ex) {
//                        ex.printStackTrace();
//                    }
//                    for (int i = 1; i <= colNum; i++) {
//                        String colVal = null;
//                        try {
//                            colVal = rset.getString(i);
//                        } catch (SQLException ex) {
//                            ex.printStackTrace();
//                        }
//                        System.out.print(colVal +"\t");
//                        }
//                        System.out.println("");
//                    }


            }
        });
        userType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userType.getSelectedIndex() == 1) {
                    cardNumField.setEditable(false);
                    cvvField.setEditable(false);
                    streetField.setEditable(false);
                    countryField.setEditable(false);
                    provinceField.setEditable(false);
                    cityField.setEditable(false);
                    zipcodeField.setEditable(false);
                } else {
                    cardNumField.setEditable(true);
                    cvvField.setEditable(true);
                    streetField.setEditable(true);
                    countryField.setEditable(true);
                    provinceField.setEditable(true);
                    cityField.setEditable(true);
                    zipcodeField.setEditable(true);
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Login log = new Login();
            }
        });
    }
}
