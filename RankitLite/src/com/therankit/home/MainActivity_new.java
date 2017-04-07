package com.therankit.home;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.sqlLite.LangueTable;
import com.sqlLite.SecteurTable;
import com.therankit.rankitlite.R;
import com.url.AppController;
import com.volley.Const;
import com.volley.JsonUTF8Request;

public class MainActivity_new extends Activity implements AnimationListener {
	private String TAG = MainActivity.class.getSimpleName();
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
	TextView txtMessage1, txtMessage2, txtMessage3;
	private ProgressDialog pDialog;
	private String registrationId=""; 
	Animation animFadeIn, animFadeOut ;	
	private SecteurTable secteurBdd;
	private LangueTable langueBdd;
	private SharedPreferences settings;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_new);
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);    
		//getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#73C2FB")));
		//getSherlockActivity().getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		//getSupportActionBar().setIcon(R.drawable.ic_launcher);
		//getSupportActionBar().setTitle(R.string.app_name);
		txtMessage1 = (TextView) findViewById(R.id.textView1);
		txtMessage2 = (TextView) findViewById(R.id.textView2);
		txtMessage3 = (TextView) findViewById(R.id.textView3);
		 pDialog = new ProgressDialog(this);
		 //pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		 pDialog.setMessage(""+getString(R.string.st_chargement));
		 pDialog.setCancelable(false);
		// loading animation
		animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
		animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
		animFadeIn.setAnimationListener(this);
		animFadeOut.setAnimationListener(this);
		
		txtMessage1.startAnimation(animFadeIn);
		txtMessage2.startAnimation(animFadeIn);
		txtMessage3.startAnimation(animFadeIn);
		settings = getSharedPreferences(Const.PREFRENCES_NAME, Context.MODE_PRIVATE);
		/*    GCMRegistrar.checkDevice(this);		
		GCMRegistrar.checkManifest(this);
 
		//Récupération du code d'enregistrement
	    registrationId = GCMRegistrar.getRegistrationId(this);
		//Si aucun code d'enregistrement
		if (registrationId.equals(""))
		{
			// Enregistrement du service
			GCMRegistrar.register(this,  Const.PROJECT_ID);
			//Récupération du code d'enregistrement
		    registrationId = GCMRegistrar.getRegistrationId(this);
		    settings.edit().putString("device_key",registrationId).commit();
		    createDialog("  OiutJson","device_key::"+registrationId);
		}
		*/
		 
		 secteurBdd = new SecteurTable(getBaseContext());  
		 langueBdd = new LangueTable(getBaseContext());  
		
		Button btn1 = (Button) findViewById(R.id.button1);
		btn1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent(MainActivity_new.this, MainActivity_Menu.class);
				
				startActivity(i);
				finish();
				  
				}
		});
		
		
		
	
	}
		
	 @Override
	    public boolean onCreateOptionsMenu (Menu menu) {
	    	//menu.add(R.string.About).setIcon(android.R.drawable.ic_dialog_info);
	    	    SubMenu m = menu.addSubMenu(0, 0, 0, this.getResources().getString(R.string.lng));
				m.add(0, 1001, 0, "English");
				m.add(0, 1002, 0, "Fran�ais");
				@SuppressWarnings("unused")
				SubMenu l = menu.addSubMenu(0, 1003, 0, "Application");
				return true;
					}
	    	// return super.onCreateOptionsMenu(menu);
	    	//menu.add("Language").setIcon(android.R.drawable.ic_menu_manage);
	    
	       @Override
			public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case 1001: setLocal(Locale.ENGLISH);break;
			case 1002: setLocal(Locale.FRENCH);break;
			case 1003: AlertDialog();break;
			}
				return true;
			}
	       
	       private void setLocal(Locale locale) 
			{
			Resources res = getResources();
			Configuration conf = res.getConfiguration();
			conf.locale = locale;
			res.updateConfiguration(conf, res.getDisplayMetrics());
			
			try {
				Context context = this.createPackageContext(getPackageName(), Context.CONTEXT_INCLUDE_CODE);
				Intent lng_intent = new Intent(context, MainActivity.class);
				startActivity(lng_intent);
				finish();			
			} catch (Exception e) {e.printStackTrace();}
			};
	    	
			public void AlertDialog(){
	    	   
	    		AlertDialog.Builder builder = new Builder(this);
	    		builder.setMessage(R.string.dialog_mes)
	    		        .setTitle(R.string.About)
	    		        .setInverseBackgroundForced(true)
	    		        .setIcon(R.drawable.ic_launcher)
	    		        .setPositiveButton("OK",
	    		        		new DialogInterface.OnClickListener()
	    		                    {
	    		                       public void onClick(DialogInterface dialog,
	    		                              int id)
	    		                            {
	    		                                //OK
	    		                    	    }
	    		                        })
	    		        .setNegativeButton(R.string.Terms,
	                            new DialogInterface.OnClickListener()
	    	                        {
	    	                            public void onClick(DialogInterface dialog,
	    	                            	   int id)
	    		                            {
	    		                               //OK
	    		                            }
	    		                        });
	                  
	    		        AlertDialog dialog = builder.create();
	    		        dialog.show();
	    		        TextView textView1 = (TextView) dialog.findViewById(android.R.id.message);
	    		        textView1.setTextSize(13);
	    	}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}	
			
			 public void createDialog(String title, String text)
			 {
			 	// Cr�ation d'une popup affichant un message
			 	AlertDialog ad = new AlertDialog.Builder(this)
			 			.setPositiveButton("Ok", null).setTitle(title).setMessage(text)
			 			.create();
			 	ad.show();

			 }
			 
			
				
}