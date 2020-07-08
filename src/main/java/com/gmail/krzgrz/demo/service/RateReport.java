package com.gmail.krzgrz.demo.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a single exchange rate reported by NBP.
 * This class reflects the format of an external REST API and thus does not belong to "domain".
 */
//@JsonIgnoreProperties(ignoreUnknown = true
//    @JsonDeserialize(using = RateReportDeser.class)
public class RateReport {

    private String no;
    private String effectiveDate;
    private String mid;

    @JsonCreator
    public RateReport (@JsonProperty("no") String no, @JsonProperty("effectiveDate") String effectiveDate, @JsonProperty("mid") String mid) {
        this.no = no;
        this.effectiveDate = effectiveDate;
        this.mid = mid;
    }

    public String getNo() {
        return no;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public String getMid() {
        return mid;
    }

    @Override
    public String toString() {
        return "RateReport{no='" + no + "' effectiveDate='" + effectiveDate + "', mid='" + mid + "'}";
    }

}
