package tech.appzilla.com.setmydate.Utils;

import android.content.Context;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ConvertTime {



    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return "just now";
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

   public static String getDateAndTime(long time){
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        return sfd.format(new Date(time));
    }

    public static String getMyPrettyDate(long neededTimeMilis) {
        Calendar nowTime = Calendar.getInstance();
        Calendar neededTime = Calendar.getInstance();
        neededTime.setTimeInMillis(neededTimeMilis);

        if ((neededTime.get(Calendar.YEAR) == nowTime.get(Calendar.YEAR))) {

            if ((neededTime.get(Calendar.MONTH) == nowTime.get(Calendar.MONTH))) {

                if (neededTime.get(Calendar.DATE) - nowTime.get(Calendar.DATE) == 1) {
                    //here return like "Tomorrow at 12:00"
                    return "Tomorrow at " + DateFormat.format("hh:mm:aa", neededTime);

                } else if (nowTime.get(Calendar.DATE) == neededTime.get(Calendar.DATE)) {
                    //here return like "Today at 12:00"
                    return "Today at " + DateFormat.format("hh:mm:aa", neededTime);

                } else if (nowTime.get(Calendar.DATE) - neededTime.get(Calendar.DATE) == 1) {
                    //here return like "Yesterday at 12:00"
                    return "Yesterday at " + DateFormat.format("hh:mm:aa", neededTime);

                } else {
                    //here return like "May 31, 12:00"
                    return DateFormat.format("MMMM d, hh:mm", neededTime).toString();
                }

            } else {
                //here return like "May 31, 12:00"
                return DateFormat.format("MMMM d, hh:mm", neededTime).toString();
            }

        } else {
            //here return like "May 31 2010, 12:00" - it's a different year we need to show it
            return DateFormat.format("MMMM dd yyyy, hh:mm", neededTime).toString();
        }
    }
}