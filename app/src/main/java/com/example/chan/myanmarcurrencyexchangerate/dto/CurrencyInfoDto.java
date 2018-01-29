package com.example.chan.myanmarcurrencyexchangerate.dto;

import java.util.Map;

/**
 * Created by techfun on 1/25/2018.
 */

public class CurrencyInfoDto {
    private Map<String, String> currencies;

    private String description;

    private String info;

    public Map<String, String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Map<String, String> currencies) {
        this.currencies = currencies;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "CurrencyInfoDto{" +
                "currencies=" + currencies +
                ", description='" + description + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
