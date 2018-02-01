package com.example.chan.myanmarcurrencyexchangerate.common.base;

import android.app.Application;
import android.content.Context;

import com.example.chan.myanmarcurrencyexchangerate.common.helper.LocaleHelper;


public class MainApplication extends Application {
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
	}
}
