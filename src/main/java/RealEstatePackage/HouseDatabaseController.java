package RealEstatePackage;

import java.sql.*;
import java.util.ArrayList;

public class HouseDatabaseController {
    static Connection c = null;

    private static HouseDatabaseController ourInstance = new HouseDatabaseController();

    public static HouseDatabaseController getInstance() {
        return ourInstance;
    }

    private HouseDatabaseController() {
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        insert(new House("ghazaliii", 100, "آپارتمان", "ekbatan", false, new Price(0, 0, 100), "091228", "h"));
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
