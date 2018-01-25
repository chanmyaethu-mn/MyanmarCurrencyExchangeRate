package com.example.chan.myanmarcurrencyexchangerate.api;


import com.example.chan.myanmarcurrencyexchangerate.dto.LatestDto;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by techfun on 1/25/2018.
 */

public interface LatestService {

    @GET("api/latest")
    Call<LatestDto> getLatestExchangeRate();
}
