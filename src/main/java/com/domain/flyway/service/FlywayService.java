package com.domain.flyway.service;

import com.domain.flyway.config.datasource.DataSourceConfig;
import com.domain.flyway.service.vo.ResultVo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FlywayService {

    private final DataSourceConfig config;
    private static String baseUrl = "jdbc:mysql://localhost:3306/TEST";
    private static List<String> urls = List.of("jdbc:mysql://localhost:3306/TEST",
        "jdbc:mysql://localhost:3306/TEST_00002", "jdbc:mysql://localhost:3306/TEST_00001");

    private static Map<String, ResultVo> baseSchema;

    // 적용하려는 대상과 불러진 데이터와 비교합니다.
    public void checkSchema() {
        for(String url : urls){
            reportFlyway(baseSchema, url);
        }
    }

    public void road() {
        // TODO: ulrs를 초기화 한다.
    }

    // flyway를 실행
    // 결과 report를 반환합니다.
    public void migrate() {
        if (urls.size() != 0) {
            config.migrate(urls);
        }
    }

    public Map<String, ResultVo> getDefaultSchemaStatus() {
        baseSchema = baseData(baseUrl);
        return baseSchema;
    }

    private Map<String, ResultVo> baseData(String url) {
        final String query = "SELECT installed_rank, description, success FROM flyway_schema_history";

        try (Connection connection = DriverManager.getConnection(url, "root", "test");
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()) {

            System.out.println("%nConnected to: " + url);
            System.out.println("Query Results:");

            Map<String, ResultVo> ddd = new HashMap<>();

            while(resultSet.next()) {
                final String installedRank = resultSet.getString("installed_rank");
                final String description = resultSet.getString("description");
                final String success = resultSet.getString("success");

                System.out.printf("id: %s, description: %s, success: %s%n", installedRank, description, success);
                ddd.put(description, new ResultVo(description, success));
            }
            return ddd;

        } catch (SQLException e) {
            // 예외 처리
            System.err.println("Error during operation on: " + url);
            e.printStackTrace();
        }

        throw new RuntimeException();
    }

    public void reportFlyway(Map<String, ResultVo> baseSchema, String url) {
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

                if (baseSchema.get(description) == null || "0".equals(success)) {
                    System.out.printf("fail id: %d, description: %s, %n", installedRank, description);
                }
            }
        } catch (SQLException e) {
            // 예외 처리
            System.err.println("Error during operation on: " + url);
            e.printStackTrace();
        }
    }

}
