package RealEstatePackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class RealEstateDatabaseController {

    static Connection c;

    private static RealEstateDatabaseController ourInstance = new RealEstateDatabaseController();

    public static RealEstateDatabaseController getInstance() {
        return ourInstance;
    }

    private RealEstateDatabaseController() {
    }

    public static void main(String[] args) { // OH MY GOD
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
//            c.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        System.out.println("INJA");
        insert(new RealEstate("khaneBeDoosh", "http://acm.ut.ac.ir/khaneBeDoosh/house"));
        ArrayList<RealEstate> re = select();
        System.out.println(re.get(0).getURL());
    }

    public static void insert(RealEstate realEstate) {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql = "INSERT INTO real_estate (url,name) " +
                    "VALUES ('"+ realEstate.getURL() + "','"
                    + realEstate.getName() + "');";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
//            c.commit();
//            c.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public static ArrayList<RealEstate> select() {
        Statement stmt = null;
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM real_estate;" );

            ArrayList<RealEstate> realEstates = new ArrayList<RealEstate>();

            while ( rs.next() ) {
                String name = rs.getString("name");
                String url  = rs.getString("url");
                RealEstate realEstate = new RealEstate(name, url);
                realEstates.add(realEstate);
            }
            rs.close();
            stmt.close();
//            c.close();
            return realEstates;
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        System.out.println("Operation done successfully");
        return null;
    }
}
