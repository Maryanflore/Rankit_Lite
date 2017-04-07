package com.therankit.services;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView; 
import com.android.volley.toolbox.Volley;



import com.load.img.ImageLoaderProfil;
import com.reponse.Reponse;
import com.therankit.community.Publier_epeople;
import com.therankit.rankitlite.R;
import com.url.AppController;
import com.volley.Const;
import com.volley.EncodeDecode;
import com.volley.JsonUTF8Request;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;  
import android.content.Intent; 
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable; 
import android.os.Bundle;   
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View; 
import android.widget.Button;
import android.widget.EditText;  
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView; 
import android.widget.Toast;
 
@SuppressLint("NewApi")
public class detail_entreprise_service extends ActionBarActivity {
	private String TAG = detail_entreprise_service.class.getSimpleName();
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
	private ProgressDialog	pDialog; 
	private ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	private String envoye; 
    public ImageLoaderProfil imageProfilLoader;
    private FeedImageView image_publication;
    private ImageView imgback;
    private ImageView imgLogo;
    private String logo;
    public ImageLoaderProfil imageProfil;
	 @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.entreprise_description_services);
 
     //  getSupportActionBar().setDisplayHomeAsUpEnabled(true); 
       imageProfilLoader=new ImageLoaderProfil(getBaseContext());
	 //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#73C2FB")));
		//getSherlockActivity().getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
	//	getSupportActionBar().setIcon(R.drawable.ic_maps_directions_walk_blanc);
	//	getSupportActionBar().setTitle("Community");
       imageLoader = AppController.getInstance().getImageLoader();
       pDialog = new ProgressDialog(this);	    
       pDialog.setMessage(getString(R.string.st_chargement));
		//createDialog("Error","");
		pDialog.setCancelable(false);
		//showProgressDialog();
		imgback= (ImageView) findViewById(R.id.imageback); 
		imgLogo= (ImageView) findViewById(R.id.imgLogo); 
		imageProfil=new ImageLoaderProfil(this);
		imgback.setOnClickListener(new View.OnClickListener() {
		      	    @Override
		      	    public void onClick(View v) { 

		      	    	 
		      	    	finish();
		      	    }
		      	});
      	
   	Bundle extras=getIntent().getExtras();
   	if(extras != null)
	{
   		logo=extras.getString("logo"); 
		
	}
   	//createDialog("info", logo+" ");
    imageProfil.DisplayImage(Const.ipUriLogo+logo, imgLogo);
    
   //	imgLogo.setImageUrl(Const.ipUriImage+logo, imageLoader);
 
 	 

   	

    //mListView = (ListView) findViewById(R.id.listCommentaires);

   	
   
  
  
	}

 

		private void showProgressDialog() {
			//if (!pDialog.isShowing())
				pDialog.show();
		}

		private void hideProgressDialog() {
			if (pDialog.isShowing())
				pDialog.hide();
		}
		/**
		 * Making json object request
		 * */
		private void makeJsonObjReq(JSONObject json,String url) {
			JsonUTF8Request jsonObjReq = new JsonUTF8Request(Method.POST,
					url, json,
					new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							Log.d(TAG, response.toString());
							
							if(envoye.equals("post"))
							{ 
								try {
									JSONObject jsonObjSend = new JSONObject(response.toString());
									//createDialog("Resultat",jsonObjSend.getString(Reponse.retour));
						            Toast.makeText(getBaseContext(), ""+jsonObjSend.getString(Reponse.retour), Toast.LENGTH_LONG).show();
						             
						           
						         
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}  
							}
							
							/*traitement du retour lors de la verification si le login n'est pas encore utilise*/
							else  
							{     
				//	Toast.makeText(getBaseContext(), "11", Toast.LENGTH_LONG).show();
								parseJsonFeed(response);
							 		}
							hideProgressDialog();
				   			//commentaireDialog();
						}
					}, new Response.ErrorListener() {


						@Override
						public void onErrorResponse(VolleyError error) {
							VolleyLog.d(TAG, "Error: " + error.getMessage());
							
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
			
			AppController.getInstance().addToRequestQueue(jsonObjReq,
					tag_json_obj);
	 	}
		
		
		

		

		/**
		 * Parsing json reponse and passing the data to feed view list adapter
		 * */
		private void parseJsonFeed(JSONObject response) {
			try {

				JSONArray feedArray = response.getJSONArray("resultat");
				
				//imagePublication.clear();
				//titrePublication.clear();
		 				if(feedArray.length()>0){
		 					 for (int i =0; i <feedArray.length(); i++) 
		 					{
		 						JSONObject feedObj = (JSONObject) feedArray.get(i);
		 						  
		 					} 
		 					
		 				} 

			} catch (JSONException e) {
				e.printStackTrace();
			}

				
		}
	 		
		
		 


		
		
	
		 public void createDialog(String title, String text)
		 {
		 	// Création d'une popup affichant un message
	AlertDialog ad = new AlertDialog.Builder(this).setPositiveButton("Ok", null).setTitle(title).setMessage(text).create();
		 		ad.show();

		 }
		 
			public boolean onOptionsItemSelected(MenuItem item) {
				Intent intent;
				switch (item.getItemId()) {	 
			    case android.R.id.home:  
			    	finish();
			        return true;
			        /*
			    case R.id.edit_item: 			   	 
			    	commentaireDialog();
			          return true;
			          */
			    }

			    return false;
			}
			

		
			
			
			

			 
}
