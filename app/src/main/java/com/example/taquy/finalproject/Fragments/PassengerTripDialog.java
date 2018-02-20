package com.example.taquy.finalproject.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.taquy.finalproject.R;

/**
 * Created by taquy on 2/20/2018.
 */

public class PassengerTripDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_passenger_trip_detail, null);
        builder.setView(view)
        .setPositiveButton("A", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        }).setPositiveButton("B", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        })
        .setNegativeButton("C", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                closeDialog();
            }
        });
        return builder.create();
    }

    public FragmentManager getManager() {
        return getFragmentManager();
    }

    public void closeDialog() {
        PassengerTripDialog.this.getDialog().cancel();
    }
}