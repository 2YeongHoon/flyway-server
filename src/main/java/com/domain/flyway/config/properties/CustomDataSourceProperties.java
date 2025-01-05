package com.domain.flyway.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "custom.flyway")
public class CustomDataSourceProperties {

    private String urlOption;
    private String userName;
    private String password;

}
