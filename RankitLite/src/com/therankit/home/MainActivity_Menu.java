package com.therankit.home;
 
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle; 
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView; 
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


import com.therankit.best.BestTabsActivity;
import com.therankit.product.ProductTabsActivity;
import com.therankit.rankitlite.R;

import com.therankit.services.IconTextTabsActivity;
import com.volley.Const;


@SuppressLint("NewApi")
public class MainActivity_Menu extends AppCompatActivity implements OnItemClickListener{

	
	private DrawerLayout drawlayout=null;
	private ListView listview=null;
	private ActionBarDrawerToggle actbardrawertoggle=null;
	
	private String[] titre=null;
    private String[] descripttitre=null; 
    private int[] icon=null; 
	private int randkiteUser_id;
	private String telephoneUtilisateur;
	private String imgUtilisateur;   
	private String NomPrenom;
	private String categorie="tous";
	private int onglet;
	private String loginUtilisateur;
	private String uriImage;
	private String emailUtilisateur;
	private String device_key;
	private  SharedPreferences settings;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

        
        
        	settings = getSharedPreferences(Const.PREFRENCES_NAME, Context.MODE_PRIVATE);
			//settings.edit().putString("pathNameImage_dynamique", "0").commit();
			
			 
	    	Bundle extras=getIntent().getExtras();
			
	    	if(settings.getString("init", "0").equals("0"))
			{
	    		device_key=new String(extras.getString("device_key"));
		    	randkiteUser_id=new Integer(extras.getInt("randkiteUser_id"));
		    	telephoneUtilisateur=new String(extras.getString("randkiteUser_phone"));
		    	imgUtilisateur=new String(extras.getString("randkiteUser_picture")); 
		    	NomPrenom=new String(extras.getString("randkiteUser_name"));
		    	loginUtilisateur=new String(extras.getString("randkiteUser_surname"));
		    	emailUtilisateur=new String(extras.getString("randkiteUser_email"));
		    	//categorie=new String(extras.getString("categorie"));
		    	onglet=new Integer(extras.getInt("onglet"));
			}
	    	else
	    	{
	    		
	    	
	    		
	    		device_key=settings.getString("device_key","");
		    	randkiteUser_id=settings.getInt("randkiteUser_id",0);
		    	telephoneUtilisateur=settings.getString("randkiteUser_phone","");
		    	imgUtilisateur=settings.getString("randkiteUser_picture",""); 
		    	NomPrenom=settings.getString("randkiteUser_name","");
		    	loginUtilisateur=settings.getString("randkiteUser_surname","");
		    	emailUtilisateur=settings.getString("randkiteUser_email","");
		    	onglet=settings.getInt("onglet",0);
	    	}

    	//createDialog("-----", ""+Url_imgProfil+imgUtilisateur);
		titre = new String[] {"Home", getApplicationContext().getString(R.string.best),getApplicationContext().getString(R.string.servicesName),getApplicationContext().getString(R.string.productName),getApplicationContext().getString(R.string.setting), getApplicationContext().getString(R.string.deconnexion)};
			if(loginUtilisateur.equals(""))
			{
				uriImage=imgUtilisateur;
			}
			else
			{
				uriImage=Const.ipUriImage+imgUtilisateur;
			}
		descripttitre = new String[] {uriImage+"###"+NomPrenom+"###"+telephoneUtilisateur+"###"+emailUtilisateur, getApplicationContext().getString(R.string.bestDetail),getApplicationContext().getString(R.string.servicesDetail), 
				getApplicationContext().getString(R.string.productDetail),"", ""};
		icon = new int[] {0, R.drawable.ic_best, R.drawable.services,R.drawable.product, R.drawable.parametres,R.drawable.deconnection};
         
         drawlayout = (DrawerLayout)findViewById(R.id.drawer_layout);
         listview = (ListView) findViewById(R.id.listview_drawer);

        // getSupportActionBar().setHomeButtonEnabled(true);
         //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         //getSupportActionBar().setDisplayShowHomeEnabled(true);
         //getSupportActionBar().setDisplayShowTitleEnabled(true);
         
         drawlayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
         drawlayout.setBackgroundColor(Color.WHITE);
         
         MenuListAdapter menuadapter=new MenuListAdapter(getApplicationContext(),titre, descripttitre, icon); 
         listview.setAdapter(menuadapter);
          
         actbardrawertoggle= new ActionBarDrawerToggle(this, drawlayout, R.drawable.ic_action_list_blanc, R.string.drawer_open, R.string.drawer_close)
            {
        	 public void onDrawerClosed(View view) {
 				super.onDrawerClosed(view);
 			}

 			public void onDrawerOpened(View drawerView) {
 				super.onDrawerOpened(drawerView);
 			
 			}
        	 
         };
         drawlayout.setDrawerListener(actbardrawertoggle);
         
