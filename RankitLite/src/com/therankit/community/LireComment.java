package com.therankit.community;
 
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
import com.therankit.rankitlite.R;
import com.url.AppController;
import com.volley.Const;
import com.volley.EncodeDecode;
import com.volley.JsonUTF8Request;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;  
import android.content.Intent; 
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable; 
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
 
public class LireComment extends AppCompatActivity {
	private String TAG = LireComment.class.getSimpleName();
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
 
	public ProgressDialog	pDialog; 
	private String titre;  
	private String datePublication;   

	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	private TextView titreText; 
	private String NomPublieur;
	private String ImgPublieur;
	private TextView dateText;
	private TextView nameProfil;
	private ImageView imageProfil;  

	private String idUtilisateur;
	private int    idAnonce;
	private int incrConnexion=1;

	 private String envoye; 
	 private  ArrayList<BeanRow_comment>  grpBr = new ArrayList<BeanRow_comment>();
 
		private EditText EditComment; 

		List<String> commentaire= new ArrayList<String>() ;
		List<String> date= new ArrayList<String>() ;
		List<String> nom= new ArrayList<String>() ;
		List<String> prenom= new ArrayList<String>() ;
		List<String> img= new ArrayList<String>() ; 
		List<String> idligne= new ArrayList<String>() ;
		List<String> imagePublication= new ArrayList<String>() ;
		List<String> titrePublication= new ArrayList<String>() ;
		
