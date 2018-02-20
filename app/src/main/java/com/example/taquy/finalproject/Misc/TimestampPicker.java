package com.example.taquy.finalproject.Misc;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by taquy on 2/20/2018.
 */

public class TimestampPicker {

    private Context ctx;
    private EditText ipt;
    private String date;
    private String time;

    public TimestampPicker(Context ctx, EditText ipt) {
        this.ctx = ctx;
        this.ipt = ipt;
        this.date = "";
        this.time = "";
    }

    public void show() {
        datePicker();
    }

    private void datePicker () {
        // Preset date for date picker
        final Calendar c = Calendar.getInstance();
        int nowYear = c.get(Calendar.YEAR);
        int nowMonth = c.get(Calendar.MONTH);
        int nowDay = c.get(Calendar.DAY_OF_MONTH);

        // Attach event listener for date picker
        DatePickerDialog picker = new DatePickerDialog(ctx,
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    timepicker();
                }
            }, nowYear, nowMonth, nowDay);

        // Show date picker dialog
        picker.show();
    }
    private void timepicker(){
        // Preset time for time picker
        final Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m = c.get(Calendar.MINUTE);

        // Attach event listener for time picker
        TimePickerDialog picker = new TimePickerDialog(ctx,
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int h, int m) {
                    time =  h + ":" + m + ":" + c.get(Calendar.SECOND);
                    ipt.setText(time + " " + date);
                }
            }, h, m,false);

        // Show time picker dialog
        picker.show();
    }

}
