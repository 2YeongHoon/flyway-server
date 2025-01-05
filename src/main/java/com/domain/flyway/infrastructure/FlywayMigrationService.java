package com.domain.flyway.infrastructure;

import com.domain.flyway.config.properties.CustomDataSourceProperties;
import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlywayMigrationService {

    private final CustomDataSourceProperties properties;

    public void migrateAll(List<String> urls) {
        for(String url : urls){
            migrateByJdbc(url);
        }
    }

    private void migrateByJdbc(String url) {
        try {
            DataSource dataSource = new DriverManagerDataSource(
                url + properties.getUrlOption(),
                properties.getUserName(),
                properties.getPassword()
            );

            Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .baselineOnMigrate(true)
                .load();

            flyway.migrate();
        } catch (Exception ignore) {
            // 실패하더라도 다음 스키마에 대한 작업을 진행한다.
            ignore.printStackTrace();
        }
    }

    /**
     * HikariCP dataSource
     */
//    private void migrateHikari(String url) {
//
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl(url);
//        config.setUsername(properties.getUserName());
//        config.setPassword(properties.getPassword());
//
//        Flyway flyway = Flyway.configure()
//            .dataSource(new HikariDataSource(config))
//            .baselineOnMigrate(true)
//            .load();
//
//        flyway.migrate();
//    }

}
