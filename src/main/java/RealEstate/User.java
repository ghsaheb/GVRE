package RealEstate;

import java.util.ArrayList;

public abstract class User {
    private String name;
    private ArrayList<House> ownedHouses;


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

    public void addHouse(House house){
        ownedHouses.add(house);
    }
}
