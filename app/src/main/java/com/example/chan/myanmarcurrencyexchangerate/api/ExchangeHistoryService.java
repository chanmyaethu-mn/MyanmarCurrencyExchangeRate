package com.example.chan.myanmarcurrencyexchangerate.api;


import com.example.chan.myanmarcurrencyexchangerate.dto.HistoryExchangeInfoDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by techfun on 1/25/2018.
 */

public interface ExchangeHistoryService {
    @GET("api/history/{date}")
    Call<HistoryExchangeInfoDto> getExchangeHistory(@Path("date") String date);
}
