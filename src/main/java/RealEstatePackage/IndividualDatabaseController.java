package RealEstatePackage;

import java.sql.*;
import java.util.ArrayList;

public class IndividualDatabaseController {
    private static Connection c = null;

    private static IndividualDatabaseController ourInstance = new IndividualDatabaseController();

    public static IndividualDatabaseController getInstance() {
        return ourInstance;
    }

    private IndividualDatabaseController() {
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS \"individual\" (\n" +
                    "  \"username\" char PRIMARY KEY NOT NULL,\n" +
                    "  \"credit\" integer(128) NOT NULL,\n" +
                    "  \"password\" char(128) NOT NULL,\n" +
                    "  \"phone\" char(128) NOT NULL,\n" +
                    "  \"name\" char(128) NOT NULL,\n" +
                    "  \"isAdmin\" char(128) NOT NULL\n" +
                    ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        insert(new Individual("بهنام همایون", "09123456789", 0, "Bugs", "Bunny", true));
        insert(new Individual("حمید هیراد", "09123456788", 0, "Hirad", "Thief", false));
    }

    public void insert(Individual individual) {
        PreparedStatement stmt = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            stmt = c.prepareStatement("INSERT INTO individual (username,credit,password,phone,name,isAdmin) VALUES (?,?,?,?,?,?);");
            stmt.setString(1, individual.getUsername());
            stmt.setInt(2, individual.getCredit());
            stmt.setString(3, individual.getPassword());
            stmt.setString(4, individual.getPhone());
            stmt.setString(5, individual.getName());
            stmt.setString(6, String.valueOf(individual.isAdmin()));
//            String sql = "INSERT INTO individual (username,credit,password,phone,name,isAdmin) " +
//                    "VALUES ('"+ individual.getUsername() + "','" +  individual.getCredit() + "','" + individual.getPassword() + "','" + individual.getPhone() + "','" + individual.getName() + "','" + individual.isAdmin() + "');";
//            System.out.println(sql);
            stmt.executeUpdate();
            stmt.close();
//            c.commit();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Individual individual) {
        PreparedStatement stmt = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.prepareStatement("UPDATE individual set credit = ? where username= ? ;");
//            String sql = "UPDATE individual set credit = " + individual.getCredit() + " where username='" + individual.getUsername() + "';";
            stmt.setInt(1, individual.getCredit());
            stmt.setString(2, individual.getUsername());
//            System.out.println(sql);
            stmt.executeUpdate();
            c.commit();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Operation done successfully");
    }

    public Individual select(String username) throws IndividualNotFoundException {
        PreparedStatement stmt = null;
        Individual individual = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.prepareStatement("SELECT * FROM individual I WHERE I.username= ? ;");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while ( rs.next() ) {
                String uname = rs.getString("username");
                String password  = rs.getString("password");
                int credit  = rs.getInt("credit");
                String phone  = rs.getString("phone");
                String name  = rs.getString("name");
                Boolean isAdmin = Boolean.parseBoolean(rs.getString("isAdmin"));
                individual = new Individual(name,phone,credit,uname,password,isAdmin);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Operation done successfully");
        if (individual == null) {
            throw new IndividualNotFoundException();
        }
        return individual;
    }

    public ArrayList<Individual> select() {
        Statement stmt = null;
        ArrayList<Individual> individuals = new ArrayList<Individual>();
        try {
            c = DriverManager.getConnection("jdbc:sqlite:gvre.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM individual;");
            while ( rs.next() ) {
                String uname = rs.getString("username");
                String password  = rs.getString("password");
                int credit  = rs.getInt("credit");
                String phone  = rs.getString("phone");
                String name  = rs.getString("name");
                Boolean isAdmin = Boolean.parseBoolean(rs.getString("isAdmin"));
                individuals.add(new Individual(name,phone,credit,uname,password,isAdmin));
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Operation done successfully");
        return individuals;
    }
}
