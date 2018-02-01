package com.example.chan.myanmarcurrencyexchangerate.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.chan.myanmarcurrencyexchangerate.R;
import com.example.chan.myanmarcurrencyexchangerate.common.Constants;
import com.example.chan.myanmarcurrencyexchangerate.common.helper.LocaleHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView tvChangeLanguage;
    private RadioGroup rgrpLanguage;
    private RadioButton rbtnEn;
    private RadioButton rbtnMm;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        registerUIs(view);

        registerValues();

        registerEvents();

        return view;
    }

    private void registerUIs(View view) {
        rgrpLanguage = view.findViewById(R.id.rgrp_language);
        rbtnEn = view.findViewById(R.id.rbtn_en);
        rbtnMm = view.findViewById(R.id.rbtn_mm);
        tvChangeLanguage = view.findViewById(R.id.tv_change_language);
    }

    private void registerValues() {
        String defaultLanguage = LocaleHelper.getLanguage(this.getContext());
        if (TextUtils.equals(defaultLanguage, Constants.LANGUAGE_EN)) {
            rbtnEn.setChecked(true);
        } else {
            rbtnMm.setChecked(true);
        }
    }

    private void registerEvents() {
        rgrpLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                doCheckEvent(checkId);
            }
        });
    }

    private void doCheckEvent(int checkId) {
        switch (checkId) {
            case R.id.rbtn_en:
                changeLocale(Constants.LANGUAGE_EN);
                break;
            case R.id.rbtn_mm:
                changeLocale(Constants.LANGUAGE_MM);
                break;
            default:
                break;
        }
    }

    private void changeLocale(String languageCode) {
        Context context = LocaleHelper.setLocale(SettingFragment.this.getContext(), languageCode);
        Resources resources = context.getResources();

        rbtnEn.setText(resources.getString(R.string.label_en));
        rbtnMm.setText(resources.getString(R.string.label_mm));
        tvChangeLanguage.setText(resources.getString(R.string.label_change_language));
    }

    private void sendBroadCast(String changedLanguage) {
        Intent intent = new Intent();
        intent.putExtra(Constants.CHANGED_LANGUAGE, changedLanguage);
        intent.setAction(Constants.LANGUAGE_CHANGE_ACTION);
        this.getContext().sendBroadcast(intent);
    }

}
