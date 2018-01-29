package com.example.chan.myanmarcurrencyexchangerate.dto;

/**
 * Created by CHAN MYAE THU on 1/29/2018.
 */

public class CurrencyListItemInfoDto {
    private String currencyType;

    private String country;

    public CurrencyListItemInfoDto(){}

    public CurrencyListItemInfoDto(String currencyType, String country) {
        this.currencyType = currencyType;
        this.country = country;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
