package lookInnaBook;

import java.awt.*;
import java.sql.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JPanel panel1;
    private JTextField email_Field;
    private JLabel email_Label;
    private JPasswordField password_Field;
    private JLabel password_Label;
    private JButton login_Button;
    private JButton signup_Button;
    private JComboBox<String> loginType;

    private final String url = "jdbc:postgresql://localhost/COMP3005_Project";
    private final String user = "postgres";
    private final String password = "SERAphimon10!";

    private final String[] types = {"User", "Seller"};

    Connection con;
    Statement state;
    ResultSet rset;
    PreparedStatement pset;
    String id;

    public Login()
    {
        connect();
        frame();

    }



    public void connect() {




//        try(con = DriverManager.getConnection(url, user, password);
//            state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
//                    ResultSet.CONCUR_READ_ONLY);) {
//
//            if(con != null) {
//                System.out.println("Connected to PGSQL");
//            }else {
//                System.out.println("No connection... What else do you expect?");
//            }



        try
        {

//            con = DriverManager.getConnection(url, user, password);
            con = Connect.ConnectDB();
            state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            boolean x = true;
//            while (x) {
//
//                ArrayList<String> prereqID = new ArrayList<>();
//                Scanner s = new Scanner(System.in);
//                System.out.println("Enter column name (enter 'quit' to exit program):\t");
//                String a = s.nextLine();
//
//                if (a.equals("quit")) {
//                    x = false;
//
//                }else{
//
////                    ResultSet rset = state.executeQuery("select * " + " from book;");
//                    String temp = "select * from book where b_id = ?;";
//                    PreparedStatement pset = con.prepareStatement(temp);
//                    pset.setString(1, a);
//                    ResultSet rs = pset.executeQuery();
//                    ResultSetMetaData rsmd = rs.getMetaData();
//                    int colNum = rsmd.getColumnCount();
//
////                    while (rset.next()){
////                        for (int i = 1; i <= colNum; i++) {
////                            String colVal = rset.getString(i);
////                            System.out.print(colVal +"\t");
////                        }
////                        System.out.println("");
////                    }
//                    while (rs.next()){
//                        for (int i = 1; i <= colNum; i++) {
//                            String colVal = rs.getString(i);
//                            System.out.print(colVal +"\t");
//                        }
//                        System.out.println("");
//                    }
//
//                    boolean y = true;
//                    System.out.println("\n");
//                    while (y){
//
//                        System.out.println("Would like to search for other courses? (y/n)");
//                        String b = s.nextLine();
//
//                        if (b.equals("n")) {
//                            System.out.println("End of program.");
//                            x = false;
//                            y = false;
//                        }else if (b.equals("y")) {
//                            y = false;
//                        }else{
//                            System.out.println("Please select y/n.");
//                        }
//                    }
//                }
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        }













    }

    public void frame()
    {
        JFrame frame = new JFrame("Login");
        frame.setPreferredSize(new Dimension(600,400));
        frame.setSize(1000,1000);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        for (String type : types) {
            loginType.addItem(type);
        }

        login_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try
                {
                    String temp = email_Field.getText();
                    String temp1 = password_Field.getText();

                    String sql = "select u_id, email, password from users where email = ? and password = ?";
                    String sql2 = "select s_id, email, password from seller where email = ? and password = ?";
                    if (loginType.getSelectedIndex()==0){
                        pset = con.prepareStatement(sql);
                    }else {
                        pset = con.prepareStatement(sql2);
                    }
                    pset.setString(1, temp);
                    pset.setString(2, temp1);

                    rset = pset.executeQuery();

                    int count = 0;
                    while(rset.next())
                    {
                        count = count + 1;
                        id = rset.getString(1);
                    }

                    if (count == 1){
//                        JOptionPane.showMessageDialog(panel1, "true");
                        frame.dispose();
                        if (loginType.getSelectedIndex() == 0) {
                            UserHomePage home = new UserHomePage(id);

                        }else{
                            SellerHomePage seller = new SellerHomePage(id);
                        }


                    }else if (count > 1){
                        JOptionPane.showMessageDialog(panel1, "duplicate user");
                    }else {
                        JOptionPane.showMessageDialog(panel1, "the email/password is invalid.");
                    }

                }
                catch(Exception ex)
                {

                }
            }
        });


        signup_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
//                    WindowEvent closeW = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
//                    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeW);
                    frame.dispose();
                    SignUP signUp = new SignUP();
//                    signUp.setVisible(true);


                }
                catch(Exception ex)
                {

                }

            }
        });

    }


    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        JFrame.setDefaultLookAndFeelDecorated(true);

        new Login();

    }
}
