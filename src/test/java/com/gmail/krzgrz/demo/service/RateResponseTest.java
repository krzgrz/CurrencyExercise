package com.gmail.krzgrz.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class RateResponseTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    void testDeserialization () throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper ();
//        RateReport rr = objectMapper.readValue("{\"no\":\"a\", \"effectiveDate\":\"b\", \"mid\": \"c\"}", RateReport.class);
//        logger.info("rr=" + rr);
        RateResponse rr2 = objectMapper.readValue("{\"table\":\"A\",\"currency\":\"dolar amerykański\",\"code\":\"USD\",\"rates\":[{\"no\":\"131/A/NBP/2020\",\"effectiveDate\":\"2020-07-08\",\"mid\":3.9666}]}", RateResponse.class);
        assertEquals("A", rr2.table);
        assertEquals("dolar amerykański", rr2.currency);
        assertEquals("USD", rr2.code);
        assertEquals(1, rr2.rates.size());
        //
        RateReport ratePeport = rr2.rates.get(0);
        assertEquals("131/A/NBP/2020",ratePeport.getNo());
        assertEquals("2020-07-08",ratePeport.getEffectiveDate());
        assertEquals("3.9666", ratePeport.getMid());
    }
}