package com.therankit.services;




import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask; 


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup; 

import android.widget.ImageView;
import android.widget.ListView; 
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.reponse.Reponse;
import com.sqlLite.SecteurTable;
import com.therankit.community.LireComment;
import com.therankit.home.MainActivity;
import com.therankit.rankitlite.R;
import com.url.AppController; 
import com.volley.Const; 
import com.volley.EncodeDecode;
import com.volley.JsonUTF8Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import chechPakage.Holder;
import chechPakage.ListViewAdapter2;
import chechPakage.MarginDecoration;

public class ListeAnnonceFragement extends Fragment{
	private String TAG = ListeAnnonceFragement.class.getSimpleName();
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
  
	private ProgressDialog pDialog;
	private String envoye;

	private int nbreCmdeEncours=0;
	private int nbreCmdeNew=0;
	   private AnonceAdapter2 adapter3;
	String AllImage="";
	 String AllTitre="";
	 String AllDescrip="";
	 String AllDate="";
	 String AllNews="";
  
private  ArrayList<AnonceRow>  grpBr = new ArrayList<AnonceRow>(); 

private int incrConnexion=1;

List<String> dateAnonce= new ArrayList<String>() ;
List<String> titreAnonces= new ArrayList<String>() ;
List<String> nbreCommentAnnonces= new ArrayList<String>() ;
List<String> imageAnonces= new ArrayList<String>() ;
List<Integer> idAnonces= new ArrayList<Integer>() ;
List<Integer> ListeNbreJaimePas= new ArrayList<Integer>() ;
List<Integer> ListeNbreJaime= new ArrayList<Integer>() ;
List<Integer> ListeNbreReaction= new ArrayList<Integer>() ;
List<Boolean> ListeDejaVote= new ArrayList<Boolean>() ;
List<Boolean> ListeDejaReagiEtude= new ArrayList<Boolean>() ;
List<Boolean> ListeDejaEtudeExiste= new ArrayList<Boolean>() ;
	private List<Integer> ListeidSecteur= new ArrayList<Integer>() ;
	private List<String> ListeSecteur= new ArrayList<String>() ;
	private List<Integer> ListeidReponseEtude= new ArrayList<Integer>() ;
	private List<String> ListeReponseEtude= new ArrayList<String>() ;

	List<String> idAnnonceur= new ArrayList<String>() ;
List<String> photosAnnonceur= new ArrayList<String>() ;	
List<String> NomAnnonceur= new ArrayList<String>() ;
List<String> prenomAnnonceur= new ArrayList<String>() ;
List<String> descriptionAnnonceur= new ArrayList<String>() ;
	 	

