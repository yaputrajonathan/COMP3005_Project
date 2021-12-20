package lookInnaBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class billingAndAddress {
    private JLabel billingLabel;
    private JList<String> billingList;
    private JLabel addressLabel;
    private JList<String> addressList;
    private JButton continueButton;
    private JPanel panel1;
    private JLabel courierLabel;
    private JList<String> courierList;

    Connection con;
    Statement state;
    ResultSet rset;
    ResultSet rset2;
    ResultSet rset3;
    PreparedStatement pset;

    String street, country, city, province, zipCode, courName;
    int cvv, courPrice, courDays;
    long cardNum;
    ArrayList<String> aid = new ArrayList<>();
    ArrayList<String> bid = new ArrayList<>();
    ArrayList<String> cid = new ArrayList<>();

    public billingAndAddress(String uid, String baskid) {
        try{
            con = Connect.ConnectDB();
            state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultListModel<String> addressModel = new DefaultListModel<>();
        DefaultListModel<String> billModel = new DefaultListModel<>();
        DefaultListModel<String> courModel = new DefaultListModel<>();


        addressList.setModel(addressModel);
        billingList.setModel(billModel);
        courierList.setModel(courModel);

        try {
            assert state != null;
            rset = state.executeQuery("select * from address where u_id = '" + uid + "'");
            while(rset.next()){

                aid.add(rset.getString(1));
                street = rset.getString(3);
                country = rset.getString(4);
                city = rset.getString(5);
                province = rset.getString(6);
                zipCode = rset.getString(7);


                addressModel.addElement(street + " " + country + " " + city + " " + province + " " + zipCode);

            }

            rset2 = state.executeQuery("select * from billing where u_id = '" + uid + "'");
            while(rset2.next()){

                bid.add(rset2.getString(1));
                cardNum = rset2.getLong(3);
                cvv = rset2.getInt(4);

                billModel.addElement(cardNum+"");
            }

            rset3 = state.executeQuery("select * from courier");
            while(rset3.next()){

                cid.add(rset3.getString(1));
                courName = rset3.getString(2);
                courPrice = rset3.getInt(5);
                courDays = rset3.getInt(6);

                courModel.addElement(courName+" Price: $" + courPrice +" Delivery Days: " + courDays);
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JFrame frame = new JFrame("Billing Information");
        frame.setPreferredSize(new Dimension(600,400));
        frame.setContentPane(panel1);
        frame.pack();
        frame.setVisible(true);
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int billIndex = billModel.indexOf(billingList.getSelectedValue());
                int addressIndex = addressModel.indexOf(addressList.getSelectedValue());
                int courIndex = courModel.indexOf(courierList.getSelectedValue());

                frame.dispose();
                Summary summ = new Summary(uid, aid.get(addressIndex), bid.get(billIndex), cid.get(courIndex));
//                System.out.println(aid.get(addressIndex) +" " +bid.get(billIndex) + " "+cid.get(courIndex));
            }
        });
    }

//    public static void main(String[] args) {
//        billingAndAddress test = new billingAndAddress("10012", "12");
//    }
}
