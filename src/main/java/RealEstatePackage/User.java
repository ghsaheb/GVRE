package RealEstatePackage;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public abstract class User {
    private String name;
    ArrayList<House> ownedHouses;


    public User(String name) {
        this.name = name;
        this.ownedHouses = new ArrayList<House>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    ArrayList<House> getOwnedHouses() {
        return ownedHouses;
    }

    public void addHouse(House house){
        ownedHouses.add(house);
    }

    public abstract House findHouse(String id) throws HouseNotFindException, IOException, ParseException;
}
