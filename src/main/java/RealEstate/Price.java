package RealEstate;

public class Price {
    private int basePrice;
    private int rentPrice;
    private int sellPrice;

    public Price(int basePrice, int rentPrice, int sellPrice) {
        this.basePrice = basePrice;
        this.rentPrice = rentPrice;
        this.sellPrice = sellPrice;
    }

    public Price() {
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

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public void setRentPrice(int rentPrice) {
        this.rentPrice = rentPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }
}
