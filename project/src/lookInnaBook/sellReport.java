package lookInnaBook;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class sellReport {
    private JPanel panel1;
    private JLabel reportLabel;
    private JLabel soldListLabel;
    private JList<String> soldList;
    private JLabel amountLabel;
    private JList<Integer> amountList;
    private JLabel totalLabel;
    private JLabel total;
    private JLabel dateLabel;
    private JList<String> dateList;

    Connection con;
    Statement state;
    ResultSet rset;
    ResultSet rset2;
    ResultSet rset3;
    PreparedStatement pset;
    int income = 0;
    int d,m,y;

    public sellReport(String sid) {
        try{
            con = Connect.ConnectDB();
            state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultListModel<String> bookModel = new DefaultListModel<>();
        DefaultListModel<Integer> amountModel = new DefaultListModel<>();
        DefaultListModel<String> dateModel = new DefaultListModel<>();

        soldList.setModel(bookModel);
        amountList.setModel(amountModel);
        dateList.setModel(dateModel);

        try {
            assert state != null;
//            rset = state.executeQuery("select * from seller natural join product_seller natural join book natural join basket where s_id = '" + sid + "'");
            rset = state.executeQuery("select * from orders natural join product_seller where s_id = '" + sid + "'");

            while(rset.next()) {
                bookModel.addElement(rset.getString(1));
                amountModel.addElement(rset.getInt(10));
//                income += ((rset.getInt(17))*(rset.getInt(14)));
                income = rset.getInt(11);

                d = rset.getInt(5);
                m = rset.getInt(6);
                y = rset.getInt(7);

                dateModel.addElement(d + ":" + m + ":" + y);
            }
            total.setText("$" + income);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < bookModel.size(); i++) {
            bookModel.set(i, findTitle(bookModel.get(i)));
        }

        JFrame frame = new JFrame("Sales Report");
        frame.setPreferredSize(new Dimension(600,400));
        frame.setContentPane(panel1);
        frame.pack();
        frame.setVisible(true);
    }

    private String findTitle (String b) {
        try {
            rset3 = state.executeQuery("select book_name from book where b_id = '" + b + "'");
            if (rset3.next()) {
                return rset3.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
