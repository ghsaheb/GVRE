package RealEstatePackage;

import java.sql.*;
import java.util.ArrayList;

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
        PreparedStatement stmt = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            stmt = c.prepareStatement("INSERT INTO paid (iid, hid) VALUES (?,?);");
            stmt.setString(1, individual.getUsername());
            stmt.setString(2, id);
//            String sql ;
//            sql = "INSERT INTO paid (iid, hid) " +
//                    "VALUES ('"+ individual.getUsername() + "','" + id + "');";
//            System.out.println(sql);
            stmt.executeUpdate();
            stmt.close();
//            c.commit();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean select(Individual individual, String id) throws IndividualNotFoundException {
        PreparedStatement stmt = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.prepareStatement("SELECT * FROM paid P WHERE P.iid= ? AND P.hid= ? ;");
            stmt.setString(1, individual.getUsername());
            stmt.setString(2, id);
            ResultSet rs = stmt.executeQuery();
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

    public ArrayList<String> select(Individual individual) throws IndividualNotFoundException {
        PreparedStatement stmt = null;
        ArrayList<String> houseIds = new ArrayList<String>();
        try {
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.prepareStatement(" SELECT p.hid FROM paid p WHERE p.iid = ?");
            stmt.setString(1, individual.getUsername());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                houseIds.add(rs.getString("hid"));
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return houseIds;
    }
}
