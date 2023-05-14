package aplication.abstraction.service;

import aplication.abstraction.domain.Individual;

import java.io.IOException;
import java.util.ArrayList;

public interface IExportToFileService {
    void export(ArrayList<Individual> population, String fileNamePrefix) throws IOException;
}
