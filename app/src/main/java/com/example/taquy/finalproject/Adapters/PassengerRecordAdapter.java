package com.example.taquy.finalproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taquy.finalproject.Entities.Trip;
import com.example.taquy.finalproject.Entities.User;
import com.example.taquy.finalproject.Misc.Tool;
import com.example.taquy.finalproject.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by taquy on 2/18/2018.
 */

public class PassengerRecordAdapter  extends BaseAdapter {

    private View view;
    private Context ctx;
    private ArrayList<Trip> records;

    public PassengerRecordAdapter(View view, ArrayList<Trip> records) {
        if (records == null) records = new ArrayList<>();
        this.view = view;
        this.ctx = view.getContext();
        this.records = records;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View record;
        if (convertView == null) {
            record = inflater.inflate(R.layout.component_passenger_record, null);

            Trip trip = records.get(position);

            ImageView img_avatar = record.findViewById(R.id.img_avatar);

            String avaUrl = trip.getDriver().getAvatar();
            if (avaUrl != null) try {
                img_avatar.setImageBitmap(Tool.loadBitmap(avaUrl));
            } catch (IOException e) {
                e.printStackTrace();
            }

            TextView txt = record.findViewById(R.id.txt_driver_name);

            User driver = trip.getDriver();
            txt.setText(driver.getName());

            txt = record.findViewById(R.id.txt_bike_info);
            txt.setText(driver.getBrand() + " " + driver.getPlate());

            txt = record.findViewById(R.id.txt_time);
            txt.setText(trip.getTimeDisplay());

            txt = record.findViewById(R.id.txt_price);
            txt.setText(trip.getPriceDisplay());

            txt = record.findViewById(R.id.txt_destination);
            txt.setText(trip.getToDisplay());
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
