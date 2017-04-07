package com.therankit.services;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.url.AppController;
import com.volley.Const;
import com.volley.JsonUTF8Request;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.sqlLite.LangueTable;
import com.sqlLite.SecteurTable;
import com.sqlLite.VerifNoteTable;
import com.therankit.home.MainActivity;
import com.therankit.rankitlite.FeedImageView;
import com.therankit.rankitlite.R;
import com.therankit.rankitlite.MyAdapterMarket.Item;
import com.therankit.rankitlite.MyAdapterMarket.ViewHolder;

import butterknife.OnClick;
import chechPakage.MyAdapterMarket;

public class Home extends Fragment {
	   private List<Integer> tabGridIdEntreprise= new ArrayList<Integer>() ;
	    private List<String> tabGridNameEntreprise= new ArrayList<String>() ;
	    private List<Integer> tabGridIdSecteur= new ArrayList<Integer>() ;
	    private List<String> tabGridUriLogo= new ArrayList<String>() ;
	    private SimpleDateFormat formater;
	    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	  
	    private ProgressDialog pDialog;
	    private Resources res;
	    private Configuration conf;
	    private SharedPreferences settings;
	  
	    private GridView gridViewMarket;
	    private MyAdapterMarket listAdapter;
	    private JSONObject jsonObjSend;
	    private String TAG = Home.class.getSimpleName();
		private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
		private Cache cache;
		private Entry entry;
		private String data;
		private JSONArray feedArray;
		private int randkiteUser_id;
		private SecteurTable secteurBdd;
		private LangueTable langueBdd;
		private VerifNoteTable verifNoteBdd;
		private JSONArray jsonArrayMenuServices;
		 IntentFilter gcmFilter;
	private FloatingActionButton fab;
		 private Bundle extras;
			// A BroadcastReceiver must override the onReceive() event.
						private BroadcastReceiver gcmReceiver = new BroadcastReceiver() {

							@Override
							public void onReceive(Context context, Intent intent) {
					        
					       
								listAdapter.notifyDataSetChanged();
					       
							}
					        };
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get the view from favorites
		Bundle extras= getActivity().getIntent().getExtras(); 
		
		if(extras != null)
		{
			
			randkiteUser_id=new Integer(extras.getInt("randkiteUser_id"));   
			
			 
		} 
		settings =  getActivity().getSharedPreferences(Const.PREFRENCES_NAME, Context.MODE_PRIVATE);
		View view = inflater.inflate(R.layout.list_item, container, false);
		
		 pDialog = new ProgressDialog(getActivity());
		 pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		 pDialog.setMessage(""+getString(R.string.st_chargement));
		 pDialog.setCancelable(false);
		 gridViewMarket = (GridView)view.findViewById(R.id.gridview);
		fab = (FloatingActionButton) view.findViewById(R.id.fab);

		 //getSherlockActivity().getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#73C2FB")));
		 //getSherlockActivity().getSupportActionBar().setIcon(R.drawable.white_service);
		 langueBdd= new LangueTable( getActivity().getBaseContext());
		 secteurBdd = new SecteurTable( getActivity().getBaseContext());  
		 verifNoteBdd = new VerifNoteTable( getActivity().getBaseContext());  
	    // getSherlockActivity().getSupportActionBar().setTitle("Services-Rank");	
	     extras= getActivity().getIntent().getExtras(); 
			gcmFilter = new IntentFilter();
			gcmFilter.addAction("GCM_SEND_NOTES");
			  // Register the BroadcastReceiver
	        
		 getActivity().registerReceiver(gcmReceiver, gcmFilter);

	     jsonObjSend = new JSONObject();
	    // showProgressDialog();
	     secteurBdd.open();

		 try {
			 jsonArrayMenuServices=secteurBdd.getListSecteur(settings.getString("langue", "en"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 secteurBdd.close();
		 setHasOptionsMenu(true);
	     FineLastPublication(0);
	   
		 return view;
	}
 
	
	
	/*
	 * 
	 * debut methode permetttant d'aller charger les entreprises
	 * 
	 * 
	 */
	
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
								 tabGridIdEntreprise.clear() ;
								   tabGridNameEntreprise.clear() ;
								  tabGridIdSecteur.clear() ;
								    tabGridUriLogo.clear() ;
								
								 for (int i = 0; i < feedArray.length(); i++) 
				 					{
				 						try {
											JSONObject feedObj = (JSONObject) feedArray.get(i);
											tabGridIdEntreprise.add(feedObj.getInt("entreprise_id"));
											   tabGridNameEntreprise.add(feedObj.getString("entreprise_name")) ;
											  tabGridIdSecteur.add(feedObj.getInt("secteur_id")) ;
											    tabGridUriLogo.add(feedObj.getString("entreprise_logo")) ;
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}	
								
				 					}
								 listAdapter=new MyAdapterMarket(getActivity(),tabGridIdEntreprise,tabGridNameEntreprise,tabGridIdSecteur,tabGridUriLogo);
								 gridViewMarket.setAdapter(listAdapter); 
								 listAdapter.notifyDataSetChanged();
								gridViewMarket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
									public void onItemClick(AdapterView<?> parent, View v,
															int position, long id) {
										Date d=new Date();
										new Locale("FR","fr");
										formater = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
										formater.format(d);
										verifNoteBdd.open();

										if(!verifNoteBdd.getVerification(tabGridIdEntreprise.get(position), formater.format(d)))
										{
											Intent i = new Intent(getActivity(), detailService.class);

											i.putExtra("entreprise_id",tabGridIdEntreprise.get(position));
											i.putExtra("entreprise_logo",tabGridUriLogo.get(position));
											i.putExtra("entreprise_name",tabGridNameEntreprise.get(position));
											i.putExtra("secteur_id",tabGridIdSecteur.get(position)+"");
											i.putExtra("randkiteUser_id",randkiteUser_id);
											settings.edit() .putString("sourceNoteS","0") .commit();
											startActivity(i);

										}
										else
										{
											createDialog("", getActivity().getApplicationContext().getString(R.string.msg_error_vote));
										}
										verifNoteBdd.close();


									}
								});
					 		   // hideProgressDialog();
							}
							else
							{
								createDialog("Infos","Aucun r�sultat");	
								
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
	 	AlertDialog ad = new AlertDialog.Builder(getActivity())
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
					// search.add(0, 1005, 0,this.getResources().getString(R.string.Services_par_autre));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				

				
				// Locale.ENGLISH.toString());
		    	    SubMenu m = ((Menu) menu).addSubMenu(0, 0, 0, this.getResources().getString(R.string.lng));
					m.add(0, 1001, 0, "English");
					m.add(0, 1002, 0, "Fran�ais");
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
	    	   
	    		AlertDialog.Builder builder = new Builder(getActivity());
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
