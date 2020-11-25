package com.yindwe.tmdata;

import com.yindwe.tmdata.config.UrlConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableConfigurationProperties(UrlConfigProperties.class)
@EnableSwagger2
public class TmDataApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(TmDataApplication.class, args);
        UrlConfigProperties bean = run.getBean(UrlConfigProperties.class);
        System.out.println(bean);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