		private MySecondeAdapter_comment adapter3;
		private ListView mListView;
		private JSONObject jsonObjSend;
		private String idEnd="0";
		private String image;
		private FeedImageView imageText; 
		private String sender;
		private TextView result;
		public ImageLoaderProfil imageProfilLoader;
		private FeedImageView image_publication;
	    private ImageView send_button_comment;
	     private Toolbar toolbar;
	private TextView	textToolHeader;
	 @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.commentaire);
 

       imageProfilLoader=new ImageLoaderProfil(getBaseContext());

       pDialog = new ProgressDialog(this);	    
       pDialog.setMessage(getString(R.string.st_chargement));
		//createDialog("Error","");
		pDialog.setCancelable(false);
		showProgressDialog();
      	jsonObjSend = new JSONObject();
      	
   	Bundle extras=getIntent().getExtras();
   	image=new String(extras.getString("nameImage")); 
   	titre=new String(extras.getString("titre")); 
   	datePublication=new String(extras.getString("datePublication")); 
   	NomPublieur=new String(extras.getString("NomPublieur"));
   	ImgPublieur=new String(extras.getString("ImgPublieur"));
		 idAnonce=new Integer(extras.getInt("idAnonce"));
   	idUtilisateur=new String(extras.getString("idUtilisateur"));
 
 	 

   	imageProfil= (ImageView) findViewById(R.id.img_profil); 
   	image_publication=(FeedImageView) findViewById(R.id.feedImage1);
   	titreText=(TextView) findViewById(R.id.titre);  	
   	dateText=(TextView) findViewById(R.id.date);
   	nameProfil = (TextView) findViewById(R.id.Nom_prenom);
	 EditComment= (EditText) findViewById(R.id.Editcomment);

    mListView = (ListView) findViewById(R.id.listCommentaires);
		 toolbar = (Toolbar) findViewById(R.id.toolbar);
		 setSupportActionBar(toolbar);


		 ActionBar actionBar = getSupportActionBar();
		 actionBar.setDisplayHomeAsUpEnabled(true);
		 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 getSupportActionBar().setTitle("");
		 textToolHeader = (TextView) toolbar.findViewById(R.id.toolbar_title);
   //	EditComment = (EditText) findViewById(R.id.Editcomment); 
   	
   	titreText.setText(titre); 
   	dateText.setText(datePublication);
   	nameProfil.setText(NomPublieur);  
   	
   /*******************commentaires*********************/
   	imageProfilLoader.DisplayImage(Const.ipUriLogo2+image, imageProfil);
   	//imageProfil.setImageUrl(Const.ipUriImageUsers+;image, imageLoader);
   	if(ImgPublieur.equals(""))
   	{
   		image_publication.setVisibility(View.GONE);
   	}
   	else
   	{
   		
   	   	image_publication.setImageUrl(Const.ipUriImageAnonce+ImgPublieur, imageLoader);
   	}
   	
    //createDialog("", Const.ipUriImage+ImgPublieur);
		 send_button_comment = (ImageView) findViewById(R.id.send_button_comment);
		 send_button_comment.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View v) {

				 sender=EditComment.getText().toString().trim();
				 if(sender.equals(""))
				 {
					 Toast.makeText(getBaseContext(), ""+getString(R.string.st_toast_error), Toast.LENGTH_LONG).show();
				 }
				 else
				 {
					 publier();
				 }
			 }
		 });





   			TimerCread();
	}

 private void publier()
 {
	 envoye="post";
		try {
			showProgressDialog();
	       	jsonObjSend.put("idAnonce",idAnonce);
	       	jsonObjSend.put("idCommentateur",Integer.parseInt(idUtilisateur));
	       	jsonObjSend.put("commentaire",sender); 	 
			makeJsonObjReq(jsonObjSend,Const.urlGestionAnonce+"PutCommentAnonce");
		} catch (JSONException e) {
			e.printStackTrace();
		} 	 

 }
		private void TimerCread()
		{		 
			envoye="affiche";
			try {   
		       	jsonObjSend.put("id_anonce",idAnonce);
		      // 	jsonObjSend.put("idEnd",idEnd); 
	            			
			} catch (JSONException e) {
				e.printStackTrace();
			}

			makeJsonObjReq(jsonObjSend,Const.urlGestionAnonce+"ListeCommentAnonce");
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
						           // Toast.makeText(getBaseContext(), ""+jsonObjSend.getString(Reponse.retour), Toast.LENGTH_LONG).show();
						            EditComment.setText(""); 
						            //result.setText("Publié avec succès");
						            TimerCread();
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
							if(incrConnexion<=Const.count)
							{
								publier();	
							}
							else
							{
								Toast.makeText(getBaseContext(),getString(R.string.st_connection_failled), Toast.LENGTH_LONG).show();
								hideProgressDialog();	
							}
							incrConnexion++;
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
			/*
			int socketTimeout = Const.timer_connection_request;//30 seconds - change to what you want
			RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
			jsonObjReq.setRetryPolicy(policy);
			mRequestQueue.add(jsonObjReq);
			*/
			// Adding request to request queue
			AppController.getInstance().addToRequestQueue(jsonObjReq,
					tag_json_obj);
	 	}
		
		
		

		

		/**
		 * Parsing json reponse and passing the data to feed view list adapter
		 * */
		private void parseJsonFeed(JSONObject response) {
			try {

				JSONArray feedArray = response.getJSONArray("resultat");
				commentaire.clear();
				date.clear();
				nom.clear();
				img.clear();
				idligne.clear();
				//imagePublication.clear();
				//titrePublication.clear();
		 				if(feedArray.length()>0){
		 					 for (int i =0; i <feedArray.length(); i++) 
		 					{
		 						JSONObject feedObj = (JSONObject) feedArray.get(i);
		 						    commentaire.add(""+feedObj.getString("commentaire"));
									date.add(""+feedObj.getString("comment_date"));
									nom.add(""+feedObj.getString("nomCommentateur"));
									prenom.add(""+feedObj.getString("prenomCommentateur"));
									img.add(""+feedObj.getString("profilCommentateur"));
									idligne.add(""+feedObj.getInt("comment_id"));
									//imagePublication.add("");
									//titrePublication.add("");
		 					} 
		 					affiche_annonces();
		 				} 

			} catch (JSONException e) {
				e.printStackTrace();
			}
/*
				commentaire.add("vide");
				date.add("");
				nom.add("");
				prenom.add("");
				img.add("");
				idligne.add("");
				imagePublication.add(""+ImgPublieur);
				titrePublication.add(""+titre);
				 hideProgressDialog() ;
					affiche_annonces();
					*/
				//createDialog("response", response.toString());
		}
	 		
		
		 


		public void affiche_annonces() {
			// TODO Auto-generated method stub
	
			grpBr = new ArrayList<BeanRow_comment>(); 
			grpBr=affiche_news(grpBr);  
			adapter3 = new MySecondeAdapter_comment(getBaseContext(), R.layout.commentaire_listview_item, grpBr);
			mListView.setAdapter(adapter3);
			
		}

		
		 private ArrayList<BeanRow_comment> affiche_news(ArrayList<BeanRow_comment> grpBr) { 

				//Toast.makeText(getBaseContext(), commentaire.size()+"-------", Toast.LENGTH_LONG).show();
		        for(int i=commentaire.size()-1;i>=0;i--)
					 { 	
		        	BeanRow_comment br2 =new BeanRow_comment(i,false,nom.get(i),date.get(i),img.get(i),commentaire.get(i));
		          grpBr.add(br2);
		          //Toast.makeText(getBaseContext(), imagePublication.get(i)+"-------", Toast.LENGTH_LONG).show();
					 } 
		        return grpBr;	  		
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



			public void commentaireDialog()
			{		 
			 	//On instancie notre layout en tant que View
		        LayoutInflater factory = LayoutInflater.from(this);
		         final View alertDialogView = factory.inflate(R.layout.comment_dialog, null);
		         final AlertDialog.Builder adb = new AlertDialog.Builder(this);
		   	     final AlertDialog alertDialog = adb.create();
				 adb.setView(alertDialogView);
		 		 adb.setTitle(" Votre commentaire"); 
			         adb.setIcon(android.R.drawable.ic_dialog_email);

			         EditComment=(EditText)alertDialogView.findViewById(R.id.Editcomment); 
			   	     result = (TextView)alertDialogView.findViewById(R.id.result); 
			   	     result.setVisibility(alertDialogView.GONE); 


		   	       final ImageView buttonSend = (ImageView)alertDialogView.findViewById(R.id.send_button_comment);
		   	    buttonSend.setOnClickListener(new View.OnClickListener() {
					    public void onClick(View view) {
						    	if(EditComment.getText().toString().equals(""))
						    	{
						    		result.setVisibility(view.VISIBLE); 
						    		result.setText("Remplir ce champ pour envoyer");  			           			
								}
								else
								{
						    		result.setVisibility(view.VISIBLE); 
							 		 sender=EditComment.getText().toString().trim();
									 result.setText("Envoie en cour..."); 
									 publier(); 
									 alertDialog.dismiss();
								}  
		     	    }
					   });
			   	 adb.show();
			         
			 }
			
			
			

			 
}
