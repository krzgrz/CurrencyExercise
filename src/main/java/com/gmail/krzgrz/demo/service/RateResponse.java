package com.gmail.krzgrz.demo.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.annotation.JsonAppend;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a response received from NBP's REST API (https://api.nbp.pl/api/exchangerates/rates/A/USD?format=json).
 * This class reflects the format of an external REST API and thus does not belong to "domain".
 */
public class RateResponse {

    public String table;
    public String currency;
    public String code;
    public List <RateReport> rates = new ArrayList<>();
}
