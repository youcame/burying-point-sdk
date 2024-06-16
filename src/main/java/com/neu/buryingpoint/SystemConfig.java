package com.neu.buryingpoint;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(value = "travel.track")
@Data
@ComponentScan
@Configuration
public class SystemConfig {

    /**
     * 发送埋点系统的来源
     */
    String sysId;

    /**
     * 发送埋点系统的token
     */
    String token;

    /**
     * 系统描述
     */
    String sysDescription;

    @Bean
    public SystemInfo systemInfo() {
        return new SystemInfo(sysId, token, sysDescription);
    }


}
