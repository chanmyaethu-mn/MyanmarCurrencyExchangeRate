package com.example.chan.myanmarcurrencyexchangerate;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.chan.myanmarcurrencyexchangerate.api.CurrencyInfoService;
import com.example.chan.myanmarcurrencyexchangerate.api.LatestService;
import com.example.chan.myanmarcurrencyexchangerate.dto.CurrencyInfoDto;
import com.example.chan.myanmarcurrencyexchangerate.dto.ExchangeRateInfoDto;
import com.example.chan.myanmarcurrencyexchangerate.dto.HistoryExchangeInfoDto;
import com.example.chan.myanmarcurrencyexchangerate.common.helper.RetrofitHelper;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testCallLatest();
    }

    private void testCallLatest() {
        LatestService latestService = RetrofitHelper.getLatestExchangeRateApi();
        Call<ExchangeRateInfoDto> latestCall = latestService.getLatestExchangeRate();

        getLatestExchangeRate(latestCall, this);

        /*ExchangeHistoryService exchangeHistoryService = RetrofitHelper.getExchangeHistoryApi();
        Call<ExchangeRateInfoDto> historyCall = exchangeHistoryService.getExchangeHistory("25-01-2018");
        getExchangeHistory(historyCall, this);*/


        CurrencyInfoService currencyInfoService = RetrofitHelper.getCurrencyInfoApi();
        Call<CurrencyInfoDto> currencyInfoCall = currencyInfoService.getCurrencyInfo();
        getCurrencyInfo(currencyInfoCall, this);

    }

    private void getLatestExchangeRate(final Call<ExchangeRateInfoDto> call, Context context) {
        AsyncTask<Void, Void, ExchangeRateInfoDto> asyncTask = new AsyncTask<Void, Void, ExchangeRateInfoDto>() {
            @Override
            protected ExchangeRateInfoDto doInBackground(Void... voids) {
                ExchangeRateInfoDto exchangeRateInfoDto = null;
                try {
                    Response<ExchangeRateInfoDto> response = call.execute();
                    if (null != response.body()) {
                        exchangeRateInfoDto = new ExchangeRateInfoDto();
                        exchangeRateInfoDto.setInfo(response.body().getInfo());
                        exchangeRateInfoDto.setTimestamp(response.body().getTimestamp());
                        exchangeRateInfoDto.setDescription(response.body().getDescription());
                        exchangeRateInfoDto.setRates(response.body().getRates());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return exchangeRateInfoDto;
            }

            @Override
            protected void onPostExecute(ExchangeRateInfoDto testDto) {
                super.onPostExecute(testDto);
                Log.e("LatestExchange ==> ", testDto.toString());
            }
        };

        asyncTask.execute();
    }

    /*private void getLatestExchangeRate(final Call<LatestDto> call, Context context) {
        AsyncTask<Void, Void, LatestDto> asyncTask = new AsyncTask<Void, Void, LatestDto>() {
            @Override
            protected LatestDto doInBackground(Void... voids) {
                LatestDto latestDto = null;
                try {
                    Response<LatestDto> response = call.execute();
                    if (null != response.body()) {
                        latestDto = new LatestDto();
                        latestDto.setInfo(response.body().getInfo());
                        latestDto.setTimestamp(response.body().getTimestamp());
                        latestDto.setDescription(response.body().getDescription());
                        latestDto.setRates(response.body().getRates());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return latestDto;
            }

            @Override
            protected void onPostExecute(LatestDto latestDto) {
                super.onPostExecute(latestDto);
                Log.e("LatestExchange ==> ", latestDto.toString());
            }
        };

        asyncTask.execute();
    }*/

    private void getExchangeHistory(final Call<HistoryExchangeInfoDto> call, Context context) {
        AsyncTask<Void, Void, HistoryExchangeInfoDto> asyncTask = new AsyncTask<Void, Void, HistoryExchangeInfoDto>() {
            @Override
            protected HistoryExchangeInfoDto doInBackground(Void... voids) {
                HistoryExchangeInfoDto historyExchangeInfoDto = null;
                try {
                    Response<HistoryExchangeInfoDto> response = call.execute();
                    if (null != response.body()) {
                        historyExchangeInfoDto = new HistoryExchangeInfoDto();
                        historyExchangeInfoDto.setInfo(response.body().getInfo());
                        historyExchangeInfoDto.setTimestamp(response.body().getTimestamp());
                        historyExchangeInfoDto.setDescription(response.body().getDescription());
                        historyExchangeInfoDto.setRates(response.body().getRates());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return historyExchangeInfoDto;
            }

            @Override
            protected void onPostExecute(HistoryExchangeInfoDto historyExchangeInfoDto) {
                super.onPostExecute(historyExchangeInfoDto);
                Log.e("ExchangeHistory ==> ", historyExchangeInfoDto.toString());
            }
        };

        asyncTask.execute();
    }

    private void getCurrencyInfo(final Call<CurrencyInfoDto> call, Context context) {
        AsyncTask<Void, Void, CurrencyInfoDto> asyncTask = new AsyncTask<Void, Void, CurrencyInfoDto>() {
            @Override
            protected CurrencyInfoDto doInBackground(Void... voids) {
                CurrencyInfoDto currencyInfoDto = null;
                try {
                    Response<CurrencyInfoDto> response = call.execute();
                    if (null != response.body()) {
                        currencyInfoDto = new CurrencyInfoDto();
                        currencyInfoDto.setInfo(response.body().getInfo());
                        currencyInfoDto.setDescription(response.body().getDescription());
                        currencyInfoDto.setCurrencies(response.body().getCurrencies());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return currencyInfoDto;
            }

            @Override
            protected void onPostExecute(CurrencyInfoDto currencyInfoDto) {
                super.onPostExecute(currencyInfoDto);
                Log.e("CurrencyInfo ==> ", currencyInfoDto.toString());
            }
        };

        asyncTask.execute();
    }
}
