package lookInnaBook;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;

public class UserHomePage{

    private JList<String> bookList;
    private JScrollPane bookScroll;
    private JPanel panel1;
    private JLabel title;
    private JButton cartButton;
    private JButton orderButton;
    private JButton logoutButton;
    private JTextField searchField;
    private JButton searchButton;
    private JButton addCartButton;


    ArrayList<String> books;
    ArrayList<String> bookID;

    String bookTitle = null;

    Connection con;
    Statement state;
    ResultSet rset;
    int rset2;
    ResultSet rset3;

    PreparedStatement pset;

    public UserHomePage(String id) {
        try{
            con = Connect.ConnectDB();
            state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        books = new ArrayList<>();
        DefaultListModel<String> bookModel = new DefaultListModel<>();
        bookList.setModel(bookModel);
        try {
            assert state != null;
            rset = state.executeQuery("select book_name from book");
            while(rset.next()){
                bookModel.addElement(rset.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JFrame frame = new JFrame("Home Page");
        frame.setPreferredSize(new Dimension(600,400));
        frame.setSize(1000,1000);

//        addCartButton.setEnabled(false);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
//        bookList.addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                addCartButton.setEnabled(true);
//            }
//        });
        bookList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
//                    frame.setVisible(false);
                    bookTitle = bookList.getSelectedValue();
                    bookDisplay bookDisp = new bookDisplay(bookList.getSelectedValue(), id);

                }else if (e.getClickCount() == 1) {
                    bookTitle = bookList.getSelectedValue();
                }
            }
        });
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Basket bask = new Basket(id);
            }
        });

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Orders orders = new Orders(id);
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Login login = new Login();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (searchField.getText() != null && (bookModel.contains(searchField.getText()))) {

                    bookDisplay temp = new bookDisplay(searchField.getText(), id);
                }else {
                    JOptionPane.showMessageDialog(panel1, "Book cannot be found.");
                }
            }
        });

        searchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if ((e.getClickCount() > 0) && (searchField.getText().equals("Search Title"))) {
                    searchField.setText(null);
                }
            }
        });
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

//    public static void main(String[] args) {
//        UserHomePage home = new UserHomePage();
//    }

}
