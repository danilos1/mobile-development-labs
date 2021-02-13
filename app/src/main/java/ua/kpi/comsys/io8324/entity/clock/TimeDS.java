package ua.kpi.comsys.io8324.entity.clock;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeDS {
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat12 = new SimpleDateFormat("hh:mm:ss a");
    private int hours;
    private int minutes;
    private int seconds;

    public static void setDateFormat(SimpleDateFormat dateFormat) {
        TimeDS.dateFormat = dateFormat;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public TimeDS() {
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
    }

    public Date toDate() {
        @SuppressLint("DefaultLocale") String input = String.format("%d:%d:%d", hours, minutes, seconds);
        Date date = null;
        try {
            date = dateFormat.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public TimeDS(int hours, int minutes, int seconds) {
        checkTime(hours, minutes, seconds);
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    private void checkTime(int hours, int minutes, int seconds) {
        if ((hours < 0 || hours > 23) || (minutes < 0 || minutes > 59) || (seconds < 0 || seconds > 59)) {
            throw new IllegalArgumentException("Invalid time format: "+String.format("%d:%d:%d", hours, minutes, seconds));
        }
    }

    public TimeDS(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date object cannot equal to null");
        }
        this.hours = date.getHours();
        this.minutes = date.getMinutes();
        this.seconds = date.getSeconds();

    }

    @Override
    public String toString() {
        @SuppressLint("DefaultLocale") String input = String.format("%d:%d:%d", hours, minutes, seconds);
        Date date = null;
        try {
            date = dateFormat.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateFormat12.format(date);
    }

    public TimeDS addTime(TimeDS time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.toDate());
        calendar.add(Calendar.HOUR, time.getHours());
        calendar.add(Calendar.MINUTE, time.getMinutes());
        calendar.add(Calendar.SECOND, time.getSeconds());

        return new TimeDS(calendar.getTime());
    }

    public TimeDS subtractTime(TimeDS time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.toDate());
        calendar.add(Calendar.HOUR, -time.getHours());
        calendar.add(Calendar.MINUTE, -time.getMinutes());
        calendar.add(Calendar.SECOND, -time.getSeconds());

        return new TimeDS(calendar.getTime());
    }

    public static TimeDS add(TimeDS time1, TimeDS time2) {
        return time1.addTime(time2);
    }

    public static TimeDS sub(TimeDS time1, TimeDS time2) {
        return time1.subtractTime(time2);
    }
}
