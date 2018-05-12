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
            String sql = "CREATE TABLE IF NOT EXISTS `house` (\n" +
                    "\t`id`\tchar NOT NULL,\n" +
                    "\t`area`\tinteger ( 128 ) NOT NULL,\n" +
                    "\t`building_type`\tchar ( 128 ) NOT NULL,\n" +
                    "\t`image_URL`\tchar ( 128 ),\n" +
                    "\t`deal_type`\tchar ( 128 ) NOT NULL,\n" +
                    "\t`base_price`\tINTEGER ( 128 ),\n" +
                    "\t`rent_sell_price`\tINTEGER ( 128 )NOT NULL,\n" +
                    "\t`address`\tchar ( 128 )NOT NULL,\n" +
                    "\t`description`\tchar ( 128 ),\n" +
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
        PreparedStatement stmt = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            if (!house.isDealType()){
                stmt = c.prepareStatement("INSERT INTO house (id,area,building_type,image_URL,deal_type,base_price,rent_sell_price,address,description,iid,reid) VALUES (?,?,?,?,?,?,?,?,?,?,?);");
                stmt.setString(1,house.getId());
                stmt.setLong(2,house.getArea());
                stmt.setString(3,house.getBuildingType());
                stmt.setString(4,house.getImageURL());
                stmt.setString(5, String.valueOf(house.isDealType()));
                stmt.setString(6,null);
                stmt.setInt(7,house.getPrice().getSellPrice());
                stmt.setString(8,house.getAddress());
                stmt.setString(9,house.getDescription());
                stmt.setString(10,null);
                stmt.setString(11,realEstate.getURL());

//                sql = "INSERT INTO house (id,area,building_type,image_URL,deal_type,base_price,rent_sell_price,address,description,iid,reid) " +
//                        "VALUES ('"+ house.getId() + "',"
//                        + house.getArea() + ",'" +  house.getBuildingType() + "'," + imageURL + ",'" + String.valueOf(house.isDealType()) + "'," +
//                         "null" + "," +house.getPrice().getSellPrice() + ",'" + house.getAddress() + "'," + description +  "," + "null" + ",'" + realEstate.getURL() + "');";
            }
            else {
                stmt = c.prepareStatement("INSERT INTO house (id,area,building_type,image_URL,deal_type,base_price,rent_sell_price,address,description,iid,reid) VALUES (?,?,?,?,?,?,?,?,?,?,?);");
                stmt.setString(1,house.getId());
                stmt.setLong(2,house.getArea());
                stmt.setString(3,house.getBuildingType());
                stmt.setString(4,house.getImageURL());
                stmt.setString(5, String.valueOf(house.isDealType()));
                stmt.setInt(6,house.getPrice().getBasePrice());
                stmt.setInt(7,house.getPrice().getRentPrice());
                stmt.setString(8,house.getAddress());
                stmt.setString(9,house.getDescription());
                stmt.setString(10,null);
                stmt.setString(11,realEstate.getURL());

//                sql = "INSERT INTO house (id,area,building_type,image_URL,deal_type,base_price,rent_sell_price,address,description,iid,reid) " +
//                        "VALUES ('"+ house.getId() + "',"
//                        + house.getArea() + ",'" +  house.getBuildingType() + "'," + imageURL + ",'" + String.valueOf(house.isDealType()) + "'," +
//                        house.getPrice().getBasePrice() + "," + house.getPrice().getRentPrice() + ",'" + house.getAddress() + "'," + description +  "," + "null" + ",'" + realEstate.getURL() + "');";
            }
            stmt.executeUpdate();
            stmt.close();
//            c.commit();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(House house, Individual individual) { // TODO
        PreparedStatement stmt = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            String description;
            if (house.getDescription() == null){
                description = "null";
            }
            else {
                description = "'" + house.getDescription() + "'";
            }
            String imageURL;
            if (house.getImageURL() == null){
                imageURL = "null";
            }
            else {
                imageURL = "'" + house.getImageURL() + "'";
            }
            String sql ;
            if (!house.isDealType()){
                stmt = c.prepareStatement("INSERT INTO house (id,area,building_type,image_URL,deal_type,base_price,rent_sell_price,address,description,iid,reid) VALUES (?,?,?,?,?,?,?,?,?,?,?);");
                stmt.setString(1,house.getId());
                stmt.setLong(2,house.getArea());
                stmt.setString(3,house.getBuildingType());
                stmt.setString(4,imageURL);
                stmt.setString(5, String.valueOf(house.isDealType()));
                stmt.setString(6,null);
                stmt.setInt(7,house.getPrice().getSellPrice());
                stmt.setString(8,house.getAddress());
                stmt.setString(9,house.getDescription());
                stmt.setString(10,null);
                stmt.setString(11,individual.getUsername());
//                sql = "INSERT INTO house (id,area,building_type,image_URL,deal_type,base_price,rent_sell_price,address,description,iid,reid) " +
//                        "VALUES ('"+ house.getId() + "',"
//                        + house.getArea() + ",'" +  house.getBuildingType() + "'," + imageURL + ",'" + String.valueOf(house.isDealType()) + "'," +
//                        "null" + "," + house.getPrice().getSellPrice() + ",'" + house.getAddress() + "'," + description +  ",'" + individual.getUsername() + "'," + "null" + ");";
            }
            else {
                stmt = c.prepareStatement("INSERT INTO house (id,area,building_type,image_URL,deal_type,base_price,rent_sell_price,address,description,iid,reid) VALUES (?,?,?,?,?,?,?,?,?,?,?);");
                stmt.setString(1,house.getId());
                stmt.setLong(2,house.getArea());
                stmt.setString(3,house.getBuildingType());
                stmt.setString(4,imageURL);
                stmt.setString(5, String.valueOf(house.isDealType()));
                stmt.setInt(6,house.getPrice().getBasePrice());
                stmt.setInt(7,house.getPrice().getRentPrice());
                stmt.setString(8,house.getAddress());
                stmt.setString(9,house.getDescription());
                stmt.setString(10,null);
                stmt.setString(11,individual.getUsername());
//                sql = "INSERT INTO house (id,area,building_type,image_URL,deal_type,base_price,rent_sell_price,address,description,iid, reid) " +
//                        "VALUES ('"+ house.getId() + "',"
//                        + house.getArea() + ",'" +  house.getBuildingType() + "'," + imageURL + ",'" + String.valueOf(house.isDealType()) + "'," +
//                        house.getPrice().getBasePrice() + "," + house.getPrice().getRentPrice() + ",'" + house.getAddress() + "'," + description +  ",'" + individual.getUsername() + "'," + "null" + ");";
            }
