package RealEstatePackage;

import java.sql.*;
import java.util.ArrayList;

public class RealEstateDatabaseController {

    private static Connection c;

    private static RealEstateDatabaseController ourInstance = new RealEstateDatabaseController();

    static RealEstateDatabaseController getInstance() {
        return ourInstance;
    }

    private RealEstateDatabaseController() {
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS \"real_estate\" (\n" +
                    "  \"name\" char(128),\n" +
                    "  \"url\" char(128) PRIMARY KEY\n" +
                    ");";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        insert(new RealEstate("khaneBeDoosh", "http://acm.ut.ac.ir/khaneBeDoosh/house"));
    }

    public void insert(RealEstate realEstate) {
        Statement stmt = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            stmt = c.createStatement();
            String sql = "INSERT INTO real_estate (url,name) " +
                    "VALUES ('"+ realEstate.getURL() + "','"
                    + realEstate.getName() + "');";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
//            c.commit();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<RealEstate> select() {
        Statement stmt = null;
        ArrayList<RealEstate> realEstates = new ArrayList<RealEstate>();
        try {
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM real_estate;" );
            while ( rs.next() ) {
                String name = rs.getString("name");
                String url  = rs.getString("url");
                RealEstate realEstate = new RealEstate(name, url);
                realEstates.add(realEstate);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Operation done successfully");
        return realEstates;
    }
}
