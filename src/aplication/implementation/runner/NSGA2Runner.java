package aplication.implementation.runner;

import aplication.abstraction.domain.Individual;
import aplication.abstraction.runner.IDiferentialEvolutionRunner;
import aplication.abstraction.service.IExportToFileService;
import aplication.implementation.domain.ComparatorNSGA;
import aplication.implementation.domain.FNDSIndividual;
import aplication.implementation.service.ExportToFileService;
import domain.EnumDomain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class NSGA2Runner implements IDiferentialEvolutionRunner {
    @Override
    public ArrayList<Individual> execute(ArrayList<Individual> initialPopulation, int maxGenerations, Double mutationFactor, Double crossoverFactor) throws IOException {
        int individualDimension = initialPopulation.size();
        int generations = 0;
        IExportToFileService exportToFile = new ExportToFileService();
        Random random = new Random();

        while(generations <= maxGenerations){

            ArrayList<Individual> intermediary = new ArrayList<Individual>(initialPopulation);

            Collections.shuffle(initialPopulation);
            for(int i=0; i<individualDimension; i+=2){
                Individual [] children = initialPopulation.get(i).generateBlx(initialPopulation.get(i+1));
                if(random.nextDouble() > 0.9){
                    children[0].mute();
                }
                if(random.nextDouble() > 0.9){
                    children[1].mute();
                }
                intermediary.add(children[0]);
                intermediary.add(children[1]);
            }

            ArrayList<ArrayList<Individual>> frontends = fnds(intermediary);
            ArrayList<Individual> newPopulation = new ArrayList<Individual>();

            for(int i=0; i<frontends.size(); i++){
                if(newPopulation.size() >= individualDimension) break;

                if(frontends.get(i).size() + newPopulation.size() > individualDimension){
                    ArrayList<Individual>  indCd = crowdingDistance(frontends.get(i));
                    for (int j = 0; j < indCd.size(); j++){
                        if(newPopulation.size() < individualDimension){
                            newPopulation.add(indCd.get(j));
                        }else break;
                    }
                }else{
                    newPopulation.addAll(frontends.get(i));
                }
            }

            initialPopulation = newPopulation;
            generations++;

            if(generations==1 || generations==10 || generations==50 || generations==100 || generations==1000){
                exportToFile.export(initialPopulation, "g_"+generations);
            }

            Individual bestIndividual = getBestIndividual(initialPopulation);
            System.out.print(generations +": x -> ");
            for(int i=0; i<bestIndividual.getGenes().length; i++){
                System.out.print(bestIndividual.getGenes()[i]);
                if(bestIndividual.getGenes().length != i+1) System.out.print(", ");
            }
            System.out.print(";\t y -> ");
            for(int i=0; i<bestIndividual.getAvaliation().length; i++){
                System.out.print(bestIndividual.getAvaliation()[i]);
                if(bestIndividual.getAvaliation().length != i+1) System.out.print(", ");
            }
            System.out.println();
        }

        return initialPopulation;
    }

    private EnumDomain dominates(Individual first, Individual second){
        boolean dominates = true;
        boolean dominated = true;
        for(int i=0; i< first.getAvaliation().length; i++){
            if(first.getAvaliation()[i] > second.getAvaliation()[i]){
                dominates = false;
            }else{
                dominated = false;
            }
        }
        if(dominates) return EnumDomain.DOMINATES;
        if(dominated) return EnumDomain.DOMINATED;

        return EnumDomain.NEUTRAL;
    }

    private int[] generateUniqueR1R2R3(int individualDimension){
        Random random = new Random();
        int r1 = random.nextInt(individualDimension-1);
        int r2;
        int r3;
        do{
            r2 = random.nextInt(individualDimension-1);
        }while(r2 == r1);
        do{
            r3 = random.nextInt(individualDimension-1);
        }while(r3 == r2 || r3 == r1);

        int[] r1r2r3 = {r1, r2, r3};
        return r1r2r3;
    }

    private ArrayList<ArrayList<Individual>> fnds(ArrayList<Individual> population){
        FNDSIndividual[] points = new FNDSIndividual[population.size()];
        for(int i=0; i<population.size(); i++){
            points[i] = new FNDSIndividual(population.get(i));
        }

        FNDSIndividual point;
        ArrayList<ArrayList<FNDSIndividual>> frontends = new ArrayList<>();
        ArrayList<FNDSIndividual> f1 = new ArrayList<>();
        for(int i=0; i<points.length; i++){
            point = points[i];
            point.setN(0);
            point.setS(new ArrayList<FNDSIndividual>());
            FNDSIndividual q;
            for(int j=0; j < points.length; j++){
                if(i==j) continue;

                q = points[j];
                if(dominates(point.getIndividual(), q.getIndividual()) == EnumDomain.DOMINATES){
                    point.getS().add(q);
                }else if(dominates(q.getIndividual(), point.getIndividual()) == EnumDomain.DOMINATES){
                    point.setN(point.getN()+1);
                }
            }

            if(point.getN() == 0){
                point.setRank(1);
                f1.add(point);
            }
        }
        frontends.add(f1);

        int i=0;
        ArrayList<FNDSIndividual> fi = frontends.get(i);
        while(fi.size() != 0){
            ArrayList<FNDSIndividual> qs = new ArrayList<>();
            for(FNDSIndividual p: fi){
                ArrayList<FNDSIndividual> sp = p.getS();
                for(FNDSIndividual q: sp){
                    q.setN(q.getN()-1);
                    if(q.getN() == 0){
                        q.setRank(i + 1);
                        qs.add(q);
                    }
                }
            }
            i++;
            frontends.add(qs);
            fi = qs;
        }

        ArrayList<ArrayList<Individual>> frontendsIndividuals = new ArrayList<>();
        for(int j=0; j<frontends.size(); j++){

            if(frontends.get(j).size()==0) continue;

            frontendsIndividuals.add(new ArrayList<Individual>());
            for(int c=0; c<frontends.get(j).size(); c++){
                frontendsIndividuals.get(j).add(frontends.get(j).get(c).getIndividual());
            }
        }
        return frontendsIndividuals;
    }

    private ArrayList<Individual> crowdingDistance (ArrayList<Individual> T){
        int size = T.size();
        for(Individual individual : T) individual.setD(0);

        Individual firstIndividual = T.get(0);
        for(int m=0; m<firstIndividual.getAvaliation().length; m++){
            sort(T, m);
            T.get(0).setD(Double.POSITIVE_INFINITY);
            T.get(size - 1).setD(Double.POSITIVE_INFINITY);
            for (int i = 1; i < size - 1; i++) {
                Individual n = T.get(i+1);
                Individual p = T.get(i-1);

                double aux = (n.getAvaliation()[m] - p.getAvaliation()[m]) / (T.get(size - 1).getAvaliation()[m] - T.get(0).getAvaliation()[m]);

                T.get(i).setD(T.get(i).getD()+aux);
            }
        }
        Collections.sort(T, new ComparatorNSGA());
        return T;
    }

    private void sort(ArrayList<Individual> frontend, int qtdObjectives){
        for (int i = 0; i < frontend.size()-1; i++) {
            for (int j = i+1; j < frontend.size(); j++) {
                if(frontend.get(i).getAvaliation()[qtdObjectives] > frontend.get(j).getAvaliation()[qtdObjectives]){
                    Individual aux = frontend.get(i);
                    frontend.set(i, frontend.get(j));
                    frontend.set(j, aux);
                }
            }
        }
    }

    private Individual getBestIndividual(ArrayList<Individual> population){

        Individual bestIndividual = population.get(0);

        for(int i=1; i < population.size(); i++){
            bestIndividual = chooseByDomination(population.get(i), bestIndividual);
        }

        return bestIndividual;
    }

    private Individual chooseByDomination(Individual first, Individual second){

        EnumDomain dominationResult = dominates(first, second);
        switch (dominationResult){
            case DOMINATED: return second;
            case DOMINATES: return first;
            default: {
                Random r = new Random();
                Individual[] options = {first, second};
                return options[r.nextInt(2)];
            }
        }
    }
}
