package com.therankit.product;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.sqlLite.LangueTable;
import com.sqlLite.SecteurTable;
import com.sqlLite.SousCatTable;
import com.sqlLite.VerifNoteTable;
import com.therankit.home.MainActivity;
import com.therankit.rankitlite.R;
import com.therankit.services.IconTextTabsActivity;
import com.url.AppController;
import com.volley.Const;
import com.volley.JsonUTF8Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.OnClick;
import chechPakage.ItemAdapter;
import chechPakage.MarginDecoration;
import chechPakage.MyAdapterMarket2;

public class ProductActivite extends Fragment implements SearchView.OnQueryTextListener{

	    private SimpleDateFormat formater;
	    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	    private ProgressDialog pDialog;
	    private Resources res;
	    private Configuration conf;
	    private SharedPreferences settings;

	   // private GridView gridViewMarket;
	    private MyAdapterMarket2 listAdapter;
	    private JSONObject jsonObjSend;
	    private String TAG = ProductActivite.class.getSimpleName();
		private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
		private Cache cache;
		private Entry entry;
		private String data;
		private JSONArray feedArray;
		private int randkiteUser_id;
	private String telephoneUtilisateur;
	private String imgUtilisateur;
	private String NomPrenom;
	private String loginUtilisateur;

	private String emailUtilisateur;
		private SousCatTable sousCatTable;
		private LangueTable langueBdd;
	   private SecteurTable secteurBdd;
		private VerifNoteTable verifNoteBdd;
		private JSONArray jsonArrayMenuServices;
	    private RecyclerView recyclerView;
	private ArrayList<ItemAdapter>        ListitemAdapter;
		 IntentFilter gcmFilter;
	private FloatingActionButton fab;
		 private Bundle extras;
	private List<Integer> ListeidSecteur= new ArrayList<Integer>() ;
	private List<String> ListeSecteur= new ArrayList<String>() ;
	private List<Integer> ListeidSousCat= new ArrayList<Integer>() ;
	private List<String> ListeSousCat= new ArrayList<String>() ;
	private JSONArray jsonArraySousCat;
	private Toolbar toolbar;
	private TextView	textToolHeader;
			// A BroadcastReceiver must override the onReceive() event.
						private BroadcastReceiver gcmReceiver = new BroadcastReceiver() {

							@Override
							public void onReceive(Context context, Intent intent) {


								listAdapter.notifyDataSetChanged();

							}
					        };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get the view from favorites
		Bundle extras= getActivity().getIntent().getExtras();

		if(extras != null)
		{

			randkiteUser_id=new Integer(extras.getInt("randkiteUser_id"));
			telephoneUtilisateur=new String(extras.getString("randkiteUser_phone"));
			imgUtilisateur=new String(extras.getString("randkiteUser_picture"));
			NomPrenom=new String(extras.getString("randkiteUser_name"));
			loginUtilisateur=new String(extras.getString("randkiteUser_surname"));
			emailUtilisateur=new String(extras.getString("randkiteUser_email"));


		}
		settings =  getActivity().getSharedPreferences(Const.PREFRENCES_NAME, Context.MODE_PRIVATE);
		View view = inflater.inflate(R.layout.list_item2, container, false);

