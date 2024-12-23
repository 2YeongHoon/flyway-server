package com.domain.test.service;

import com.domain.flyway.config.datasource.DataSourceConfig;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestService {

    private final DataSourceConfig config;
    public static List<String> urls = new LinkedList<>();

    // db 정보를 외부로 부터 load합니다.
    public void loadUrls() {
    }

    // 적용하려는 대상과 불러진 데이터와 비교합니다.
    public void checkSchema() {

    }

    // flyway를 실행
    // 결과 report를 반환합니다.
    public void run() {
        config.migrate();
    }

    public void reportFlyway() {

    }

}
