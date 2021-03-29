package com.playmuscial.api.config;

import com.playmuscial.api.datasource.DatabaseSelector;
import com.playmuscial.api.datasource.RoutingDataSource;
import com.playmuscial.api.enums.DBType;
import com.playmuscial.api.util.SKMRestTemplate;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private final DatabaseSelector databaseSelector;

    @Value("${AccountDB.keyid}")
    private String account1KeyId;

    @Value("${AccountDB2.keyid}")
    private String account2KeyId;

    final private SKMRestTemplate<Secret> skmRestTemplate;

    @Bean(name = "Db01Datasource")
    public DataSource firstDataSource() {
        return getDataSource(account1KeyId);
    }

    @Bean(name = "Db02Datasource")
    public DataSource secondDataSource() {
        return getDataSource(account2KeyId);
    }

    public DataSource getDataSource(String keyId) {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        Secret secret = skmRestTemplate.getInfo(keyId, Secret.class);
        return dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver")
            .url(secret.getUrl())
            .username(secret.getUsername())
            .password(secret.getPassword())
            .build();
    }

    @Bean
    @Primary
    public DataSource createRoutingDatasource(
        @Qualifier("Db01Datasource") DataSource firstDataSource,
        @Qualifier("Db02Datasource") DataSource secondDataSource) {
        AbstractRoutingDataSource routingDataSource = new RoutingDataSource();

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBType.ACCOUNT1, firstDataSource);
        targetDataSources.put(DBType.ACCOUNT2, secondDataSource);

        databaseSelector.addDatabaseKey(DBType.ACCOUNT1);
        databaseSelector.addDatabaseKey(DBType.ACCOUNT2);

        routingDataSource.setTargetDataSources(targetDataSources);

        return routingDataSource;
    }

    @Getter
    @Setter
    public static class Secret {

        private String url;
        private String username;
        private String password;
    }
}
