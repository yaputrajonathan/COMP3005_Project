package lookInnaBook;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class test {

    private final String url = "jdbc:postgresql://localhost/COMP3005_Project";
    private final String user = "postgres";
    private final String password = "SERAphimon10!";

    private void Q6() {
        try(Connection con = DriverManager.getConnection(url, user, password);
            Statement state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);) {

            if(con != null) {
                System.out.println("Connected to PGSQL");
            }else {
                System.out.println("No connection... What else do you expect?");
            }

            boolean x = true;
            while (x) {

                ArrayList<String> prereqID = new ArrayList<>();
                Scanner s = new Scanner(System.in);
                System.out.println("Enter column name (enter 'quit' to exit program):\t");
                String a = s.nextLine();

                if (a.equals("quit")) {
                    x = false;

                }else{

//                    ResultSet rset = state.executeQuery("select * " + " from book;");
                    String temp = "select * from book where b_id = ?;";
                    PreparedStatement pset = con.prepareStatement(temp);
                    pset.setString(1, a);
                    ResultSet rs = pset.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int colNum = rsmd.getColumnCount();

//                    while (rset.next()){
//                        for (int i = 1; i <= colNum; i++) {
//                            String colVal = rset.getString(i);
//                            System.out.print(colVal +"\t");
//                        }
//                        System.out.println("");
//                    }
                    while (rs.next()){
                        for (int i = 1; i <= colNum; i++) {
                            String colVal = rs.getString(i);
                            System.out.print(colVal +"\t");
                        }
                        System.out.println("");
                    }

                    boolean y = true;
                    System.out.println("\n");
                    while (y){

                        System.out.println("Would like to search for other courses? (y/n)");
                        String b = s.nextLine();

                        if (b.equals("n")) {
                            System.out.println("End of program.");
                            x = false;
                            y = false;
                        }else if (b.equals("y")) {
                            y = false;
                        }else{
                            System.out.println("Please select y/n.");
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void findPrereq(ResultSet res, String s, ArrayList<String> arr) throws SQLException {

        res.first();
        if (s.equals(res.getString("course_id"))) {
            if(!(arr.contains(res.getString("prereq_id")))){
                arr.add(res.getString("prereq_id"));
                findPrereq(res, res.getString("prereq_id"), arr);
            }
        }
        while (res.next()) {
            if (s.equals(res.getString("course_id"))) {
                if(!(arr.contains(res.getString("prereq_id")))){
                    arr.add(res.getString("prereq_id"));
                    findPrereq(res, res.getString("prereq_id"), arr);
                }
            }
        }
    }

    public static void main(String[] args) {
        test test1 = new test();
        test1.Q6();

    }

}
