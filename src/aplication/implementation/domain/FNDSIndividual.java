package aplication.implementation.domain;

import aplication.abstraction.domain.Individual;

import java.util.ArrayList;

public class FNDSIndividual {

    private Individual individual;
    private ArrayList<FNDSIndividual> s;
    private int n;
    private int rank;

    public FNDSIndividual (Individual individual){
        this.individual = individual;
        s = new ArrayList<FNDSIndividual>();
    }

    public Individual getIndividual() {
        return individual;
    }

    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public ArrayList<FNDSIndividual> getS() {
        return s;
    }

    public void setS(ArrayList<FNDSIndividual> s) {
        this.s = s;
    }
}
