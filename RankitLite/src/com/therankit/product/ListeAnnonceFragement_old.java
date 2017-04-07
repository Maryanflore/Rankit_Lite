package com.therankit.product;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.reponse.Reponse;
import com.therankit.home.MainActivity;
import com.therankit.rankitlite.R;
import com.url.AppController;
import com.volley.Const;
import com.volley.JsonUTF8Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ListeAnnonceFragement_old extends Fragment{
	private String TAG = ListeAnnonceFragement_old.class.getSimpleName();
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
  
	private ProgressDialog pDialog;
	private String envoye;  
	private  OnClickListener onClick;

	private int nbreCmdeEncours=0;
	private int nbreCmdeNew=0;
	   private AnnonceAdapter adapter3; 
	String AllImage="";
	 String AllTitre="";
	 String AllDescrip="";
	 String AllDate="";
	 String AllNews="";
  
private  ArrayList<AnonceRow>  grpBr = new ArrayList<AnonceRow>(); 

private int incrConnexion=1;
List<String> telephoneAnnonceur= new ArrayList<String>() ;
List<String> descriptionPetitNews= new ArrayList<String>() ;
List<String> contenueNews= new ArrayList<String>() ;
List<String> dateNews= new ArrayList<String>() ;
List<String> titreNews= new ArrayList<String>() ;
List<String> alltitreNews= new ArrayList<String>() ;
List<String> imageNews= new ArrayList<String>() ;	
List<String> statutNews= new ArrayList<String>() ;
List<String> idNews= new ArrayList<String>() ;	
List<String> titre= new ArrayList<String>() ;
List<String> monnaie= new ArrayList<String>() ;	


List<String> idAnnonceur= new ArrayList<String>() ;
List<String> photosAnnonceur= new ArrayList<String>() ;	
List<String> NomAnnonceur= new ArrayList<String>() ;
List<String> prenomAnnonceur= new ArrayList<String>() ;	
	 	

		private NumberFormat numberFormatter;
	private ListView mListView;
	private String id_end="0";  
	@SuppressWarnings("unused")
	private boolean signal=true;
	private TextView textload;
	private String idUtilisateur="";
	private String telephoneUtilisateur;
	private String imgUtilisateur;
	private JSONObject jsonObjSend;
	private JSONArray feedArray;
	private String data ;
	private int nbreElement;
 private int taillTab;
private String idEnd="0";
private Timer timer;
private TimerTask task_2;
private int var=0;
private String statut;
private String idpublieur;
private String title;
private int nbreElementGlobal;
private ImageView imageHomePannier;
	@SuppressLint("NewApi")
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.activity_epeople, container, false);
		
        mListView = (ListView) view.findViewById(R.id.list); 
        numberFormatter = NumberFormat.getNumberInstance();
		// These two lines not needed,
		// just to get the look of facebook (changing background color & hiding the icon)
	//	getSherlockActivity().getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#73C2FB")));
		//getSherlockActivity().getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		//getSherlockActivity().getSupportActionBar().setIcon(R.drawable.ic_maps_directions_walk_blanc);
		//getSherlockActivity().getSupportActionBar().setTitle("Service");
		
		jsonObjSend = new JSONObject();
  
		
		
		Bundle bundle=this.getArguments(); 
		if(bundle != null)
		{
			 idUtilisateur=bundle.getString("idUtilisateur"); 
			 telephoneUtilisateur=bundle.getString("telephoneUtilisateur");
			 imgUtilisateur=bundle.getString("imgUtilisateur"); 
		}
		 	
        //Toast.makeText(getActivity(), ""+idUtilisateur, Toast.LENGTH_LONG).show();
		pDialog = new ProgressDialog(getActivity());       
		pDialog.setMessage(getString(R.string.st_chargement)); 	
		 // showProgressDialog();
			  
		  setHasOptionsMenu(true);
		 requestCread();
		 
	
		return view;
	}
	
 

	private void requestCread()
	{	
		
		try {
			envoye="affichage";
			jsonObjSend.put(Const.TAG_IDEND,idEnd);
			makeJsonObjReq(jsonObjSend,Const.URL_JSON_AFFICHAGE_E_PEOPLE);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
		
	
		 }


	


	private void showProgressDialog() { 
		 	pDialog.show();  
			
	}

	private void hideProgressDialog() { 
		pDialog.dismiss(); 
	}
	/**
	 * Making json object request
	 * */
	
	private void makeJsonObjReq(final JSONObject json,final String url) {
		//RequestQueue mRequestQueue = Volley.newRequestQueue(getSherlockActivity().getApplicationContext());
		JsonUTF8Request jsonObjReq = new JsonUTF8Request(Method.POST,url,json,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						
						Log.d(TAG, response.toString());
						VolleyLog.d(TAG, "Response: " + response.toString());
						if (response != null) {		
							try {
								try {
									feedArray = response.getJSONArray("resultat");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if(feedArray.length()!=0)
								{
								parseJsonFeed(response); 
								}
								else
								{
	Toast.makeText(getActivity(),"Aucun r�sultat", Toast.LENGTH_LONG).show();

	hideProgressDialog();
								}

							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}  
						}  
						hideProgressDialog();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						if(incrConnexion<=Const.count)
						{
							//publier();	
						}
						else
						{
							Toast.makeText(getActivity(),getString(R.string.st_connection_failled), Toast.LENGTH_LONG).show();
							hideProgressDialog();	
						}
						incrConnexion++;
						hideProgressDialog();
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
		AppController.getInstance().addToRequestQueue(jsonObjReq,
				tag_json_obj);
 	}
	
	

	/**
	 * Parsing json reponse and passing the data to feed view list adapter
	 * @throws UnsupportedEncodingException 
	 * */
	private void parseJsonFeed(JSONObject response) throws UnsupportedEncodingException {

		if(idEnd.equals("0")){
			statut="init";
		}
	else{

		statut="deja";	
	}

	
		try {

		  
			JSONArray feedArray = response.getJSONArray("resultat");
	// nbreElement=0;
	
	 int nbre_img_desc=2;
			for (int i = 0; i < feedArray.length(); i++) 
			{
				JSONObject feedObj = (JSONObject) feedArray.get(i);

				// Toast.makeText(getActivity(),feedObj.getString(Reponse.retourVerifUser), Toast.LENGTH_SHORT).show();

				 if(feedObj.getBoolean(Reponse.retourVerifUser))
				 {			 
			        String image = feedObj.isNull("imagePeople") ? null : feedObj.getString("imagePeople");		
				    titreNews.add(""+feedObj.getString("descriptionPeople"));

			    	
					dateNews.add(""+feedObj.getString("datePublicationPeople"));
					 //Toast.makeText(getActivity(), "" +feedObj.getString("prenomPublieur"), Toast.LENGTH_SHORT).show();	
					//photosAnnonceur.add(""+feedObj.getString("profilPublieur"));
				
					
					
					 title=feedObj.getString("descriptionPeople");
					 idpublieur=feedObj.getString("idPublieur");
					//imageNews.add(""+image); 
					 //imageNews.add("slide2-basse_res.jpg");
						if(nbre_img_desc%2==0)
						{
							NomAnnonceur.add("Allianz");					
							prenomAnnonceur.add("");
							photosAnnonceur.add("allianz_logo.jpg");
							imageNews.add("allianz.jpg");
							nbre_img_desc=3;	
						}
						else if(nbre_img_desc%3==0)
						{   NomAnnonceur.add("Uba");					
						  prenomAnnonceur.add("");
						  photosAnnonceur.add("UBA.gif");
							imageNews.add("uba.jpg");
							nbre_img_desc=7;	
						}
						else if(nbre_img_desc%7==0)
						{
							NomAnnonceur.add("Afriland");					
							prenomAnnonceur.add("Bank");
							photosAnnonceur.add("Capture6.PNG");
							imageNews.add("afriland.jpg");
							nbre_img_desc=5;	
						}
						
						else if(nbre_img_desc%5==0)
						{NomAnnonceur.add(""+feedObj.getString("nomPublieur"));					
						prenomAnnonceur.add(""+feedObj.getString("prenomPublieur"));
						photosAnnonceur.add("inscription_IMG_1438020213439.jpg");
							imageNews.add("slide2-basse_res.jpg");
							nbre_img_desc=11;	
						}
						else if(nbre_img_desc%11==0)
						{
							NomAnnonceur.add(""+feedObj.getString("nomPublieur"));					
							prenomAnnonceur.add(""+feedObj.getString("prenomPublieur"));
							photosAnnonceur.add("inscription_IMG_1438020213439.jpg");
							imageNews.add("");
							nbre_img_desc=2;	
						}
					 
					if(statut.equals("deja")&&(i==0))
					{
					idEnd=feedObj.getString("idPublieurPeople");
					}
					 
					idNews.add(""+feedObj.getString("idPublieurPeople"));					
					alltitreNews.add(""+feedObj.getString("nombreComment"));				
					nbreCmdeNew++;
					nbreElementGlobal++;

				 }
			 
			 }
			
						if(nbreCmdeNew>nbreCmdeEncours)
						{
						 nbreCmdeEncours=nbreCmdeNew;
								
								 if(statut.equals("deja"))							
								{
									if(Integer.parseInt(idpublieur)!=Integer.parseInt(idUtilisateur))
						 			{
										 createNotification("4",title);
									}
								}
							 affiche_annonces(); 
						}
						 hideProgressDialog() ; 

						 signal=true; 	
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	

	 private ArrayList<AnonceRow> affiche_news(ArrayList<AnonceRow> grpBr) { 
	       if(idEnd.equals("0"))
	       {
	    	    for(int i=0;i<dateNews.size();i++)
				 { 	  	if(i==0)
						{
					 idEnd=idNews.get(i);	
						}
 
				 AnonceRow br2 =new AnonceRow(i,false,NomAnnonceur.get(i)+" "+prenomAnnonceur.get(i),dateNews.get(i),"",
				        		  alltitreNews.get(i),"",photosAnnonceur.get(i),imageNews.get(i),
				        		  titreNews.get(i),"",idNews.get(i));
				          grpBr.add(br2);
				          //Toast.makeText(getActivity(), "----"+i, Toast.LENGTH_SHORT).show();
				 } 

				 nbreElementGlobal=0;	
	       }
	       else
	       {
	    	  	  for(int j=dateNews.size()-1;j>=dateNews.size()-nbreElementGlobal;j--)
					 { 
	    	  		AnonceRow br2 =new AnonceRow(j,false,NomAnnonceur.get(j)+" "+prenomAnnonceur.get(j),dateNews.get(j),"",
			        		  alltitreNews.get(j),"",photosAnnonceur.get(j),imageNews.get(j),
			        		  titreNews.get(j),"",idNews.get(j));    		  
			          grpBr.add(br2);
			          //Toast.makeText(getActivity(), "//////"+j, Toast.LENGTH_SHORT).show();
					 }
		    	  
		    	 for(int j=0;j<dateNews.size()-nbreElementGlobal;j++)
				 {   
		    		 AnonceRow br2 =new AnonceRow(j,false,NomAnnonceur.get(j)+" "+prenomAnnonceur.get(j),dateNews.get(j),"",
			        		  alltitreNews.get(j),"",photosAnnonceur.get(j),imageNews.get(j),
			        		  titreNews.get(j),"",idNews.get(j));
			          grpBr.add(br2);
			         // Toast.makeText(getActivity(), "*****"+j, Toast.LENGTH_SHORT).show();
			     }  
	       }
	       
	        return grpBr;
  		
} 

		private void affiche_annonces() throws UnsupportedEncodingException {
		grpBr = new ArrayList<AnonceRow>(); 
			grpBr=affiche_news(grpBr); 
			onClick = onClick( grpBr); 
			adapter3 = new AnnonceAdapter(getActivity(), R.layout.anonce_produit_item, grpBr, onClick);
			mListView.setAdapter(adapter3);
			
			}
		 
	 
		
		private OnClickListener onClick(final ArrayList<AnonceRow> grpBr) {
		    OnClickListener event = new OnClickListener() {

					public void onClick(View v) {
						   View parent;
				           HolderAnonce holder;

				            switch (v.getId()) {
				             case R.id.iconShare: 
				                    parent = (View) v.getParent();  
				                    parent = (View) parent.getParent(); 
				                    parent = (View) parent.getParent(); 
				                    parent = (View) parent.getParent();   
				                    holder = (HolderAnonce) parent.getTag();
//Toast.makeText(getActivity(), "-------"+grpBr.get(holder.position).getTitreArticle(), Toast.LENGTH_SHORT).show();
Share (grpBr.get(holder.position).getNomPublieur()+" a publier sur e-Pepole : "+grpBr.get(holder.position).getTitreArticle());
			                    break;	
			                    
				             case R.id.partager: 
				                    parent = (View) v.getParent();  
				                    parent = (View) parent.getParent(); 
				                    parent = (View) parent.getParent(); 
				                    parent = (View) parent.getParent();   
				                    holder = (HolderAnonce) parent.getTag();
//Toast.makeText(getActivity(), "-------"+grpBr.get(holder.position).getTitreArticle(), Toast.LENGTH_SHORT).show();
Share (grpBr.get(holder.position).getNomPublieur()+" a publier sur e-Pepole : "+grpBr.get(holder.position).getTitreArticle()
		+" a "+grpBr.get(holder.position).getDate());
			                    break;	
			                    		                    
			                    
			                    
			                    
				             case R.id.nbre_comment: 
				                    parent = (View) v.getParent();  
				                    parent = (View) parent.getParent(); 
				                    parent = (View) parent.getParent(); 
				                    parent = (View) parent.getParent(); 

				                    holder = (HolderAnonce) parent.getTag();
//Toast.makeText(getActivity(), "-------"+grpBr.get(holder.position).getTitreArticle(), Toast.LENGTH_SHORT).show();
 
ActivityCommentaire(grpBr.get(holder.position).getImageProfil()
			 ,grpBr.get(holder.position).getTitreArticle()
     ,grpBr.get(holder.position).getNomPublieur() 
     ,grpBr.get(holder.position).getId_nomPublieur()
     ,grpBr.get(holder.position).getDate(),
      grpBr.get(holder.position).getImagePublier());
    
			                    break; 	
			                    
				             case R.id.iconComment: 
				                    parent = (View) v.getParent();  
				                    parent = (View) parent.getParent(); 
				                    parent = (View) parent.getParent(); 
				                    parent = (View) parent.getParent(); 
   
				                    holder = (HolderAnonce) parent.getTag();
//Toast.makeText(getActivity(), "-------"+grpBr.get(holder.position).getTitreArticle(), Toast.LENGTH_SHORT).show();


				                    ActivityCommentaire(grpBr.get(holder.position).getImageProfil()
			 ,grpBr.get(holder.position).getTitreArticle()
        ,grpBr.get(holder.position).getNomPublieur() 
        ,grpBr.get(holder.position).getId_nomPublieur()
        ,grpBr.get(holder.position).getDate(),
         grpBr.get(holder.position).getImagePublier());
			                    break;	
 			                   
					 
			             } 
				            }
		    	};
		    return event;
		}
 	
		
		private void ActivityCommentaire (
				String nameImg,
				String titreVue,
				String nameProfil,
				String idPublication,
				String date,
				String imgPublication)
		{
	    	/*Intent intent = new Intent(getActivity(), LireComment.class);
	    	intent.putExtra("nameImage",nameImg);
	    	intent.putExtra("titre",titreVue); 
	    	intent.putExtra("datePublication",date);
	    	intent.putExtra("NomPublieur",nameProfil);
	    	intent.putExtra("ImgPublieur",imgPublication); 
	    	intent.putExtra("idPublication",idPublication); 
	    	intent.putExtra("idUtilisateur",idUtilisateur);
		    startActivity(intent); */
		}
 
			private void Share (String texteShare){ 
				final Intent MessIntent = new Intent(Intent.ACTION_SEND);
		        	MessIntent.setType("text/plain");
		        	MessIntent.putExtra(Intent.EXTRA_TEXT,texteShare);
		        	ListeAnnonceFragement_old.this.startActivity(Intent.createChooser(MessIntent, "Partager avec..."));
		        }
			 		 
	

 

		public void DetailDialog(final String infos)
		{
 
		}
		
 
		 public boolean onCreateOptionsMenu(Menu menu) {
			    MenuInflater inflater = getActivity().getMenuInflater();
			    inflater.inflate(R.menu.menu_invited, menu);

			    return super.getActivity().onCreateOptionsMenu(menu);
			}

public boolean onOptionsItemSelected(MenuItem item) {
	Intent intent;
	switch (item.getItemId()) {

  /*case R.id.edit_item: 
	 
  	intent = new Intent(getActivity(), Publier_epeople.class);
  	intent.putExtra("codeVue","1");
  	intent.putExtra("idUtilisateur",idUtilisateur);
	    startActivity(intent);
	    
	    //getSherlockActivity().finish();
      return true;*/
     
 
  default:
      break;
  }

  return false;
}
  


	//gestin de lq notificqtion

private final void createNotification(String onglet,String msgEmis)
{ 
   //R�cup�ration du notification Manager 
   final NotificationManager notificationManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE); 

   //Cr�ation de la notification avec sp�cification de l'ic�ne de la notification et le texte qui apparait � la cr�ation de la notification 
   final Notification notification = new Notification(R.drawable.coranker,msgEmis, System.currentTimeMillis()); 
   
   Intent intent=new Intent(getActivity(), MainActivity.class);  
	intent.putExtra("idUtilisateur",idUtilisateur);
	
	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	//Intent.FLAG_ACTIVITY_CLEAR_TOP
   //D�finition de la redirection au moment du clic sur la notification. Dans notre cas la notification redirige vers notre application 
   final PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0); 
   //System.exit(0);
   //Notification & Vibration 
   //notification.setLatestEventInfo(getActivity(), msgEmis, "E-People", pendingIntent);
   //Pour ce qui est de la notification � l�aide d�une sonnerie :
   notification.defaults = Notification.DEFAULT_SOUND;
   
    //Et pour finir les leds :
  	notification.defaults |= Notification.DEFAULT_LIGHTS;
//
   notification.flags |= Notification.FLAG_AUTO_CANCEL;
   notification.vibrate = new long[] {0,200,100,200,100,200};
   notification.defaults |= Notification.DEFAULT_VIBRATE;
   notification.flags |= Notification.FLAG_SHOW_LIGHTS;
   int NOTIFICATION_ID = 0;
	notificationManager.notify(NOTIFICATION_ID, notification); 
	
}

private void deleteNotification(){
	final NotificationManager notificationManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
	int NOTIFICATION_ID = 0;
	//la suppression de la notification se fait gr�ce a son ID
	notificationManager.cancel(NOTIFICATION_ID);
}

@Override
public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (isVisibleToUser ) {
    //   loadLectures(); 
    //  _areLecturesLoaded = true;
    }
}
public void onBackPressed() {
 	// Cr�ation d'une popup affichant un message
 AlertDialog ad = new AlertDialog.Builder(getActivity()).setNegativeButton("NON", new AlertDialog.OnClickListener(){

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		arg0.dismiss();	
	}})
.setPositiveButton("OUI", new AlertDialog.OnClickListener(){

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(), ProductActivite_old.class);
		 startActivity(intent);
		 getActivity().finish();	
	}}).setTitle("")
.setMessage("�tes-vous sur de vouloir retourner a l'acceuil ?").create();
 		ad.show();
}

}
