package RealEstatePackage;

import java.sql.*;

public class PhoneDatabaseController {

    Connection c = null;
    private static PhoneDatabaseController ourInstance = new PhoneDatabaseController();

    public static PhoneDatabaseController getInstance() {
        return ourInstance;
    }

    private PhoneDatabaseController() {
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS paid (\n" +
                    "  iid CHAR(128) NOT NULL,\n" +
                    "  hid CHAR(128) NOT NULL,\n" +
                    "  PRIMARY KEY (iid, hid),\n" +
                    "  FOREIGN KEY (hid) REFERENCES house(id),\n" +
                    "  FOREIGN KEY (iid) REFERENCES individual(username)\n" +
                    ");";
            System.out.println("sql statement:"+sql);
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Individual individual, String id) {
        Statement stmt = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            stmt = c.createStatement();
            String sql ;
            sql = "INSERT INTO paid (iid, hid) " +
                    "VALUES ('"+ individual.getUsername() + "','" + id + "');";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
///            c.commit();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean select(String username, String id) throws IndividualNotFoundException {
        Statement stmt = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM paid P WHERE P.iid='"+username+"' AND P.hid='"+ id +"';");
            boolean result = false;
            if (rs.next() ) {
                result = true;
            }
            rs.close();
            stmt.close();
            c.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