		 pDialog = new ProgressDialog(getActivity());
		 pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		 pDialog.setMessage(""+getString(R.string.st_chargement));
		 pDialog.setCancelable(false);
		recyclerView = (RecyclerView)view.findViewById(R.id.card_recycler_view);
		recyclerView.addItemDecoration(new MarginDecoration(getActivity().getBaseContext()));
		recyclerView.setHasFixedSize(true);
		RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getBaseContext(),2);
		recyclerView.setLayoutManager(layoutManager);
		 langueBdd= new LangueTable( getActivity().getBaseContext());
		 secteurBdd = new SecteurTable( getActivity().getBaseContext());
		sousCatTable= new SousCatTable( getActivity().getBaseContext());
		 verifNoteBdd = new VerifNoteTable( getActivity().getBaseContext());
	     extras= getActivity().getIntent().getExtras();
			gcmFilter = new IntentFilter();
			gcmFilter.addAction("GCM_SEND_NOTES");
			  // Register the BroadcastReceiver
		toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		textToolHeader = (TextView) toolbar.findViewById(R.id.toolbar_title);


		 getActivity().registerReceiver(gcmReceiver, gcmFilter);

	     jsonObjSend = new JSONObject();
	    // showProgressDialog();
	     secteurBdd.open();

		 try {
			 jsonArrayMenuServices=secteurBdd.getListeSecteur(settings.getString("langue", "en"),Const.TYPE_PRODUIT);
			 try {
				 if(jsonArrayMenuServices.length()>=0)
				 {
					 ListeidSecteur.clear();
					 ListeSecteur.clear();
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
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 secteurBdd.close();

		 return view;
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		fab=(FloatingActionButton) view.findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {


			public void onClick(View view) {





				new MaterialDialog.Builder( getActivity())
						.title(getActivity().getString(R.string.info_sector))
						.items(ListeSecteur)
						.itemsCallback(new MaterialDialog.ListCallback() {
							@Override
							public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

								sousCatTable.open();

								try {
									jsonArraySousCat=sousCatTable.getListSousCat(settings.getString("langue", "en"),ListeidSecteur.get(which));

									if(jsonArraySousCat.length()>=0)
									{
										ListeSousCat=new ArrayList<String>() ;
										ListeidSousCat=new ArrayList<Integer>() ;
										for (int i = 0; i < jsonArraySousCat.length(); i++)
										{

											JSONObject feedObj = (JSONObject) jsonArraySousCat.get(i);
											ListeidSousCat.add(feedObj.getInt(SousCatTable.COLUMN_ID_SOUSCAT));
											ListeSousCat.add(feedObj.getString(SousCatTable.COLUMN_SOUSCAT_NAME));


										}

									}

								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								sousCatTable.close();


								new MaterialDialog.Builder( getActivity())
										.title("Sous Categorie")
										.items(ListeSousCat)
										.itemsCallback(new MaterialDialog.ListCallback() {
											@Override
											public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
												textToolHeader.setText(ListeSousCat.get(which));
												FineLastPublication(ListeidSousCat.get(which));
											}
										})
										.positiveText(android.R.string.cancel)
										.show();

							}
						})
						.positiveText(android.R.string.cancel)
						.show();

			}
			});
		setHasOptionsMenu(true);
		new MaterialDialog.Builder( getActivity())
				.title(getActivity().getString(R.string.info_sector))
				.items(ListeSecteur)
				.itemsCallback(new MaterialDialog.ListCallback() {
					@Override
					public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

						sousCatTable.open();

						try {
							jsonArraySousCat=sousCatTable.getListSousCat(settings.getString("langue", "en"),ListeidSecteur.get(which));

							if(jsonArraySousCat.length()>=0)
							{
								ListeSousCat=new ArrayList<String>() ;
								ListeidSousCat=new ArrayList<Integer>() ;
								for (int i = 0; i < jsonArraySousCat.length(); i++)
								{

									JSONObject feedObj = (JSONObject) jsonArraySousCat.get(i);
									ListeidSousCat.add(feedObj.getInt(SousCatTable.COLUMN_ID_SOUSCAT));
									ListeSousCat.add(feedObj.getString(SousCatTable.COLUMN_SOUSCAT_NAME));


								}

							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						sousCatTable.close();


						new MaterialDialog.Builder( getActivity())
								.title("Sous Categorie")
								.items(ListeSousCat)
								.itemsCallback(new MaterialDialog.ListCallback() {
									@Override
									public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
										textToolHeader.setText(ListeSousCat.get(which));
										FineLastPublication(ListeidSousCat.get(which));
									}
								})
								.positiveText(android.R.string.cancel)
								.show();

					}
				})
				.positiveText(android.R.string.cancel)
				.show();
		textToolHeader.setText("");
	}


	/*
	 *
	 * debut methode permetttant d'aller charger les entreprises
	 *
	 *
	 */

	private void FineLastPublication(int id_sousCat)
	  {
		pDialog.show();
		  try
			{  	jsonObjSend.put("id_entreprise",0);
				jsonObjSend.put("id_sousCat",id_sousCat);

				//createDialog("infos00",Const.URL_JSON_AFFICHAGE_E_MARKET+""+jsonObjSend);
				makeJsonObjReq(jsonObjSend,Const.urlNote+"listeProduit");
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
	  }

	/*
	 *
	 * debut methode permetttant d'aller charger les entreprises
	 *
	 *
	 */


	private void makeJsonObjReq(JSONObject json,String url) {


		JsonUTF8Request jsonObjReq = new JsonUTF8Request(Method.POST,url, json,
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
								ListitemAdapter= new ArrayList<ItemAdapter>();
								for (int i = 0; i < feedArray.length(); i++)
								{
									try {
										JSONObject feedObj = (JSONObject) feedArray.get(i);

										ListitemAdapter.add(new ItemAdapter(i,feedObj.getInt("produit_id"),feedObj.getString("produit_name"),feedObj.getInt("sousCategori_id"),feedObj.getString("produit_picture")));

									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}


								listAdapter=new MyAdapterMarket2(getActivity(),getActivity(),ListitemAdapter,randkiteUser_id,Const.TYPE_PRODUIT);



								recyclerView.setAdapter(listAdapter);
								 listAdapter.notifyDataSetChanged();
								((MyAdapterMarket2) listAdapter).setOnItemClickListener(new MyAdapterMarket2
										.MyClickListener() {
									@Override
									public void onItemClick(int position, View v) {
										Log.i(TAG, " Clicked on Item " + position);
										ItemAdapter item = (ItemAdapter) listAdapter.getItem(position);
										Date d=new Date();
										new Locale("FR","fr");
										formater = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
										formater.format(d);
										verifNoteBdd.open();

										if(!verifNoteBdd.getVerification(item.getIdEntreprise(), formater.format(d))) {

											Intent i = new Intent(getActivity(), detailProduct.class);

											i.putExtra("entreprise_id", item.getIdEntreprise());
											i.putExtra("entreprise_logo", item.getLogo());
											i.putExtra("entreprise_name", item.getNameEntreprise());
											i.putExtra("secteur_id", item.getIdSecteur() + "");
											i.putExtra("randkiteUser_id", randkiteUser_id);
											settings.edit().putString("sourceNoteS", "0").commit();
											startActivity(i);
										}
										else
										{
											createDialog("", getActivity().getApplicationContext().getString(R.string.msg_error_vote));
										}
										verifNoteBdd.close();
										listAdapter.notifyDataSetChanged();


									}
								});
								listAdapter.notifyDataSetChanged();
					 		   // hideProgressDialog();
							}
							else
							{
								createDialog("Infos",getActivity().getApplicationContext().getString(R.string.info_recher));

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

	public void createDialog(String title, String text)
	 {
	 	// Cr�ation d'une popup affichant un message
	 	AlertDialog ad = new Builder(getActivity())
	 			.setPositiveButton("Ok", null).setTitle(title).setMessage(text)
	 			.create();
	 	ad.show();

	 }


	@OnClick(R.id.fab)
	public void onFabClick() {
		Snackbar.make(fab, "Here's a Snackbar", Snackbar.LENGTH_LONG)
				.setAction("Undo", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText( getActivity(), "Fab is clicked", Toast.LENGTH_SHORT).show();

					}

				}).show();
	}

	 public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {

		 super.onCreateOptionsMenu(menu, inflater);
		 inflater.inflate(R.menu.menu_services, menu);
				//getSupportMenuInflater().inflate(R.menu.menu,  menu);

		 final MenuItem item = menu.findItem(R.id.action_search);
		 final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
		 searchView.setOnQueryTextListener(this);
		 MenuItemCompat.setOnActionExpandListener(item,
				 new MenuItemCompat.OnActionExpandListener() {
					 @Override
					 public boolean onMenuItemActionCollapse(MenuItem item) {
						 // Do something when collapsed
						 listAdapter.animateTo(ListitemAdapter);
						 return true; // Return true to collapse action view
					 }
					 @Override
					 public boolean onMenuItemActionExpand(MenuItem item) {
						 // Do something when expanded
						 return true; // Return true to expand action view
					 }
				 });





				// Locale.ENGLISH.toString());
		    	    SubMenu m = ((Menu) menu).addSubMenu(0, 0, 0, this.getResources().getString(R.string.lng));
					m.add(0, 1001, 0, "English");
					m.add(0, 1002, 0, "Fran�ais");
				@SuppressWarnings("unused")
				SubMenu l = ((Menu) menu).addSubMenu(0, 1003, 0, "Application");

		}



	     public boolean onOptionsItemSelected(MenuItem item) {

	        String idSecteur=""+item.getItemId();

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
				Intent intentService = new Intent(context, ProductTabsActivite.class);
				intentService.putExtra("randkiteUser_id",randkiteUser_id);
				intentService.putExtra("randkiteUser_phone",telephoneUtilisateur);
				intentService.putExtra("randkiteUser_picture",imgUtilisateur);
				intentService.putExtra("randkiteUser_name",NomPrenom);
				intentService.putExtra("randkiteUser_surname",loginUtilisateur);
				intentService.putExtra("randkiteUser_email",emailUtilisateur);

				startActivity(intentService);
				 getActivity().finish();
			} catch (Exception e) {e.printStackTrace();}
			};

			public void AlertDialog(){

	    		Builder builder = new Builder(getActivity());
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

	@Override
	public boolean onQueryTextChange(String newText) {
		// adapter.setFilter(mCountryModel, newText);
		final ArrayList<ItemAdapter> filteredModelList = filter(ListitemAdapter, newText);
		listAdapter.animateTo(filteredModelList);
		recyclerView.scrollToPosition(0);


		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return false;
	}

	private ArrayList<ItemAdapter> filter(ArrayList<ItemAdapter> models, String query) {
		query = query.toLowerCase();

		final ArrayList<ItemAdapter> filteredModelList = new ArrayList<>();
		for (ItemAdapter model : models) {
			final String text = model.getNameEntreprise().toLowerCase();
			if (text.contains(query)) {
				filteredModelList.add(model);
			}
		}
		return filteredModelList;
	}

			public void onBackPressed() {
		        // Write your code here
				Builder builder = new Builder(getActivity());
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
