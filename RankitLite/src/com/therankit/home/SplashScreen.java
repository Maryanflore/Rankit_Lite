package com.therankit.home;
 

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import android.support.v7.app.ActionBarActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.sqlLite.LangueTable;
import com.sqlLite.ProducTable;
import com.sqlLite.SecteurTable;
import com.sqlLite.CritereNotationTable;
import com.sqlLite.SousCatTable;
import com.therankit.rankitlite.R;
import com.url.AppController;
import com.volley.Const;
import com.volley.JsonUTF8Request;



@SuppressWarnings("deprecation")
public class SplashScreen extends ActionBarActivity {
	private String TAG = SplashScreen.class.getSimpleName();
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
	// Splash screen timer
	private static int SPLASH_TIME_OUT = 8000;
	
	private String registrationId=""; 
	/***
	 * debut attribut de connexion
	 */
	private String idUtilisateur="0";
	private String imgUtilisateur="profile.png";
	private String nomUtilisateur=" ";
	private String prenomUtilisateur=" ";
	private String telephoneUtilisateur=" ";
	private String loginUtilisateur=" ";
	private String emailUtilisateur=" ";
	private String codepostalUtilisateur=" ";
	private SharedPreferences settings;
	private SecteurTable secteurBdd;
	private CritereNotationTable serviceBdd;
	private ProducTable   productBdd;
	private SousCatTable sousCatTable;
	private LangueTable langueBdd;
	
	private ProgressBar pb;
	private Handler mHandler = new Handler();
	private int pbState = 0;
	private String type_requete="";
	
	/***
	 * fin attribut de connexion
	 */

		@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_fixe);

		pb = (ProgressBar) findViewById(R.id.progressbar1);
			
				
		//getSupportActionBar().hide();

       
        
        settings = getSharedPreferences(Const.PREFRENCES_NAME, Context.MODE_PRIVATE);
        final String VerifRemember=settings.getString("init", "0");
        secteurBdd = new SecteurTable(getBaseContext()); 
        serviceBdd= new CritereNotationTable(getBaseContext());
        productBdd= new ProducTable(getBaseContext());
        sousCatTable= new SousCatTable(getBaseContext());
		 langueBdd = new LangueTable(getBaseContext()); 
		//Récupération du code d'enregistrement
	 /*   registrationId = GCMRegistrar.getRegistrationId(this);
		//Si aucun code d'enregistrement
		if (registrationId.equals(""))
		{
			// Enregistrement du service
			GCMRegistrar.register(this,  Const.PROJECT_ID);
			//Récupération du code d'enregistrement
		    registrationId = GCMRegistrar.getRegistrationId(this);
		    
		}*/
		
		
		
		if(!settings.getString("versionAppli", "0").equals("1.2.1.3"))
		{
			
			serviceBdd.open();	
			serviceBdd.CreateTableService();
			serviceBdd.close();
			
			productBdd.open();	
			productBdd.CreateTableCritere();
			productBdd.close();
			 settings.edit() 
				.putString("versionAppli","1.2.1.3")
				.putInt("onglet",0)
			    .commit(); 
		}
		if(settings.getString("connexionStatut", "0").equals("0"))
		  {
		   settings.getString("langue", "en");
		   settings.getString("sourceNoteS", "0");
		   settings.getString("sourceNoteP", "0");
		   settings.getString("profilSendNote", "profile.png");
		   settings.getString("messageService", "Message Par Defaut");
			
			  try {
				  type_requete="init";
			  JSONObject jsonObjSend = new JSONObject();
			
			jsonObjSend.put("idGcm",registrationId);
			
				
				makeJsonObjReq(jsonObjSend,Const.urlIncriptionConnexion+"initMobile");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		      			
		  }
		  else
		{
int id_last=0;
			if(settings.getString("connexionStatut", "0").equals("1"))
			{
				secteurBdd.open();
				id_last=secteurBdd.getLastIdSecteur(0);
				secteurBdd.close();
				settings.edit()
						.putString("connexionStatut","2")

						.commit();
			}
			else
			{
				secteurBdd.open();
				id_last=secteurBdd.getLastIdSecteur(1);
				secteurBdd.close();
			}
			try {
				type_requete="last_id";
				JSONObject jsonObjSend = new JSONObject();

				jsonObjSend.put("id_last_secteur",id_last);


				makeJsonObjReq(jsonObjSend,Const.urlIncriptionConnexion+"initMobile2");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		new Handler().postDelayed(new Runnable() {
			@SuppressLint("NewApi")
			@Override
			public void run() {   
				 if(VerifRemember.equals("0"))
					{
				     Intent intent = new Intent(SplashScreen.this, Welcome.class);
				   
				     startActivity(intent);
				     finish();
				
				}
				 
				 else
					{ 
					 
					 settings.edit() 
						.putString("sen_rotation","1")
					    .commit(); 
						Intent i = new Intent(SplashScreen.this, HomeGrid.class);
						
						startActivity(i);
						finish();
						  
			 				
					}
			 }
		}, SPLASH_TIME_OUT);
		
		
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

								   
							 if(jsonObjSend.getBoolean("statut")){
								 SharedPreferences settings = getSharedPreferences(Const.PREFRENCES_NAME, Context.MODE_PRIVATE); 	
								//createDialog("  Infos","registrationId::"+registrationId);
								 /*
								  * 
								  * debut du bloc permettant d'inserer les informations dans la bd en local
								  */
								 if(settings.getString("connexionStatut", "0").equals("0"))
									{
									 secteurBdd.open();	
									 secteurBdd.insertListeSecteur(jsonObjSend.getJSONArray("listeSecteur"));
									 secteurBdd.close();

										sousCatTable.open();
										sousCatTable.insertListeSousCat(jsonObjSend.getJSONArray("listeSousCategorie"));
										sousCatTable.close();
								     int id_langue=0;
									 langueBdd.open();	
									 langueBdd.insertLangue(jsonObjSend.getJSONArray("listeLangue"));
									 id_langue= langueBdd.getReturnIdlangue(settings.getString("langue", "en"));
									 langueBdd.close();
								 settings.edit() 
									.putString("connexionStatut","1")
									.putInt("id_langue",id_langue)
								    .commit(); 
								
								 //finish();
									
									}
									else
								 {
									 secteurBdd.open();
									 secteurBdd.insertListeSecteur(jsonObjSend.getJSONArray("listeSecteur"));


									 settings.edit()
											 .putInt("id_last_id",secteurBdd.getLastIdSecteur(1))

											 .commit();

									 secteurBdd.close();
								 }
								 
								 /*
								  * 
								  * fin du bloc permettant d'inserer les informations dans la bd en local
								  */
															 
							 }
									
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
					
						  
						 
						
							 {		 
							 Toast.makeText(getBaseContext(),getString(R.string.st_connection_failled), Toast.LENGTH_LONG).show();
							
							
							}
							
							 
						 
						 
						 
					}  
				 
				}) {

			/**
			 * Passing some request headers
			 * */
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
   
	public void progressB() {
		
		new Thread (new Runnable () {
		public void run() {
			while (pbState < 100 ) {
				pbState += 12.5;
			// update
				mHandler.post(new Runnable () {
					public void run () {
						pb.setProgress(pbState);
					}
			});
		}
		}
		}).start(); 
	}
	
}
