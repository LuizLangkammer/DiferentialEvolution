package aplication.implementation.domain;

import aplication.abstraction.domain.Individual;

import java.util.Comparator;

public class ComparatorNSGA implements Comparator<Individual> {

    @Override
    public int compare(Individual individual1, Individual individual2) {
        return Double.compare(individual2.getD(), individual1.getD());
    }
}
