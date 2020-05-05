package com.example.subhadiproy.androidnavigationview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Locale;
import java.util.Random;

public class Utils {

    public static ColorDrawable[] vibrantLightColorList =
            {
                    new ColorDrawable(Color.parseColor("#ffeead")),
                    new ColorDrawable(Color.parseColor("#93cfb3")),
                    new ColorDrawable(Color.parseColor("#fd7a7a")),
                    new ColorDrawable(Color.parseColor("#faca5f")),
                    new ColorDrawable(Color.parseColor("#1ba798")),
                    new ColorDrawable(Color.parseColor("#6aa9ae")),
                    new ColorDrawable(Color.parseColor("#ffbf27")),
                    new ColorDrawable(Color.parseColor("#d93947"))
            };

    public static ColorDrawable getRandomDrawbleColor() {
        int idx = new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[idx];
    }

    public static String DateToTimeFormat(String oldstringDate){
        PrettyTime p = new PrettyTime(new Locale(getCountry()));
        String isTime = null;
        try {
            isTime = oldstringDate.substring(11,16);
            int i = Integer.parseInt(isTime.substring(0,2));
            if (i > 12){

                isTime = Integer.toString(i - 12 ) + isTime.substring(2) + " PM";

            }
            else if (i == 12){

                isTime = isTime + " PM";

            }
            else{

                isTime = isTime + " AM";

            }
            /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
                    Locale.ENGLISH);
            Date date = sdf.parse(oldstringDate);
            isTime = p.format(date); */

        } catch (Exception e) {
            e.printStackTrace();
            isTime = "Not Available";
        }

        return isTime;
    }

    public static String DateFormat(String oldstringDate){
        String newDate = null;
        //SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy", new Locale(getCountry()));
        try {
            /*Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldstringDate);
            newDate = dateFormat.format(date); */

            newDate = oldstringDate.substring(8,10) + "-" + oldstringDate.substring(5,7) + "-" + oldstringDate.substring(0,4);

        } catch (Exception e) {
            e.printStackTrace();
            newDate = "Not Available";
        }

        return newDate;
    }

    public static String getCountry(){
        Locale locale = Locale.getDefault();
        String country = String.valueOf(locale.getCountry());
        return country.toLowerCase();
    }

}