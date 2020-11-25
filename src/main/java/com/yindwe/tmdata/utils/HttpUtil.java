package com.yindwe.tmdata.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * http client
 *
 * @author yindwe
 * @date 31/05/2018 4:46 PM
 * @since 1.0
 */
@Slf4j
public class HttpUtil {


    public static <T> T sendHttpGet(RestTemplate restTemplate, String spec, List<String> cookieList, Class<T> clazz) {
        log.info("httpclient通信地址为:" + spec);
        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.COOKIE, cookieList);
        RequestEntity requestEntity;
        try {
            requestEntity = new RequestEntity(null, headers, HttpMethod.GET, URI.create(spec));
        } catch (Exception e) {
            throw e;
        }
        //这里增加适配
        ResponseEntity<?> responseEntity = restTemplate.exchange(requestEntity, clazz);
        if (log.isDebugEnabled()) log.debug("响应数据:{}", responseEntity);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return (T) responseEntity.getBody();
        }
        return null;
    }

}
