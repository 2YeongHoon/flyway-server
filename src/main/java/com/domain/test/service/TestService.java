package com.domain.test.service;

import com.domain.flyway.config.datasource.DataSourceConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestService {

    private final DataSourceConfig config;
    private static List<String> urls = List.of("jdbc:mysql://localhost:3306/TEST",
        "jdbc:mysql://localhost:3306/TEST_00002", "jdbc:mysql://localhost:3306/TEST_00001");

    // 적용하려는 대상과 불러진 데이터와 비교합니다.
    public void checkSchema() {
        for(String url : urls){
            reportFlyway(null, url);
        }
    }

    // flyway를 실행
    // 결과 report를 반환합니다.
    public void run() {
        config.migrate();
    }

    public void reportFlyway(String data, String url) {
        final String query = "SELECT installed_rank, description, success FROM flyway_schema_history";
        final int errCount = 0;

        try (Connection connection = DriverManager.getConnection(url, "root", "test");
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Connected to: " + url);
            System.out.println("Query Results:");

            // 조회 결과 출력
            while (resultSet.next()) {
                final long installedRank = resultSet.getLong("installed_rank");
                final String description = resultSet.getString("description");
                final String success = resultSet.getString("success");

                System.out.printf("id: %d, description: %s, success: %s%n", installedRank, description, success);
            }
        } catch (SQLException e) {
            // 예외 처리
            System.err.println("Error during operation on: " + url);
            e.printStackTrace();
        }
    }

}
