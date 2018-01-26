package com.example.chan.myanmarcurrencyexchangerate.dto;

import java.io.Serializable;

/**
 * Created by techfun on 1/26/2018.
 */

public class ExchangeListItemInfoDto {

    private String currencyType;

    private String exchangeRate;

    public ExchangeListItemInfoDto() {

    }

    public ExchangeListItemInfoDto(String currencyType, String exchangeRate) {
        this.currencyType = currencyType;
        this.exchangeRate = exchangeRate;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
