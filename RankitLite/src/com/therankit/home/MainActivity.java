package com.therankit.home;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.google.android.gcm.GCMRegistrar;
import com.reponse.Reponse;
import com.sqlLite.LangueTable;
import com.sqlLite.SecteurTable;
import com.therankit.rankitlite.R;
import com.url.AppController;
import com.volley.Const;
import com.volley.JsonUTF8Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {
	private String TAG = MainActivity.class.getSimpleName();
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
	TextView txtMessage1, txtMessage2, txtMessage3;
	private ProgressDialog pDialog;
	private String registrationId="";
	Animation animFadeIn, animFadeOut ;
	private SecteurTable secteurBdd;
	private LangueTable langueBdd;
	private SharedPreferences settings;
	private String envoye;
	private List<String> liste_pays_name = new ArrayList<String>();
	private List<Integer> liste_pays_id = new ArrayList<Integer>();
	private List<Integer> liste_pays_code = new ArrayList<Integer>();
	private ArrayAdapter<String> dataAdapter;
	private Spinner spinner;
	private JSONObject jsonObjSend;
	private EditText Num_Tel;
	private String   tel="";
	//private GoogleCloudMessaging gcm;
	// Your Facebook APP ID
	private static String APP_ID = "256852383586"; // Replace with your App ID

	// Instance of Facebook Class
	//private Facebook facebook = new Facebook(APP_ID);
	//private AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs;
	Button btnFbLogin;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

		/*if (PrefUtils.getNotificationId(MainActivity.this).length() == 0) {
			if (gcm == null) {
				gcm = GoogleCloudMessaging.getInstance(MainActivity.this);
				createDialog("registrationId 0",registrationId);
			}
			try {

				registrationId = gcm.register(Const.PROJECT_ID);
				createDialog("registrationId 1",registrationId);
			}catch (IOException ex) {
				ex.printStackTrace();
				Log.e("error", ex.toString());
				registrationId="2";
			}
			if (!registrationId.equals("")) {
				createDialog("registrationId 2",registrationId);
			}
		}*/
	/*	txtMessage1 = (TextView) findViewById(R.id.textView1);
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
		txtMessage3.startAnimation(animFadeIn);*/
		jsonObjSend = new JSONObject();
		pDialog = new ProgressDialog(this);
		//pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pDialog.setMessage(""+getString(R.string.st_chargement));
		pDialog.setCancelable(false);
		// Spinner element
		spinner = (Spinner) findViewById(R.id.editText2);

		// Spinner click listener
		spinner.setOnItemSelectedListener(this);

		// Spinner Drop down elements






		// attaching data adapter to spinner
		getListContry();

		settings = getSharedPreferences(Const.PREFRENCES_NAME, Context.MODE_PRIVATE);
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
			//  createDialog("  OiutJson","device_key::"+registrationId);
		}
		//createDialog("  OiutJson","device_key::"+registrationId);

		secteurBdd = new SecteurTable(getBaseContext());
		langueBdd = new LangueTable(getBaseContext());
		Num_Tel=(EditText) findViewById(R.id.editText3);
		Button btn1 = (Button) findViewById(R.id.button);
		btn1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), SignUpActivity.class);
				intent.putExtra("pays_iso","237");
				intent.putExtra("pays_name","Cameroun");
				intent.putExtra("device_key",registrationId);
				startActivityForResult(intent, 0);
				overridePendingTransition(R.anim.slidein, R.anim.slideout);
				//finish();
			}
		});

		Button btn2 = (Button) findViewById(R.id.button2);
		btn2.setOnClickListener(new OnClickListener() {


			@Override
			public void onClick(View v) {

				tel=Num_Tel.getText().toString().trim();
				connexion();
			}
		});




	}

	private void showProgressDialog() {
		//if (!pDialog.isShowing())

		pDialog.setMessage(""+getString(R.string.st_chargement));
		pDialog.show();
	}

	private void hideProgressDialog() {
		if (pDialog.isShowing())
			pDialog.hide();
	}
	/**
	 * Making json object request
	 * */
	private void makeJsonObjReq(JSONObject json, String url, final String file) {
		showProgressDialog();
		RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		JsonUTF8Request jsonObjReq = new JsonUTF8Request(Request.Method.POST,
				url, json,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						if(envoye.equals("doLogin")){
							Log.d(TAG, response.toString());
							// Dismiss the progress dialog
							JSONObject jsonObjSend = null;
							try {
								jsonObjSend = new JSONObject(response.toString());

								if(jsonObjSend.getBoolean(Reponse.retourVerifUser)){


									settings.edit()

											.putString("randkiteUser_id",jsonObjSend.getInt("randkiteUser_id")+"")
											.putString("randkiteUser_name",jsonObjSend.getString("randkiteUser_name"))
											.putString("randkiteUser_surname",jsonObjSend.getString("randkiteUser_surname"))
											.putString("randkiteUser_picture",jsonObjSend.getString("randkiteUser_picture"))
											.putString("randkiteUser_phone",jsonObjSend.getString("randkiteUser_phone"))
											.putString("randkiteUser_email",jsonObjSend.getString("randkiteUser_email"))
											.putString("pays_id",jsonObjSend.getInt("pays_id")+"")
											.putString("pays_name",jsonObjSend.getString("pays_name"))
											.putInt("onglet",0)
											.putString("device_key",registrationId)
											.commit();


									Intent intent = new Intent(MainActivity.this, HomeGrid.class);
									intent.putExtra("randkiteUser_id",jsonObjSend.getInt("randkiteUser_id"));
									intent.putExtra("randkiteUser_name",jsonObjSend.getString("randkiteUser_name"));
									intent.putExtra("randkiteUser_surname",jsonObjSend.getString("randkiteUser_surname"));
									intent.putExtra("randkiteUser_picture",jsonObjSend.getString("randkiteUser_picture"));
									intent.putExtra("randkiteUser_phone",jsonObjSend.getString("randkiteUser_phone"));
									intent.putExtra("randkiteUser_email",jsonObjSend.getString("randkiteUser_email"));
									intent.putExtra("pays_id",jsonObjSend.getInt("pays_id"));
									intent.putExtra("pays_name",jsonObjSend.getString("pays_name"));
									intent.putExtra("device_key",registrationId);
									intent.putExtra("onglet",0);
									startActivityForResult(intent, 0);
									settings.edit()
											.putString("sen_rotation","1")
											.commit();
									overridePendingTransition(R.anim.slideout, R.anim.slidein);
									settings.edit().putString("init","1").commit();
									finish();
									settings.edit() .putInt("randkiteUser_id",jsonObjSend.getInt("randkiteUser_id")) .commit();

									if(settings.getString("lien_condiction", "0").equals("0"))
									{
										settings.edit()
												.putString("lien_condiction","1")
												.commit();
									}

								}
								else
								{
									createDialog("  Infos","Vos parametres de connexion sont incorrects.");
									//  Toast.makeText(getBaseContext(), "Vos param�tre de connexion sont incorrects.", Toast.LENGTH_LONG).show();
								}

								pDialog.dismiss();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

						else if(envoye.equals("getListContry"))
						{
							JSONObject jsonObjSend = null;
							try {
								jsonObjSend = new JSONObject(response.toString());

								if(jsonObjSend.getBoolean(Reponse.retourVerifUser))
								{
									JSONArray feedArray = response.getJSONArray("listePays");
									//  createDialog("Infos", response.toString());
									if(feedArray.length()!=0) {


										for (int i = 0; i < feedArray.length(); i++) {

											JSONObject feedObj = (JSONObject) feedArray.get(i);
											liste_pays_name.add(feedObj.getString("pays_name"));
											liste_pays_id.add(feedObj.getInt("pays_id"));
											liste_pays_code.add(feedObj.getInt("pays_phonecode"));

										}
										// Creating adapter for spinner
										dataAdapter = new ArrayAdapter<String>(getBaseContext(), R.layout.spinner_item, liste_pays_name);

										// Drop down layout style - list view with radio button
										dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
										spinner.setAdapter(dataAdapter);
									}
									//codePostal.setText(jsonObjSend.getString("pays_phonecode"));
									//pays_id=jsonObjSend.getInt("pays_id");
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							pDialog.dismiss();
						}
						hideProgressDialog();
					}
				}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "Error: " + error.getMessage());
				Toast.makeText(getBaseContext(),getString(R.string.st_connection_failled), Toast.LENGTH_LONG).show();
				hideProgressDialog();
			}
		}) {

			/**
			 * Passing some request headers
			 * */
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json;charset=UTF-8");
				return headers;
			}


		};
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq,
				tag_json_obj);
	}
	public boolean onCreateOptionsMenu (Menu menu) {
		//menu.add(R.string.About).setIcon(android.R.drawable.ic_dialog_info);


		SubMenu m = menu.addSubMenu(0, 0, 0, this.getResources().getString(R.string.lng));
		m.add(0, 1001, 0, "English");
		m.add(0, 1002, 0, "Français");
		@SuppressWarnings("unused")
		SubMenu l = menu.addSubMenu(0, 1003, 0, "Application");
		return true;
	}
	// return super.onCreateOptionsMenu(menu);
	//menu.add("Language").setIcon(android.R.drawable.ic_menu_manage);

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

		Builder builder = new Builder(this);
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


	private void createDialog(String title, String text)
	{
		// Cr�ation d'une popup affichant un message
		AlertDialog ad = new Builder(this)
				.setPositiveButton("Ok", null).setTitle(title).setMessage(text)
				.create();
		ad.show();

	}


	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// On selecting a spinner item
		String item = parent.getItemAtPosition(position).toString();

		// Showing selected spinner item
		//Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
		Num_Tel.setText("+"+liste_pays_code.get(position).toString());
	}
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}


	private void getListContry()
	{
		envoye="getListContry";
		showProgressDialog();

		try
		{
			jsonObjSend.put("pays_iso","");

			makeJsonObjReq(jsonObjSend,Const.urlContry+"getListContry","");

		} catch (JSONException e) {
			e.printStackTrace();
		}


	}

	public void connexion()
	{

		envoye="doLogin";
		try {
			jsonObjSend = new JSONObject();
			jsonObjSend.put(Const.TAG_LOGIN,tel.toString().trim());
			jsonObjSend.put(Const.TAG_PWD,"");

			//makeJsonObjReq(jsonObjSend,"http://www.e-yamo.com/mobile_gestion_compte/connexion/dologin");
			makeJsonObjReq(jsonObjSend,Const.URL_JSON_CONNEXION,"");

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}