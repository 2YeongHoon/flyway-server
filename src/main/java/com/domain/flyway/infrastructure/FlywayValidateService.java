package com.domain.flyway.infrastructure;

import com.domain.flyway.service.vo.ResultVo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlywayValidateService {

    public Map<String, ResultVo> baseData(String url) {
        final String query = "SELECT installed_rank, description, success FROM flyway_schema_history";

        try (Connection connection = DriverManager.getConnection(url, "root", "ikoobdev1@");
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Connected to: " + url);
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
        final int baseSchemaCount = baseSchema.size();
        boolean existBaseLine = false;
        String failReason = "";

        try (Connection connection = DriverManager.getConnection(url, "root", "ikoobdev1@");
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Connected to: " + url);

            // 조회 결과 출력
            while (resultSet.next()) {
                final long installedRank = resultSet.getLong("installed_rank");
                final String description = resultSet.getString("description");
                final String success = resultSet.getString("success");

                // baseline 존재 여부 확인
                if ("<< Flyway Baseline >>".equals(description)) {
                    existBaseLine = true;
                }

                // 성공 여부 검증
                if ("0".equals(success)) {
                    failReason = "id: " + installedRank + "not success";
                    break;
                }
            }

            // baseLine 여부에 따라서 최종 카운트 수 조절
            int size = existBaseLine ? resultSet.getFetchSize() - 1: resultSet.getFetchSize();

            // 기본 스키마 상태 검증
            if(size != baseSchemaCount){
                failReason = "different row size";
            }

        } catch (SQLException ignore) {
            // 예외 처리
            failReason = "unKnown Exception";
            ignore.printStackTrace();
        } finally {
            if ("".equals(failReason)) {

            } else {
                System.out.println("fail: " + failReason);
            }
        }
    }

}
