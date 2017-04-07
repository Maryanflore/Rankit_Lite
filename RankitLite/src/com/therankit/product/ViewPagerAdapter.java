package com.therankit.product;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class ViewPagerAdapter extends FragmentPagerAdapter {
 
    // Declare the number of ViewPager pages
    final int PAGE_COUNT = 3;
 
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int arg0) {
        switch (arg0) {
 
        // Open FragmentTab1.java
        case 0:
        	ProductActivite_old fragmenttab1 = new ProductActivite_old();
            return fragmenttab1;
            // Open FragmentTab2.java
              
        case 1:
        	ListeAnnonceFragement fragmenttab2 = new ListeAnnonceFragement();
            return fragmenttab2;
  
        // Open FragmentTab3.java
        case 2:
        	ProductActivite_old fragmenttab3 = new ProductActivite_old();
            return fragmenttab3;
        }
        return null;
    }
 
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return PAGE_COUNT;
    }
 
}