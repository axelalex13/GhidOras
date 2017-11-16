package com.example.alex.ghidoras;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import com.example.alex.ghidoras.AddFragment.OnFragmentInteractionListener;
import com.example.alex.ghidoras.utils.ViewPagerAdapter;

public class HomeActivity extends AppCompatActivity
        implements OnFragmentInteractionListener,EventFragment.OnFragmentInteractionListener {
    ViewPager view_pager;
    ViewPagerAdapter adapter;
    public static SlidingTabLayout tabs;
    CharSequence Titles[] = {"Evenimente","Adauga"};
    int Numboftabs = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Ghid Oras");
        getSupportActionBar().setIcon(getResources().getDrawable(R.drawable.man));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.black)));


//        this.getSupportActionBar().setElevation(0);




       /// Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        view_pager = (ViewPager) findViewById(R.id.view_pagerHome);
        view_pager.setAdapter(adapter);
        //seteaza cate pagini vor fi retinute! daca vrem sa avem refresh de fiecare data trebuie setata la 0
        view_pager.setOffscreenPageLimit(2);


        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabsHome);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setCustomTabView(R.layout.custom_tab, 0);
        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(view_pager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.menu_lang) {

            Intent i = new Intent(HomeActivity.this, UserProfile.class);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }
}
