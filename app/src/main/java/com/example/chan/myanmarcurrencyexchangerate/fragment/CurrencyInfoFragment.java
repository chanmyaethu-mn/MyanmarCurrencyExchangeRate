package com.example.chan.myanmarcurrencyexchangerate.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chan.myanmarcurrencyexchangerate.R;
import com.example.chan.myanmarcurrencyexchangerate.activity.CurrencyDetailActivity;
import com.example.chan.myanmarcurrencyexchangerate.adapter.CurrencyInfoListAdapter;
import com.example.chan.myanmarcurrencyexchangerate.adapter.ExchangeListAdapter;
import com.example.chan.myanmarcurrencyexchangerate.api.CurrencyInfoService;
import com.example.chan.myanmarcurrencyexchangerate.api.LatestService;
import com.example.chan.myanmarcurrencyexchangerate.common.Constants;
import com.example.chan.myanmarcurrencyexchangerate.common.helper.ConnectionHelper;
import com.example.chan.myanmarcurrencyexchangerate.dto.CurrencyInfoDto;
import com.example.chan.myanmarcurrencyexchangerate.dto.CurrencyListItemInfoDto;
import com.example.chan.myanmarcurrencyexchangerate.dto.ExchangeRateInfoDto;
import com.example.chan.myanmarcurrencyexchangerate.helper.RetrofitHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrencyInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrencyInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView ctDateTextView;
    private ListView ctListView;

    private ProgressBar progressBar;
    private TextView errorTextView;

    public CurrencyInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrencyInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrencyInfoFragment newInstance(String param1, String param2) {
        CurrencyInfoFragment fragment = new CurrencyInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_currency_info, container, false);

        registerUIs(view);

        errorTextView.setVisibility(View.GONE);

        loadCurrencyInfo();

        return view;
    }

    private void registerUIs(View view) {
        ctListView = (ListView) view.findViewById(R.id.ct_listview);

        progressBar = (ProgressBar) view.findViewById(R.id.ct_progress);
        errorTextView = (TextView) view.findViewById(R.id.ct_load_error_textview);
    }

    private void loadCurrencyInfo() {
        AsyncTask<Void, Void, Boolean> asyncTask = new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected void onPreExecute() {
                ctListView.setVisibility(View.GONE);
                errorTextView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                return ConnectionHelper.isInternetAvailable();
            }

            @Override
            protected void onPostExecute(Boolean isConAvailable) {
                if (isConAvailable) {
                    CurrencyInfoService currencyInfoService = RetrofitHelper.getCurrencyInfoApi();
                    Call<CurrencyInfoDto> currencyInfoCall = currencyInfoService.getCurrencyInfo();
                    getCurrencyInfo(currencyInfoCall, CurrencyInfoFragment.this.getContext());
                } else {
                    errorTextView.setText(R.string.no_internet);
                    showError();
                }
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
                bindCurrencyInfoList(currencyInfoDto);
            }
        };

        asyncTask.execute();
    }

    private void bindCurrencyInfoList(CurrencyInfoDto currencyInfoDto) {
        if ( null !=  currencyInfoDto && 0 != currencyInfoDto.getCurrencies().size()) {
            progressBar.setVisibility(View.GONE);

            final List<CurrencyListItemInfoDto> currencyList = getCurrencyListItemList(currencyInfoDto.getCurrencies());
            CurrencyInfoListAdapter adapter = new CurrencyInfoListAdapter(this.getContext(), currencyList);

            View header = getLayoutInflater().inflate(R.layout.ct_list_header, null);
            ctListView.addHeaderView(header);

            ctListView.setAdapter(adapter);
            ctListView.setVisibility(View.VISIBLE);

            ctListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    // to prevent header click
                    if (0 == position) {
                        return;
                    }

                    LatestService latestService = RetrofitHelper.getLatestExchangeRateApi();
                    Call<ExchangeRateInfoDto> latestCall = latestService.getLatestExchangeRate();
                    loadCurrencyInfoDetail(currencyList, position-=1, latestCall);
                }
            });
        } else {
            showError();
        }
    }

    private void loadCurrencyInfoDetail(final List<CurrencyListItemInfoDto> currencyList, final int position, final Call<ExchangeRateInfoDto> call) {

        AsyncTask<Void, Void, ExchangeRateInfoDto> asyncTask = new AsyncTask<Void, Void, ExchangeRateInfoDto>() {
            boolean isConAvailable;
            @Override
            protected ExchangeRateInfoDto doInBackground(Void... voids) {
                ExchangeRateInfoDto exchangeRateInfoDto = null;
                if (ConnectionHelper.isInternetAvailable()) {
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
                }
                return exchangeRateInfoDto;
            }

            @Override
            protected void onPreExecute() {
                isConAvailable = ConnectionHelper.isInternetAvailable();
            }

            @Override
            protected void onPostExecute(ExchangeRateInfoDto  exchangeRateInfoDto) {
                if (null != exchangeRateInfoDto) {
                    String currencyType = currencyList.get(position).getCurrencyType();
                    String country = currencyList.get(position).getCountry();
                    String exchangeRate = exchangeRateInfoDto.getRates().get(currencyType);
                    launchCurrencyInfoDetail(currencyType, country, exchangeRate);
                } else {
                    showError();
                }
            }
        };

        asyncTask.execute();
    }

    private void launchCurrencyInfoDetail(String currencyType, String country, String exchangeRate) {
        Intent intent = new Intent(getActivity(), CurrencyDetailActivity.class);
        intent.putExtra(Constants.CURRENCY_TYPE, currencyType);
        intent.putExtra(Constants.COUNTRY, country);
        intent.putExtra(Constants.EXCHANGE_RATE, exchangeRate);

        startActivity(intent);
    }

    private List<CurrencyListItemInfoDto> getCurrencyListItemList(Map<String, String> currency) {
        Set<String> keySet = currency.keySet();
        List<CurrencyListItemInfoDto> resultLit = new ArrayList<>();

        for (String key : keySet) {
            resultLit.add(new CurrencyListItemInfoDto(key, currency.get(key)));
        }
        return resultLit;
    }

    private void showError() {
        ctListView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
    }


}
