package com.therankit.community;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.therankit.rankitlite.R;
import com.volley.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimerTask;


public class RequireFragement extends Fragment implements OnItemClickListener {
	private String TAG = RequireFragement.class.getSimpleName();
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
  
	private ProgressDialog pDialog;
	   
	private  OnClickListener onClick;
  
	String AllImage="";
	 String AllTitre="";
	 String AllDescrip="";
	 String AllDate="";
	 String AllNews="";
   

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
		boolean[] statue;	
		

		List<String> idAnnonceur= new ArrayList<String>() ;
		List<String> photosAnnonceur= new ArrayList<String>() ;	
		List<String> NomAnnonceur= new ArrayList<String>() ;
		List<String> prenomAnnonceur= new ArrayList<String>() ;	
		
 
	private ListView mListView;
	private String id_end="0"; 
	TimerTask task_2; 
	@SuppressWarnings("unused")
	private boolean signal;
	private TextView textload;
	private String idUtilisateur;
	private String telephoneUtilisateur;
	private String imgUtilisateur;

	private JSONObject jsonObjSend;
	private List<ContactBean> list0 = new ArrayList<ContactBean>(); 
	private boolean existed;

	private int indice=0 ;
	private String name ;
	private String phoneNumberContact ;
	private ContactBean objContact0 ;

	private JSONObject contact;
	private JSONArray listeContact = new JSONArray();
	private JSONObject contactItem; 
	private LinearLayout linearLayout;
	private View view;
    //Cr�ation d'une instance de ma classe LivresBDD
	private ProgressBar progressBar;
	private String device_key;
	private Toolbar toolbar;
	private TextView	textToolHeader;
	
	@SuppressLint("NewApi")
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.reclamation, container, false);
		setHasOptionsMenu(true);
		
		
		 
		   		 
		   		 

		  SharedPreferences 
    	settings =getActivity().getSharedPreferences(Const.PREFRENCES_NAME, Context.MODE_PRIVATE);
		Bundle extras=getActivity().getIntent().getExtras(); 
		
		//getSherlockActivity().getSupportActionBar().setIcon(R.drawable.ic_maps_directions_walk_blanc);
		//getSherlockActivity().getSupportActionBar().setTitle("Community");


        progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
		toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		textToolHeader = (TextView) toolbar.findViewById(R.id.toolbar_title);
		textToolHeader.setText("RANKIT LITE");

		jsonObjSend = new JSONObject();   


	 	 		

	  
		return view;
	}
	
 public void contact() throws JSONException
 {
	 	int k=0; 

        boolean tel=false; 
        boolean verifContact=false; 

		String telephone=""; 
		String photo="";
		int Idcontact=0;
		int inc=0;
		list0.clear();

	 for(int i=0;i<4;i++)
	 {
		 objContact0 = new ContactBean();
		 objContact0.setName(""+i);
		 objContact0.setId(""+i);
		 objContact0.setStatut(false);
		 objContact0.setPhotos("");

		 if(i==0)
		 {
			 objContact0.setPhoneNo(getString(R.string.dr1));

		 }
		 else if(i==1)
		 {
			 objContact0.setPhoneNo(getString(R.string.dr2));

		 }

		 else if(i==2)
		 {
			 objContact0.setPhoneNo(getString(R.string.dr3));

		 }
		 else if(i==3)
		 {
			 objContact0.setPhoneNo(getString(R.string.dr4));

		 }
		 list0.add(objContact0);
	 }


					if (null != list0 && list0.size() != 0) {
						Collections.sort(list0, new Comparator<ContactBean>() {
							@Override
							public int compare(ContactBean lhs, ContactBean rhs) {
								return lhs.getName().compareTo(rhs.getName());
							}
						});		
						// taille des conatact==list.size() 
						} else {
						showToast("Pas de contact trouv�!!!");
					}
				    mListView.setVisibility(view.VISIBLE);

				    
					ContanctAdapter objAdapter = new ContanctAdapter(getActivity(), R.layout.list_infos, list0);
					mListView.setAdapter(objAdapter);
			 

 }
	public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {

		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_info, menu);
		//getSupportMenuInflater().inflate(R.menu.menu,  menu);





	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

			case R.id.action_icon_info:

				new MaterialDialog.Builder(getActivity())
						.title("Information")
						.content(getString(R.string.info_publication_plainte))
						.positiveText("OK")

						.show();


		}
		return true;
	}



	private void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}



	public void onItemClick(AdapterView<?> listview, View v, int position,
			long id) {
		ContactBean bean = (ContactBean) listview.getItemAtPosition(position);
		//showCallDialog(bean.getName(),bean.getPhotos(),bean.getId(), bean.getPhoneNo());
	}
	

	@SuppressWarnings("deprecation")
	private void showCallDialog(final String name,String photos,final String id, final String phoneNo) { 
	 	/*
	    */
if(!id.equals("0")){

	
}
else
{
		AlertDialog alert = new AlertDialog.Builder(getActivity())
				.create();
		alert.setTitle(" Appeller?");

		alert.setMessage("Voulez-vous joindre " +name+" ?");

		alert.setButton("non", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		alert.setButton2("oui", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			 
				String phoneNumber = "tel:" + phoneNo;
				Intent intent = new Intent(Intent.ACTION_CALL, Uri
						.parse(phoneNumber));
				startActivity(intent);
				 
		 
			}
		});
		alert.show();
}
	}

 	 
		
 

  	 
 

 
	
 
	private void Share (String date){
//Toast.makeText(getActivity(), "Partager" +date, Toast.LENGTH_SHORT).show();
		final Intent MessIntent = new Intent(Intent.ACTION_SEND);
        	MessIntent.setType("text/plain");
        	MessIntent.putExtra(Intent.EXTRA_TEXT,date);
        	RequireFragement.this.startActivity(Intent.createChooser(MessIntent, "Inviter avec..."));
        }
	
	 
	 public void createDialog(String title, String text)
	 {
	 	// Cr�ation d'une popup affichant un message
	 	AlertDialog ad = new AlertDialog.Builder(getActivity())
	 			.setPositiveButton("Ok", null).setTitle(title).setMessage(text)
	 			.create();
	 	ad.show();

	 }
	
 	
}
