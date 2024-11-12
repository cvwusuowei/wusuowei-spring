package com.wusuowei.config;

import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;

import static com.wusuowei.enums.ZoneEnum.SHANGHAI;

@Configuration
public class GlobalZoneConfig {
    //全局时间
    @PostConstruct
    public void setGlobalZone() {
        TimeZone.setDefault(TimeZone.getTimeZone(SHANGHAI.getZone()));
    }

}
