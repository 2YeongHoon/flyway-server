package com.domain.flyway.service.vo;

import lombok.Getter;

@Getter
public class ResultVo {

    private final String description;
    private final String success;

    public ResultVo(String description, String success) {
        this.description = description;
        this.success = success;
    }

}