//            System.out.println(sql);
            stmt.executeUpdate();
            stmt.close();
//            c.commit();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<House> select(long area, String dealType, String buildingType, int maxPrice){
        System.out.println("here inside select");
        PreparedStatement stmt = null;
        ArrayList<House> houses = new ArrayList<House>();
        try {
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
//            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            ResultSet rs;
            if (dealType == null && buildingType == null){
                stmt = c.prepareStatement("SELECT * FROM house H WHERE H.area >= ? AND H.rent_sell_price <= ?;");
                stmt.setLong(1, area);
                stmt.setInt(2, maxPrice);
                rs = stmt.executeQuery();
            }
            else if (dealType == null && buildingType != null){
                stmt = c.prepareStatement("SELECT * FROM house H WHERE H.area >= ? AND H.rent_sell_price <= ? AND H.building_type = ?;");
                stmt.setLong(1, area);
                stmt.setInt(2, maxPrice);
                rs = stmt.executeQuery();
            }
            else if (dealType != null && buildingType == null){
                stmt = c.prepareStatement("SELECT * FROM house H WHERE H.area >= ? AND H.rent_sell_price <= ? AND H.deal_type = ?;");
                stmt.setLong(1, area);
                stmt.setInt(2, maxPrice);
                stmt.setString(3, dealType);
                rs = stmt.executeQuery();
            }
            else {
                stmt = c.prepareStatement("SELECT * FROM house H WHERE H.area >= ? AND H.rent_sell_price <= ? AND H.deal_type = ? AND H.building_type = ?;");
                stmt.setLong(1, area);
                stmt.setInt(2, maxPrice);
                stmt.setString(1, dealType);
                stmt.setString(1, buildingType);
                rs = stmt.executeQuery();
            }
            while (rs.next()) {
                House s;
                if (!Boolean.parseBoolean(rs.getString("deal_type"))) {
                    s = new House(rs.getString("id"), rs.getLong("area"), rs.getString("building_type"),
                            rs.getString("address"), Boolean.parseBoolean(rs.getString("deal_type")), new Price(0, 0, rs.getInt("rent_sell_price")), null, rs.getString("description"));
                }
                else {
                    s = new House(rs.getString("id"), rs.getLong("area"), rs.getString("building_type"),
                            rs.getString("address"), Boolean.parseBoolean(rs.getString("deal_type")),  new Price(rs.getInt("base_price"), rs.getInt("rent_sell_price"), 0), null, rs.getString("description"));
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

    public void delete(RealEstate realEstate){
        PreparedStatement stmt = null;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
//            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.prepareStatement("DELETE from house WHERE reid= ? ;");
            stmt.setString(1, realEstate.getURL());
//            String sql = "DELETE from house WHERE reid='"+ realEstate.getURL() + "';";
//            System.out.println("checking delete sql :"+sql);
            stmt.executeUpdate();
//            c.commit();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Delete done successfully");
    }

    public RealEstate selectRealEstate(String id){
        PreparedStatement stmt = null;
        RealEstate realEstate = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.prepareStatement("SELECT * FROM real_estate re WHERE re.url IN (SELECT h.reid FROM house h WHERE h.id = ? )");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            while ( rs.next() ) {
                String name = rs.getString("name");
                String url  = rs.getString("url");
                realEstate = new RealEstate(name, url, false);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Operation done successfully");
        return realEstate;
    }

    public House selectWithIndividualPhone(String id){
        PreparedStatement stmt = null;
        House house = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.prepareStatement("SELECT * FROM house h, (SELECT i.username, i.phone FROM individual i) AS temp_user WHERE h.id = ? AND h.iid = temp_user.username");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            while ( rs.next() ) {
                if (!Boolean.parseBoolean(rs.getString("deal_type"))) {
                    house = new House(rs.getString("id"), rs.getLong("area"), rs.getString("building_type"),
                            rs.getString("address"), Boolean.parseBoolean(rs.getString("deal_type")), new Price(0, 0, rs.getInt("rent_sell_price")), rs.getString("phone"), rs.getString("description"));
                }
                else {
                    house = new House(rs.getString("id"), rs.getLong("area"), rs.getString("building_type"),
                            rs.getString("address"), Boolean.parseBoolean(rs.getString("deal_type")),  new Price(rs.getInt("base_price"), rs.getInt("rent_sell_price"), 0), rs.getString("phone"), rs.getString("description"));
                }
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Operation done successfully");
        return house;
    }
}
