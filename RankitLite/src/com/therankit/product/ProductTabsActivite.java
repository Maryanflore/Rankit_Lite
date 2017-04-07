package com.therankit.product;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogssample.PreferenceActivity;
import com.afollestad.materialdialogssample.PreferenceActivityCompat;
import com.load.img.ImageLoaderProfil;
import com.therankit.community.CommunityTabsActivit;
import com.therankit.home.MainActivity;
import com.therankit.rankitlite.R;
import com.therankit.services.IconTextTabsActivity;

import com.volley.Const;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;


public class ProductTabsActivite extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton fab;
    private ActionBarDrawerToggle drawerToggle;
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private int[] tabIcons = {
            R.drawable.all,
            R.drawable.new_anonce,
            R.drawable.ranking
    };
    private int randkiteUser_id;
    private String telephoneUtilisateur;
    private String imgUtilisateur;
    private String NomPrenom;
    private String loginUtilisateur;
    private String uriImage;
    private String emailUtilisateur;
    private SharedPreferences settings;
    private ImageView profil_picture;
    public ImageLoaderProfil imageLoader;
    private TextView mail;
    private TextView nom_profil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_text_tabs);
        imageLoader=new ImageLoaderProfil(this);
        settings = getSharedPreferences(Const.PREFRENCES_NAME, Context.MODE_PRIVATE);
        //settings.edit().putString("pathNameImage_dynamique", "0").commit();


        Bundle extras=getIntent().getExtras();

        if(settings.getString("init", "0").equals("0"))
        {
            randkiteUser_id=new Integer(extras.getInt("randkiteUser_id"));
            telephoneUtilisateur=new String(extras.getString("randkiteUser_phone"));
            imgUtilisateur=new String(extras.getString("randkiteUser_picture"));
            NomPrenom=new String(extras.getString("randkiteUser_name"));
            loginUtilisateur=new String(extras.getString("randkiteUser_surname"));
            emailUtilisateur=new String(extras.getString("randkiteUser_email"));
            //categorie=new String(extras.getString("categorie"));

        }
        else
        {




            randkiteUser_id=settings.getInt("randkiteUser_id",0);
            telephoneUtilisateur=settings.getString("randkiteUser_phone","");
            imgUtilisateur=settings.getString("randkiteUser_picture","");
            NomPrenom=settings.getString("randkiteUser_name","");
            loginUtilisateur=settings.getString("randkiteUser_surname","");
            emailUtilisateur=settings.getString("randkiteUser_email","");

        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0);
        drawerLayout.setDrawerListener(drawerToggle);


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);


                        return true;
                    }
                });
        View header = LayoutInflater.from(this).inflate(R.layout.navigation_view_header, null);
        //navigationView.addHeaderView(header);
        View v = navigationView.getHeaderView(0);
        profil_picture = (ImageView) v.findViewById(R.id.photos);
        mail  =(TextView) v.findViewById(R.id.mail_profil);
        nom_profil=(TextView) v.findViewById(R.id.nom_profil);
        mail.setText(emailUtilisateur);
        nom_profil.setText(NomPrenom);
        imageLoader.DisplayImage(Const.ipUriImage+imgUtilisateur,profil_picture);
      setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag( new ProductActivite(), getString(R.string.onglet_all));
        adapter.addFrag(new ListeAnnonceFragement(), getString(R.string.onglet_nouv));
        adapter.addFrag(new ProductActivite_old(), getString(R.string.onglet_class));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) ||
                super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        Snackbar.make(fab, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    public void selectDrawerItem(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.servicesHome:
                Intent intentService = new Intent(getBaseContext(), IconTextTabsActivity.class);
                intentService.putExtra("randkiteUser_id", randkiteUser_id);
                intentService.putExtra("randkiteUser_phone", telephoneUtilisateur);
                intentService.putExtra("randkiteUser_picture", imgUtilisateur);
                intentService.putExtra("randkiteUser_name", NomPrenom);
                intentService.putExtra("randkiteUser_surname", loginUtilisateur);
                intentService.putExtra("randkiteUser_email", emailUtilisateur);
                startActivity(intentService);
                finish();
                break;
            case R.id.ProductHome:
                Intent intentProduct = new Intent(getBaseContext(), ProductTabsActivite.class);
                intentProduct.putExtra("randkiteUser_id", randkiteUser_id);
                intentProduct.putExtra("randkiteUser_phone", telephoneUtilisateur);
                intentProduct.putExtra("randkiteUser_picture", imgUtilisateur);
                intentProduct.putExtra("randkiteUser_name", NomPrenom);
                intentProduct.putExtra("randkiteUser_surname", loginUtilisateur);
                intentProduct.putExtra("randkiteUser_email", emailUtilisateur);
                startActivity(intentProduct);
                finish();

                break;
            case R.id.CommunityHome:
                Intent intentCommunity = new Intent(getBaseContext(), CommunityTabsActivit.class);
                intentCommunity.putExtra("randkiteUser_id", randkiteUser_id);
                intentCommunity.putExtra("randkiteUser_phone", telephoneUtilisateur);
                intentCommunity.putExtra("randkiteUser_picture", imgUtilisateur);
                intentCommunity.putExtra("randkiteUser_name", NomPrenom);
                intentCommunity.putExtra("randkiteUser_surname", loginUtilisateur);
                intentCommunity.putExtra("randkiteUser_email", emailUtilisateur);
                startActivity(intentCommunity);
                finish();
                break;

            case R.id.deconnexionHome:
                settings.edit().putString("init","0").commit();
                Intent lng_intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(lng_intent);
                finish();
                break;
            case R.id.settingHome:
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1)
                    startActivity(new Intent(getApplicationContext(), PreferenceActivity.class));
                else
                    startActivity(new Intent(getApplicationContext(), PreferenceActivityCompat.class));

                break;

        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        drawerLayout.closeDrawers();
    }



}
