package com.nathandiepeveen.nexelroosters.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeConvertion {

    public String ConvertMilliSecondsToTime(long milliSeconds)
    {
        String dateFormat = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return simpleDateFormat.format(calendar.getTime());
    }

    public String ConvertMilliSecondsToFormattedDate(long milliSeconds)
    {
        String dateFormat = "dd-M-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return simpleDateFormat.format(calendar.getTime());
    }

    public String ConvertMilliSecondsToFormattedMonth(long milliSeconds)
    {
        String dateFormat = "M-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return simpleDateFormat.format(calendar.getTime());
    }

    public String ConvertDayToFormattedDay(int day)
    {
        String dateFormat = "dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getStartDate(day).getTime());
        return simpleDateFormat.format(calendar.getTime());
    }

    public Date ConvertFormattedDateToMilliSeconds(String formattedDate)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        Date getDate = new Date();

        try {
            getDate = sdf.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getDate;
    }

    public Date getStartDate(int day)
    {
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE) + day;
        String dateString = date + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.YEAR);
        return ConvertFormattedDateToMilliSeconds(dateString);
    }

}
