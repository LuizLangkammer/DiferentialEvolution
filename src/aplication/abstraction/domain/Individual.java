package aplication.abstraction.domain;

import java.util.Random;

public abstract class Individual {

    protected Double[] genes;
    protected Double[] avaliation;

    protected Individual(int dimension, int maxRange, int minRange){
        this.genes = this.initializeGenes(dimension, maxRange, minRange);
        this.avaliation = this.avaliate();
    }

    protected Individual(Double [] genes){
        this.genes = genes;
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

    public Double[] getGenes() {
        return genes;
    }

    public Double[] getAvaliation() {
        return avaliation;
    }

    protected Double[] combineU(Individual individual2, Individual individual3, Double mutationFactor){

        int genesDimension = this.genes.length;

        Double[] genes = new Double[genesDimension];

        for (int i = 0; i < genesDimension; i++) {
            genes[i] = individual3.getGenes()[i] + (mutationFactor * (this.genes[i] - individual2.getGenes()[i]));
        }

        return genes;
    }

    public Double [] combineExperimental(Individual individualU, Double crossoverFactor){

        int genesDimension = this.genes.length;
        Random random  = new Random();
        Double[] genes = new Double[genesDimension];

        for (int i = 0; i < genesDimension; i++) {

            Double r = random.nextDouble();

            if(r < crossoverFactor){
                genes[i] = this.genes[i];
            }else{
                genes[i] = individualU.getGenes()[i];
            }
        }

        return genes;
    }

    public abstract Individual generateExperimental(Individual individual, Double crossoverFactor);

    public abstract Individual generateU(Individual individual2, Individual individual3, Double mutationFactor);

    public abstract Double[] avaliate();



}
