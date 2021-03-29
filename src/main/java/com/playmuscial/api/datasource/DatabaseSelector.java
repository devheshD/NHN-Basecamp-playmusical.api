package com.playmuscial.api.datasource;

import com.playmuscial.api.enums.DBType;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class DatabaseSelector {

    private ThreadLocal<DBType> contextHolder = new ThreadLocal<>();
    private List<DBType> databaseKeys = new ArrayList<>();

    public DBType getDataSource() {
        return contextHolder.get();
    }

    public void setDataSource(DBType key) {
        contextHolder.set(key);
    }

    public void setDbIndicator(String userId) {
        setDataSource(getDbIndicator(userId));
    }

    public void addDatabaseKey(DBType key) {
        databaseKeys.add(key);
    }

    public final DBType getDbIndicator(String userId) {
        if (userId == null) {
            return DBType.ACCOUNT1;
        }
        return DBType.values()[Math.abs(userId.hashCode()) % 2];
    }
}