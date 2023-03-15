package aplication.implementation.domain;

import aplication.abstraction.domain.Individual;

public class X1X2Individual extends Individual {


    public X1X2Individual(){
        super(2,20,-20);
    }

    @Override
    public Double[] avaliate() {
        Double result = Math.pow(this.genes[0], 2) + Math.pow(this.genes[1], 2);
        Double[] results = {result};
        return results;
    }
}
