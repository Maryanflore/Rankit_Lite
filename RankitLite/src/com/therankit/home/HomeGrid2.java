
package com.therankit.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.therankit.community.CommunityTabsActivit;
import com.therankit.product.ProductTabsActivite;
import com.therankit.rankitlite.R;
import com.therankit.services.IconTextTabsActivity;
import com.volley.Const;

public class HomeGrid2 extends AppCompatActivity {


	private int randkiteUser_id;
	private String telephoneUtilisateur;
	private String imgUtilisateur;
	private String NomPrenom;
	private String categorie="tous";
	private int onglet;
	private String loginUtilisateur;
	private String uriImage;
	private String emailUtilisateur;
	private String device_key;
	private  SharedPreferences settings;
	private Toolbar toolbar;

private  int incr=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			// Get the view from favorites
			//Bundle bundle=this.getArguments();

		setContentView(R.layout.accueil);
		/*toolbar = (Toolbar) findViewById(R.id.toolbar);

		setSupportActionBar(toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);*/

			settings = getSharedPreferences(Const.PREFRENCES_NAME, Context.MODE_PRIVATE);

			int id_rotation=0;
		if(settings.getString("sen_rotation", "0").equals("1"))
				{
				id_rotation=R.anim.anim_rotate;
				}
			 else
			 {
				 id_rotation=R.anim.anim_rotate2;
			 }

		Bundle extras=getIntent().getExtras();


    	if(settings.getString("init", "0").equals("0"))
		{
    		device_key=new String(extras.getString("device_key"));
	    	randkiteUser_id=new Integer(extras.getInt("randkiteUser_id"));
	    	telephoneUtilisateur=new String(extras.getString("randkiteUser_phone"));
	    	imgUtilisateur=new String(extras.getString("randkiteUser_picture"));
	    	NomPrenom=new String(extras.getString("randkiteUser_name"));
	    	loginUtilisateur=new String(extras.getString("randkiteUser_surname"));
	    	emailUtilisateur=new String(extras.getString("randkiteUser_email"));
	    	//categorie=new String(extras.getString("categorie"));
	    	onglet=new Integer(extras.getInt("onglet"));
		}
    	else
    	{



    		device_key=settings.getString("device_key","");
	    	randkiteUser_id=settings.getInt("randkiteUser_id",0);
	    	telephoneUtilisateur=settings.getString("randkiteUser_phone","");
	    	imgUtilisateur=settings.getString("randkiteUser_picture","");
	    	NomPrenom=settings.getString("randkiteUser_name","");
	    	loginUtilisateur=settings.getString("randkiteUser_surname","");
	    	emailUtilisateur=settings.getString("randkiteUser_email","");
	    	onglet=settings.getInt("onglet",0);
    	}
			final Animation animRotate = AnimationUtils.loadAnimation(getBaseContext(), id_rotation);
			final Animation animRotate2 = AnimationUtils.loadAnimation(getBaseContext(), id_rotation);
			final Animation animRotate3 = AnimationUtils.loadAnimation(getBaseContext(), id_rotation);
			final Animation animRotate4 = AnimationUtils.loadAnimation(getBaseContext(), id_rotation);

			final Button btnRotate = (Button)findViewById(R.id.button1);
			final Button btnRotate2 = (Button)findViewById(R.id.button2);
			final Button btnRotate3 = (Button)findViewById(R.id.button3);
			final Button btnRotate4 = (Button)findViewById(R.id.button4);

				btnRotate.setOnClickListener(new OnClickListener(){
				    public void onClick(View view)
				    {

				    	view.startAnimation(animRotate);



				   new     Handler().postDelayed(new Runnable()
				        {
				            public void run()
				            {
				            	 Intent intentService = new Intent(getBaseContext(), IconTextTabsActivity.class);
				 	            intentService.putExtra("randkiteUser_id",randkiteUser_id);
								intentService.putExtra("randkiteUser_phone",telephoneUtilisateur);
								intentService.putExtra("randkiteUser_picture",imgUtilisateur);
								intentService.putExtra("randkiteUser_name",NomPrenom);
								intentService.putExtra("randkiteUser_surname",loginUtilisateur);
								intentService.putExtra("randkiteUser_email",emailUtilisateur);

				 			    startActivity(intentService);
				            }
				        }, 520);
				    }
				});

