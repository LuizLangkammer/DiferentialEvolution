import aplication.abstraction.domain.Individual;
import aplication.abstraction.factory.IIndividualFactory;
import aplication.abstraction.runner.IDiferentialEvolutionRunner;
import aplication.implementation.factory.IndividualFactory;
import aplication.implementation.runner.NSGARunner;
import aplication.implementation.runner.SimpleDiferentialEvolutionRunner;
import domain.EnumIndividualTypes;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        IIndividualFactory individualFactory = new IndividualFactory();
        EnumIndividualTypes type = EnumIndividualTypes.F2;
        int generations = 1000;
        int populationDimension = 20;
        Double crossoverFactor = 0.8;
        Double mutationFactor = 0.5;

        IDiferentialEvolutionRunner runner = new SimpleDiferentialEvolutionRunner();

        ArrayList<Individual> initialPopulation = new ArrayList<>();
        for(int i=0; i < populationDimension; i++){
            initialPopulation.add (individualFactory.obtem(type));
        }

        try {
            ArrayList<Individual> result = runner.execute(initialPopulation, generations, mutationFactor, crossoverFactor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}