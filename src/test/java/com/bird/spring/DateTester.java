package com.bird.spring;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DateTester {

    @Test
    public void testAddDay(){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(addDay(new Date(), 1000000)));
    }
    public static Date addDay(Date when, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(when);
        c.add(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }
}
