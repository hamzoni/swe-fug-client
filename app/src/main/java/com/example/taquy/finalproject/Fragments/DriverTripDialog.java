package com.example.taquy.finalproject.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.taquy.finalproject.Entities.Trip;
import com.example.taquy.finalproject.R;

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

        ipt = v.findViewById(R.id.txt_status);
        ipt.setText(trip.getStatus());

        ipt = v.findViewById(R.id.txt_description);
        ipt.setText(trip.getDescription());

        btn = v.findViewById(R.id.btn_status);
        btn.setOnClickListener(this);

        btn = v.findViewById(R.id.btn_submit);
        btn.setOnClickListener(this);

        btn = v.findViewById(R.id.btn_close);
        btn.setOnClickListener(this);

    }

    public void closeDialog() {
        DriverTripDialog.this.getDialog().cancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_status:

                break;
            case R.id.btn_submit:

                break;
            case R.id.btn_close:
                closeDialog();
                break;
        }
    }
}