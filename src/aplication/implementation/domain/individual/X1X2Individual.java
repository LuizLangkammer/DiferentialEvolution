package aplication.implementation.domain.individual;

import aplication.abstraction.domain.Individual;

public class X1X2Individual extends Individual {


    public X1X2Individual(){
        super(2,20,-20);
    }

    public X1X2Individual(Double [] genes){
        super(genes);
    }

    public Individual [] generateBlx(Individual partner){
        Double[][] childrenGenes = this.combineBLX(partner);
        Individual firstChild = new X1X2Individual(childrenGenes[0]);
        Individual secondChild = new X1X2Individual(childrenGenes[1]);
        Individual [] children = {firstChild, secondChild};
        return children;
    }

    @Override
    public Individual generateExperimental(Individual individual, Double crossoverFactor) {
        Double [] genes = this.combineExperimental(individual, crossoverFactor);
        return new X1X2Individual(genes);
    }

    @Override
    public Individual generateU(Individual individual2, Individual individual3, Double mutationFactor) {
        Double [] genes = this.combineU(individual2, individual3, mutationFactor);
        return new X1X2Individual(genes);
    }

    @Override
    public Double[] avaliate() {
        Double result = Math.pow(this.genes[0], 2) + Math.pow(this.genes[1], 2);
        Double[] results = {result};
        return results;
    }
}
