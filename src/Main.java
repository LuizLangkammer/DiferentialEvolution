import aplication.abstraction.domain.Individual;
import aplication.abstraction.factory.IIndividualFactory;
import aplication.abstraction.runner.IDiferentialEvolutionRunner;
import aplication.implementation.domain.X1X2Individual;
import aplication.implementation.factory.IndividualFactory;
import aplication.implementation.runner.SimpleDiferentialEvolutionRunner;
import domain.EnumIndividualTypes;

public class Main {
    public static void main(String[] args) {

        IIndividualFactory individualFactory = new IndividualFactory();
        int generations = 100;
        int populationDimension = 20;
        Double crossoverFactor = 0.8;
        Double mutationFactor = 0.5;

        IDiferentialEvolutionRunner runner = new SimpleDiferentialEvolutionRunner();

        Individual[] initialPopulation = new Individual[populationDimension];
        for(int i=0; i< initialPopulation.length; i++){
            initialPopulation[i] = individualFactory.obtem(EnumIndividualTypes.X1X2);
        }

        Individual [] result = runner.execute(initialPopulation, generations, mutationFactor, crossoverFactor);

    }
}