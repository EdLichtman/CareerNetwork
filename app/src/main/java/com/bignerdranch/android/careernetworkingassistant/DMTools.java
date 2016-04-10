package com.bignerdranch.android.careernetworkingassistant;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by edwardlichtman on 9/16/15.
 */
public class DMTools {
    public static final Date TODAY_DATE = Calendar.getInstance().getTime();
    private static String mDateFormat = "MMM dd, yyyy";

    public static String formatDateTime(Date dateOrNull) {
        return (dateOrNull == null ? "" : (String)
                android.text.format.DateFormat.format(mDateFormat, dateOrNull));
    }

}


