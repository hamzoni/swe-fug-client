package com.example.taquy.finalproject.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.taquy.finalproject.Entities.Trip;
import com.example.taquy.finalproject.Misc.Tool;
import com.example.taquy.finalproject.R;

import java.util.ArrayList;

public class DriverRecordAdapter extends BaseAdapter {

    private View view;
    private Context ctx;
    private ArrayList<Trip> records;

    public DriverRecordAdapter(View view, ArrayList<Trip> records) {
        if (records == null) records = new ArrayList<>();
        this.view = view;
        this.ctx = view.getContext();
        this.records = records;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View record;
        if (convertView == null) {
            record = inflater.inflate(R.layout.component_driver_record, null);

            Trip trip = records.get(position);

            TextView txtView = (TextView) record.findViewById(R.id.txt_trip_id);
            txtView.setText(trip.getId() + "");

            txtView = (TextView) record.findViewById(R.id.txt_time);
            txtView.setText(Tool.dateToString(trip.getTime()));

            txtView = (TextView) record.findViewById(R.id.txt_destination);
            txtView.setText(trip.getTo());

            txtView = (TextView) record.findViewById(R.id.txt_status);
            txtView.setText(trip.getStatus() + "");
        } else {
            record = (View) convertView;
        }

        return record;
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return records.get(position).getId();
    }
}
