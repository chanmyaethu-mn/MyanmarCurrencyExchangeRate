package com.example.chan.myanmarcurrencyexchangerate.common.helper;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by techfun on 2/1/2018.
 */

public final class NumberHelper {

    /**
     * It will convert myanmar number to english number
     * @param inputString "၀၁-၀၂-၂၀၁၈"
     * @return "01-02-2018"
     */
    public static final String convertMMToEn(@NonNull String inputString) {

        String result = "";
        String[] strArr = inputString.split("");

        Map<String, String> DICT = new HashMap<>();
        {
            DICT.put("၀", "0");
            DICT.put("၁", "1");
            DICT.put("၂", "2");
            DICT.put("၃", "3");
            DICT.put("၄", "4");
            DICT.put("၅", "5");
            DICT.put("၆", "6");
            DICT.put("၇", "7");
            DICT.put("၈", "8");
            DICT.put("၉", "9");
        }

        for (String curString : strArr) {
            String tmpStr = DICT.get(curString);
            if (TextUtils.isEmpty(tmpStr)) {
                result += curString;
            } else {
                result += tmpStr;
            }
        }

        return result;
    }
}
