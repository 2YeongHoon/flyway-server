package com.domain.flyway.config.datasource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "custom.datasource")
public class CustomDataSourceProperties {

//    private String url;
//    private String urlOption;
//    private String schema;
//    private String username;
//    private String password;
//    private String schemaPrefix;
//    private List<String> hospitals;
//    private List<String> hospitalDbs;

}
