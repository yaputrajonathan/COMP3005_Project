package lookInnaBook;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.sql.*;
import java.text.NumberFormat;

public class bookDisplay {
    private JLabel bookTitleLabel;
    private JLabel bookAuthorLabel;
    private JLabel bookYearLabel;
    private JLabel bookPublishLabel;
    private JLabel bookPagesLabel;
    private JLabel bookISBNLabel;
    private JLabel bookPriceLabel;
    private JLabel bookStockLabel;
    private JLabel bookTitle;
    private JLabel bookAuthor;
    private JLabel bookYear;
    private JLabel bookPublish;
    private JLabel bookPages;
    private JLabel bookISBN;
    private JLabel bookPrice;
    private JLabel bookStock;
    private JPanel panel1;
    private JButton addToBasketButton;
    private JLabel amount;
    private JTextField amountField;

    String bookID = null;
    int book_Stock= 0;
    int bookAmount = 0;

    Connection con;
    Statement state;
    ResultSet rset;
    int rset2;
    PreparedStatement pset;

    public bookDisplay(String t, String id) {
        try{
            con = Connect.ConnectDB();
            state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            assert state != null;
            rset = state.executeQuery("select * from book where book_name = '"+ t + "'");
            while(rset.next()){
//                bookModel.addElement(rset.getString(1));
//                System.out.println(rset.getString(2));
                bookID = rset.getString(1);
                bookTitle.setText(rset.getString(2));
                bookAuthor.setText(rset.getString(3));
                bookPublish.setText(rset.getString(4));
                bookISBN.setText(rset.getString(5));
                bookPages.setText(rset.getString(6));
                bookYear.setText(rset.getString(7));
                bookPrice.setText("$"+rset.getString(8));
                bookStock.setText(rset.getString(9));
                book_Stock = (rset.getInt(9));


            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JFrame frame = new JFrame("Book Information");
        frame.setSize(1000,1000);
        frame.setContentPane(panel1);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
//        JFormattedTextField amountField = new JFormattedTextField(formatter);



        addToBasketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookAmount = Integer.parseInt(amountField.getText());
                try {
                    if(bookAmount > book_Stock) {
                        JOptionPane.showMessageDialog(panel1, "Cannot add to basket, amount is larger than the available stock");
                    }else {
                        rset2 = state.executeUpdate("insert into basket values ('"+ id +"','" + bookID + "','"+ bookAmount +"')");
                        state.executeUpdate("update book set stock ='" + (book_Stock-bookAmount) + "' where b_id = '" + bookID + "'" );
                        JOptionPane.showMessageDialog(panel1, "Book has been added to your basket");

                    }


                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });


    }

//    public static void main(String[] args) {
//        bookDisplay test = new bookDisplay("The Little Prince","10015");
//    }



}
