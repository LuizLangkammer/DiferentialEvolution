package aplication.implementation.service;

import aplication.abstraction.domain.Individual;
import aplication.abstraction.service.IExportToFileService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ExportToFileService implements IExportToFileService {

    private final String defaultPath = "./src/files";

    @Override
    public void export(ArrayList<Individual> population, String fileNamePrefix) throws IOException {

        String objectivesPath = defaultPath+"/"+fileNamePrefix+"_objectives.txt";
        String variablesPath = defaultPath+"/"+fileNamePrefix+"_variables.txt";

        FileWriter variablesFile =  new FileWriter(variablesPath);
        FileWriter objectivesFile =  new FileWriter(objectivesPath);

        BufferedWriter variableWriter = new BufferedWriter(variablesFile);
        BufferedWriter objectivesWriter = new BufferedWriter(objectivesFile);

        for(Individual individual: population){
            for(int j=0; j<individual.getAvaliation().length; j++){
                objectivesWriter.write(individual.getAvaliation()[j]+"\t");
            }
            objectivesWriter.newLine();
        }
        variableWriter.flush();
        objectivesWriter.flush();
        variableWriter.close();
        objectivesWriter.close();


    }

}