				btnRotate2.setOnClickListener(new OnClickListener(){
				    public void onClick(View view)
				    {

				    	view.startAnimation(animRotate);



				   new     Handler().postDelayed(new Runnable()
				        {
				            public void run()
				            {
				            	Intent intent = new Intent(getBaseContext(), ProductTabsActivite.class);
					        	intent.putExtra("randkiteUser_id",randkiteUser_id);
								intent.putExtra("randkiteUser_phone",telephoneUtilisateur);
								intent.putExtra("randkiteUser_picture",imgUtilisateur);
								intent.putExtra("randkiteUser_name",NomPrenom);
								intent.putExtra("randkiteUser_surname",loginUtilisateur);
								intent.putExtra("randkiteUser_email",emailUtilisateur);
							    startActivity(intent);
				            }
				        }, 520);
				    }
				});

				btnRotate3.setOnClickListener(new OnClickListener(){
				    public void onClick(View view)
				    {

				    	view.startAnimation(animRotate);



				   new     Handler().postDelayed(new Runnable()
				        {
				            public void run()
				            {
				            	/* Intent intentbest = new Intent(getBaseContext(), BestTabsActivity.class);
					        	 intentbest.putExtra("randkiteUser_id",randkiteUser_id);
								intentbest.putExtra("randkiteUser_phone",telephoneUtilisateur);
								intentbest.putExtra("randkiteUser_picture",imgUtilisateur);
								intentbest.putExtra("randkiteUser_name",NomPrenom);
								intentbest.putExtra("randkiteUser_surname",loginUtilisateur);
								intentbest.putExtra("randkiteUser_email",emailUtilisateur);
								 startActivity(intentbest); */
				            }
				        },520);


						new MaterialDialog.Builder(getBaseContext())
								.content("EN cours de finission")
								.positiveText("OK")
								//.negativeText(R.string.disagree)

								.show();
				    }
				});


				btnRotate4.setOnClickListener(new OnClickListener(){
				    public void onClick(View view)
				    {

				    	view.startAnimation(animRotate);



				   new     Handler().postDelayed(new Runnable()
				        {
				            public void run()
				            {
				            	 Intent intentbest = new Intent(getBaseContext(), CommunityTabsActivit.class);
					        	 intentbest.putExtra("randkiteUser_id",randkiteUser_id);
								intentbest.putExtra("randkiteUser_phone",telephoneUtilisateur);
								intentbest.putExtra("randkiteUser_picture",imgUtilisateur);
								intentbest.putExtra("randkiteUser_name",NomPrenom);
								intentbest.putExtra("randkiteUser_surname",loginUtilisateur);
								intentbest.putExtra("randkiteUser_email",emailUtilisateur);
								 startActivity(intentbest);
				            }
				        },520);
				    }
				});
				/*btnRotate.setOnClickListener(new Button.OnClickListener(){

					  @Override
					  public void onClick(View arg0) {
						  arg0.startAnimation(animRotate);

					  }});*/

