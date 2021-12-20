package lookInnaBook;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class Basket {
    private JLabel basketLabel;
    private JList<String> basketList;
    private JLabel totalLabel;
    private JLabel total;
    private JPanel panel1;
    private JButton deleteButton;
    private JList<Integer> amountList;
    private JLabel amountLabel;
    private JButton checkoutButton;
    private JList<String> priceList;
    private JLabel priceLabel;
    private JScrollPane baskScroll;
    private JScrollPane amountScroll;


    String bookTitle;
    String bookID;
    int book_Stock;


    Connection con;
    Statement state;
    ResultSet rset;
    int rset2;
    ResultSet rset3;
    PreparedStatement pset;

    ArrayList<String> bookIDs = new ArrayList<>();

    public Basket(String id) {
        try{
            con = Connect.ConnectDB();
            state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultListModel<String> baskModel = new DefaultListModel<>();
        DefaultListModel<String> priceModel = new DefaultListModel<>();
        DefaultListModel<Integer> costModel = new DefaultListModel<>();
        DefaultListModel<Integer> amountModel = new DefaultListModel<>();



        basketList.setModel(baskModel);
        priceList.setModel(priceModel);
        amountList.setModel(amountModel);




        try {
            assert state != null;
            rset = state.executeQuery("select b_id, book_name, price, stock, book_amount from basket natural join book where u_id = '" + id + "'");
            while(rset.next()){
//                rset2 = state.executeQuery("select book_name from book where b_id = '"+ (rset.getString(1)) + "'");


                baskModel.addElement(rset.getString(2));
                priceModel.addElement("$"+rset.getInt(3));
                costModel.addElement(rset.getInt(3)*rset.getInt(5));
                amountModel.addElement(rset.getInt(5));
                book_Stock = rset.getInt(4);
//                bookIDs.add(rset.getString(1));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JFrame frame = new JFrame("Your Basket");
        frame.setSize(1000,1000);
        frame.setContentPane(panel1);
        deleteButton.setEnabled(false);
        frame.pack();
        frame.setVisible(true);

        total.setText("$" + findCost(costModel));

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = baskModel.indexOf(bookTitle);
                Integer amount = amountModel.get(index);
                System.out.println(amount);
                bookID = findID(bookTitle);
                try {
//                    System.out.println(id + bookTitle);
                    rset2 = state.executeUpdate("delete from basket where u_id ='" + id + "' and b_id = '" + bookID + "'");
                    state.executeUpdate("update book set stock ='" + (book_Stock+amount) + "' where b_id = '" + bookID + "'" );
                    baskModel.removeElement(bookTitle);
                    priceModel.removeElementAt(index);
                    costModel.removeElementAt(index);
                    amountModel.removeElementAt(index);
                    total.setText("$" + findCost(costModel));
//                    panel1.revalidate();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

        basketList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                deleteButton.setEnabled(true);
                bookTitle = basketList.getSelectedValue();
            }
        });
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                billingAndAddress billAdd = new billingAndAddress(id, "123");
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
        Integer temp = 0;
        for(int i = 0; i < l.getSize(); i++) {
            temp = temp + l.get(i);
        }
        return temp;
    }
}
