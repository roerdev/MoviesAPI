package com.roerdev.moviesapi.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@Slf4j
public class DateUtils {

    private DateUtils() {
    }

    public static synchronized Date getToFullDay() {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        format.setCalendar(cal);
        return cal.getTime();
    }
}
