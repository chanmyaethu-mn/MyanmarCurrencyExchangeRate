package com.example.chan.myanmarcurrencyexchangerate.dto;

import java.util.Map;

/**
 * Created by techfun on 1/26/2018.
 */

public class ExchangeRateInfoDto {
    private String timestamp;

    private String description;

    private Map<String, String> rates;

    private String info;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getRates() {
        return rates;
    }

    public void setRates(Map<String, String> rates) {
        this.rates = rates;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ExchangeRateInfoDto{" +
                "timestamp='" + timestamp + '\'' +
                ", description='" + description + '\'' +
                ", rates=" + rates +
                ", info='" + info + '\'' +
                '}';
    }
}
