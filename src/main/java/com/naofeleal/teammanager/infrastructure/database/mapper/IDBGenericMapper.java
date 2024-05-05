package com.naofeleal.teammanager.infrastructure.database.mapper;

import java.util.List;

public interface IDBGenericMapper<MODEL, DBO> {
    DBO fromDomainModel(MODEL model);
    MODEL toDomainModel(DBO DBO);

    List<DBO> fromDomainModels(List<MODEL> models);
    List<MODEL> toDomainModels(List<DBO> DBOs);
}
