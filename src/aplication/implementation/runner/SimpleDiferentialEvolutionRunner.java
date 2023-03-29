package aplication.implementation.runner;

import aplication.abstraction.domain.Individual;
import aplication.abstraction.runner.IDiferentialEvolutionRunner;

import java.util.Random;

public class SimpleDiferentialEvolutionRunner implements IDiferentialEvolutionRunner {

    @Override
    public Individual[] execute(Individual[] initialPopulation, int maxGenerations, Double mutationFactor, Double crossoverFactor) {
        int individualDimension = initialPopulation.length;
        int generations = 0;
        Random random = new Random();

        while(generations <= maxGenerations){

            Individual[] newPopulation = new Individual[individualDimension];

            for(int i=0; i<individualDimension; i++){

                int[] r1r2r3 = this.generateUniqueR1R2R3(individualDimension);
                int r1 = r1r2r3[0];
                int r2 = r1r2r3[1];
                int r3 = r1r2r3[2];

                Individual individual1 = initialPopulation[r1];
                Individual individual2 = initialPopulation[r2];
                Individual individual3 = initialPopulation[r3];

                Individual individualU = individual1.generateU(individual2, individual3, mutationFactor);
                Individual experimental = initialPopulation[i].generateExperimental(individualU, crossoverFactor);

                if(dominates(experimental, initialPopulation[i])){
                    newPopulation[i] = experimental;
                }else{
                    newPopulation[i] = initialPopulation[i];
                }

            }
            initialPopulation = newPopulation;

            Individual bestIndividual = getBestIndividual(initialPopulation);
            System.out.print(generations +": ");
            for(int i=0; i<bestIndividual.getAvaliation().length; i++){
                System.out.print(bestIndividual.getAvaliation()[i]);
                if(bestIndividual.getAvaliation().length != i-1) System.out.print(", ");
            }
            System.out.println();
            generations++;
        }

        return initialPopulation;
    }

    private boolean dominates(Individual first, Individual second){
        boolean dominates = true;
        for(int i=0; i< first.getAvaliation().length; i++){
            if(first.getAvaliation()[i] > second.getAvaliation()[i]){
                dominates = false;
            }
        }
        return dominates;
    }
    private Individual getBestIndividual(Individual[] population){

        Individual bestIndividual = population[0];

        for(int i=1; i < population.length; i++){
            if(dominates(population[i], bestIndividual)){
                bestIndividual = population[i];
            }
        }

        return bestIndividual;
    }


    private int[] generateUniqueR1R2R3(int individualDimension){
        Random random = new Random();
        int r1 = random.nextInt(individualDimension-1);
        int r2;
        int r3;
        do{
            r2 = random.nextInt(individualDimension-1);
        }while(r2 == r1);
        do{
            r3 = random.nextInt(individualDimension-1);
        }while(r3 == r2 || r3 == r1);

        int[] r1r2r3 = {r1, r2, r3};
        return r1r2r3;
    }

}
