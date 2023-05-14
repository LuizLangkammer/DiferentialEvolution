package aplication.implementation.runner;

import aplication.abstraction.domain.Individual;
import aplication.abstraction.runner.IDiferentialEvolutionRunner;
import domain.EnumDomain;

import java.util.ArrayList;
import java.util.Random;

public class SimpleDiferentialEvolutionRunner implements IDiferentialEvolutionRunner {

    @Override
    public ArrayList<Individual> execute(ArrayList<Individual> initialPopulation, int maxGenerations, Double mutationFactor, Double crossoverFactor) {
        int individualDimension = initialPopulation.size();
        int generations = 0;
        Random random = new Random();

        while(generations <= maxGenerations){

            ArrayList<Individual> newPopulation = new ArrayList<Individual>(individualDimension);

            for(int i=0; i<individualDimension; i++){

                int[] r1r2r3 = this.generateUniqueR1R2R3(individualDimension);
                int r1 = r1r2r3[0];
                int r2 = r1r2r3[1];
                int r3 = r1r2r3[2];

                Individual individual1 = initialPopulation.get(r1);
                Individual individual2 = initialPopulation.get(r2);
                Individual individual3 = initialPopulation.get(r3);

                Individual individualU = individual1.generateU(individual2, individual3, mutationFactor);
                Individual experimental = initialPopulation.get(i).generateExperimental(individualU, crossoverFactor);

                newPopulation.set(i, chooseByDomination(experimental, initialPopulation.get(i)));

            }
            initialPopulation = newPopulation;
            if(generations == 90)  {
                System.out.println();
            }
            Individual bestIndividual = getBestIndividual(initialPopulation);
            System.out.print(generations +": x -> ");
            for(int i=0; i<bestIndividual.getGenes().length; i++){
                System.out.print(bestIndividual.getGenes()[i]);
                if(bestIndividual.getGenes().length != i+1) System.out.print(", ");
            }
            System.out.print(";\t y -> ");
            for(int i=0; i<bestIndividual.getAvaliation().length; i++){
                System.out.print(bestIndividual.getAvaliation()[i]);
                if(bestIndividual.getAvaliation().length != i+1) System.out.print(", ");
            }
            System.out.println();
            generations++;
        }

        return initialPopulation;
    }

    private Individual chooseByDomination(Individual first, Individual second){

        EnumDomain dominationResult = dominates(first, second);
        switch (dominationResult){
            case DOMINATED: return second;
            case DOMINATES: return first;
            default: {
                Random r = new Random();
                Individual[] options = {first, second};
                return options[r.nextInt(2)];
            }
        }
    }
    private EnumDomain dominates(Individual first, Individual second){
        boolean dominates = true;
        boolean dominated = true;
        for(int i=0; i< first.getAvaliation().length; i++){
            if(first.getAvaliation()[i] > second.getAvaliation()[i]){
                dominates = false;
            }else{
                dominated = false;
            }
        }
        if(dominates) return EnumDomain.DOMINATES;
        if(dominated) return EnumDomain.DOMINATED;

        return EnumDomain.NEUTRAL;
    }
    private Individual getBestIndividual(ArrayList<Individual> population){

        Individual bestIndividual = population.get(0);

        for(int i=1; i < population.size(); i++){
            bestIndividual = chooseByDomination(population.get(i), bestIndividual);
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
