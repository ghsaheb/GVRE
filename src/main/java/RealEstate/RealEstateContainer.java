package RealEstate;

import java.util.ArrayList;

public class RealEstateContainer {
    private static RealEstateContainer realEstateContainer = null;
    private ArrayList<RealEstate> realEstates;

    private RealEstateContainer() {
        realEstates = new ArrayList<RealEstate>();
        realEstates.add(new RealEstate("khaneBeDoosh", "http://acm.ut.ac.ir/khaneBeDoosh/house"));
    }

    public static RealEstateContainer getRealEstateContainer() {
        if (realEstateContainer == null){
            realEstateContainer = new RealEstateContainer();
        }
        return realEstateContainer;
    }

    public int numberOfRealEstates(){
        return realEstates.size();
    }

    public RealEstate getRealEstate(int i) {
        return realEstates.get(i);
    }
}
