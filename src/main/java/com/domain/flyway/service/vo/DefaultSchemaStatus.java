package com.domain.flyway.service.vo;

import lombok.Getter;

@Getter
public class DefaultSchemaStatus {

    private final String rank;
    private final ResultVo vo;

    public DefaultSchemaStatus(String rank, ResultVo vo) {
        this.rank = rank;
        this.vo = vo;
    }

}
