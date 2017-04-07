package com.therankit.best;
    
import chechPakage.BeanRow;
import chechPakage.Holder;
import chechPakage.ListViewAdapter;
import chechPakage.ListViewAdapterDetailNote;

 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.load.img.FeedImageView;
import com.load.img.ImageLoaderProfil;
import com.sqlLite.MySQLiteGestion;
import com.sqlLite.SecteurTable;
import com.sqlLite.VerifNoteTable;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest; 
 
import com.therankit.rankitlite.R;

import com.squareup.picasso.Picasso;
import com.url.AppController;
import com.volley.Const;
import com.volley.EncodeDecode;
import com.volley.JsonUTF8Request;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;  
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent; 
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable; 
import android.graphics.drawable.Drawable;
import android.os.Bundle;   
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View; 
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;  
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;  
import android.widget.Toast;
public class detailBest extends Activity {
	private String TAG = detailBest.class.getSimpleName();
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

	ImageLoader imageLoader2 = AppController.getInstance().getImageLoader();
	 private  ImageLoaderProfil imageLoaderProfil;
	 private ImageView profilSendNotes;
	 ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	public ProgressDialog	progressDialog;
	private String entreprise_logo;
	private String entreprise_name;
	@SuppressWarnings("unused")
	private String datePublication;   
	private SimpleDateFormat formater;
	

	private int incrConnexion=1;

	private int randkiteUser_id;

	private int entreprise_id;
	private JSONObject jsonObjSend;
	private ProgressDialog pDialog;
	private String prixArticle; 
	private NumberFormat numberFormatter;

	private FeedImageView imageText;
	private String CallNumber;



	private ProgressBar progressBar;
	private ProgressBar progressBarDialog;


	private int counter=0;

	//Création d'une instance de ma classe LivresBDD
	private VerifNoteTable verifNoteBdd;
	private SharedPreferences settings;

	private String secteur_id;
	private String prixInit;
	private EditText EditComment;
	private TextView MessageNotes;
	private TextView result;
	private Spinner tailleSpinner;
	private ArrayAdapter<String> dataAdapter ;
	private ArrayList<String> listTaille = new ArrayList<String>();
	private ArrayList<String> listValueTaille= new ArrayList<String>();
	private  Uri bmpUri;
	

	private String TelephoneUser;



	private  ArrayList<BeanRow>  grpBr = new ArrayList<BeanRow>();

	private OnClickListener onClick;

