package com.gmail.krzgrz.demo.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Component
public class RateService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    public void start () throws JsonProcessingException {
        // TODO: make it asynchronous and fail-safe...
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity <RateResponse> response = restTemplate.getForEntity("https://api.nbp.pl/api/exchangerates/rates/A/USD?format=json", RateResponse.class);
        if (response.getStatusCode().is2xxSuccessful()) {
//            String map = response.getBody();
//            logger.info("rateReport=" + map);
            ObjectMapper objectMapper = new ObjectMapper();
            RateReport rr = objectMapper.readValue("{\"no\":\"a\", \"effectiveDate\":\"b\", \"mid\": \"c\"}", RateReport.class);
            logger.info("rr=" + rr);
            RateResponse rr2 = objectMapper.readValue("{\"rates\":[{\"no\":\"a\", \"effectiveDate\":\"b\", \"mid\": \"c\"}]}", RateResponse.class);
            logger.info("rr2=" + rr2);
            logger.info("rr2=" + rr2.rates.size());
            logger.info("rr2=" + rr2.rates.get(0));
            RateResponse rr3 = response.getBody();//objectMapper.readValue(map, RateResponse.class);
            logger.info("rr3=" + rr3);
            logger.info("rr3=" + rr3.rates.size());
            logger.info("rr3=" + rr3.rates.get(0));
//            logger.info("rateReport=" + map.get("rates"));
//            logger.info("rateReport=" + map.get("rates").getClass());
//            logger.info("rateReport=" + ((List)map.get("rates")).get(0).getClass());
//            logger.info("rateReport=" + ((List)map.get("rates")).get(0));
//            logger.info("rateReport=" + map.rates);
        };
    }

    /**
     * Returns current exchange rate for given currency pair.
     * @param currency1
     * @param currency2
     * @return  Should be interpreted as "1 currency1 = nnn currency2".
     */
    public BigDecimal getExchangeRate (Currency currency1, Currency currency2) {
        return new BigDecimal (1.23);
    }
}
