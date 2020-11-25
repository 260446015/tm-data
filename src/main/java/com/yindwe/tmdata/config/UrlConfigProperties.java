package com.yindwe.tmdata.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author yindwe@yonyou.com
 * @Date 2020/11/25
 */
@ConfigurationProperties(prefix = "tm-data")
@Data
public class UrlConfigProperties {
    private String trendUrl;
    private String trendDetailUrl;
}
