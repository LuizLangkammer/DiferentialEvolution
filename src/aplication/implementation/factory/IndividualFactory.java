package aplication.implementation.factory;

import aplication.abstraction.domain.Individual;
import aplication.abstraction.factory.IIndividualFactory;
import aplication.implementation.domain.individual.F2;
import aplication.implementation.domain.individual.F3Individual;
import aplication.implementation.domain.individual.F4Individual;
import aplication.implementation.domain.individual.X1X2Individual;
import domain.EnumIndividualTypes;

public class IndividualFactory implements IIndividualFactory {

    @Override
    public Individual obtem(EnumIndividualTypes type) {
        switch(type){
            case X1X2: return new X1X2Individual();
            case F2: return new F2();
            case F3Individual: return new F3Individual();
            case F4Individual: return new F4Individual();
            default: return null;
        }
    }

}
