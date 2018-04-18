package RealEstatePackage;

import java.sql.*;
import java.util.ArrayList;

public class HouseDatabaseController {
    static Connection c = null;

    private static HouseDatabaseController ourInstance = new HouseDatabaseController();

    public static HouseDatabaseController getInstance() {
        return ourInstance;
    }

    private HouseDatabaseController() {}

    public static void main(String[] args) { // OH MY GOD
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE `house` (\n" +
                    "\t`id`\tchar NOT NULL,\n" +
                    "\t`area`\tinteger ( 128 ) NOT NULL,\n" +
                    "\t`buildingType`\tchar ( 128 ) NOT NULL,\n" +
                    "\t`imageURL`\tchar ( 128 ),\n" +
                    "\t`dealType`\tchar ( 128 ) NOT NULL,\n" +
                    "\t`expireTime`\tinteger ( 128 ) NOT NULL,\n" +
                    "\t`basePrice`\tchar ( 128 ) NOT NULL,\n" +
                    "\t`rentPrice`\tchar ( 128 ),\n" +
                    "\t`iid`\tchar ( 128 ),\n" +
                    "\t`reid`\tchar ( 128 ),\n" +
                    "\tPRIMARY KEY(`id`)\n" +
                    "\tFOREIGN KEY(iid) REFERENCES individual(username)\n" +
                    "\tFOREIGN KEY(reid) REFERENCES real_estate(url)\n" +
                    ");";
            System.out.println("sql statement:"+sql);
            stmt.executeUpdate(sql);
            stmt.close();
//            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("INJA");
        insert(new House("ghazaliii", 100, "آپارتمان", "ekbatan", false, new Price(0, 0, 100), "091228", "h"));
    }

    public static void insert(House house) {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql = "INSERT INTO house (id,area,buildingType,imageURL,dealType,expireTime,basePrice,rentPrice,iid, reid) " +
                    "VALUES ('"+ house.getId() + "',"
                    + house.getArea() + ",'" +  house.getBuildingType() + "','" + "ghazali.com" + "','" + String.valueOf(house.isDealType()) + "'," + 123 + "," +
                    house.getPrice().getSellPrice() + "," + "null" + "," + "'asghar'" + "," + "null" + ");";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
//            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

//    public static ArrayList<RealEstate> select() {
//        Statement stmt = null;
//        try {
//            c.setAutoCommit(false);
//            System.out.println("Opened database successfully");
//
//            stmt = c.createStatement();
//            ResultSet rs = stmt.executeQuery( "SELECT * FROM real_estate;" );
//
//            ArrayList<RealEstate> realEstates = new ArrayList<RealEstate>();
//
//            while ( rs.next() ) {
//                String name = rs.getString("name");
//                String url  = rs.getString("url");
//                RealEstate realEstate = new RealEstate(name, url);
//                realEstates.add(realEstate);
//            }
//            rs.close();
//            stmt.close();
////            c.close();
//            return realEstates;
//        } catch ( Exception e ) {
//            e.printStackTrace();
//        }
//        System.out.println("Operation done successfully");
//        return null;
//    }

}
