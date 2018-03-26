package RealEstatePackage;

import java.util.ArrayList;

public class IndividualContainer {
    private static IndividualContainer individualContainer = null;
    private ArrayList<Individual> individuals;
    
    private IndividualContainer(){
        individuals = new ArrayList<Individual>();
        individuals.add(new Individual("بهنام همایون", 1, "09123456789", 0, "Bugs", "Bunny"));
    }
    
    public static IndividualContainer getIndividualContainer(){
        if (individualContainer == null){
            individualContainer = new IndividualContainer();
        }
        return individualContainer;
    }

    public Individual getIndividual(){
        return individuals.get(0);
    }
}
