package lookInnaBook;

import javax.swing.*;
import java.sql.*;

public class Orders {
    private JPanel panel1;
    private JLabel ordersLabel;
    private JLabel bookListLabel;
    private JList<String> bookList;
    private JLabel amountLabel;
    private JList<Integer> amountList;
    private JLabel statusLabel;
    private JList<String> statusList;
    private JLabel dateLabel;
    private JList<String> dateList;
    private JLabel courLabel;
    private JList<String> courList;
    private JLabel totalLabel;
    private JLabel total;

    String bookTitle;
    String bookID;
    int book_Stock;


    Connection con;
    Statement state;
    ResultSet rset;
    ResultSet rset2;
    ResultSet rset3;
    ResultSet rset4;

    PreparedStatement pset;

    String street, country, city, province, zipCode, courName, last_order_id, bid, totalCost;
    int d,m,y;

    public Orders(String uid){
        try{
            con = Connect.ConnectDB();
            state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultListModel<String> bookModel = new DefaultListModel<>();
        DefaultListModel<String> statusModel = new DefaultListModel<>();
        DefaultListModel<String> dateModel = new DefaultListModel<>();
        DefaultListModel<Integer> amountModel = new DefaultListModel<>();
        DefaultListModel<String> courModel = new DefaultListModel<>();

        bookList.setModel(bookModel);
        statusList.setModel(statusModel);
        amountList.setModel(amountModel);
        dateList.setModel(dateModel);
        courList.setModel(courModel);

        try {
            assert state != null;

            rset = state.executeQuery("select * from orders where u_id = '" + uid + "'");
            while(rset.next()){
                bookModel.addElement(rset.getString(3));
                amountModel.addElement(rset.getInt(10));
                statusModel.addElement(rset.getString(8));
                courModel.addElement(rset.getString(9));

                d = rset.getInt(5);
                m = rset.getInt(6);
                y = rset.getInt(7);

                dateModel.addElement(d + ":" + m + ":" + y);

                totalCost = rset.getString(11);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        total.setText("$"+totalCost);

        for (int i = 0; i < bookModel.size() ; i++) {
            bookModel.set(i, findTitle(bookModel.get(i)));
        }

        for (int i = 0; i < courModel.size() ; i++) {
            courModel.set(i, findCour(courModel.get(i)));
        }


        JFrame frame = new JFrame("Your Orders");
        frame.setSize(1000,1000);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setVisible(true);

    }

    private String findTitle(String id) {
        try {
            rset3 = state.executeQuery("select book_name from book where b_id = '" + id + "'");
            if (rset3.next()) {
                return rset3.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    private String findCour(String id) {
        try {
            rset3 = state.executeQuery("select cour_name from courier where cour_id = '" + id + "'");
            if (rset3.next()) {
                return rset3.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

//    public static void main(String[] args) {
//        Orders or = new Orders("10012");
//    }

}
