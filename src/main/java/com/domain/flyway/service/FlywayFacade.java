package com.domain.flyway.service;

import com.domain.flyway.controller.dto.FlywayInfoResponse;
import com.domain.flyway.infrastructure.FlywayMigrationService;
import com.domain.flyway.infrastructure.FlywayValidateService;
import com.domain.flyway.service.vo.ResultVo;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlywayFacade {

    private static String baseUrl = "jdbc:mysql://localhost:3306/TEST";
    private static List<String> urls = List.of(
        "jdbc:mysql://localhost:3306/TEST_00010",
        "jdbc:mysql://localhost:3306/TEST_00009",
        "jdbc:mysql://localhost:3306/TEST_00008",
        "jdbc:mysql://localhost:3306/TEST_00007",
        "jdbc:mysql://localhost:3306/TEST_00006",
        "jdbc:mysql://localhost:3306/TEST_00005",
        "jdbc:mysql://localhost:3306/TEST_00004",
        "jdbc:mysql://localhost:3306/TEST_00003",
        "jdbc:mysql://localhost:3306/TEST_00002",
        "jdbc:mysql://localhost:3306/TEST_00001");
    private static Map<String, ResultVo> baseSchema;

    private final FlywayMigrationService migrationService;
    private final FlywayValidateService validateService;

    public void migrate() {
        migrationService.migrateAll(urls);
    }

    /**
     * Flyway를 적용하려는 대상과 기본 url을 조회한다.
     */
    public FlywayInfoResponse getInfo() {
        return FlywayInfoResponse.of(urls, baseUrl, urls.size());
    }

    /**
     * 적용하려는 대상과 불러진 데이터와 비교합니다.
     */
    public void checkSchema() {
        for(String url : urls){
            validateService.reportFlyway(baseSchema, url);
        }

    }

    // TODO: ulrs, baseUrl를 초기화 한다.
    public void road() {

    }

    public Map<String, ResultVo> getDefaultSchemaStatus() {
        baseSchema = validateService.baseData(baseUrl);
        return baseSchema;
    }

}
