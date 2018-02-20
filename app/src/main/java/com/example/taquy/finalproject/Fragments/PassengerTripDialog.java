package com.example.taquy.finalproject.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.taquy.finalproject.Entities.Trip;
import com.example.taquy.finalproject.Misc.Debugger;
import com.example.taquy.finalproject.R;

/**
 * Created by taquy on 2/20/2018.
 */

public class PassengerTripDialog extends DialogFragment implements View.OnClickListener {

    private View v;
    private Trip trip;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.fragment_passenger_trip_detail, null);
        builder.setView(v);

        // set events listeners
        // fill dialog values
        fillContent();

        return builder.create();
    }

    private void fillContent() {
        Bundle bundle = this.getArguments();
        trip = (Trip) bundle.getSerializable("trip");

        TextView txt;
        Button btn;

        txt = v.findViewById(R.id.txt_driver_name);
        txt.setText(trip.getDriver().getName());

        txt = v.findViewById(R.id.txt_bike_info);
        txt.setText(trip.getDriver().getBikeInfo());

        txt = v.findViewById(R.id.txt_time);
        txt.setText(trip.getTimeDisplay());

        txt = v.findViewById(R.id.txt_price);
        txt.setText(trip.getPriceDisplay());

        txt = v.findViewById(R.id.txt_from);
        txt.setText(trip.getFrom());

        txt = v.findViewById(R.id.txt_to);
        txt.setText(trip.getTo());

        txt = v.findViewById(R.id.txt_description);
        txt.setText(trip.getDescription());

        btn = v.findViewById(R.id.btn_booking);
        btn.setOnClickListener(this);

        btn = v.findViewById(R.id.btn_call_driver);
        btn.setText(trip.getDriver().getPhone());
        btn.setOnClickListener(this);

        btn = v.findViewById(R.id.btn_close_dialog);
        btn.setOnClickListener(this);
    }

    public void closeDialog() {
        PassengerTripDialog.this.getDialog().cancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_booking:
                Debugger.log("Later Development");
                break;
            case R.id.btn_call_driver:
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + trip.getDriver().getPhone()));
                startActivity(intent);
                break;
            case R.id.btn_close_dialog:
                closeDialog();
                break;
        }
    }
}