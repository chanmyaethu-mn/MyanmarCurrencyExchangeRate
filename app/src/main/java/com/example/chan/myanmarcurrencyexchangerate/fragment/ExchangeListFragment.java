package com.example.chan.myanmarcurrencyexchangerate.fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chan.myanmarcurrencyexchangerate.R;
import com.example.chan.myanmarcurrencyexchangerate.adapter.ExchangeListAdapter;
import com.example.chan.myanmarcurrencyexchangerate.api.ExchangeHistoryService;
import com.example.chan.myanmarcurrencyexchangerate.api.LatestService;
import com.example.chan.myanmarcurrencyexchangerate.common.Constants;
import com.example.chan.myanmarcurrencyexchangerate.common.helper.ConnectionHelper;
import com.example.chan.myanmarcurrencyexchangerate.common.helper.DateHelper;
import com.example.chan.myanmarcurrencyexchangerate.dto.ExchangeListItemInfoDto;
import com.example.chan.myanmarcurrencyexchangerate.dto.ExchangeRateInfoDto;
import com.example.chan.myanmarcurrencyexchangerate.helper.RetrofitHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExchangeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExchangeListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView exChooseDateLabel;
    private TextView exDateTextView;
    private ListView exListView;

    private ProgressBar progressBar;
    private TextView errorTextView;

    private DatePickerDialog datePickerDialog;

    public ExchangeListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExchangeListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExchangeListFragment newInstance(String param1, String param2) {
        ExchangeListFragment fragment = new ExchangeListFragment();
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
        View view = inflater.inflate(R.layout.fragment_exchange_list, container, false);

        registerUIs(view);

        // initialize current date to choose date
        exDateTextView.setText(DateHelper.getCurrentDateString(Constants.DD_MM_YYYY));

        loadExchangeList();

        // Hide error message
        errorTextView.setVisibility(View.GONE);

        registerEvents();

        return view;
    }

    private void loadExchangeList() {

        AsyncTask<Void, Void, Boolean> asyncTask = new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected void onPreExecute() {
                exListView.setVisibility(View.GONE);
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
                    LatestService latestService = RetrofitHelper.getLatestExchangeRateApi();
                    Call<ExchangeRateInfoDto> latestCall = latestService.getLatestExchangeRate();
                    getExchangeRate(latestCall, ExchangeListFragment.this.getContext());
                } else {
                    errorTextView.setText(R.string.no_internet);
                    showError();
                }
            }
        };

        asyncTask.execute();
    }

    private void registerUIs(View view) {
        exChooseDateLabel = (TextView) view.findViewById(R.id.ex_choose_date_label);
        exDateTextView = (TextView) view.findViewById(R.id.ex_date_textview);
        exListView = (ListView) view.findViewById(R.id.ex_listview);

        progressBar = (ProgressBar) view.findViewById(R.id.ex_progress);
        errorTextView = (TextView) view.findViewById(R.id.ex_load_error_textview);
    }

    private void registerEvents() {
        exDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
    }

    private void showDatePicker() {
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        datePickerDialog = new DatePickerDialog(ExchangeListFragment.this.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year,
                                  int monthOfYear, int dayOfMonth) {
                String chooseDateString = dayOfMonth + "-" + monthOfYear + 1 + "-" + year;
                String exDate = DateHelper.fromatDateString(chooseDateString, Constants.DD_MM_YYYY);
                exDateTextView.setText(exDate);

                loadExchangeHistoryList(exDate);
            }
        }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    private void loadExchangeHistoryList(@NonNull final String historyDate) {

        AsyncTask<Void, Void, Boolean> asyncTask = new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected void onPreExecute() {
                exListView.setVisibility(View.GONE);
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
                    ExchangeHistoryService exchangeHistoryService = RetrofitHelper.getExchangeHistoryApi();
                    Call<ExchangeRateInfoDto> historyCall = exchangeHistoryService.getExchangeHistory(historyDate);
                    getExchangeRate(historyCall, ExchangeListFragment.this.getContext());
                } else {
                    errorTextView.setText(R.string.no_internet);
                    showError();
                }
            }
        };

        asyncTask.execute();

    }

    private void getExchangeRate(final Call<ExchangeRateInfoDto> call, Context context) {
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
            protected void onPostExecute(ExchangeRateInfoDto exchangeRateInfoDto) {
                bindExchangeList(exchangeRateInfoDto);
            }
        };

        asyncTask.execute();
    }

    private void bindExchangeList(ExchangeRateInfoDto exchangeRateInfoDto) {
        if (null != exchangeRateInfoDto && 0 != exchangeRateInfoDto.getRates().size()) {
            progressBar.setVisibility(View.GONE);
            String exDate = DateHelper.convertTimeStampToDateString(exchangeRateInfoDto.getTimestamp(), Constants.DD_MM_YYYY);
            exDateTextView.setText(exDate);

            List<ExchangeListItemInfoDto> exchangeList = getExchangeList(exchangeRateInfoDto.getRates());
            ExchangeListAdapter adapter = new ExchangeListAdapter(this.getContext(), exchangeList);

            if (null == exListView.getAdapter()) {
                View header = getLayoutInflater().inflate(R.layout.ex_list_header, null);
                exListView.addHeaderView(header);

                exListView.setAdapter(adapter);
            } else {
                exListView.setAdapter(adapter);
            }
            exListView.setVisibility(View.VISIBLE);
        } else {
            showError();
        }
    }

    private void showError() {
        exListView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
    }

    private List<ExchangeListItemInfoDto> getExchangeList(Map<String, String> rates) {
        Set<String> keySet = rates.keySet();
        List<ExchangeListItemInfoDto> resultLit = new ArrayList<>();

        for (String key : keySet) {
            resultLit.add(new ExchangeListItemInfoDto(key, rates.get(key)));
        }
        return resultLit;
    }

}
