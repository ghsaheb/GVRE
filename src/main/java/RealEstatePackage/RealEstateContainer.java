package RealEstatePackage;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

class RealEstateContainer {
    private static RealEstateContainer realEstateContainer = null;
    private ArrayList<RealEstate> realEstates;

    private RealEstateContainer() {
        realEstates = new ArrayList<RealEstate>();
        realEstates.add(new RealEstate("khaneBeDoosh", "http://acm.ut.ac.ir/khaneBeDoosh/house"));
    }

    static RealEstateContainer getRealEstateContainer() {
        if (realEstateContainer == null){
            realEstateContainer = new RealEstateContainer();
        }
        return realEstateContainer;
    }

    RealEstate getRealEstate() {
        return realEstates.get(0);
    }

    void updateHouses() throws IOException, ParseException {
        for (RealEstate re : realEstates) {
            re.updateHouses();
        }
    }
}
