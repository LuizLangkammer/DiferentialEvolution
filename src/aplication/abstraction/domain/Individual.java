package aplication.abstraction.domain;

import java.util.Random;

public abstract class Individual {

    protected Double[] genes;
    protected Double[] avaliation;

    protected Individual(int dimension, int maxRange, int minRange){
        this.genes = this.initializeGenes(dimension, maxRange, minRange);
        this.avaliation = this.avaliate();
    }
    private Double[] initializeGenes(int dimension, int maxRange, int minRange){
        Double[] genes = new Double[dimension];
        Random random = new Random();
        for(int i=0; i<genes.length; i++){
            genes[i] = random.nextDouble() * maxRange * 2 * minRange;
        }
        return genes;
    }

    public abstract Double[] avaliate();



}
