package com.therankit.home;
 

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest; 
import com.android.volley.toolbox.Volley;


import com.reponse.Reponse;
import com.therankit.rankitlite.R;
import com.url.AppController;
import com.volley.Const;
import com.volley.JsonUTF8Request;

import android.annotation.SuppressLint;
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
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View; 
import android.view.View.OnClickListener;
import android.widget.Button; 
import android.widget.CheckBox;
import android.widget.EditText; 
import android.widget.TextView;
import android.widget.Toast; 
 
@SuppressLint("NewApi")
public class SignInActivity extends ActionBarActivity {
	private String TAG = SignInActivity.class.getSimpleName();
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
 
	private EditText UserPass;
	private EditText UserName;
    private TextView lien_condiction;
	private String InfoLogin;
	private String Infopwd;
	private Button connectionButon;
	private ProgressDialog pDialog;
	private JSONObject jsonObjSend;
	private Button inscriptionButon;
	private CheckBox remember;
	private int incrConnexion=1;
	 private boolean SaveSession;
	 private  String registrationId="";
	 private SharedPreferences settings;
 

		
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		//Log.d(LOG_TAG, "D�marrage PushActivity");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_login);
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#73C2FB")));
		//getSherlockActivity().getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		//getSupportActionBar().setIcon(R.drawable.ic_launcher);
		//getSupportActionBar().setTitle(R.string.Welcome1);

		//Vérification des pré-requis pour l'utilisation de GCM
/*		GCMRegistrar.checkDevice(this);		
		GCMRegistrar.checkManifest(this);
 
		//Récupération du code d'enregistrement
	    registrationId = GCMRegistrar.getRegistrationId(this);
		//Si aucun code d'enregistrement
		if (registrationId.equals(""))
		{
			// Enregistrement du service
			GCMRegistrar.register(this, Const.PROJECT_ID);
			//Récupération du code d'enregistrement
		    registrationId = GCMRegistrar.getRegistrationId(this);
		    //createDialog("  InJson","device_key::"+registrationId);
		}
*/
	    //createDialog("  OiutJson","device_key::"+registrationId);
	 
		
        settings = getSharedPreferences(Const.PREFRENCES_NAME, Context.MODE_PRIVATE);

		 jsonObjSend = new JSONObject();

		 pDialog = new ProgressDialog(this);
		 //pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		 pDialog.setMessage(""+getString(R.string.st_chargement));
		 pDialog.setCancelable(false);
			
		UserPass = (EditText) findViewById(R.id.password);
        UserName = (EditText) findViewById(R.id.login);

		//settings.edit().putString("init","1").commit();	 
        
        
        remember   = (CheckBox) findViewById(R.id.remember);
        remember.setOnClickListener(new OnClickListener() {              	 
      	 

		@Override
      	  public void onClick(View v) {
                      //is chkIos checked?
      		if (((CheckBox) v).isChecked()) {
    			settings.edit().putString("init","1").commit();	
    			SaveSession=true;
      		}
      		else
      		{
      			SaveSession=false;
    			settings.edit().putString("init","0").commit();	
      		}
       
      	  }
      	});
        
    

		
		 connectionButon = (Button) findViewById(R.id.connectionButon);
		 lien_condiction = (TextView) findViewById(R.id.lien_condiction);
	        lien_condiction.setMovementMethod(LinkMovementMethod.getInstance());
	        if(!settings.getString("lien_condiction", "0").equals("0"))
			{
	        	lien_condiction.setVisibility(View.GONE);
	        	 connectionButon.setText(getApplicationContext().getString(R.string.Titlebar1));
			}
		 
