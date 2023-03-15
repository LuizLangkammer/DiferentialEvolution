package aplication.abstraction.runner;

import aplication.abstraction.domain.Individual;

public interface IDiferentialEvolutionRunner {
    public void execute(Individual[] initialPopulation, int maxGenerations);
}
