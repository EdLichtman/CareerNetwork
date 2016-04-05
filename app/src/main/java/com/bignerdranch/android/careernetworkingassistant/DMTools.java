package com.bignerdranch.android.careernetworkingassistant;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by edwardlichtman on 9/16/15.
 */
public class DMTools {
    public static final Date TODAY_DATE = Calendar.getInstance().getTime();

    public static CharSequence FormatDate(Date date) {
        String mDateFormat = "MMM dd, yyyy";
        return android.text.format.DateFormat.format(mDateFormat, date);
    }

}


