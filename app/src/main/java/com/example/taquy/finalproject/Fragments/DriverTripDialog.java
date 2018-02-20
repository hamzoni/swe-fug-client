package com.example.taquy.finalproject.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.taquy.finalproject.Entities.Trip;
import com.example.taquy.finalproject.Misc.TimestampPicker;
import com.example.taquy.finalproject.Misc.Tool;
import com.example.taquy.finalproject.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by taquy on 2/20/2018.
 */

public class DriverTripDialog extends DialogFragment implements View.OnClickListener {

    private View v;
    private Trip trip;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.fragment_driver_trip_detail, null);

        builder.setView(v);

        // set events listeners
        // fill dialog values
        fillContent();

        return builder.create();
    }

    private void fillContent() {
        Bundle bundle = this.getArguments();
        trip = (Trip) bundle.getSerializable("trip");
        Button btn;
        EditText ipt;

        ipt = v.findViewById(R.id.txt_from);
        ipt.setText(trip.getFrom());

        ipt = v.findViewById(R.id.txt_to);
        ipt.setText(trip.getTo());

        ipt = v.findViewById(R.id.txt_price);
        ipt.setText(trip.getPriceDisplay());

        ipt = v.findViewById(R.id.txt_time);
        ipt.setText(trip.getTimeDisplay());
        ipt.setOnClickListener(this);

        Spinner spn;
        spn = v.findViewById(R.id.txt_status);

        // Spinner Status
        List<String> items = new ArrayList<String>();
        items.add(Trip.STATUS[0].getName());
        items.add(Trip.STATUS[1].getName());
        items.add(Trip.STATUS[2].getName());

        int spnLayout = R.layout.support_simple_spinner_dropdown_item;
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(getContext(), spnLayout, items);
        spn.setAdapter(spnAdapter);

        spn.setSelection(trip.getStatus());
        //

        ipt = v.findViewById(R.id.txt_description);
        ipt.setText(trip.getDescription());

        btn = v.findViewById(R.id.btn_submit);
        btn.setOnClickListener(this);

        btn = v.findViewById(R.id.btn_close);
        btn.setOnClickListener(this);

    }

    public void closeDialog() {
        DriverTripDialog.this.getDialog().cancel();
    }

    @Override
    public void onClick(View view) {
        EditText ipt = v.findViewById(R.id.txt_time);
        switch (view.getId()) {
            case R.id.txt_time:
                new TimestampPicker(getContext(), ipt).show();
                break;
            case R.id.btn_submit:
                try {
                    Date date = Tool.stringToDate2(ipt.getText().toString());
                    String sqltime = Tool.dateToString2(date);
                    Toast.makeText(getContext(), sqltime, Toast.LENGTH_LONG).show();
                } catch (ParseException e) {
                    Toast.makeText(getContext(), "Wrong date time format", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;
            case R.id.btn_close:
                closeDialog();
                break;
        }
    }


}