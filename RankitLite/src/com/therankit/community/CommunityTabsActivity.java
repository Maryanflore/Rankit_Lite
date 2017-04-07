package com.therankit.community; 
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle; 
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager; 
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.reponse.Reponse;
import com.volley.Const;
import com.therankit.rankitlite.R;
@SuppressLint("NewApi")
public class CommunityTabsActivity extends ActionBarActivity {
	 
    private ActionBar actionBar;
    private ViewPager viewPager;
 
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_init);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00A99D")));
		getSupportActionBar().setIcon(R.drawable.product);
		getSupportActionBar().setTitle("Community");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true); 
		
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOnPageChangeListener(onPageChangeListener);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        addActionBarTabs();
    }
 
    private ViewPager.SimpleOnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            actionBar.setSelectedNavigationItem(position);
        }
    };
 
    private void addActionBarTabs() {
        actionBar = getSupportActionBar();
        String[] tabs = {"Home","Co-Rankeur","Activite" };
        for (String tabTitle : tabs) {
            ActionBar.Tab tab = actionBar.newTab().setText(tabTitle)
                    .setTabListener(tabListener);
            actionBar.addTab(tab);
        }
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
      
        //
    }
 
    private ActionBar.TabListener tabListener = new ActionBar.TabListener() {
        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            viewPager.setCurrentItem(tab.getPosition());
        }
 
        @SuppressLint("NewApi")
		@Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        	
        }
 
        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
      
        }
    };
    
}