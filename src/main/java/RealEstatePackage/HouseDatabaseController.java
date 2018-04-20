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
                    "\t`building_type`\tchar ( 128 ) NOT NULL,\n" +
                    "\t`image_URL`\tchar ( 128 ),\n" +
                    "\t`deal_type`\tchar ( 128 ) NOT NULL,\n" +
                    "\t`expire_time`\tinteger ( 128 ),\n" +
                    "\t`base_price`\tINTEGER ( 128 ),\n" +
                    "\t`rent_sell_price`\tINTEGER ( 128 )NOT NULL,\n" +
                    "\t`iid`\tchar ( 128 ),\n" +
                    "\t`reid`\tchar ( 128 ),\n" +
                    "\tPRIMARY KEY(`id`)\n" +
                    "\tFOREIGN KEY(iid) REFERENCES individual(username)\n" +
                    "\tFOREIGN KEY(reid) REFERENCES real_estate(url)\n" +
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

    public void insert(House house, RealEstate realEstate) {
        Statement stmt = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            stmt = c.createStatement();
            String sql ;
            if (!house.isDealType()){
                sql = "INSERT INTO house (id,area,building_type,image_URL,deal_type,expire_time,base_price,rent_sell_price,iid, reid) " +
                        "VALUES ('"+ house.getId() + "',"
                        + house.getArea() + ",'" +  house.getBuildingType() + "','" + house.getImageURL() + "','" + String.valueOf(house.isDealType()) + "'," + house.getExpireTime() + "," +
                         "null" + "," +house.getPrice().getSellPrice() + "," + "null" + ",'" + realEstate.getURL() + "');";
            }
            else {
                sql = "INSERT INTO house (id,area,building_type,image_URL,deal_type,expire_time,base_price,rent_sell_price,iid, reid) " +
                        "VALUES ('"+ house.getId() + "',"
                        + house.getArea() + ",'" +  house.getBuildingType() + "','" + house.getImageURL() + "','" + String.valueOf(house.isDealType()) + "'," + house.getExpireTime() + "," +
                        house.getPrice().getBasePrice() + "," + house.getPrice().getRentPrice() + "," + "null" + ",'" + realEstate.getURL() + "');";
            }
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
///            c.commit();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(House house, Individual individual) {
        Statement stmt = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            stmt = c.createStatement();
            String sql ;
            if (!house.isDealType()){
                sql = "INSERT INTO house (id,area,building_type,image_URL,deal_type,expire_time,base_price,rent_sell_price,iid, reid) " +
                        "VALUES ('"+ house.getId() + "',"
                        + house.getArea() + ",'" +  house.getBuildingType() + "','" + house.getImageURL() + "','" + String.valueOf(house.isDealType()) + "'," + house.getExpireTime() + "," +
                        house.getPrice().getSellPrice() + "," + "null" + ",'" + individual.getUsername() + "'," + "null" + ");";
            }
            else {
                sql = "INSERT INTO house (id,area,building_type,image_URL,deal_type,expire_time,base_price,rent_sell_price,iid, reid) " +
                        "VALUES ('"+ house.getId() + "',"
                        + house.getArea() + ",'" +  house.getBuildingType() + "','" + house.getImageURL() + "','" + String.valueOf(house.isDealType()) + "'," + house.getExpireTime() + "," +
                        house.getPrice().getBasePrice() + "," + house.getPrice().getRentPrice() + "," + "null" + ",'" + individual.getUsername() + "'," + "null" + ");";
            }
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
///            c.commit();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<House> select(long area, String dealType, String buildingType, int maxPrice){
        Statement stmt = null;
        ArrayList<House> houses = new ArrayList<House>();
        try {
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs;
            if (dealType == null && buildingType == null){
                rs = stmt.executeQuery( "SELECT * FROM house H WHERE H.area >= " + area + " AND H.rent_sell_price <= " + maxPrice + ";");
            }
            else if (dealType == null && buildingType != null){
                rs = stmt.executeQuery( "SELECT * FROM house H WHERE H.area >= " + area + " AND H.rent_sell_price <= " + maxPrice + " AND H.building_type = '" + buildingType + "';");
            }
            else if (dealType != null && buildingType == null){
                rs = stmt.executeQuery( "SELECT * FROM house H WHERE H.area >= " + area + " AND H.rent_sell_price <= " + maxPrice + " AND H.deal_type = '" + dealType + "';");
            }
            else {
                rs = stmt.executeQuery( "SELECT * FROM house H WHERE H.area >=" + area + " AND H.rent_sell_price <=" + maxPrice + " AND H.deal_type = '" + dealType + "' AND H.building_type = '" + buildingType + "';");
            }
            while (rs.next()) {
                House s;
                System.out.println("get Boolean:"+Boolean.parseBoolean(rs.getString("deal_type")));
                if (!Boolean.parseBoolean(rs.getString("deal_type"))) {
                    s = new House(rs.getString("id"), rs.getLong("area"), rs.getString("building_type"),
                            null, Boolean.parseBoolean(rs.getString("deal_type")), new Price(0, 0, rs.getInt("rent_sell_price")), null, null);
                }
                else {
                    s = new House(rs.getString("id"), rs.getLong("area"), rs.getString("building_type"),
                            null, Boolean.parseBoolean(rs.getString("deal_type")),  new Price(rs.getInt("base_price"), rs.getInt("rent_sell_price"), 0), null, null);
                }
                houses.add(s);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Operation done successfully");
        return houses;
    }

}
