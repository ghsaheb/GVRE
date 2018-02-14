package main.java.RealEstate;

import java.util.Date;

public class House {
    private int id;
    private int area;
    private boolean buildingType;
    private String address;
    private String imageURL;
    private boolean dealType;
    private int basePrice;
    private int rentPrice;
    private int sellPrice;
    private String phone;
    private String description;
    private Date expireTime;

    public House(int id, int area, boolean buildingType, String address, boolean dealType, int price, String phone, String description) {
        this.id = id;
        this.area = area;
        this.buildingType = buildingType;
        this.address = address;
        this.dealType = dealType;
        this.basePrice = price;
        this.phone = phone;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getArea() {
        return area;
    }

    public boolean isBuildingType() {
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

    public int getBasePrice() {
        return basePrice;
    }

    public int getRentPrice() {
        return rentPrice;
    }

    public int getSellPrice() {
        return sellPrice;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public void setBuildingType(boolean buildingType) {
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

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public void setRentPrice(int rentPrice) {
        this.rentPrice = rentPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
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
}