				if(settings.getString("sen_rotation", "0").equals("1"))
				{
				 // animRotate.setStartOffset(1000);

					   new     Handler().postDelayed(new Runnable()
					        {
					            public void run()
					            {
					            	 btnRotate.startAnimation(animRotate);
					            }
					        },1000);


				  //animRotate2.setStartOffset(2000);
					   new     Handler().postDelayed(new Runnable()
				        {
				            public void run()
				            {
				            	btnRotate2.startAnimation(animRotate2);
				            }
				        },2000);


				  //animRotate3.setStartOffset(3000);
					   new     Handler().postDelayed(new Runnable()
				        {
				            public void run()
				            {
				            	btnRotate3.startAnimation(animRotate3);
				            }
				        },3000);
				//  btnRotate3.startAnimation(animRotate3);

				  //animRotate4.setStartOffset(4000);
				  //btnRotate4.startAnimation(animRotate4);
				  new     Handler().postDelayed(new Runnable()
			        {
			            public void run()
			            {
			            	btnRotate4.startAnimation(animRotate4);
			            }
			        },4000);
				}
				else
				{
					  //animRotate4.setStartOffset(1000);


					  new     Handler().postDelayed(new Runnable()
				        {
				            public void run()
				            {
				            	 btnRotate4.startAnimation(animRotate4);
				            }
				        },1000);



					  new     Handler().postDelayed(new Runnable()
				        {
				            public void run()
				            {
				            	  btnRotate3.startAnimation(animRotate3);
				            }
				        },2000);

					  //animRotate2.setStartOffset(3000);

					  new     Handler().postDelayed(new Runnable()
				        {
				            public void run()
				            {
				            	btnRotate2.startAnimation(animRotate2);
				            }
				        },3000);
					//  animRotate.setStartOffset(4000);
					  new     Handler().postDelayed(new Runnable()
				        {
				            public void run()
				            {
				            	 btnRotate.startAnimation(animRotate);
				            }
				        },4000);

				}



				//getActivity().getActionBar().setIcon(R.drawable.ic_launcher);
				//getActivity().getActionBar().setTitle(getActivity().getApplicationContext().getString(R.string.bienvenu) +", "+loginUtilisateur+"");


	 }

		public void service(View v) {
				goActivity(2);
			}


			public void product(View v) {
					goActivity(3);
				}



			public void best(View v) {
					goActivity(1);
				}



		private void goActivity(int onglet)
		{

	   	      Intent intent = new Intent(getBaseContext(), MainActivity_Menu.class);
	   	    intent.putExtra("randkiteUser_id",randkiteUser_id);
			intent.putExtra("randkiteUser_name",NomPrenom);
			intent.putExtra("randkiteUser_surname",loginUtilisateur);
			intent.putExtra("randkiteUser_picture",imgUtilisateur);
			intent.putExtra("randkiteUser_phone",telephoneUtilisateur);
			intent.putExtra("randkiteUser_email",emailUtilisateur);
			intent.putExtra("device_key",device_key);
			intent.putExtra("onglet",onglet);
			startActivity(intent);
			finish();

		}

		public void onBackPressed() {


	   	      Intent intent = new Intent(getBaseContext(), MainActivity.class);


	   	  finish();

			/*	 	// Cr�ation d'une popup affichant un message
				 AlertDialog ad = new AlertDialog.Builder(this).setNegativeButton("NON", new AlertDialog.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						arg0.dismiss();
					}})
				.setPositiveButton("OUI", new AlertDialog.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						 Intent intent ;
						 settings.edit()
							.putString("sen_rotation","-1")
						    .commit();
							 intent = new Intent(MainActivity_Menu.this, HomeGrid.class);

							    intent.putExtra("randkiteUser_id",randkiteUser_id);
								intent.putExtra("randkiteUser_name",NomPrenom);
								intent.putExtra("randkiteUser_surname",loginUtilisateur);
								intent.putExtra("randkiteUser_picture",imgUtilisateur);
								intent.putExtra("randkiteUser_phone",telephoneUtilisateur);
								intent.putExtra("randkiteUser_email",emailUtilisateur);
								intent.putExtra("device_key",device_key);
						 startActivity(intent);
						 finish();
					}}).setTitle("")
				.setMessage("�tes-vous sur de vouloir retourner a l'acceuil ?").create();
				 		ad.show();
				}*/
	    }


		public boolean onOptionsItemSelected(MenuItem item) {
		    if (item.getItemId() == android.R.id.home) {
		    	Intent intent = new Intent(getBaseContext(), MainActivity.class);
		    	finish();
		    	startActivity(intent);

		        return true;
		    }
		    return super.onOptionsItemSelected((MenuItem) item);
		}
		
 
		

}
