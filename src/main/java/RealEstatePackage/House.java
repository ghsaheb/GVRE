package RealEstatePackage;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class House {
    private String id;
    private long area;
    private String buildingType;
    private String address;
    private String imageURL;
    private boolean dealType;
    private Price price;
    private String phone;
    private String description;
    private Date expireTime;


    public House() {
    }

    public House(String id, long area, String buildingType, String address, boolean dealType, Price price, String phone, String description) {
        this.id = id;
        this.area = area;
        this.buildingType = buildingType;
        this.address = address;
        this.dealType = dealType;
        this.price = price;
        this.phone = phone;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public long getArea() {
        return area;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public String getAddress() {
        return address;
    }

    public String getImageURL() {
        return imageURL;
    }

    public boolean isDealType() {
        return dealType;
    }

    public Price getPrice() {
        return price;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setArea(long area) {
        this.area = area;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setDealType(boolean dealType) {
        this.dealType = dealType;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public static ArrayList<House> getFiltered(long area, String dealType, String buildingType, int maxPrice){
        RealEstateDatabaseController.getInstance();
        return HouseDatabaseController.getInstance().select(area, dealType, buildingType, maxPrice);
    }

    public static House findHouse(String id) throws HouseNotFindException {
//        try {
            RealEstate realEstate = HouseDatabaseController.getInstance().selectRealEstate(id);
            if (realEstate != null){
                House house = realEstate.findHouse(id);
                return house;
            }
            House house = HouseDatabaseController.getInstance().selectWithIndividualPhone(id);
            if (house != null){
                return house;
            }
            throw new HouseNotFindException();
    }
}
