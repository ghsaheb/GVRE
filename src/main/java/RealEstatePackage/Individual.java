package RealEstatePackage;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class Individual extends User {
    private int id;
    private String phone;
    private int balance;
    private String username;
    private String password;
    private ArrayList<House> paidHouses;


    Individual(String name, int id, String phone, int balance, String username, String password) {
        super(name);
        this.id = id;
        this.phone = phone;
        this.balance = balance;
        this.username = username;
        this.password = password;
        this.paidHouses = new ArrayList<House>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void increaseBalance(int inc){
        this.balance += inc;
    }

    public void decreaseBalance(int dec){
        this.balance -= dec;
    }

    public void addPaidHouse(House house){
        for (House paidHouse : paidHouses) {
            if (paidHouse.getId().equals(house.getId()))
                return;
        }
        paidHouses.add(house);
    }

    public boolean searchForHouse(House house){
        for (House paidHouse : paidHouses) {
            if (paidHouse.getId().equals(house.getId()))
                return true;
        }
        return false;
    }

    public House findHouse(String id) throws IOException, ParseException {
        for (House ownedHouse : ownedHouses) {
            if (ownedHouse.getId().equals(id)) {
                return ownedHouse;
            }
        }
        return null;
    }

}