         listview.setOnItemClickListener(this);
        
         if (savedInstanceState == null) {
             selectItem(onglet);
         }
}
	
 
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		actbardrawertoggle.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		actbardrawertoggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		selectItem(position);
		
	}
 
	 private void selectItem(int position) {
		 
	        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	       
	        // Locate Position
	        switch (position) {
	        case 0:
				Intent homeGrid = new Intent(MainActivity_Menu.this, HomeGrid.class);



				homeGrid.putExtra("randkiteUser_id",randkiteUser_id);
				homeGrid.putExtra("device_key",device_key);
				homeGrid.putExtra("randkiteUser_phone",telephoneUtilisateur);
				homeGrid.putExtra("randkiteUser_picture",imgUtilisateur);
				homeGrid.putExtra("randkiteUser_name",NomPrenom);
				homeGrid.putExtra("randkiteUser_surname",loginUtilisateur);
				homeGrid.putExtra("randkiteUser_email",emailUtilisateur);
				startActivity(homeGrid);

	           
	           // homeGrid.setArguments(b0);
	            break;
	        case 1: 
	        	/*HomeGrid homeGrid=new HomeGrid();
	            ft.replace(R.id.content_frame, homeGrid);
	            Bundle b0 = new Bundle();
		    	
		  
	            b0.putInt("randkiteUser_id",randkiteUser_id); 
	            b0.putString("device_key",device_key); 
	            b0.putString("randkiteUser_phone",telephoneUtilisateur); 
	            b0.putString("randkiteUser_picture",imgUtilisateur); 
	            b0.putString("randkiteUser_name",NomPrenom); 
	            b0.putString("randkiteUser_surname",loginUtilisateur);
	            b0.putString("randkiteUser_email",emailUtilisateur);
	           
	            homeGrid.setArguments(b0);*/
	        	 Intent intentbest = new Intent(MainActivity_Menu.this, BestTabsActivity.class);
	        	 intentbest.putExtra("randkiteUser_id",randkiteUser_id);
				 startActivity(intentbest); 
	            
	            break;
	            
	        case 2:
	        	
	        	 Intent intentService = new Intent(MainActivity_Menu.this, IconTextTabsActivity.class);
		            intentService.putExtra("randkiteUser_id",randkiteUser_id);
				    startActivity(intentService); 
	            break;
	            
	        
	        case 3:
	        	
	        	Intent intent = new Intent(MainActivity_Menu.this, ProductTabsActivity.class);
	        	intent.putExtra("randkiteUser_id",randkiteUser_id);
			    startActivity(intent); 
	           
	            break;
	        case 4:
	        	 Toast.makeText(getBaseContext(),"", Toast.LENGTH_LONG).show();
			   
	            
	            break;
	        
	        case 5:
	        	 
	        	 
			    
	        		settings.edit().putString("init","0").commit();	
		        	Intent lng_intent = new Intent(getBaseContext(), Welcome.class);
					startActivity(lng_intent);
	            break;
	  
	        case 6:
	        	Toast.makeText(getBaseContext(),"", Toast.LENGTH_LONG).show();
	            
	            break;
	  
	       
	        }
	        ft.commit();
	        listview.setItemChecked(position, true);
	        setTitle(titre[position]);
	        //setIcon(icon[position]);
	        drawlayout.closeDrawer(listview);
	    }
	 
	 public boolean onOptionsItemSelected(MenuItem item) {
	
		 if(item.getItemId()==android.R.id.home)
		 {
			 if(drawlayout.isDrawerOpen(listview))
			 {
				 drawlayout.closeDrawer(listview);
			 }
			 else {
				drawlayout.openDrawer(listview);
			}
		 }
		return super.onOptionsItemSelected((android.view.MenuItem) item);
	}	 

	 public void createDialog(String title, String text)
	 {
		 	// Cr�ation d'une popup affichant un message
		 AlertDialog ad = new AlertDialog.Builder(this).setPositiveButton("Ok", null).setTitle(title).setMessage(text).create();
		 	 		ad.show();

	 }
	 



public void onBackPressed() {

	 /*Intent intent ;
	 settings.edit() 
		.putString("sen_rotation","-1")
	    .commit(); 
		 intent = new Intent(MainActivity_Menu.this, HomeGrid.class); 
	
		    intent.putExtra("randkiteUser_id",randkiteUser_id); 
			intent.putExtra("randkiteUser_name",NomPrenom); 
			intent.putExtra("randkiteUser_surname",loginUtilisateur);
			intent.putExtra("randkiteUser_picture",imgUtilisateur); 
			intent.putExtra("randkiteUser_phone",telephoneUtilisateur); 
			intent.putExtra("randkiteUser_email",emailUtilisateur);
			intent.putExtra("device_key",device_key);
	 startActivity(intent);*/
	 finish();	
 
}
	

}
