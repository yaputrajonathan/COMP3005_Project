package lookInnaBook;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Summary {
    private JLabel summaryLabel;
    private JLabel bookListLabel;
    private JList<String> bookList;
    private JLabel amountLabel;
    private JList<Integer> amountList;
    private JLabel priceLabel;
    private JList<String> priceList;
    private JLabel courLabel;
    private JLabel courNameLabel;
    private JLabel courPriceLabel;
    private JLabel totalLabel;
    private JLabel total;
    private JPanel panel1;
    private JLabel addressLabel;
    private JLabel address;
    private JButton payButton;
    private JLabel billLabel;
    private JLabel billing;

    String bookTitle;
    String bookID;
    int book_Stock;


    Connection con;
    Statement state;
    ResultSet rset;
    ResultSet rset2;
    ResultSet rset3;
    ResultSet rset4;
    ResultSet rset5;

    PreparedStatement pset;

    String street, country, city, province, zipCode, courName, last_order_id;
    int  courPrice ,courDays;
    long cardNum, cvv;

    public Summary (String uid, String aid, String bid, String cid) {
        try{
            con = Connect.ConnectDB();
            state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultListModel<String> bookModel = new DefaultListModel<>();
        DefaultListModel<String> priceModel = new DefaultListModel<>();
        DefaultListModel<Integer> costModel = new DefaultListModel<>();
        DefaultListModel<Integer> amountModel = new DefaultListModel<>();

//        DefaultListModel<String> addressModel = new DefaultListModel<>();
//        DefaultListModel<String> billModel = new DefaultListModel<>();
//        DefaultListModel<String> courModel = new DefaultListModel<>();


        bookList.setModel(bookModel);
        priceList.setModel(priceModel);
        amountList.setModel(amountModel);

        try {
            assert state != null;
            rset = state.executeQuery("select b_id, book_name, price, stock, book_amount from basket natural join book where u_id = '" + uid + "'");
            while(rset.next()){
//                rset2 = state.executeQuery("select book_name from book where b_id = '"+ (rset.getString(1)) + "'");


                bookModel.addElement(rset.getString(2));
                priceModel.addElement("$"+rset.getInt(3));
                costModel.addElement(rset.getInt(3)*rset.getInt(5));
                amountModel.addElement(rset.getInt(5));
                book_Stock = rset.getInt(4);
//                bookIDs.add(rset.getString(1));

            }

            rset2 = state.executeQuery("select * from address where u_id ='" + uid +"' and a_id = '" + aid +"'");
            while(rset2.next()) {

                street = rset2.getString(3);
                country = rset2.getString(4);
                city = rset2.getString(5);
                province = rset2.getString(6);
                zipCode = rset2.getString(7);
                address.setText(street + " " + city + " " + province + " " + country + " " + zipCode);
            }

            rset3 = state.executeQuery("select * from courier where cour_id = '" + cid + "'");
            while(rset3.next()) {
                courName = rset3.getString(2);
                courPrice = rset3.getInt(5);
                courDays = rset3.getInt(6);

                courNameLabel.setText(courName);
                courPriceLabel.setText("$"+courPrice);
            }

            rset5 = state.executeQuery("select * from billing where bill_id = '" + bid + "'");
            while(rset5.next()) {
                cardNum = rset5.getLong(3);
                cvv = rset5.getLong(4);

                billing.setText(String.valueOf(cardNum));
//                courPriceLabel.setText("$"+courPrice);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JFrame frame = new JFrame("Your Summary");
        frame.setSize(1000,1000);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setVisible(true);

        total.setText("$" + (findCost(costModel)+ courPrice));
        String sql = "insert into orders values (?,?,?,?,?,?,?,?,?,?,?)";
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SimpleDateFormat d = new SimpleDateFormat("dd");
                SimpleDateFormat m = new SimpleDateFormat("MM");
                SimpleDateFormat y = new SimpleDateFormat("yyyy");

                Date now = new Date();

                int day = Integer.parseInt(d.format(now));
                int month = Integer.parseInt(m.format(now));
                int year = Integer.parseInt(y.format(now));


                try {
                    rset4 = state.executeQuery("select o_id from orders ORDER BY o_id DESC LIMIT 1;");
                    while(rset4.next()) {
                        last_order_id = rset4.getString(1);
                    }
                    if (last_order_id == null) { last_order_id = "10001";}
                    last_order_id = Integer.toString(Integer.parseInt(last_order_id) + 1);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                for(int i = 0; i < bookModel.size(); i++) {

                    try {
                        rset4 = state.executeQuery("select o_id from orders ORDER BY o_id DESC LIMIT 1;");
                        while(rset4.next()) {
                            last_order_id = rset4.getString(1);
                        }
                        assert last_order_id != null;
                        last_order_id = Integer.toString(Integer.parseInt(last_order_id) + 1);

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }


                    try {
                        pset = con.prepareStatement(sql);
                        pset.setString(1, last_order_id);
                        pset.setString(2, uid);
                        pset.setString(3,findID(bookModel.get(i)));
                        pset.setString(4,bid);
                        pset.setInt(5,day);
                        pset.setInt(6,month);
                        pset.setInt(7,year);
                        pset.setString(8,"Processing");
                        pset.setString(9,cid);
                        pset.setInt(10,amountModel.get(i));
                        pset.setInt(11,findCost(costModel));
                        pset.executeUpdate();

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                try {
                    state.executeUpdate("delete from basket where u_id = '" + uid + "'");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


                frame.dispose();
                JOptionPane.showMessageDialog(panel1, "Your Order is currently being processed");
            }
        });
    }

    private String findID (String b) {
        try {
            rset3 = state.executeQuery("select b_id from book where book_name = '" + b + "'");
            if (rset3.next()) {
                return rset3.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Integer findCost(DefaultListModel<Integer> l ) {
        if (l.isEmpty()){ return 0; }
        int temp = 0;
        for(int i = 0; i < l.getSize(); i++) {
            temp = temp + l.get(i);
        }
        return temp;
    }


}
