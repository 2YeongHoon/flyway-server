package com.domain.flyway.config.datasource;

import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DataSourceConfig {

    private final CustomDataSourceProperties properties;
    private static List<String> urls = List.of("jdbc:mysql://localhost:3306/TEST",
        "jdbc:mysql://localhost:3306/TEST_00002", "jdbc:mysql://localhost:3306/TEST_00001");

    public void migrate() {
        migrateHospitalSchemas();
    }

    private void migrateHospitalSchemas() {
        for (String url : urls) {
            migrateSchema(url);
        }
    }

    private void migrateSchema(String url) {

        // jdbc 연결
        DataSource dataSource = new DriverManagerDataSource(
            url + "?serverTimezone=UTC&useLegacyDatetimeCode=false&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true",
            "test",
            "test"
        );

        // hikari cp 연결
//        HikariConfig config = new HikariConfig();
//
//        config.setJdbcUrl(url);
//        config.setUsername("root");
//        config.setPassword("ikoobdev1@");
//
//        DataSource dataSource = new HikariDataSource(config);

        Flyway flyway = Flyway.configure()
            .dataSource(dataSource)
            .baselineOnMigrate(true)
            .load();

        flyway.migrate();
    }

}
