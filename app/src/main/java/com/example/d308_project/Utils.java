package com.example.d308_project;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static void showDatePickerDialog(final Context context, final EditText dateEditText) {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    dateEditText.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    public static Date parseDate(final String dateString){
        final String dateFormat = "yy-MM-dd";
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
        Date parsedDate = null;

        try {
            parsedDate = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return parsedDate;
    }
}


