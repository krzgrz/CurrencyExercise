package com.gmail.krzgrz.demo.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Represents a single exchange rate reported by NBP.
 * This class reflects the format of an external REST API and thus does not belong to "domain".
 */
//@JsonIgnoreProperties(ignoreUnknown = true
//    @JsonDeserialize(using = RateReportDeser.class)
public class RateReport {

    /** Format for parsing effective date. */
    private static final String EFFECTIVE_DATE_FORMAT = "yyyy-MM-dd";

    /** Time zone for parsing effective date. */
    private static final TimeZone EFFECTIVE_DATE_TIMEZONE = TimeZone.getTimeZone("Europe/Warsaw");

    private static final int SCALE = 4;

    private String no;
    private Date effectiveDate;
    private BigDecimal mid;

    @JsonCreator
    public RateReport (@JsonProperty("no") String no, @JsonProperty("effectiveDate") String effectiveDate, @JsonProperty("mid") String mid) {
        try {
            SimpleDateFormat df = new SimpleDateFormat (EFFECTIVE_DATE_FORMAT);
            df.setTimeZone(EFFECTIVE_DATE_TIMEZONE);
            this.no = no;
            this.effectiveDate = df.parse(effectiveDate);
            this.mid = new BigDecimal (mid).setScale(SCALE);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String getNo() {
        return no;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public BigDecimal getMid() {
        return mid;
    }

    @Override
    public String toString() {
        return "RateReport{no='" + no + "' effectiveDate='" + effectiveDate + "', mid='" + mid + "'}";
    }

}
