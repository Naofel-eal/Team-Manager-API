package com.naofeleal.teammanager.shared.mapper;

import java.util.List;

public interface IGenericMapper<MODEL, OUTPUT> {
    OUTPUT fromDomainModel(MODEL model);
    MODEL toDomainModel(OUTPUT output);

    List<OUTPUT> fromDomainModels(List<MODEL> models);
    List<MODEL> toDomainModels(List<OUTPUT> outputs);
}
