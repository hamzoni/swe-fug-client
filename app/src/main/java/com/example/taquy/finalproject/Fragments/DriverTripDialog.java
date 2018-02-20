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
import com.example.taquy.finalproject.Misc.Debugger;
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

    private EditText ipt_from;
    private EditText ipt_to;
    private EditText ipt_price;
    private EditText ipt_time;
    private EditText ipt_description;

    private void fillContent() {
        Bundle bundle = this.getArguments();
        trip = (Trip) bundle.getSerializable("trip");
        Button btn;


        ipt_from = v.findViewById(R.id.txt_from);
        ipt_from.setText(trip.getFrom());

        ipt_to = v.findViewById(R.id.txt_to);
        ipt_to.setText(trip.getTo());

        ipt_price = v.findViewById(R.id.txt_price);
        ipt_price.setText(trip.getPriceDisplay());

        ipt_time = v.findViewById(R.id.txt_time);
        ipt_time.setText(trip.getTimeDisplay());
        ipt_time.setOnClickListener(this);

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

        ipt_description = v.findViewById(R.id.txt_description);
        ipt_description.setText(trip.getDescription());

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
        switch (view.getId()) {
            case R.id.txt_time:
                new TimestampPicker(getContext(), ipt_time).show();
                break;
            case R.id.btn_submit:
                try {
                    Date date = Tool.stringToDate2(ipt_time.getText().toString());
                    String sqltime = Tool.dateToString2(date);

                    if (validateFormData()) {
                        
                    }
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

    private boolean validateFormData() {
        String errorMsg = "";

        String from = ipt_from.getText().toString();
        if (from != null) from = from.trim();

        String to = ipt_to.getText().toString();
        if (to != null) to = to.trim();

        String price = ipt_price.getText().toString();

        // from, to must not be empty
        boolean c3 = from.length() != 0 && to.length() != 0;
        if (!c3) errorMsg += "from, to must not be empty\n";

        // from and to must not matched
        boolean c1 = !from.equals(to);
        if (!c1) errorMsg += "from and to must not matched\n";

        // price must contains only digits
        boolean c2 = true;
        try {
            Double.parseDouble(price);
        } catch (Exception e) {
            c2 = false;
            e.printStackTrace();
        }
        if (!c2) errorMsg += "price must contains only digits";

        if (!(c1 && c2 && c3)) {
            Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
        }
        return (c1 && c2 && c3);
    }
}