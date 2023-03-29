package aplication.implementation.domain;

import aplication.abstraction.domain.Individual;

public class F2 extends Individual {


    public F2(){
        super(1,10,-10);
    }

    public F2(Double [] genes){
        super(genes);
    }

    @Override
    public Individual generateExperimental(Individual individual, Double crossoverFactor) {
        Double [] genes = this.combineExperimental(individual, crossoverFactor);
        return new F2(genes);
    }

    @Override
    public Individual generateU(Individual individual2, Individual individual3, Double mutationFactor) {
        Double [] genes = this.combineU(individual2, individual3, mutationFactor);
        return new F2(genes);
    }

    @Override
    public Double[] avaliate() {
        Double function1 = this.genes[0]*this.genes[0];
        Double function2 = Math.pow(this.genes[0]-1, 2);
        Double[] results = {function1, function2};
        return results;
    }
}
