import aplication.abstraction.domain.Individual;
import aplication.abstraction.factory.IIndividualFactory;
import aplication.implementation.domain.X1X2Individual;
import aplication.implementation.factory.IndividualFactory;
import domain.EnumIndividualTypes;

public class Main {
    public static void main(String[] args) {

        IIndividualFactory individualFactory = new IndividualFactory();
        int generations = 100;
        int populationDimension = 20;

        Individual[] initialPopulation = new Individual[populationDimension];
        for(int i=0; i< initialPopulation.length; i++){
            initialPopulation[i] = individualFactory.obtem(EnumIndividualTypes.X1X2);
        }




    }
}