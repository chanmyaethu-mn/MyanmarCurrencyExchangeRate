package com.example.chan.myanmarcurrencyexchangerate.common.helper;

import com.example.chan.myanmarcurrencyexchangerate.api.CurrencyInfoService;
import com.example.chan.myanmarcurrencyexchangerate.api.ExchangeHistoryService;
import com.example.chan.myanmarcurrencyexchangerate.api.LatestService;
import com.example.chan.myanmarcurrencyexchangerate.common.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by techfun on 1/25/2018.
 */

public final class RetrofitHelper {

    public static LatestService getLatestExchangeRateApi() {
        final GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        Gson gson = builder.create();

        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
        LatestService resultApi = retrofit.create(LatestService.class);
        return resultApi;
    }

    public static ExchangeHistoryService getExchangeHistoryApi() {
        final GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        Gson gson = builder.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ExchangeHistoryService resultApi = retrofit.create(ExchangeHistoryService.class);
        return resultApi;
    }

    public static CurrencyInfoService getCurrencyInfoApi() {
        final GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        Gson gson = builder.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CurrencyInfoService resultApi = retrofit.create(CurrencyInfoService.class);
        return resultApi;
    }
}
