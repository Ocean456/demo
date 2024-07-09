package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getYearMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(new Date());
    }
}
