package lookInnaBook;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SellerHomePage {
    private JLabel listLabel;
    private JList<String> sellList;
    private JPanel panel1;
    private JButton addButton;
    private JButton reportButton;
    private JButton logoutButton;
    private JButton deleteButton;
    private JButton refreshButton;

    Connection con;
    Statement state;
    ResultSet rset;
    ResultSet rset2;
    ResultSet rset3;
    PreparedStatement pset;

    public SellerHomePage(String sid) {
        try{
            con = Connect.ConnectDB();
            state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultListModel<String> listModel = new DefaultListModel<>();
        sellList.setModel(listModel);

        try {
            assert state != null;
            rset = state.executeQuery("select * from seller natural join product_seller natural join book where s_id = '" + sid + "'");
            while(rset.next()) {
                listModel.addElement(rset.getString(8));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        deleteButton.setEnabled(false);
        JFrame frame = new JFrame("Home Page");
        frame.setPreferredSize(new Dimension(600,400));
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Login login = new Login();
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookListing bookList = new bookListing(sid);
                frame.revalidate();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBook(findID(sellList.getSelectedValue()), sid);
                listModel.removeElement(sellList.getSelectedValue());

            }
        });

        sellList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                deleteButton.setEnabled(true);
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                SellerHomePage sell = new SellerHomePage(sid);
            }
        });
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellReport rep = new sellReport(sid);
            }
        });
    }

    private void deleteBook(String bid, String sid) {
        try {
            state.executeUpdate("delete from product_seller where b_id = '" + bid + "' and s_id = '" + sid + "'");
            state.executeUpdate("delete from book where b_id = '" + bid + "'");

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