		 connectionButon.setOnClickListener(new View.OnClickListener() {


		public void onClick(View view) {
			
		InfoLogin=UserName.getText().toString().trim();
      	Infopwd=UserPass.getText().toString().trim();
      
      	/*
      	
      		*/
       
			   	if(InfoLogin.equals("")||Infopwd.equals(""))
			   	{  
			   		Toast.makeText(getBaseContext(), ""+getString(R.string.st_toast_error), Toast.LENGTH_LONG).show();
			   	}
			   	else
			   	{  
			   		pDialog.show();
			   	 incrConnexion=1;
			   
					   	if(tesConnection())
				      	{
					   	 publier();
				      	} 
						else
				      	{
				      		//Toast.makeText(getBaseContext(), ""+getString(R.string.st_toast_error_connection), Toast.LENGTH_LONG).show();	
							 pDialog.dismiss();
							 createDialog("  ERROR",""+getString(R.string.st_toast_error_connection)); 
	
				      	}
			   }     		
       

			}
    
});

		 		 

					
	}

 public void publier()
 {
		try {  	 
			jsonObjSend.put(Const.TAG_LOGIN,InfoLogin.toString().trim());
			jsonObjSend.put(Const.TAG_PWD,Infopwd.toString().trim()); 
			
			//makeJsonObjReq(jsonObjSend,"http://www.e-yamo.com/mobile_gestion_compte/connexion/dologin");
			makeJsonObjReq(jsonObjSend,Const.URL_JSON_CONNEXION);
	        			
		} catch (JSONException e) {
			e.printStackTrace();
		}
 }

 
	/**
	 * Making json object request
	 * */
	private void makeJsonObjReq(JSONObject json,String url) {
		//createDialog("Error",json.toString());
	
		RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		JsonUTF8Request jsonObjReq = new JsonUTF8Request(Method.POST,
				url, json,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, response.toString());
						 
							// Dismiss the progress dialog
						 	 JSONObject jsonObjSend = null;
							try {
								jsonObjSend = new JSONObject(response.toString());

								   //createDialog("  Infos",""+response.toString()); 
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
							  			 				    			  

									Intent intent = new Intent(SignInActivity.this, HomeGrid.class);
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
									UserPass.setText("");
									UserName.setText(""); 	 
									 if(settings.getString("lien_condiction", "0").equals("0"))
										{
									     settings.edit() 
										.putString("lien_condiction","1")
									    .commit(); 
										}
												 
							 }
							 else
							 {
						   createDialog("  Infos","Vos paramétres de connexion sont incorrects.");
						   //  Toast.makeText(getBaseContext(), "Vos param�tre de connexion sont incorrects.", Toast.LENGTH_LONG).show();
							 }

							 pDialog.dismiss();
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
					 
						 
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						//createDialog("Error",error.getMessage()); 
					
						  
						 
							if(incrConnexion<=Const.count)
							{
								publier();	
								//Toast.makeText(getBaseContext(),getString(R.string.st_connection_failled), Toast.LENGTH_LONG).show();
							}
							else
							 {		 
							 Toast.makeText(getBaseContext(),getString(R.string.st_connection_failled), Toast.LENGTH_LONG).show();
							 pDialog.dismiss();		
								 
							}
							incrConnexion++;
							 
						 
						 
						 
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
		/*
		int socketTimeout = Const.timer_connection_request;//30 seconds - change to what you want
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		jsonObjReq.setRetryPolicy(policy);
		mRequestQueue.add(jsonObjReq);
		*/
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
 	}
	
	
	public boolean tesConnection()
	{
		boolean test=true; 
		 ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
         if ( cm.getActiveNetworkInfo() == null ) {
        	 test=false;
         } 
		return test;
	}
	
	
	 
	 public void createDialog(String title, String text)
	 {
	 	// Cr�ation d'une popup affichant un message
	 	AlertDialog ad = new AlertDialog.Builder(this)
	 			.setPositiveButton("Ok", null).setTitle(title).setMessage(text)
	 			.create();
	 	ad.show();

	 }
		
  	 
		public void onBackPressed() {
	        // Write your code here       	    	 
			AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
			builder.setMessage(R.string.info_quit);
			builder.setCancelable(false);
			builder.setNegativeButton(R.string.info_oui, new DialogInterface.OnClickListener() {       
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(SignInActivity.this, MainActivity.class);
					startActivityForResult(intent, 0);
					overridePendingTransition(R.anim.slideout, R.anim.slidein);
						finish();  
						//System.exit(0);
					}});        
			builder.setPositiveButton(R.string.info_non, new DialogInterface.OnClickListener() {
				public void onClick(final DialogInterface dialog, final int id) {     
					dialog.cancel();      
				}});
			builder.show(); 
	    }
		
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
	    
	       public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case 1001: setLocal(Locale.ENGLISH);break;
			case 1002: setLocal(Locale.FRENCH);break;
			case 1003: AlertDialog();break;
			case android.R.id.home:  finish();
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
}