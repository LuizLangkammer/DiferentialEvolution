package aplication.abstraction.runner;

import aplication.abstraction.domain.Individual;

import java.util.ArrayList;

public interface IDiferentialEvolutionRunner {
    public ArrayList<Individual> execute(ArrayList<Individual> initialPopulation, int maxGenerations, Double mutationFactor, Double crossoverFactor);

}
