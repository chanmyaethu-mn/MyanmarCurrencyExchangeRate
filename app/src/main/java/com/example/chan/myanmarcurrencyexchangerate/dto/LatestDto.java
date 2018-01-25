package com.example.chan.myanmarcurrencyexchangerate.dto;

/**
 * Created by techfun on 1/25/2018.
 */

public class LatestDto {
    private String timestamp;

    private String description;

    private Rates rates;

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

    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
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
        return "ClassPojo [timestamp = " + timestamp + ", description = " + description + ", rates = " + rates + ", info = " + info + "]";
    }
}
