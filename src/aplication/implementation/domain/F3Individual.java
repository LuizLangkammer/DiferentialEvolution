package aplication.implementation.domain;

import aplication.abstraction.domain.Individual;

public class F3Individual extends Individual {


    public F3Individual(){
        super(2,10,-10);
    }

    public F3Individual(Double [] genes){
        super(genes);
    }

    @Override
    public Individual generateExperimental(Individual individual, Double crossoverFactor) {
        Double [] genes = this.combineExperimental(individual, crossoverFactor);
        return new F3Individual(genes);
    }

    @Override
    public Individual generateU(Individual individual2, Individual individual3, Double mutationFactor) {
        Double [] genes = this.combineU(individual2, individual3, mutationFactor);
        return new F3Individual(genes);
    }

    @Override
    public Double[] avaliate() {
        Double function1 = Math.pow(this.genes[0], 2) + Math.pow(this.genes[1], 2);
        Double function2 = Math.pow(this.genes[0], 2) + Math.pow(this.genes[1]-1, 2);

        Double[] results = {function1, function2};
        return results;
    }
}
