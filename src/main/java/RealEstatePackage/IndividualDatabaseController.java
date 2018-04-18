package RealEstatePackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class IndividualDatabaseController {
    static Connection c = null;

    private static IndividualDatabaseController ourInstance = new IndividualDatabaseController();

    public static IndividualDatabaseController getInstance() {
        return ourInstance;
    }

    private IndividualDatabaseController() {
    }

    public static void main(String[] args) { // OH MY GOD
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
                    "  \"name\" char(128) NOT NULL\n" +
                    ");";
            stmt.executeUpdate(sql);
            stmt.close();
//            c.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        System.out.println("INJA");
        Individual individual = new Individual("بهنام همایون", "09123456789", 0, "Bugs", "Bunny");
        insert(individual);
        individual.addCredit(100000);
        update(individual);
        System.out.println(select("Bugs").getUsername());
    }

    public static void insert(Individual individual) {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql = "INSERT INTO individual (username,credit,password,phone,name) " +
                    "VALUES ('"+ individual.getUsername() + "','" +  individual.getCredit() + "','" + individual.getPassword() + "','" + individual.getPhone() + "','" + individual.getName() + "');";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
//            c.commit();
//            c.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public static void update(Individual individual) {
        Statement stmt = null;
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "UPDATE individual set credit = " + individual.getCredit() + " where username='" + individual.getUsername() + "';";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
//            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    public static Individual select(String username) {
        Statement stmt = null;
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM individual I WHERE I.username='"+username+"';");
            Individual individual = null;
            while ( rs.next() ) {
                String uname = rs.getString("username");
                String password  = rs.getString("password");
                int credit  = rs.getInt("credit");
                String phone  = rs.getString("phone");
                String name  = rs.getString("name");
                individual = new Individual(name,phone,credit,uname,password);
            }
            rs.close();
            stmt.close();
//            c.close();
            return individual;
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        System.out.println("Operation done successfully");
        return null;
    }
}
