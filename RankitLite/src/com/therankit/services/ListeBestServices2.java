package com.therankit.services;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
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
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.sqlLite.LangueTable;
import com.sqlLite.SecteurTable;
import com.therankit.community.ContactBean;
import com.therankit.community.ContanctAdapter;
import com.therankit.home.MainActivity;
import com.therankit.rankitlite.R;
import com.url.AppController;
import com.volley.Const;
import com.volley.JsonUTF8Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimerTask;


public class ListeBestServices2 extends Fragment implements OnItemClickListener {
	private String TAG = ListeBestServices2.class.getSimpleName();
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
	private ProgressDialog pDialog;
	private  OnClickListener onClick;
	private View view;
	private ProgressBar progressBar;
	private List<BestServicesBean2> bestServicesBean2 = new ArrayList<BestServicesBean2>();
	private ListView listView;
	private BestServicesAdapter2 adapter;
	private Cache cache;
	private Cache.Entry entry;
	private JSONArray feedArray;
	private String data;
	private JSONObject jsonObjSend;
	private Resources res;
	private Configuration conf;
	private SharedPreferences settings;
	private JSONArray jsonArrayMenuServices;
	private SecteurTable secteurBdd;
	private LangueTable langueBdd;
	private FloatingActionButton fab;
	private List<Integer> ListeidSecteur= new ArrayList<Integer>() ;
	private List<String> ListeSecteur= new ArrayList<String>() ;
	private Toolbar toolbar;
	private TextView	textToolHeader;
	@SuppressLint("NewApi")
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.liste_des_contact, container, false);
		setHasOptionsMenu(true);
		settings =  getActivity().getSharedPreferences(Const.PREFRENCES_NAME, Context.MODE_PRIVATE);
		langueBdd= new LangueTable( getActivity().getBaseContext());
		secteurBdd = new SecteurTable( getActivity().getBaseContext());
		pDialog = new ProgressDialog(getActivity());
		pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pDialog.setMessage(""+getString(R.string.st_chargement));
		pDialog.setCancelable(false);
		toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		textToolHeader = (TextView) toolbar.findViewById(R.id.toolbar_title);
		listView = (ListView) view.findViewById(R.id.listannonces);
		adapter = new BestServicesAdapter2(getActivity(), bestServicesBean2);
		listView.setAdapter(adapter);
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
		setHasOptionsMenu(true);
		jsonObjSend = new JSONObject();
		fab=(FloatingActionButton) view.findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {


			public void onClick(View view) {




				new MaterialDialog.Builder( getActivity())
						.title(getActivity().getApplicationContext().getString(R.string.info_sector))
						.items(ListeSecteur)
						.itemsCallback(new MaterialDialog.ListCallback() {
							@Override
							public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
								textToolHeader.setText(ListeSecteur.get(which));
								FineLastPublication(ListeidSecteur.get(which));
							}
						})
						.positiveText(android.R.string.cancel)
						.show();

			}
		});
		FineLastPublication(0);
		return view;
	}

	private void FineLastPublication(int id_secteur)
	{
		pDialog.show();
		try
		{  	jsonObjSend.put("id_entreprise",0);
			jsonObjSend.put("id_secteur",id_secteur);

			//createDialog("infos00",Const.URL_JSON_AFFICHAGE_E_MARKET+""+jsonObjSend);
			makeJsonObjReq(jsonObjSend,Const.urlNote+"listeEntreprise");
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}


	private void makeJsonObjReq(JSONObject json,String url) {


		JsonUTF8Request jsonObjReq = new JsonUTF8Request(Request.Method.POST,url, json,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, response.toString());
						VolleyLog.d(TAG, "Response: " + response.toString());
						if (response != null) {
							try {
								feedArray = response.getJSONArray("resultat");

								//createDialog("ServerData", ""+feedArray.toString());
								cache = AppController.getInstance().getRequestQueue().getCache();
								entry = cache.get("");
								if (entry != null) {
									data = new String(entry.data, "UTF-8");
								}
								//JSONObject json=new JSONObject(data);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(feedArray.length()!=0)
							{

								bestServicesBean2.clear();
								for (int i = 0; i < feedArray.length(); i++)
								{
									try {
										JSONObject feedObj = (JSONObject) feedArray.get(i);
										BestServicesBean2 movie = new BestServicesBean2();
										movie.setTitle(feedObj.getString("entreprise_name"));
										movie.setThumbnailUrl(feedObj.getString("entreprise_logo"));
										movie.setRating(((Number) i)
												.doubleValue());
										movie.setYear(i+1);


										ArrayList<String> genre = new ArrayList<String>();
										genre.add(feedObj.getString("entreprise_name"));
										genre.add(feedObj.getString("entreprise_name"));
										// Genre is json array
										/*JSONArray genreArry = obj.getJSONArray("genre");
										for (int j = 0; j < genreArry.length(); j++) {
											genre.add((String) genreArry.get(j));
										}*/
										movie.setGenre(genre);

										// adding movie to movies array
										bestServicesBean2.add(movie);

									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}

								adapter.notifyDataSetChanged();
								// hideProgressDialog();
							}
							else
							{
								//createDialog("Infos",getActivity().getApplicationContext().getString(R.string.info_resultat));

							}
							pDialog.dismiss();
						}
					}
				}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "Error: " + error.getMessage());

				Toast.makeText(getActivity(),"Error", Toast.LENGTH_LONG).show();

				pDialog.dismiss();
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
		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq,tag_json_obj);

	}
 
 private void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}



	public void onItemClick(AdapterView<?> listview, View v, int position,
			long id) {
		ContactBean bean = (ContactBean) listview.getItemAtPosition(position);
		//showCallDialog(bean.getName(),bean.getPhotos(),bean.getId(), bean.getPhoneNo());
	}











	public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {

		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_services, menu);
		//getSupportMenuInflater().inflate(R.menu.menu,  menu);





		SubMenu search= ((Menu) menu).addSubMenu(0, 0, 0, this.getResources().getString(R.string.Search));
		try {
			res = getResources();
			conf = res.getConfiguration();



			if(jsonArrayMenuServices.length()>=0)
			{
				String idString="555";
				for (int i = 0; i < jsonArrayMenuServices.length(); i++)
				{

					JSONObject feedObj = (JSONObject) jsonArrayMenuServices.get(i);
					idString="555";
					idString=idString+feedObj.getInt(SecteurTable.COLUMN_ID_SECTEUR);
					search.add(0, Integer.parseInt(idString), 0,feedObj.getString(SecteurTable.COLUMN_SECTEUR_NAME));

				}

			}
			search.add(0, 1005, 0,this.getResources().getString(R.string.Services_par_autre));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		SubMenu search2= ((Menu) menu).addSubMenu(0, 0, 0, this.getResources().getString(R.string.Menu));
		search2.add(0, 1006, 0, this.getResources().getString(R.string.ServicesFavorite));
		search2.add(0, 1007, 0,this.getResources().getString(R.string.ServicesNew));
		search2.add(0, 1008, 0,this.getResources().getString(R.string.ServicesSugestion));
		search2.add(0, 1009, 0,this.getResources().getString(R.string.ServicesAll));

		// Locale.ENGLISH.toString());
		SubMenu m = ((Menu) menu).addSubMenu(0, 0, 0, this.getResources().getString(R.string.lng));
		m.add(0, 1001, 0, "English");
		m.add(0, 1002, 0, "Français");
		@SuppressWarnings("unused")
		SubMenu l = ((Menu) menu).addSubMenu(0, 1003, 0, "Application");
		//return true;
		//return super.onCreateOptionsMenu(menu,inflater);
	}

	//menu.add("Language").setIcon(android.R.drawable.ic_menu_manage);

	public boolean onOptionsItemSelected(MenuItem item) {

		String idSecteur=""+item.getItemId();
		if(idSecteur.length()>3)
		{
			if(idSecteur.substring(0, 3).equals("555"))
			{
				// createDialog("",idSecteur.substring(3));
				FineLastPublication(Integer.parseInt(idSecteur.substring(3)));
			}
		}
		switch (item.getItemId()) {
			case 1001: setLocal(Locale.ENGLISH);break;
			case 1002: setLocal(Locale.FRENCH);break;
			case 1003: AlertDialog();break;
			case android.R.id.home:  getActivity().finish();  Toast.makeText( getActivity(), "Home is clicked", Toast.LENGTH_SHORT).show();

		}
		return true;
	}

	private void setLocal(Locale localeMobile)
	{
		res = getResources();
		conf = res.getConfiguration();
		conf.locale = localeMobile;
		res.updateConfiguration(conf, res.getDisplayMetrics());
		settings.edit() .putString("langue",localeMobile.toString()) .commit();
		int id_langue=0;
		langueBdd.open();


		try {
			id_langue= langueBdd.getReturnIdlangue(settings.getString("langue", "en"));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		langueBdd.close();
		settings.edit()

				.putInt("id_langue",id_langue)
				.commit();
		try {
			Context context =  getActivity().createPackageContext( getActivity().getPackageName(), Context.CONTEXT_INCLUDE_CODE);
			Intent lng_intent = new Intent(context, MainActivity.class);
			startActivity(lng_intent);
			getActivity().finish();
		} catch (Exception e) {e.printStackTrace();}
	};

	public void AlertDialog(){

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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



	public void createDialog(String title, String text)
	 {
	 	// Création d'une popup affichant un message
	 	AlertDialog ad = new AlertDialog.Builder(getActivity())
	 			.setPositiveButton("Ok", null).setTitle(title).setMessage(text)
	 			.create();
	 	ad.show();

	 }
	public void onBackPressed() {
		// Write your code here
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.info_quit);
		builder.setCancelable(false);
		builder.setNegativeButton(R.string.info_oui, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				getActivity().finish();
				//System.exit(0);
			}});
		builder.setPositiveButton(R.string.info_non, new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, final int id) {
				dialog.cancel();
			}});
		builder.show();
	}
 	
}
