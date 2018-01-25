package com.example.chan.myanmarcurrencyexchangerate.dto;

/**
 * Created by techfun on 1/25/2018.
 */

public class CurrencyInfoDto {
    private Currencies currencies;

    private String description;

    private String info;

    public Currencies getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Currencies currencies) {
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
        return "ClassPojo [currencies = " + currencies + ", description = " + description + ", info = " + info + "]";
    }
}
