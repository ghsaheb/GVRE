package main.java.RealEstate;

import java.util.ArrayList;

public abstract class User {
    private String name;
    private int id;
    private ArrayList<House> paidHouses;
    private ArrayList<House> ownedHouses;


    public User(String name, int id) {
        this.name = name;
        this.id = id;
        this.paidHouses = new ArrayList<>();
        this.ownedHouses = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;

    }

    public void addPaidHouse(House house){
        for (int i = 0; i < paidHouses.size();i++){
            if (paidHouses.get(i).getId().equals(house.getId()))
                return;
        }
        paidHouses.add(house);
    }

    public void addHouse(House house){
        ownedHouses.add(house);
    }

    public boolean searchForHouse(House house){
        for (int i = 0; i < paidHouses.size();i++){
            if (paidHouses.get(i).getId().equals(house.getId()))
                return true;
        }
        return false;
    }
}
