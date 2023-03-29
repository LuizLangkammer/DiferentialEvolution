package aplication.implementation.domain;

import aplication.abstraction.domain.Individual;

public class F4Individual extends Individual {


    public F4Individual(){
        super(3,10,-10);
    }

    public F4Individual(Double [] genes){
        super(genes);
    }

    @Override
    public Individual generateExperimental(Individual individual, Double crossoverFactor) {
        Double [] genes = this.combineExperimental(individual, crossoverFactor);
        return new F4Individual(genes);
    }

    @Override
    public Individual generateU(Individual individual2, Individual individual3, Double mutationFactor) {
        Double [] genes = this.combineU(individual2, individual3, mutationFactor);
        return new F4Individual(genes);
    }

    @Override
    public Double[] avaliate() {
        Double function1 = Math.pow(this.genes[0]-1, 2) + Math.pow(this.genes[1], 2) + Math.pow(this.genes[2], 2);
        Double function2 = Math.pow(this.genes[0], 2) + Math.pow(this.genes[1]-1, 2) + Math.pow(this.genes[2], 2);
        Double function3 = Math.pow(this.genes[0], 2) + Math.pow(this.genes[1], 2) + Math.pow(this.genes[2]-1, 2);

        Double[] results = {function1, function2, function3};
        return results;
    }
}