    private String typeRequete="";
    ListViewAdapterDetailNote adapter;
	  ListView listView;
	  private Button ValidationButon;
	  private String msg;
	  private JSONArray Listenotes = new JSONArray();
     private JSONObject notes = null;
     private Boolean etatVote=false;
     private    View alertDialogView ;
     private  Boolean toutVote=false;
     private   AlertDialog.Builder adb;
     private  AlertDialog.Builder adbSendMsg;
     private  Button buttonSendComment;
     private  AlertDialog alertDialogSendMsg;
	 @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.detail_best); 
       
      
      	Bundle extras=getIntent().getExtras();
      	entreprise_id=new Integer(extras.getInt("entreprise_id"));
      	entreprise_logo=new String(extras.getString("entreprise_logo"));
      	entreprise_name=new String(extras.getString("entreprise_name"));
      	prixArticle=new String(extras.getString("entreprise_name"));
      	prixInit=new String(extras.getString("entreprise_name"));
      	secteur_id=new String(extras.getString("secteur_id"));
      	randkiteUser_id=new Integer(extras.getInt("randkiteUser_id"));  

       TelephoneUser=new String(extras.getString("entreprise_name")); 

   
	   settings = getSharedPreferences(Const.PREFRENCES_NAME, Context.MODE_PRIVATE); 	
       numberFormatter = NumberFormat.getNumberInstance(); 
   	
      	//imageProfil=(ImageView) findViewById(R.id.img_profil); 
      //	imageText=(ImageView) findViewById(R.id.img_article);
      			 
	CallNumber="+"+TelephoneUser;
	
	
	
		pDialog = new ProgressDialog(this);
      	pDialog.setMessage("Chargement..."); 
   	//showProgressDialog();
   	jsonObjSend = new JSONObject();

	 progressBar=(ProgressBar)findViewById(R.id.progress); 
	 progressBar.setVisibility(View.VISIBLE);
	  
	 imageLoaderProfil=new ImageLoaderProfil(getBaseContext().getApplicationContext());

	
		 imageText = (FeedImageView) findViewById(R.id.img_article); 
			imageText.setImageUrl(Const.ipUriLogo+entreprise_logo, imageLoader);
	   	//imageText.setImageUrl(Const.ipUriLogo+entreprise_logo, imageText);
	   	//imageLoaderProfil.DisplayImage(Const.ipUriLogo+entreprise_logo,imageText);
		// Picasso.with(this).load(Const.ipUriLogo+entreprise_logo).error(R.drawable.load).into(imageText);
	    //bmpUri = getLocalBitmapUri(imageText); 
	    
	    ValidationButon = (Button) findViewById(R.id.validation);
	    
	    ValidationButon.setOnClickListener(new View.OnClickListener() {


		public void onClick(View view) {
			SendNote();
		}
		 });
	   ValidationButon.setVisibility(View.GONE);
	   /* imageText.setOnClickListener(new OnClickListener(){
	        public void onClick(View view) 
	        { 
                Intent i = new Intent(getBaseContext(), FullScreenViewActivity.class);
                i.putExtra("TabFiles",Galleries);
                i.putExtra("infosProd",prixArticle+" "+monnaieArticle);
                startActivity(i);
	          }});*/
	   if(settings.getString("sourceNoteS", "0").equals("1"))
		{
		   GetMessageDialog();
		}
	    ListeServices();
	   	listValueTaille= new ArrayList<String>();
	   	listValueTaille.add("0");
	   	listValueTaille.add("1");
	   	listValueTaille.add("2");
	   	listValueTaille.add("3");
	   	listValueTaille.add("4");
	   	listValueTaille.add("5");
	   	listValueTaille.add("6");
	   	listValueTaille.add("7");
	   	listValueTaille.add("8");
	   	listValueTaille.add("9");
	   	listValueTaille.add("10");
	   	listValueTaille.add("11");
	   	
	   	listTaille = new ArrayList<String>();
	   	listTaille.add("Taille");
	   	listTaille.add("S");
	   	listTaille.add("M");
	   	listTaille.add("L");
	   	listTaille.add("XS");
	   	listTaille.add("3XL");
	   	listTaille.add("S/2");
	   	listTaille.add("M/3");
	   	listTaille.add("L/4");
	   	listTaille.add("XL/5");
	   	listTaille.add("XXL/6");
	   	listTaille.add("XXXL/7");
	   	listTaille.add("XXXXL/8");
    	//creation de la BD
	   	verifNoteBdd = new VerifNoteTable(getBaseContext());  
	   	
	   	
	//getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#73C2FB")));
   //// getSupportActionBar().setDisplayHomeAsUpEnabled(true); 
    //getSupportActionBar().setIcon(R.drawable.white_best);
	//getSupportActionBar().setTitle(" "+entreprise_name+" ("+prixArticle+")"); 

   	// maListViewService = (ListView) findViewById(R.id.modelePrecis);
   	     listView=(ListView)findViewById(R.id.modelePrecis);
   	  
   
 		
 	/*	
 		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
 		{
 		      @Override
 		      public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
 		      {
 		    	int pos=position+1;
 		    	Toast.makeText(getApplicationContext(), Integer.toString(pos)+" Clicked", Toast.LENGTH_SHORT).show();  
 		      }
 		      
 		});*/
   	      	
   	 
   	    /* adapterService= new MySecondeAdapter(getApplicationContext(), R.layout.affichagemodele, grpBr, onClick);
   	     maListViewService.setAdapter(adapterService);*/
	 }
 
 

	 
	 
	 private OnClickListener onClick(final ArrayList<BeanRow> grpBr) {
		    OnClickListener event = new OnClickListener() {

					public void onClick(View v) {
		                    View parent;
		                   
		                    Holder holder;
		                
	                         
		                    switch (v.getId()) {
		                    
		               
		                  
		                    case R.id.gender:
		                    	 /** v = textview */
		                    	 parent = (View) v.getParent();  
		                         holder = (Holder) parent.getTag();
		                         Toast.makeText(getApplicationContext(), grpBr.get(holder.position).getLibelleService(), Toast.LENGTH_SHORT).show();
		                     break;
		                    
		                    case R.id.img:
		                    	 /** v = textview */
		                    	 parent = (View) v.getParent();  
		                         holder = (Holder) parent.getTag();
		                         if(etatVote)
		                         {
		                        	 createDialog("",getApplicationContext().getString(R.string.msg_error_vote));
		                        }
		                         else
		                         {
		                        	 grpBr.get(holder.position).setVote(true);
			                         grpBr.get(holder.position).setNoteService(1);
			               Toast.makeText(getApplicationContext(), "" +getApplicationContext().getString(R.string.like), Toast.LENGTH_SHORT).show();
			               adapter.notifyDataSetChanged(); 
			               getBoutonValidate();
		                         }
		                        
		                         
		    /** on notifie le changement de la liste pour que le check se met à jour */
		                         
		    
		     
		                     break;
		                     
		                    case R.id.img2:
		                    	 /** v = textview */
		                    	 parent = (View) v.getParent();  
		                         holder = (Holder) parent.getTag();
		                         if(etatVote)
		                         {
		                        	 createDialog("",getApplicationContext().getString(R.string.msg_error_vote));
		                        }
		                         else
		                         {
		                        	 grpBr.get(holder.position).setVote(true);
			                         grpBr.get(holder.position).setNoteService(-1);
			                       Toast.makeText(getApplicationContext(), "" + getApplicationContext().getString(R.string.likenot), Toast.LENGTH_SHORT).show();
			                         /** on notifie le changement de la liste pour que le check se met à jour */
			                         getBoutonValidate();
			                         adapter.notifyDataSetChanged();
		                         }
		                         
		   
		                     break;
		                     
		                    case R.id.annuler:
		                    	 /** v = textview */
		                    	 parent = (View) v.getParent();  
		                         holder = (Holder) parent.getTag();
		                         grpBr.get(holder.position).setVote(false);
		                         grpBr.get(holder.position).setNoteService(0);
		                         adapter.notifyDataSetChanged();
		                         /** on notifie le changement de la liste pour que le check se met à jour */
		                         
		                         getBoutonValidate();
		                     break;
		         }
		            }

				 
		    };
		    return event;
		}
		

 
	 public void SendMessageDialog()
		{		 
			 
		 
	        LayoutInflater factory = LayoutInflater.from(this);
	        alertDialogView = factory.inflate(R.layout.message_service, null);
	        adbSendMsg = new AlertDialog.Builder(this);
	        alertDialogSendMsg = adbSendMsg.create();
	   	 adbSendMsg.setView(alertDialogView);
	   	 adbSendMsg.setTitle(getApplicationContext().getString(R.string.title_dialog_send_msg)); 
	    adbSendMsg.setIcon(android.R.drawable.ic_dialog_email);
		    progressBarDialog=(ProgressBar)alertDialogView.findViewById(R.id.progress); 
		    progressBarDialog.setVisibility(alertDialogView.GONE);
		         EditComment=(EditText)alertDialogView.findViewById(R.id.message);
		   	     
		         result = (TextView)alertDialogView.findViewById(R.id.result); 
		         result.setVisibility(alertDialogView.GONE); 
		         
		        	
		        	
		        
		         
		        	
		         
	   	       buttonSendComment = (Button)alertDialogView.findViewById(R.id.send_button_comment);
	   	       buttonSendComment.setOnClickListener(new View.OnClickListener() {
				   
				
					public void onClick(View view) {
						
						msg=EditComment.getText().toString().trim();
				 		
					    	if(EditComment.getText().toString().equals("") || msg.equals("") )
					    	{
					    		String qtee="Veuillez remplir ce champ!";
					    		
					    		result.setVisibility(view.VISIBLE); 
					    		result.setText(qtee); 
					    					           			
					    	}
					    	else
					    	{
					    		progressBarDialog.setVisibility(alertDialogView.VISIBLE);
					    		EditComment.setVisibility(alertDialogView.GONE);
					    		requeteSendNotes();
					    		EditComment.setText("");  	
					    		
					    	}
					    
								 
							
								
							
	     	    }
				   });
	   	       
	   	       
	   	    adbSendMsg.show();

		         
		 }
		
	 
	 
	 /*
	  * 
	  * debut Dialog lors de l'envoie d'une notification
	  */
	 public void GetMessageDialog()
		{		 
			 
		 
	        LayoutInflater factory = LayoutInflater.from(this);
	         final View alertDialogView = factory.inflate(R.layout.get_message_service, null);
	         adb = new AlertDialog.Builder(this);
	   	     final AlertDialog alertDialog = adb.create();
			 adb.setView(alertDialogView);
			
	 		 adb.setTitle(getApplicationContext().getString(R.string.msg_dialog_opinion)); 
		     adb.setIcon(R.drawable.ic_maps_directions_walk);
		        
			 adb.setNegativeButton(getApplicationContext().getString(R.string.rapel),new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, just close
							// the dialog box and do nothing
							finish();
						}
					});
			     
			     adb.setPositiveButton("Rankit !",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, just close
							// the dialog box and do nothing
							dialog.cancel();
						}
					});
		         profilSendNotes = (ImageView) alertDialogView.findViewById(R.id.photos);
		        
		         MessageNotes=(TextView)alertDialogView.findViewById(R.id.message);
		       
		        	 imageLoaderProfil.DisplayImage(Const.ipUriLogo+entreprise_logo,profilSendNotes);
		        	 MessageNotes.setText(settings.getString("messageService", "Message Par Defaut")); 
		        
		        
		        
		        
	   	       
	   	       
		   	 adb.show();
		   	
    	    settings.edit() .putString("profilSendNote","profile.png") .commit();
    	    settings.edit() .putString("messageService","Message Par Defaut") .commit();
    	   // createDialog("", entreprise_logo);

		         
		 }
	 
	 /*
	  * 
	  * fin Dialog lors de l'envoie d'une notification
	  */
 private void publier()
 {
	 
	 
	try
	{  	 		
		jsonObjSend.put("",entreprise_id); 

makeJsonObjReq(jsonObjSend,""); 

	} catch (JSONException e) {
		e.printStackTrace();
	}
	
 
 }

		private void showProgressDialog() {
			pDialog.setCancelable(false); 
			 	pDialog.show(); 
				
		}

		private void hideProgressDialog() { 
			pDialog.hide(); 
		}
	 
		private void makeJsonObjReq(JSONObject json,String url) {
		    
		   // showProgressDialog();
			
		    RequestQueue mRequestQueue = Volley.newRequestQueue( getApplicationContext());
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
									if(typeRequete.equals("ListeService"))
									{
										JSONArray feedArray = response.getJSONArray("resultat");
										if(feedArray.length()!=0)
										{
											 grpBr = new ArrayList<BeanRow>();
											
											 for (int i = 0; i < feedArray.length(); i++) 
							 					{
							 						try {
														JSONObject feedObj = (JSONObject) feedArray.get(i);
														 BeanRow br =new BeanRow(feedObj.getInt("services_id"),feedObj.getString("services_name"),0,false);
												  	     grpBr.add(br);
													} catch (JSONException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}	
											
							 					}
										}
										 
									   	 
									   	     onClick = onClick( grpBr); 
									   	 adapter=new ListViewAdapterDetailNote(detailBest.this, grpBr,onClick);
									   	LayoutInflater inflater = getLayoutInflater();
								        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header_detail_notes, listView,
								                false);
								        ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.footer_detail_notes, listView,
								                false);
								        listView.addHeaderView(header, null, false);
								        listView.addFooterView(footer, null, false);
										listView.setAdapter(adapter);
										// createDialog("infosssssss",jsonObjSend.getString("reference"));
									}
									
									else
									{
										Date d=new Date();
										new Locale("FR","fr");
										formater = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
										formater.format(d);
										verifNoteBdd.open();
										verifNoteBdd.InsertLigne(entreprise_id, formater.format(d));
										verifNoteBdd.close();
										 Intent broadcastIntent = new Intent();
      									 broadcastIntent.setAction("GCM_SEND_NOTES");
      									etatVote=true;
      									if(toutVote)
      									{
      										adbSendMsg.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
      											public void onClick(DialogInterface dialog,int id) {
      												// if this button is clicked, just close
      												// the dialog box and do nothing
      												finish();
      											}
      										});	
      									 alertDialogSendMsg.cancel();
      								     buttonSendComment.setVisibility(alertDialogView.GONE);
      									 progressBarDialog.setVisibility(alertDialogView.GONE);
      									 ValidationButon.setVisibility(View.GONE);
      									 EditComment.setVisibility(alertDialogView.GONE);
      						        	 result.setVisibility(alertDialogView.VISIBLE);
      						        	 result.setText(getApplicationContext().getString(R.string.msg_after_vote)); 
      						        	 result.setTextColor(Color.parseColor("#000000"));
      									}
										//finish();
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}  
						  
								progressBar.setVisibility(View.GONE);
							     hideProgressDialog();
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
								progressBar.setVisibility(View.GONE);	
							}
							incrConnexion++;
							}
					}) {
 
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
		

	 
	 public void createDialog(String title, String text)
	 {
	 	// Création d'une popup affichant un message
	 	AlertDialog ad = new AlertDialog.Builder(this)
	 			.setPositiveButton("Ok", null).setTitle(title).setMessage(text)
	 			.create();
	 	ad.show();

	 }
	
	 
	 public boolean onCreateOptionsMenu(Menu menu) {
		    MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.menu_acheter, menu);

		    return super.onCreateOptionsMenu(menu);
		}
		 
		  
		public boolean onOptionsItemSelected(MenuItem item) {
			Intent intent;
			switch (item.getItemId()) {	
		    case android.R.id.home:  
		    	
		    	onBackPressed() ;
		    	
		        return true;
		        
		        
		   /* case R.id.share:  
		    	Share(Const.ipUriImage+entreprise_logo,"Slt! Petite annonce pour toi. En vente sur e-Market : "+entreprise_name+" à "+prixArticle+" "+ secteur_id+"." +
		    			" Voila son contact : "+CallNumber+". Tu peux le joindre. Visible aussi sur: www.facebook.com/e-yamo.com  ou sur play store via l'application : e-yamo. A plus."); 
				   return true;   */
 
		    }
		    return false;
		}
 

		private void Share (String namePicture,String produit){ 
	
	        Intent shareIntent = new Intent();
		     shareIntent.setAction(Intent.ACTION_SEND);
		     shareIntent.putExtra(Intent.EXTRA_TEXT, produit);
		     shareIntent.putExtra(Intent.EXTRA_STREAM,bmpUri);  //optional//use this when you want to send an image
		     shareIntent.setType("image/jpeg");
		     shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
	         startActivity(Intent.createChooser(shareIntent, "Partager avec..."));
		
	        }
		// Returns the URI path to the Bitmap displayed in specified ImageView

		@SuppressLint("NewApi")
		public Uri getLocalBitmapUri(ImageView imageView) {

		    // Extract Bitmap from ImageView drawable
		    Drawable drawable = imageView.getDrawable();
		    Bitmap bmp = null;

		    if (drawable instanceof BitmapDrawable){
		       bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
		    } else {
		       return null;
		    }
		    // Store image to default external storage directory
		    Uri bmpUri = null;
		    try {
		        File file =  new File(Environment.getExternalStoragePublicDirectory(  
			        Environment.DIRECTORY_DOWNLOADS), "randk_" + System.currentTimeMillis() + ".png");
		        file.getParentFile().mkdirs();
		        FileOutputStream out = new FileOutputStream(file);
		        bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
		        out.close();
		        bmpUri = Uri.fromFile(file);

		    } catch (IOException e) {

		        e.printStackTrace();

		    }

		    return bmpUri;

		}
		
		/*
		 * 
		 * debut methode permetttant les services d'une entreprise
		 * 
		 * 
		 */
		
		private void ListeServices()
		  {
			  try	
				{  	jsonObjSend.put("entreprise_id",entreprise_id);
					jsonObjSend.put("secteur_id",secteur_id);
					
					typeRequete="ListeService";
					makeJsonObjReq(jsonObjSend,Const.urlNote+"listeServiceEntreprise");
				}
				catch (JSONException e) 
				{
					e.printStackTrace();
				}	
		  }
		
		/*
		 * 
		 * fin methode permetttant les services d'une entreprise
		 * 
		 * 
		 */
		
		/*
		 * 
		 * debut methode permetttantd'envoyer les notes 
		 * 
		 * 
		 */
		private void SendNote()
		{
			 Boolean toutNote=true;
			 for(int i=0;i< grpBr.size() ;i++)
			 {
				 if( !grpBr.get(i).vote)
				 {
					 toutNote=false; 
					 break;
				 }
			 }	 
				 if(toutNote)
				 {
					 SendMessageDialog();
					 toutVote=true;
				 }
				 else
				 {
					 msg="0";
					 progressBar.setVisibility(View.VISIBLE);
					 requeteSendNotes();
					 
				 }
				 
			 
		}
		
		/*
		 * 
		 * fin methode permetttantd'envoyer les notes 
		 * 
		 * 
		 */
		
		
		/**
		 * 
		 * debut de la methode permettan d'afficher le bouton d'envoyer
		 * des votes
		 */
		private void getBoutonValidate()
		{
			Boolean chek=false;
		for(int i=0;i< grpBr.size() ;i++)
			 {
				if(grpBr.get(i).getVote())
				{
					chek=true;
					break;
				}
		    }
         
		if(chek)
		{
			ValidationButon.setVisibility(View.VISIBLE);
		}
		else
		{
			ValidationButon.setVisibility(View.GONE);
		}
		}
		
		
		/**
		 * 
		 * Fin de la methode permettan d'afficher le bouton d'envoyer
		 * des votes
		 */
		  private void requeteSendNotes()
		  {
			  try {
					 Listenotes = new JSONArray();
					
					 int nbreAime=0;
					 int nbreNonAime=0;
						for(int i=0;i< grpBr.size() ;i++)
						 {
							if(grpBr.get(i).getVote())
							{ 
								
								if(grpBr.get(i).getNoteService()==1)
								{
									nbreAime++;
								}
								else
								{
									nbreNonAime++;
								}
								notes = new JSONObject(); 
								notes.put("services_id", grpBr.get(i).getIdService());
								notes.put("services_notes", grpBr.get(i).getNoteService());
								Listenotes.put(notes);
								
							}
							
						 }
						
						notes = new JSONObject();
						
						if(settings.getString("sourceNoteS", "0").equals("0"))
						{
							notes.put("sourceNoteS", 0);
						}
						else
						{
							notes.put("sourceNoteS", 1);
							
						}
						if(nbreNonAime>nbreAime)
						{
							
							notes.put("moyeneNote", "0");
						}
						else
						{
							notes.put("moyeneNote", "1");
						}
						notes.put("listeNotes", Listenotes);
						notes.put("randkiteUser_id",randkiteUser_id);
						notes.put("entreprise_id",entreprise_id);
						notes.put("entreprise_logo",entreprise_logo);
						notes.put("entreprise_name",entreprise_name);
						notes.put("secteur_id",Integer.parseInt(secteur_id));
						notes.put("message",msg);
						
						typeRequete="NoteService";
						makeJsonObjReq(notes,Const.urlNote+"PutServiceEntreprise");
						
						 } catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		  }
		
		/*
		 * 
		 * debut de la methode permettant d'afficher le message lors de la sortie de cette vue
		 * 
		 
		 */
		
		public void onBackPressed() {
	        // Write your code here   
			if(!etatVote)
			{
			AlertDialog.Builder builder = new AlertDialog.Builder(detailBest.this);
			builder.setMessage(R.string.info_quit_noteS);
			builder.setCancelable(false);
			builder.setNegativeButton(R.string.info_oui, new DialogInterface.OnClickListener() {       
			public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
						//System.exit(0);
					}});        
			builder.setPositiveButton(R.string.info_non, new DialogInterface.OnClickListener() {
				public void onClick(final DialogInterface dialog, final int id) {     
					dialog.cancel();      
				}});
			builder.show(); 
			}
			else
			{
				finish();
			}
	    }
		/*
		 * 
		 * fin de la methode permettant d'afficher le message lors de la sortie de cette vue
		 * 
		 
		 */
}
