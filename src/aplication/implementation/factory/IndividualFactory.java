package aplication.implementation.factory;

import aplication.abstraction.domain.Individual;
import aplication.abstraction.factory.IIndividualFactory;
import aplication.implementation.domain.X1X2Individual;
import domain.EnumIndividualTypes;

public class IndividualFactory implements IIndividualFactory {

    @Override
    public Individual obtem(EnumIndividualTypes type) {
        switch(type){
            case X1X2: return new X1X2Individual();
            default: return null;
        }
    }

}
