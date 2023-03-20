package aplication.abstraction.runner;

import aplication.abstraction.domain.Individual;

public interface IDiferentialEvolutionRunner {
    public Individual[] execute(Individual[] initialPopulation, int maxGenerations, Double mutationFactor, Double crossoverFactor);
}
