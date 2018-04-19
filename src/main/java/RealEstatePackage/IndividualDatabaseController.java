package RealEstatePackage;

import java.sql.*;

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
                    "  \"name\" char(128) NOT NULL\n" +
                    ");";
            stmt.executeUpdate(sql);
            stmt.close();
//            c.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        insert(new Individual("بهنام همایون", "09123456789", 0, "Bugs", "Bunny"));
    }

    public void insert(Individual individual) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Individual individual) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Operation done successfully");
    }

    public Individual select(String username) throws IndividualNotFoundException {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Operation done successfully");
        return null;
    }
}
