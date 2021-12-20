package lookInnaBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class bookListing {
    private JLabel bookTitleLabel;
    private JLabel authorLabel;
    private JLabel publisherLabel;
    private JLabel isbnLabel;
    private JLabel pagesLabel;
    private JLabel yearLabel;
    private JLabel priceLabel;
    private JLabel stockLabel;
    private JTextField bookTitleField;
    private JTextField authorField;
    private JTextField publishField;
    private JTextField isbnField;
    private JTextField pagesField;
    private JTextField yearField;
    private JTextField priceField;
    private JTextField stockField;
    private JButton listButton;
    private JPanel panel1;

    Connection con;
    Statement state;
    ResultSet rset;
    ResultSet rset2;
    ResultSet rset3;
    PreparedStatement pset;

    public bookListing(String sid) {
        try{
            con = Connect.ConnectDB();
            state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Listing Book");
        frame.setPreferredSize(new Dimension(600,400));
        frame.setSize(600,400);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setVisible(true);
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String last_b_id = null;
                try {
                    rset = state.executeQuery("select b_id from book ORDER BY b_id DESC LIMIT 1;");
                    while(rset.next()){
                        last_b_id = rset.getString(1);
                    }

                    assert last_b_id != null;
                    last_b_id = Integer.toString(Integer.parseInt(last_b_id) + 1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                String bookTitle = bookTitleField.getText();
                String bookAuthor = authorField.getText();
                String bookPublish = publishField.getText();
                int bookISBN = Integer.parseInt(isbnField.getText());
                int bookPages = Integer.parseInt(pagesField.getText());
                int bookYear = Integer.parseInt(yearField.getText());
                int bookPrice = Integer.parseInt(priceField.getText());
                int bookStock = Integer.parseInt(stockField.getText());

                String sql = "insert into book values (?,?,?,?,?,?,?,?,?)";
                String sql2 = "insert into product_seller values (?,?)";
                try {
                    pset = con.prepareStatement(sql);
                    pset.setString(1,last_b_id);
                    pset.setString(2,bookTitle);
                    pset.setString(3,bookAuthor);
                    pset.setString(4,bookPublish);
                    pset.setInt(5,bookISBN);
                    pset.setInt(6,bookPages);
                    pset.setInt(7,bookYear);
                    pset.setInt(8,bookPrice);
                    pset.setInt(9,bookStock);
                    pset.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                try {
                    pset = con.prepareStatement(sql2);
                    pset.setString(1,last_b_id);
                    pset.setString(2,sid);
                    pset.executeUpdate();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                frame.dispose();
                JOptionPane.showMessageDialog(panel1, "Book has been listed.");

            }
        });
    }
}
