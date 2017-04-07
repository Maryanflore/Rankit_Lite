package com.therankit.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


//import com.countrypicker.CountryPicker;
//import com.countrypicker.CountryPickerListener;
import com.countrypicker.CountryPicker;
import com.countrypicker.CountryPickerListener;

import com.google.android.gcm.GCMRegistrar;
import com.therankit.rankitlite.R;
import com.volley.Const;
@SuppressLint("NewApi")
public class contry extends ActionBarActivity  {
	private String registrationId=""; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contry);

		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		 final SharedPreferences settings = getSharedPreferences(Const.PREFRENCES_NAME, Context.MODE_PRIVATE);
	       GCMRegistrar.checkDevice(this);
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
			}
			//createDialog("  OiutJson","device_key::"+registrationId);
		CountryPicker picker = new CountryPicker();
		picker.setListener(new CountryPickerListener() {

			@Override
			public void onSelectCountry(String name, String code) {
				/*Toast.makeText(
						contry.this,
						"Country Name: " + name + " - Code: " + code
								+ " - Currency: "
								+ CountryPicker.getCurrencyCode(code),
						Toast.LENGTH_SHORT).show();
				*/
				Intent intent = new Intent(contry.this, SignUpActivity.class);
				 intent.putExtra("pays_iso",code); 
				 intent.putExtra("pays_name",name); 
				 intent.putExtra("device_key",registrationId); 
				startActivityForResult(intent, 0);
				overridePendingTransition(R.anim.slidein, R.anim.slideout);
				finish();
			}
		});

		transaction.replace(R.id.home, picker);

		transaction.commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.show_dialog);
		item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {


			@Override
			public boolean onMenuItemClick(MenuItem item) {
				CountryPicker picker = CountryPicker.newInstance("Select Country");
				picker.setListener(new CountryPickerListener() {

					@Override
					public void onSelectCountry(String name, String code) {
					/*	Toast.makeText(
								contry.this,
								"Country Name: " + name + " - Code: " + code
										+ " - Currency: "
										+ CountryPicker.getCurrencyCode(code),
								Toast.LENGTH_SHORT).show();
						*/
						Intent intent = new Intent(contry.this, SignUpActivity.class);
						 intent.putExtra("pays_iso",code); 
						 intent.putExtra("pays_name",name); 
						 intent.putExtra("device_key",registrationId); 
						startActivityForResult(intent, 0);
						overridePendingTransition(R.anim.slidein, R.anim.slideout);
						finish();
					}
				});
				
				picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
				return false;
			}
		});
		return true;
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
