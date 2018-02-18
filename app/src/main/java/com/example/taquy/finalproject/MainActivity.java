package com.example.taquy.finalproject;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.taquy.finalproject.Fragments.DriverFragment;
import com.example.taquy.finalproject.Fragments.HistoryFragment;
import com.example.taquy.finalproject.Fragments.PassengerFragment;
import com.example.taquy.finalproject.Fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private PagerAdapter pager;

    private ViewPager viewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pager = new PagerAdapter(getSupportFragmentManager(), this);

        viewer = (ViewPager) findViewById(R.id.container);
        viewer.setAdapter(pager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        viewer.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewer));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) return true;

        return super.onOptionsItemSelected(item);
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        private AppCompatActivity app;

        public PagerAdapter(FragmentManager fm, AppCompatActivity app) {
            super(fm);
            this.app = app;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0: fragment = PassengerFragment.getInstance(); break;
                case 1: fragment = DriverFragment.getInstance(); break;
                case 2: fragment = ProfileFragment.getInstance(); break;
                case 3: fragment = HistoryFragment.getInstance(); break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