		private NumberFormat numberFormatter;
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
private FloatingActionButton fab;
	private SecteurTable secteurBdd;
	private JSONArray jsonArrayMenuServices;
	private SharedPreferences settings;
	private RecyclerView recyclerView;
	private Toolbar toolbar;
	private TextView	textToolHeader;
	private int id_anonce_courant=0;
	@SuppressLint("NewApi")
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.activity_epeople2, container, false);

        numberFormatter = NumberFormat.getNumberInstance();
		recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
		LinearLayoutManager llm = new LinearLayoutManager(getActivity());
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		settings =  getActivity().getSharedPreferences(Const.PREFRENCES_NAME, Context.MODE_PRIVATE);
		secteurBdd = new SecteurTable( getActivity().getBaseContext());
		toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		textToolHeader = (TextView) toolbar.findViewById(R.id.toolbar_title);
		recyclerView.setLayoutManager(llm);
		recyclerView.setHasFixedSize(true);

		jsonObjSend = new JSONObject();


		Bundle extras= getActivity().getIntent().getExtras();
	//	Bundle bundle=this.getArguments();
		if(extras != null)
		{
			 idUtilisateur=extras.getInt("randkiteUser_id")+"";
			// telephoneUtilisateur=extras.getString("telephoneUtilisateur");
			// imgUtilisateur=extras.getString("imgUtilisateur");
		}
	//	createDialog("", idUtilisateur+"");
		 	
        //Toast.makeText(getActivity(), ""+idUtilisateur, Toast.LENGTH_LONG).show();
		pDialog = new ProgressDialog(getActivity());       
		pDialog.setMessage(getString(R.string.st_chargement)); 	
		 // showProgressDialog();
		secteurBdd.open();

		try {
			jsonArrayMenuServices=secteurBdd.getListeSecteur(settings.getString("langue", "en"),Const.TYPE_SERVICE);
			if(jsonArrayMenuServices.length()>=0)
			{

				for (int i = 0; i < jsonArrayMenuServices.length(); i++)
				{

					JSONObject feedObj = (JSONObject) jsonArrayMenuServices.get(i);
					ListeidSecteur.add(feedObj.getInt(SecteurTable.COLUMN_ID_SECTEUR));
					ListeSecteur.add(feedObj.getString(SecteurTable.COLUMN_SECTEUR_NAME));


				}

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		secteurBdd.close();
		fab=(FloatingActionButton) view.findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {


			public void onClick(View view) {




				new MaterialDialog.Builder( getActivity())
						.title(R.string.info_sector2)
						.items(ListeSecteur)
						.itemsCallback(new MaterialDialog.ListCallback() {
							@Override
							public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
								textToolHeader.setText(ListeSecteur.get(which));
								try {
									showProgressDialog();
									envoye="affichage";
									jsonObjSend.put(Const.TAG_IDEND,idEnd);
									jsonObjSend.put("type_anonce","SERV");
									jsonObjSend.put("randkiteUser_id",Integer.parseInt(idUtilisateur));
									jsonObjSend.put("secteur_id",ListeidSecteur.get(which));
									makeJsonObjReq(jsonObjSend,Const.urlGestionAnonce+"listeAnonce");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						})
						.positiveText(android.R.string.cancel)
						.show();

			}
		});
		  setHasOptionsMenu(true);
		 requestCread();
		 
	
		return view;
	}
	
 

	private void requestCread()
	{	
		
		try {
			envoye="affichage";
			jsonObjSend.put(Const.TAG_IDEND,idEnd);
			jsonObjSend.put("type_anonce","SERV");
			jsonObjSend.put("randkiteUser_id",Integer.parseInt(idUtilisateur));
			jsonObjSend.put("secteur_id",0);
			makeJsonObjReq(jsonObjSend,Const.urlGestionAnonce+"listeAnonce");
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
							if(envoye.equals("affichage")) {
								try {
									try {
										feedArray = response.getJSONArray("resultat");
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									if (feedArray.length() != 0) {
										dateAnonce.clear();
										titreAnonces.clear();
										nbreCommentAnnonces.clear();
										imageAnonces.clear();
										idAnonces.clear();
										ListeNbreJaimePas.clear();
										ListeNbreJaime.clear();
										descriptionAnnonceur.clear();
										idAnnonceur.clear();
										photosAnnonceur.clear();
										NomAnnonceur.clear();
										prenomAnnonceur.clear();
										ListeNbreReaction.clear();
										ListeDejaVote.clear();
										ListeDejaReagiEtude.clear();
										ListeDejaEtudeExiste.clear();

										parseJsonFeed(response);
									} else {
										//Toast.makeText(getActivity(),R.string.info_resultat, Toast.LENGTH_LONG).show();
										createDialog("", getActivity().getApplicationContext().getString(R.string.info_resultat));
										hideProgressDialog();
									}


								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							else if(envoye.equals("vote_anonce"))
							{
								try {
								int nbre_courant=0;
								if(response.getBoolean("resutat_vote"))
								{
									nbre_courant=grpBr.get(id_anonce_courant).getNbreJaime()+1;
									grpBr.get(id_anonce_courant).setNbreJaime(nbre_courant);
								}
									else
								{
									nbre_courant=grpBr.get(id_anonce_courant).getNbreJaimePas()+1;
									grpBr.get(id_anonce_courant).setNbreJaimePas(nbre_courant);
								}

								grpBr.get(id_anonce_courant).setVoteAime(true);

								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								new MaterialDialog.Builder(getActivity())
										.content(getActivity().getString(R.string.msg_after_vote))
										.positiveText("OK")
										//.negativeText(R.string.disagree)

										.show();
								adapter3.notifyDataSetChanged();
							}
							else if(envoye.equals("etude_marche"))
							{
								ListeidReponseEtude.clear();
								ListeReponseEtude.clear();
								try
								{
									if(response.getBoolean("statut"))
									{

										JSONArray feedArray = response.getJSONArray("resultat");
										if(feedArray.length()>0) {
											for (int i = 0; i < feedArray.length(); i++) {
												JSONObject feedObj = (JSONObject) feedArray.get(i);


												ListeidReponseEtude.add(feedObj.getInt("reponse_id"));
												ListeReponseEtude.add(feedObj.getString("reponse"));
												//int id_etude = response.getInt("id_libelleEtude");
											}
										}
										new MaterialDialog.Builder(getActivity())
												.title(response.getString("libelleEtude"))
												.content(R.string.mtn_description)
												.items(ListeReponseEtude)
												.itemsCallbackSingleChoice(2, new MaterialDialog.ListCallbackSingleChoice() {
													@Override
													public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
													//	Toast.makeText(getActivity(),ListeReponseEtude.get(which), Toast.LENGTH_SHORT).show();
                                                      try
													  {
														showProgressDialog();
														envoye="vote_etude_marche";

														jsonObjSend.put("id_reponse",ListeidReponseEtude.get(which));
														jsonObjSend.put("randkiteUser_id",Integer.parseInt(idUtilisateur));
														makeJsonObjReq(jsonObjSend,Const.urlGestionAnonce+"PutChoixEtude");

													} catch (JSONException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
														return true; // allow selection
													}
												})
												.positiveText(R.string.md_choose_label)
												.show();
									}
									else
									{
										new MaterialDialog.Builder(getActivity())
												.content(getActivity().getString(R.string.info_etude))
												.positiveText("OK")
												//.negativeText(R.string.disagree)

												.show();
									}

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}


							}
							else if(envoye.equals("vote_etude_marche"))
							{
								int nbre_courant=0;

									nbre_courant=grpBr.get(id_anonce_courant).getNbreReaction()+1;
									grpBr.get(id_anonce_courant).setNbreReaction(nbre_courant);
								grpBr.get(id_anonce_courant).setVoteReaction(true);
								    adapter3.notifyDataSetChanged();
								new MaterialDialog.Builder(getActivity())
										.content(getActivity().getString(R.string.msg_after_vote))
										.positiveText("OK")
										//.negativeText(R.string.disagree)

										.show();
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

			for (int i = 0; i < feedArray.length(); i++) 
			{
				JSONObject feedObj = (JSONObject) feedArray.get(i);


					 dateAnonce.add(""+feedObj.getString("anonce_date"));
					 ListeNbreJaimePas.add(feedObj.getInt("anonce_nbreAimePas"));
				     ListeNbreJaime.add(feedObj.getInt("anonce_nbreAime"));
					 title=feedObj.getString("anonce_titre");
					 idpublieur=""+feedObj.getInt("entreprise_id");
				      idAnnonceur.add(""+feedObj.getInt("entreprise_id"));
							NomAnnonceur.add(feedObj.getString("entreprise_name"));
							prenomAnnonceur.add("");
							photosAnnonceur.add(feedObj.getString("entreprise_logo"));
							imageAnonces.add(feedObj.getString("anonce_photo"));
							titreAnonces.add(feedObj.getString("anonce_descp"));
				            descriptionAnnonceur.add(feedObj.getString("anonce_descp"));
				ListeNbreReaction.add(feedObj.getInt("anonce_nbreReact"));
				ListeDejaVote.add(feedObj.getBoolean("deja_vote"));
				ListeDejaReagiEtude.add(feedObj.getBoolean("deja_reagi_etude"));
				ListeDejaEtudeExiste.add(feedObj.getBoolean("anonceEtudeExite"));
					if(statut.equals("deja")&&(i==0))
					{
					idEnd=feedObj.getInt("anonce_id")+"";
					}

				idAnonces.add(feedObj.getInt("anonce_id"));
					 nbreCommentAnnonces.add(""+feedObj.getInt("anonce_nbreComt"));
					nbreCmdeNew++;
					nbreElementGlobal++;


			 
			 }

			//createDialog("", feedArray.toString());

			affiche_annonces();
			hideProgressDialog() ;

						 signal=true; 	
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	

	 private ArrayList<AnonceRow> affiche_news(ArrayList<AnonceRow> grpBr) { 

	    	    for(int i=0;i<dateAnonce.size();i++)
				 { 	  	if(i==0)
						{
					 idEnd=""+idAnonces.get(i);
						}
 
				 AnonceRow br2 =new AnonceRow(i,false,NomAnnonceur.get(i)+" "+prenomAnnonceur.get(i),dateAnonce.get(i),
						 descriptionAnnonceur.get(i), nbreCommentAnnonces.get(i),photosAnnonceur.get(i),imageAnonces.get(i),
				        		  titreAnonces.get(i),idAnnonceur.get(i),ListeNbreJaimePas.get(i),ListeNbreJaime.get(i),ListeNbreReaction.get(i),idAnonces.get(i),ListeDejaVote.get(i),ListeDejaReagiEtude.get(i),ListeDejaEtudeExiste.get(i));
				          grpBr.add(br2);
				          //Toast.makeText(getActivity(), "----"+i, Toast.LENGTH_SHORT).show();
				 } 

				 nbreElementGlobal=0;	


	       
	        return grpBr;
  		
} 

		private void affiche_annonces() throws UnsupportedEncodingException {
		grpBr = new ArrayList<AnonceRow>(); 
			grpBr=affiche_news(grpBr); 

			adapter3 = new AnonceAdapter2(getActivity(), grpBr);
			recyclerView.setAdapter(adapter3);
			adapter3.notifyDataSetChanged();

			((AnonceAdapter2) adapter3).setOnItemClickListener(new AnonceAdapter2
					.MyClickListener() {
				@Override
				public void onItemClick(int position, View v) {
					Log.i(TAG, " Clicked on Item " + position);
					Holder holder;
					Intent intent;
					int nbre_courant=0;
					switch (v.getId())

					{


						case R.id.profilePic:
							intent = new Intent(getActivity(), detail_entreprise_service.class);
							intent.putExtra("id_entreprise",0);
							intent.putExtra("logo",grpBr.get(position).getImageProfil());
							intent.putExtra("idUtilisateur",idUtilisateur);
							startActivity(intent);
							break;
						case R.id.name:
							intent = new Intent(getActivity(), detail_entreprise_service.class);
							intent.putExtra("id_entreprise",0);
							intent.putExtra("logo",grpBr.get(position).getImageProfil());
							intent.putExtra("idUtilisateur",idUtilisateur);
							startActivity(intent);
							break;

						case R.id.barr_vote:
							PopupMenu popup = new PopupMenu(getActivity(), v);
							MenuInflater inflater = popup.getMenuInflater();
							inflater.inflate(R.menu.menu_album, popup.getMenu());
							popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
							popup.show();
							break;

						case R.id.iconJaime:
if(!grpBr.get(position).getVoteAime())
{


	id_anonce_courant=position;
	try {
		showProgressDialog();
			envoye="vote_anonce";

			jsonObjSend.put("anonce_id",grpBr.get(position).getIdAnonce());
			jsonObjSend.put("randkiteUser_id",Integer.parseInt(idUtilisateur));
			jsonObjSend.put("resutat_vote",true);
			makeJsonObjReq(jsonObjSend,Const.urlGestionAnonce+"Putanonce");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}

}
							else {
	new MaterialDialog.Builder(getActivity())
			.content(getActivity().getString(R.string.vote_avis))
			.positiveText("OK")
			//.negativeText(R.string.disagree)

			.show();
}

							//grpBr.get(position).set;
							break;

						case R.id.iconJaime_pas:
							if(!grpBr.get(position).getVoteAime()) {

								id_anonce_courant=position;
								try {
									showProgressDialog();
									envoye="vote_anonce";

									jsonObjSend.put("anonce_id",grpBr.get(position).getIdAnonce());
									jsonObjSend.put("randkiteUser_id",Integer.parseInt(idUtilisateur));
									jsonObjSend.put("resutat_vote",false);
									makeJsonObjReq(jsonObjSend,Const.urlGestionAnonce+"Putanonce");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							else
							{
								new MaterialDialog.Builder(getActivity())
										.content(getActivity().getString(R.string.vote_avis))
										.positiveText("OK")
										//.negativeText(R.string.disagree)

										.show();
							}
							break;

						case R.id.nbre_comment:

							ActivityCommentaire(grpBr.get(position).getImageProfil()
									,grpBr.get(position).getTitreArticle()
									,grpBr.get(position).getNomPublieur()
									,grpBr.get(position).getId_nomPublieur()
									,grpBr.get(position).getDate(),
									grpBr.get(position).getImagePublier()
									,grpBr.get(position).getIdAnonce());

							break;

						case R.id.iconComment:

							ActivityCommentaire(grpBr.get(position).getImageProfil()
									,grpBr.get(position).getTitreArticle()
									,grpBr.get(position).getNomPublieur()
									,grpBr.get(position).getId_nomPublieur()
									,grpBr.get(position).getDate(),
									grpBr.get(position).getImagePublier(),
									grpBr.get(position).getIdAnonce());
							break;


						case R.id.market_search:
							if(!grpBr.get(position).getVoteReaction()) {

								id_anonce_courant = position;
								try {
									showProgressDialog();
									envoye = "etude_marche";

									jsonObjSend.put("id_anonce", grpBr.get(position).getIdAnonce());

									makeJsonObjReq(jsonObjSend, Const.urlGestionAnonce + "listeEtudeAnonce");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							else
							{
								new MaterialDialog.Builder(getActivity())
										.content(getActivity().getString(R.string.vote_avis))
										.positiveText("OK")
										//.negativeText(R.string.disagree)

										.show();
							}
							break;

					}

					adapter3.notifyDataSetChanged();


				}
			});


			
			}
		 

		
		private void ActivityCommentaire (
				String nameImg,
				String titreVue,
				String nameProfil,
				String idpublieur,
				String date,
				String imgPublication,
		          int idAnonce)
		{
	    	Intent intent = new Intent(getActivity(), LireComment.class);
	    	intent.putExtra("nameImage",nameImg);
	    	intent.putExtra("titre",titreVue); 
	    	intent.putExtra("datePublication",date);
	    	intent.putExtra("NomPublieur",nameProfil);
	    	intent.putExtra("ImgPublieur",imgPublication); 
	    	intent.putExtra("idpublieur",idpublieur);
			intent.putExtra("idAnonce",idAnonce);
			intent.putExtra("idUtilisateur",idUtilisateur);
		    startActivity(intent);

		}

	private void Share (String Description,String urlImage){

		Intent shareIntent = new Intent();
		URI uri=null;
		try {
		 uri = new URI("http://www.therankit.com/entreprises/upload_annonce/MoMo_fr.png");
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_TEXT, Description);
	    shareIntent.putExtra(Intent.EXTRA_STREAM,uri);  //optional//use this when you want to send an image
		shareIntent.setType("image/jpeg");
		shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		startActivity(Intent.createChooser(shareIntent, "Partager avec..."));

	}

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



	public void createDialog(String title, String text)
	{
		// Cr�ation d'une popup affichant un message
		AlertDialog ad = new Builder(getActivity())
				.setPositiveButton("Ok", null).setTitle(title).setMessage(text)
				.create();
		ad.show();

	}

		public void DetailDialog(final String infos)
		{
 
		}
		
 
		/* public boolean onCreateOptionsMenu(Menu menu) {
			    android.view.MenuInflater inflater =  getActivity().getMenuInflater();
			    inflater.inflate(R.menu.menu_invited, (android.view.Menu) menu);

			    return super.getMenuInflater.onCreateOptionsMenu(menu);
			}*/

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
		Intent intent = new Intent(getActivity(), Home.class);
		 startActivity(intent);
		 getActivity().finish();	
	}}).setTitle("")
.setMessage("êtes-vous sur de vouloir retourner a l'acceuil ?").create();
 		ad.show();
}


	/**
	 * Click listener for popup menu items
	 */
	class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
		private int id;
		public MyMenuItemClickListener(int id_c) {
			this.id=id_c;
		}

		@Override
		public boolean onMenuItemClick(MenuItem menuItem) {
			switch (menuItem.getItemId()) {
				case R.id.action_add_favourite:
					Toast.makeText(getActivity(), getActivity().getApplicationContext().getString(R.string.add_fav), Toast.LENGTH_SHORT).show();
					return true;
				case R.id.action_play_next:
				//	Toast.makeText(getActivity(), "Partarger", Toast.LENGTH_SHORT).show();
					Share(grpBr.get(id).getDescription(),"");
				default:
			}
			return false;
		}
	}
}
