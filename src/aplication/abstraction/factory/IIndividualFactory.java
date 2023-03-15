package aplication.abstraction.factory;

import aplication.abstraction.domain.Individual;
import domain.EnumIndividualTypes;

public interface IIndividualFactory {
    public Individual obtem(EnumIndividualTypes type);

}
