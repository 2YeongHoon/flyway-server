package com.domain.flyway.controller.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class FlywayInfoResponse {

    private final List<String> urls;

    private final String baseUrl;

    private final int totalUrlsCount;

    private FlywayInfoResponse(List<String> urls, String baseUrl, int totalCount) {
        this.urls = urls;
        this.baseUrl = baseUrl;
        this.totalUrlsCount = totalCount;
    }

    public static FlywayInfoResponse of(List<String> urls, String baseUrl, int totalCount) {
        return new FlywayInfoResponse(urls, baseUrl, totalCount);
    }

}
