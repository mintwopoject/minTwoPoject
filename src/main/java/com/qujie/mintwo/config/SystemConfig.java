package com.qujie.mintwo.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取系统配置
 */
@Component
@ConfigurationProperties(prefix="systemconfig")
public class SystemConfig {

    private String pagenumber;

    public String getPagenumber() {
        return pagenumber;
    }

    public void setPagenumber(String pagenumber) {
        this.pagenumber = pagenumber;
    }
}
