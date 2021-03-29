package com.playmuscial.api.datasource;

import com.playmuscial.api.enums.DBType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Autowired
    private DatabaseSelector databaseSelector;

    @Override
    protected Object determineCurrentLookupKey() {
        Object dbKey = databaseSelector.getDataSource();

        if (dbKey == null) {
            return DBType.ACCOUNT1;
        }
        return dbKey;
    }

}

