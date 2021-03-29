package com.playmuscial.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class SKMRestTemplate<T> extends RestTemplate {

    @Value("${SKM.appkey}")
    private String appKey;

    @Value("${SKM.URL}")
    private String skmUrl;

    final private ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, String> URLInfo(String appKey, String keyId) {
        Map<String, String> map = new HashMap<>();
        map.put("appKey", appKey);
        map.put("keyId", keyId);
        return map;
    }

    public T getInfo(String keyId, final Class<T> type) {
        String URL = new StringSubstitutor(URLInfo(appKey, keyId)).replace(skmUrl);
        ResponseEntity<String> responseEntity = getForEntity(URL, String.class);
        String secret;
        T result = null;
        try {
            secret = objectMapper.readValue(responseEntity.getBody(), SKMResponse.class).getBody()
                .getSecret();
            result = objectMapper.readValue(secret, type);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse SKM");
            e.printStackTrace();
        }
        return result;
    }

    @Getter
    @Setter
    public static class SKMResponse {

        private Header header;
        private Body body;
    }

    @Getter
    @Setter
    public static class Header {

        private String resultCode;
        private String resultMessage;
        private String isSuccessful;
    }

    @Getter
    @Setter
    public static class Body {

        private String secret;
    }
}
