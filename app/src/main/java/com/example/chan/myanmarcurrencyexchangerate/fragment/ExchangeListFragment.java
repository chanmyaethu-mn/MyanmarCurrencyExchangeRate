package com.example.chan.myanmarcurrencyexchangerate.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chan.myanmarcurrencyexchangerate.R;
import com.example.chan.myanmarcurrencyexchangerate.adapter.ExchangeListAdapter;
import com.example.chan.myanmarcurrencyexchangerate.api.LatestService;
import com.example.chan.myanmarcurrencyexchangerate.common.Constants;
import com.example.chan.myanmarcurrencyexchangerate.common.helper.DateHelper;
import com.example.chan.myanmarcurrencyexchangerate.dto.ExchangeListItemInfoDto;
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

        return view;
    }

    private void loadExchangeList() {
       /* ExchangeListItemInfoDto dto = new ExchangeListItemInfoDto();
        dto.setCurrencyType("CAD");
        dto.setExchangeRate("1000.356");
        List<ExchangeListItemInfoDto> list = new ArrayList<>();
        list.add(dto);
        list.add(dto);
        list.add(dto);
        list.add(dto);

        ExchangeListAdapter adapter = new ExchangeListAdapter(this.getContext(), list);

        exListView.setAdapter(adapter);*/

        LatestService latestService = RetrofitHelper.getLatestExchangeRateApi();
        Call<ExchangeRateInfoDto> latestCall = latestService.getLatestExchangeRate();

        getExchangeRate(latestCall, this.getContext());
    }

    private void registerUIs(View view) {
        exChooseDateLabel = (TextView) view.findViewById(R.id.ex_choose_date_label);
        exDateTextView = (TextView) view.findViewById(R.id.ex_date_textview);
        exListView = (ListView) view.findViewById(R.id.ex_listview);

        progressBar = (ProgressBar) view.findViewById(R.id.ex_progress);
        errorTextView = (TextView) view.findViewById(R.id.ex_load_error_textview);
    }

    private void registerEvents() {

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
                /*super.onPostExecute(testDto);
                Log.e("LatestExchange ==> ", testDto.toString());*/
                Log.e("ExchangeRate ==> ", exchangeRateInfoDto.toString());
                bindExchangeList(exchangeRateInfoDto);
            }
        };

        asyncTask.execute();
    }

    private void bindExchangeList(ExchangeRateInfoDto exchangeRateInfoDto) {
        if (0 != exchangeRateInfoDto.getRates().size()) {
            progressBar.setVisibility(View.GONE);
            String exDate = DateHelper.convertTimeStampToDateString(exchangeRateInfoDto.getTimestamp(), Constants.DD_MM_YYYY);
            exDateTextView.setText(exDate);

            List<ExchangeListItemInfoDto> exchangeList = getExchangeList(exchangeRateInfoDto.getRates());
            ExchangeListAdapter adapter = new ExchangeListAdapter(this.getContext(), exchangeList);

            View header = getLayoutInflater().inflate(R.layout.ex_list_header, null);
            exListView.addHeaderView(header);
            exListView.setAdapter(adapter);
        } else {
            progressBar.setVisibility(View.GONE);
            errorTextView.setVisibility(View.VISIBLE);
        }
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
