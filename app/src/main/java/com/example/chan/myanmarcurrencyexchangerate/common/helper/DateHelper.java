package com.example.chan.myanmarcurrencyexchangerate.common.helper;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by techfun on 1/26/2018.
 */

public final class DateHelper {

    public static String convertTimeStampToDateString(@NonNull String timeStamp,@NonNull String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long unixSeconds = Long.parseLong(timeStamp);
        Date date = new Date(unixSeconds*1000L);
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public static String getCurrentDateString(@NonNull String format) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(calendar.getTime());
    }
}
