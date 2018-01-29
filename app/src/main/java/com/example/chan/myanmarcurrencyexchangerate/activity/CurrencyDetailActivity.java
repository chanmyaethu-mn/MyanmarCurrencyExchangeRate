package com.example.chan.myanmarcurrencyexchangerate.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.chan.myanmarcurrencyexchangerate.R;
import com.example.chan.myanmarcurrencyexchangerate.common.Constants;

public class CurrencyDetailActivity extends AppCompatActivity {

    private TextView cdCurTypeTextView;

    private TextView cdCountryTextView;

    private TextView cdValueTextView;

    private TextView cdExchangeRateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_detail);

        registerUIs();

        Bundle bundle = getIntent().getExtras();
        registerValues(bundle);
    }

    private void registerUIs() {
        cdCurTypeTextView = (TextView) findViewById(R.id.cd_cur_type_textview);
        cdCountryTextView = (TextView) findViewById(R.id.cd_country_textview);
        cdValueTextView = (TextView) findViewById(R.id.cd_value_textview);
        cdExchangeRateTextView = (TextView) findViewById(R.id.cd_ex_rate_textview);
    }

    private void registerValues(Bundle bundle) {
        String currencyType = (String) bundle.get(Constants.CURRENCY_TYPE);
        String country = (String) bundle.get(Constants.COUNTRY);
        String exchangeRate = (String) bundle.get(Constants.EXCHANGE_RATE);

        cdCurTypeTextView.setText(currencyType);
        cdCountryTextView.setText(country);
        cdValueTextView.setText(R.string.cd_value);
        cdExchangeRateTextView.setText(exchangeRate);
    }
}
