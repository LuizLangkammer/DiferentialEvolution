package aplication.abstraction.domain;

import java.util.ArrayList;
import java.util.Random;

public abstract class Individual {

    protected Double[] genes;
    protected Double[] avaliation;

    protected int maxRange;

    protected int minRange;

    protected double d;

    protected Individual(int dimension, int maxRange, int minRange){
        this.maxRange = maxRange;
        this.minRange = minRange;
        this.genes = this.initializeGenes(dimension);
        this.avaliation = this.avaliate();
    }

    protected Individual(Double [] genes, int maxRange, int minRange){
        this.genes = genes;
        this.avaliation = this.avaliate();
    }
    private Double[] initializeGenes(int dimension){
        Double[] genes = new Double[dimension];
        Random random = new Random();
        for(int i=0; i<genes.length; i++){
            genes[i] = (random.nextDouble() * (maxRange - minRange)) + minRange;
        }
        return genes;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
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

    protected Double [] combineExperimental(Individual individualU, Double crossoverFactor){

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

    protected Double[][] combineBLX(Individual partner) {

        Double[] firstChildGenes = new Double[genes.length];
        Double[] secondChildGenes = new Double[genes.length];

        Double[] partnerInputs = partner.genes;

        Random random = new Random();
        double	drawn;
        double firstChildInput;
        double secondChildInput;

        for(int i=0; i<genes.length;i++) {

            drawn = random.nextGaussian(0,0.1);
            firstChildInput = genes[i] + drawn*Math.abs(genes[i]-partnerInputs[i]);
            firstChildInput = this.avaliateDomain(firstChildInput);
            firstChildGenes[i] = firstChildInput;

            drawn = random.nextGaussian(0,0.1);
            secondChildInput = partnerInputs[i] + drawn*Math.abs(genes[i]-partnerInputs[i]);

            secondChildInput = this.avaliateDomain(secondChildInput);
            secondChildGenes[i] = secondChildInput;

        }

        Double [][] childrenGenes = new Double[2][genes.length];
        childrenGenes[0] = firstChildGenes;
        childrenGenes[1] = secondChildGenes;

        return childrenGenes;
    }

    public void mute() {
        Double[] mutantGenes = new Double[genes.length];
        double mutantGene;
        Random random = new Random();
        double drawn;
        boolean muted = false;

        for(int i=0;i<genes.length;i++) {
            drawn = random.nextDouble(1);
            mutantGene = genes[i];
            if(drawn <= 0.1) {
                muted = true;
                mutantGene = genes[i] + random.nextGaussian(0, 0.5);
            }
            mutantGenes[i] = mutantGene;
        }
        if(!muted) {
            int index = random.nextInt(genes.length);
            mutantGene = mutantGenes[index]+random.nextGaussian(0,0.5);
            mutantGene = this.avaliateDomain(mutantGene);
            mutantGenes[index] = mutantGene;
        }
        this.genes = mutantGenes;
        this.avaliate();
    }


    private double avaliateDomain(double input) {

        if(input>maxRange) {
            input = maxRange;
        }else {
            if(input<minRange) {
                input = minRange;
            }
        }

        return input;
    }

    public abstract Individual [] generateBlx(Individual partner);

    public abstract Individual generateExperimental(Individual individual, Double crossoverFactor);

    public abstract Individual generateU(Individual individual2, Individual individual3, Double mutationFactor);

    public abstract Double[] avaliate();



}
