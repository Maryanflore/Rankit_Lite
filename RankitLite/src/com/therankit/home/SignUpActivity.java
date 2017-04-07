package com.therankit.home;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface; 

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
//import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint; 
import android.app.ProgressDialog;
import android.content.Context;  
import android.content.Intent;
import android.content.SharedPreferences; 
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.Uri; 
import android.os.Bundle; 
import android.os.Environment;
import android.os.Handler;
import android.os.Message; 
import android.provider.MediaStore; 
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log; 
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener; 
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner; 
import android.widget.TextView;
import android.widget.Toast;
  

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response; 
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader; 
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;   

import com.image.upload.MyImageRequest;
import com.therankit.rankitlite.R;
import com.url.AppController; 
import com.volley.Const;
import com.volley.JsonUTF8Request;
import com.reponse.Reponse;
@SuppressLint("NewApi")
public class SignUpActivity extends ActionBarActivity implements OnItemSelectedListener{
	MultipartEntity mEntity;
	 private static RequestQueue mQueue;
	
	 private String file_path;   
 
		private int incrConnexion=1;
		ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	int serverResponseCode = 0;

	private static final int FILECHOOSER_RESULTCODE   = 2888;
	  
	private ProgressDialog pDialog;
	private String registrationId=""; 
	private ImageView publierImage; 
	private String nameImage="";
	private String idUtilisateur;
	
	private ImageView imageview;


	private String TAG = SignUpActivity.class.getSimpleName();
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
 

	private JSONObject jsonObjSend;

	 
	private String envoye;

  
	private Button enregButon;
	private EditText Num_Tel;   
	private EditText login;    
	private EditText Nom ;
	private String tel=""; 
	private EditText prenom ;  
	private EditText password;  

	private String CodePal; 
	private List<String> list_login = new ArrayList<String>();
	private EditText password_confirm;
	private EditText dateChoice;
	private String Infopwd;
	private String Infopwd_confirm;
	private String NomText;
 
	private String prenomText;
	private int incr=0;
	private Spinner BlockLogin;
    static final int DATE_PICKER_ID =0; 
    private String InfoTel;
	private RadioButton radioSexButton;
	private RadioGroup radioSexGroup;
	private EditText mail;
 	private String MailText;
	private EditText codePostal;
	private EditText plus;
	private SharedPreferences settings;
	private TextView loginError;
	private TextView pswError;
	private String Sx=""; 
	private String pays_iso="";
	private String pays_name="";
	private int    pays_id=0;
	private List<String> liste_pays_name = new ArrayList<String>();
	private List<Integer> liste_pays_id = new ArrayList<Integer>();
	private List<Integer> liste_pays_code = new ArrayList<Integer>();
	private ArrayAdapter<String> dataAdapter;
	private Spinner spinner;
	private String  code_pays="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inscription);

		
		Bundle extras=getIntent().getExtras(); 
		pays_iso=new String(extras.getString("pays_iso")); 
		pays_name=new String(extras.getString("pays_name")); 
	   registrationId=new String(extras.getString("device_key"));  
           settings = getSharedPreferences(Const.PREFRENCES_NAME, Context.MODE_PRIVATE);
		 jsonObjSend = new JSONObject();

		    mQueue = Volley.newRequestQueue(getApplicationContext());
		radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);       

		pDialog = new ProgressDialog(this);
		loginError = (TextView) findViewById(R.id.loginError);
		loginError.setVisibility(View.GONE); 
		
		pswError = (TextView) findViewById(R.id.pswError);
		pswError.setVisibility(View.GONE); 
		
       // Nom = (EditText) findViewById(R.id.nom);
        //mail = (EditText) findViewById(R.id.mail);
        codePostal = (EditText) findViewById(R.id.codepostal);  
        codePostal.setEnabled(false);
        //prenom = (EditText) findViewById(R.id.prenom);
        Num_Tel=(EditText) findViewById(R.id.textTel);
        login = (EditText) findViewById(R.id.nom);
        //password = (EditText) findViewById(R.id.passwordnew);
       // password_confirm = (EditText) findViewById(R.id.password_confirm);
         dateChoice = (EditText) findViewById(R.id.textDate);  
         dateChoice.setEnabled(false);

         
         
         plus = (EditText) findViewById(R.id.plus);  
         plus.setEnabled(false);
        login.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            if(!hasFocus){

            		if(login.getText().toString().equals(""))
            			   { 
            		    Toast.makeText(getBaseContext(),""+getString(R.string.st_toast_error), Toast.LENGTH_LONG).show();	
            		   }
            		else
            		 { 
            			if(tesConnection())
				      	{
            			pDialog.setMessage("Vérification en cours...");
            			pDialog.setCancelable(false);	 
            			try { 
            			envoye="VerifUser";		 
            			jsonObjSend.put(Const.TAG_LOGIN,login.getText().toString().trim()); 
	            			
		            			makeJsonObjReq(jsonObjSend,Const.URL_JSON_VERIF_USERS,"");
					      	}
            			 catch (JSONException e) {
            			e.printStackTrace();
            			}
            		 }
            			else
            			{
            				  Toast.makeText(getBaseContext(),""+getString(R.string.st_toast_error_connection), Toast.LENGTH_LONG).show();	
            					loginError.setText("ERROR: "+getString(R.string.st_toast_error_connection));
            					loginError.setTextColor(Color.parseColor("#ff0000"));
        						  		
            			}
            		 }
            		 
            	}
            }
          });
		// Spinner element
		spinner = (Spinner) findViewById(R.id.editText2);

		// Spinner click listener
		spinner.setOnItemSelectedListener(this);

		// Spinner Drop down elements






		// attaching data adapter to spinner
		getListContry();
        //getContry();
       // TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
       //tel=tm.getLine1Number();
        //Num_Tel.setText(String.valueOf(tel)); 
 
		 enregButon = (Button) findViewById(R.id.enregButon);
		 enregButon.setOnClickListener(new View.OnClickListener() {



				@SuppressLint("NewApi")
			 	public void onClick(View view) { 
					if(tesConnection())
			      	{
					
			// get selected radio button from radioGroup
			int selectedId = radioSexGroup.getCheckedRadioButtonId();
			radioSexButton = (RadioButton) findViewById(selectedId);
			//createDialog("Alert"," "+radioSexButton.getText()); 	  
			
	//Infopwd=password.getText().toString().trim();
	//Infopwd_confirm=password_confirm.getText().toString().trim();
	InfoTel=Num_Tel.getText().toString().trim(); 

	
//NomText=Nom.getText().toString().trim();
//prenomText=prenom.getText().toString().trim();
//MailText=mail.getText().toString().trim();
CodePal=codePostal.getText().toString().trim();

/*if(NomText.equals("")||Infopwd_confirm.equals("")||Infopwd.equals("")||prenomText.equals("")||InfoTel.equals("")||login.getText().toString().equals("")||
	dateChoice.getText().toString().equals("")||mail.getText().toString().equals("")||codePostal.getText().toString().equals(""))
   { 
Toast.makeText(getBaseContext(),""+getString(R.string.st_toast_error), Toast.LENGTH_LONG).show();	
}*/

if(InfoTel.equals("")||login.getText().toString().equals("")||
								dateChoice.getText().toString().equals("")||codePostal.getText().toString().equals(""))
						{
							Toast.makeText(getBaseContext(),""+getString(R.string.st_toast_error), Toast.LENGTH_LONG).show();
						}
else
{  

     
  	if(radioSexButton.getText().equals("HOMME"))
    	{
    		Sx="M";
    	}
    	else
    	{
    		Sx="F";	
    	}
    	

		pDialog.setMessage(R.string.st_dialog_createCompt+"");
		pDialog.setCancelable(false);
		
		publier();


}
		  

			      	}
					else
					{
						  Toast.makeText(getBaseContext(),""+getString(R.string.st_toast_error_connection), Toast.LENGTH_LONG).show();	
							
					}
				}
			 
			 
	}); 

		 
	     	imageview = (ImageView) findViewById(R.id.picture);
	     	imageview.setOnClickListener(new View.OnClickListener() {
				 	@SuppressLint("NewApi")
					public void onClick(View view) { 
				 		 selectImage();
				 	 }
					});
				 
				
				
				publierImage = (ImageView) findViewById(R.id.pictureButon);
				publierImage.setOnClickListener(new View.OnClickListener() {
				 	@SuppressLint("NewApi")
					public void onClick(View view)
				 		{ 
				 		selectImage();
				 		}
					});
				 
 } 
	
	public void  selectImage(){
		final CharSequence[] options = {getString(R.string.pictureChoice1),getString(R.string.pictureChoice2),getString(R.string.pictureChoice3) };
		AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);

	        builder.setTitle(getString(R.string.pictureTitle));
	        
	        builder.setItems(options, new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int item) {

	                if (options[item].equals(getString(R.string.pictureChoice1)))

	                { 
	                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");	                    
	           
	                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
	                    startActivityForResult(intent, 1);
	                }

	                else if (options[item].equals(getString(R.string.pictureChoice2)))

	                {
	                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);	                    
	                    startActivityForResult(intent, 2);
	                }

	                else if (options[item].equals(getString(R.string.pictureChoice3))) {
	                    dialog.dismiss();
	                }

	            }

	        });
  
	        builder.show();
	 }

	@SuppressWarnings("null")
	@SuppressLint("NewApi")
	@Override   
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {
             
                File f = new File(Environment.getExternalStorageDirectory().toString());

                for (File temp : f.listFiles()) 
                {
                    if (temp.getName().equals("temp.jpg")) 
                    {
                        f = temp;
                        imageview.setImageResource(R.drawable.icone_ajout);	 
                        break;
                    }
                }
                
                try {
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),options);

                    imageview.setImageBitmap(bitmap);                    
                 
                /*	
                	BitmapFactory.Options options = new BitmapFactory.Options();
                	//options.inJustDecodeBounds = true;
                	//BitmapFactory.decodeResource(getResources(), R.id.myimage, options);
                	int imageHeight = options.outHeight;
                	int imageWidth = options.outWidth;
                	//String imageType = options.outMimeType;
 
                    options.inSampleSize = calculateInSampleSize(options,imageWidth,imageHeight);
                	Bitmap bitmap =BitmapFactory.decodeFile(nameImage,options);
                    imageview.setImageBitmap(bitmap);
                        */             	
                   //createDialog("Infos", bitmap+" -------"+nameImage);
      
//String path = android.os.Environment.getExternalStorageDirectory() + File.separator+ "Phoenix" + File.separator + "default";
					
                    f.delete();
                    OutputStream outFile = null;
                   // File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    
                 	File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "rankite_pictures");
                    if (!imageStorageDir.exists()) {
                        imageStorageDir.mkdirs();
                    }  
	                File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
	               
                	nameImage=file.getAbsolutePath();
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG,Const.sizeCompress, outFile);
                        outFile.flush();
                        outFile.close();
                    	} catch (FileNotFoundException e) {

                        e.printStackTrace();

                    	} catch (IOException e) {

                        e.printStackTrace();

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                    imageview.setImageResource(R.drawable.icone_ajout);	 
                }

            }
            else if (requestCode == 2) 
            {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                
                File f2 = new File(picturePath);
                

                final BitmapFactory.Options options2 = new BitmapFactory.Options();              
                Bitmap thumbnail = BitmapFactory.decodeFile(f2.getAbsolutePath(),options2);
                options2.inSampleSize = 8;
                int width = options2.outWidth;
                int height = options2.outHeight;
              //If you want, the MIME type will also be decoded (if possible)
                String type = options2.outMimeType;
                
             	File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "rankite_pictures");
                if (!imageStorageDir.exists()) {
                    imageStorageDir.mkdirs();
                }  
                

                File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");               
                nameImage=file.getAbsolutePath();
 
              //  Log.w("path of image from gallery", picturePath+"");
                //createDialog("picturePath", Environment.getExternalStorageDirectory().toString()+"***********"+nameImage+"........."+picturePath);
                
                OutputStream outFile = null;
                try {
                    outFile = new FileOutputStream(file);
                    thumbnail.compress(Bitmap.CompressFormat.JPEG,Const.sizeCompress, outFile);
                    
                    outFile.flush();
                    outFile.close();
                	} catch (FileNotFoundException e) {

                    e.printStackTrace();

                	} catch (IOException e) {

                    e.printStackTrace();
                }
                	catch (Exception e) 
                	{
                    e.printStackTrace();
                	}
                c.close();

                //createDialog("picturePath",f2.getAbsolutePath()+":::"+nameImage);     
              
                Bitmap thumbnail_new = BitmapFactory.decodeFile(nameImage,options2);
                imageview.setImageBitmap(thumbnail_new); 

            }

        }

    }
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
    }

    return inSampleSize;
}
	 
 public void publier()
 {

		try { 

			String TabPathImg []=nameImage.split("/"); 	
         String TabPathImgPoint=TabPathImg[TabPathImg.length-1].replace("  ", "");
         String TabPathImgSend=TabPathImgPoint.replace(" ", "");
         
			envoye="EnregistrementUser";
    	/*jsonObjSend.put(Const.TAG_NAME,NomText);
    	jsonObjSend.put(Const.TAG_PRENOM, prenomText);					 
    	jsonObjSend.put(Const.TAG_SEXE,Sx);					 
    	jsonObjSend.put(Const.TAG_DATE,dateChoice.getText().toString().trim());					 
    	jsonObjSend.put(Const.TAG_PHONE_MOBILE,InfoTel.replace(" ", ""));					 
    	jsonObjSend.put(Const.TAG_LOGIN,login.getText().toString().trim());
    	jsonObjSend.put(Const.TAG_PWD,Infopwd);
    	jsonObjSend.put(Const.TAG_EMAIL,mail.getText().toString());
    	jsonObjSend.put(Const.TAG_GCM,registrationId);
    	jsonObjSend.put(Const.TAG_PAYS_ID,pays_id);
*/

			jsonObjSend.put(Const.TAG_NAME,"");
			jsonObjSend.put(Const.TAG_PRENOM, "");
			jsonObjSend.put(Const.TAG_SEXE,Sx);
			jsonObjSend.put(Const.TAG_DATE,dateChoice.getText().toString().trim());
			jsonObjSend.put(Const.TAG_PHONE_MOBILE,"+"+code_pays+InfoTel.replace(" ", ""));
			jsonObjSend.put(Const.TAG_LOGIN,login.getText().toString().trim());
			jsonObjSend.put(Const.TAG_PWD,"");
			jsonObjSend.put(Const.TAG_EMAIL,"");
			jsonObjSend.put(Const.TAG_GCM,registrationId);
			jsonObjSend.put(Const.TAG_PAYS_ID,pays_id);
    	
    	if(TabPathImgSend.equals(""))
    	{

	       	jsonObjSend.put(Const.TAG_PICTURE, "profile.png");	
    	}
    	else
    	{
	       	jsonObjSend.put(Const.TAG_PICTURE,Const.imageNameInscription+TabPathImgSend);
    	}
		makeJsonObjReq(jsonObjSend,Const.URL_JSON_INSCRIPTION,nameImage);
         			
		} catch (JSONException e) {
			e.printStackTrace();
		}

  
 }
 
 
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {	 
	    case android.R.id.home:  
	    	finish();
	        return true;
	    }

	    return false;
	}
	


	
	public void createDialog(String title, String text)
	{
		// Cr�ation d'une popup affichant un message
		AlertDialog ad = new AlertDialog.Builder(this)
				.setPositiveButton("Ok", null).setTitle(title).setMessage(text)
				.create();
		ad.show();

	}

	

	private void showProgressDialog() {
		//if (!pDialog.isShowing())

		 pDialog.setMessage("En cours d'enregistrement...");
			pDialog.show();
	}

	private void hideProgressDialog() {
		if (pDialog.isShowing())
			pDialog.hide();
	}
	/**
	 * Making json object request
	 * */
	private void makeJsonObjReq(JSONObject json,String url,final String file) {
		 showProgressDialog();
			RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
			JsonUTF8Request jsonObjReq = new JsonUTF8Request(Method.POST,
				url, json,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						if(envoye.equals("EnregistrementUser")){
							Log.d(TAG, response.toString());
							  // Dismiss the progress dialog
								 	 JSONObject jsonObjSend = null;
									try {
										jsonObjSend = new JSONObject(response.toString());
										
										if (response != null) {
											parseJsonFeed(response,  file);
										}
								 	} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}  
		 	
						}
						else if(envoye.equals("VerifUser"))
						{


							 JSONObject jsonObjSend = null;
											try {
												jsonObjSend = new JSONObject(response.toString());
												
													if(jsonObjSend.getBoolean(Reponse.retourVerifUser))
													{
														
										 				loginError.setVisibility(View.VISIBLE);
										 				loginError.setText(" "+login.getText()+" "+getApplicationContext().getString(R.string.propositionLogin));
											        //Toast.makeText(getBaseContext(), getApplicationContext().getString(R.string.infopropositionLogin), Toast.LENGTH_LONG).show();
											        loginError.setTextColor(Color.parseColor("#ff0000"));
										              // FoundLogin(login.getText().toString());
										 				//password_confirm.setText("");
										 				//password.setText("");
										 				
									 				//login.setText("");
									 				
									 				}
													else
													{
														loginError.setVisibility(View.VISIBLE);
										 				loginError.setText(getApplicationContext().getString(R.string.User_pseudo)+" : "+login.getText()+" "+getApplicationContext().getString(R.string.valideLogin));
										 				loginError.setTextColor(Color.parseColor("#060000"));
										 				//Toast.makeText(getBaseContext(), getApplicationContext().getString(R.string.User_pseudo)+" "+getApplicationContext().getString(R.string.valideLogin), Toast.LENGTH_LONG).show();
											       }
											} catch (JSONException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}			

									 		pDialog.dismiss();
						}
						else if(envoye.equals("getContry"))
						{
							 JSONObject jsonObjSend = null;
								try {
									jsonObjSend = new JSONObject(response.toString());
									
										if(jsonObjSend.getBoolean(Reponse.retourVerifUser))
										{
											codePostal.setText(jsonObjSend.getString("pays_phonecode"));
											pays_id=jsonObjSend.getInt("pays_id");
										}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}			

						 	pDialog.dismiss();	
						}

						else if(envoye.equals("getListContry"))
						{
							JSONObject jsonObjSend = null;
							try {
								jsonObjSend = new JSONObject(response.toString());

								if(jsonObjSend.getBoolean(Reponse.retourVerifUser))
								{
									JSONArray feedArray = response.getJSONArray("listePays");
									//  createDialog("Infos", response.toString());
									if(feedArray.length()!=0) {


										for (int i = 0; i < feedArray.length(); i++) {

											JSONObject feedObj = (JSONObject) feedArray.get(i);
											liste_pays_name.add(feedObj.getString("pays_name"));
											liste_pays_id.add(feedObj.getInt("pays_id"));
											liste_pays_code.add(feedObj.getInt("pays_phonecode"));
										}
										// Creating adapter for spinner
										dataAdapter = new ArrayAdapter<String>(getBaseContext(), R.layout.spinner_item2, liste_pays_name);

										// Drop down layout style - list view with radio button
										dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
										spinner.setAdapter(dataAdapter);
									}
									//codePostal.setText(jsonObjSend.getString("pays_phonecode"));
									//pays_id=jsonObjSend.getInt("pays_id");
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							pDialog.dismiss();
						}

						hideProgressDialog();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());						 
							Toast.makeText(getBaseContext(),getString(R.string.st_connection_failled), Toast.LENGTH_LONG).show();
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
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq,
				tag_json_obj);
 	}
	
	/**
	 * Parsing json reponse and passing the data to feed view list adapter
	 * */
	private void parseJsonFeed(JSONObject response,String  file) {
		try { 
				jsonObjSend = new JSONObject(response.toString()); 
				 if((jsonObjSend.getBoolean(Reponse.retourVerifUser))&&(!file.equals("")))
				 {
					//Toast.makeText(getBaseContext(), ""+jsonObjSend.getString(Reponse.retour), Toast.LENGTH_LONG).show();

		        		pDialog.setMessage("T�l�chargement de l'image en cours...");
		        		pDialog.setCancelable(false);
		        		
					 file_path=file;
						Thread thread = new Thread(entityRunnable);
		                thread.start();

				 }  
				 else
				 {
					   Intent intent = new Intent(SignUpActivity.this, Welcome.class);
					    startActivity(intent); 
					    finish(); 
				 }
				 
					//Nom.setText("");
					//prenom.setText("");
			        //Num_Tel.setText("");
			        //login.setText("");
			        //password.setText("");
			        //password_confirm.setText("");
			        dateChoice.setText(""); 
			        //mail.setText("");
					settings.edit().putString("codePostal",codePostal.getText().toString().trim()).commit();
					//pswError.setText(" ");
					//pswError.setVisibility(View.GONE);
                 imageview.setImageResource(R.drawable.profile);	
					//Toast.makeText(getBaseContext(), ""+jsonObjSend.getString(Reponse.retour), Toast.LENGTH_LONG).show();
				   
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

 
	/**********
	 * 
	 * debut methode permettant d'envoye les images sur le serveur
	 * @author appelles
	 *
	 */
	 

	private Runnable entityRunnable = new Runnable(){
        @Override
        public void run() {
         
       
            mEntity = new MultipartEntity();
            File file = new File(file_path);
            String TabPathImg []=file_path.split("/");	
            String TabPathImgPoint=TabPathImg[TabPathImg.length-1].replace("  ", "");
            String TabPathImgSend=TabPathImgPoint.replace(" ", "");
	      // 	jsonObjSend.put(Const.image,TabPathImg[TabPathImg.length-1]);
            try {
				mEntity.addPart("file", new InputStreamBody(new FileInputStream(file),Const.imageNameInscription+TabPathImgSend) );
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            	 
        

            entityHandler.sendMessage(new Message());
        }
    };

    private Handler entityHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            testImagePost();
        }
    };

	
	public boolean tesConnection()
	{
		boolean test=true; 
		 ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
         if ( cm.getActiveNetworkInfo() == null ) {
        	 test=false;
         } 
		return test;
	}
	
	
    private void testImagePost(){
            MyImageRequest myImgRequest = new MyImageRequest(Const.ipUriUploadPictureUsers, myListener, myErrorListener);

            myImgRequest.setParams(mEntity);
            
            myImgRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mQueue.add(myImgRequest);
    }
	
    
    private Listener<JSONObject> myListener = new Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Log.d("TEST", response.toString());
     
            JSONObject jObj = null;
           
            try {
				jObj = new JSONObject(response.toString());
		 
	if(jObj.getBoolean(Reponse.retourVerifUser))
   {
     imageview.setImageResource(R.drawable.profile);
     Toast.makeText(getBaseContext(), ""+jObj.getString(Reponse.retour), Toast.LENGTH_LONG).show();
   }
   else
   {
   Toast.makeText(getBaseContext(), ""+jObj.getString(Reponse.retour), Toast.LENGTH_LONG).show();
    
   } 
    Intent intent = new Intent(SignUpActivity.this, Welcome.class);
    startActivity(intent); 
    finish(); 
	pDialog.dismiss();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    };

  
    private ErrorListener myErrorListener = new ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("TEST", error.getMessage());
        }
    }; 
	/**********
	 * 
	 * fin  methode permettant d'envoye les images sur le serveur
	 * @author appelles
	 *
	 */
	public class MultiPartRequest extends JsonRequest<JSONObject> {

	    public MultiPartRequest(int method, String url, String requestBody,
				Listener<JSONObject> listener, ErrorListener errorListener) {
			super(method, url, requestBody, listener, errorListener);
			// TODO Auto-generated constructor stub
		}

		/* To hold the parameter name and the File to upload */
	    private Map<String,File> fileUploads = new HashMap<String,File>();

	    /* To hold the parameter name and the string content to upload */
	    private Map<String,String> stringUploads = new HashMap<String,String>();

	    public void addFileUpload(String param,File file) {
	        fileUploads.put(param,file);
	    }

	    public void addStringUpload(String param,String content) {
	        stringUploads.put(param,content);
	    }

	    public Map<String,File> getFileUploads() {
	        return fileUploads;
	    }

	    public Map<String,String> getStringUploads() {
	        return stringUploads;
	    }

		@Override
		protected Response<JSONObject> parseNetworkResponse(NetworkResponse arg0) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// On selecting a spinner item
		String item = parent.getItemAtPosition(position).toString();

		// Showing selected spinner item
	//	Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
		codePostal.setText(liste_pays_code.get(position).toString());
		code_pays=liste_pays_code.get(position).toString();
		pays_id=liste_pays_id.get(position);
	}
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}


	// Return here when file selected from camera or from SDcard
		public static String getPath(Context context, Uri uri) throws URISyntaxException {
		    if ("content".equalsIgnoreCase(uri.getScheme())) {
		        String[] projection = { "_data" };
		        Cursor cursor = null;

		        try {
		            cursor = context.getContentResolver().query(uri, projection, null, null, null);
		            int column_index = cursor.getColumnIndexOrThrow("_data");
		            if (cursor.moveToFirst()) {
		                return cursor.getString(column_index);
		            }
		        } catch (Exception e) {
		            // Eat it
		        }
		    }
		    else if ("file".equalsIgnoreCase(uri.getScheme())) {
		        return uri.getPath();
		    }

		    return null;
		}  


		public void showDatePickerDialog(View v) {
		    DialogFragment newFragment = new DatePickerFragment();
		    newFragment.show(getSupportFragmentManager(), "datePicker");
		}
		
 

		@SuppressLint("ValidFragment")
		public class DatePickerFragment extends DialogFragment  implements DatePickerDialog.OnDateSetListener {
		    private int year;
		    private int month;
		    private int day;
		    
		    @Override
		    public Dialog onCreateDialog(Bundle savedInstanceState) {
		        // Use the current date as the default date in the picker
		        final Calendar c = Calendar.getInstance();
		         year = c.get(Calendar.YEAR);
		         month = c.get(Calendar.MONTH);
		         day = c.get(Calendar.DAY_OF_MONTH);

		        // Create a new instance of DatePickerDialog and return it
		        return new DatePickerDialog(getActivity(), this, year, month, day);
		    }

		    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
		        // Do something with the date chosen by the user       
		       year  = selectedYear;
		       month = selectedMonth;
		       day   = selectedDay;	  	 
	           // Show selected date
	   dateChoice.setText(new StringBuilder().append(month + 1).append("-").append(day).append("-").append(year).append(" "));
		 }
		} 
		
		

		public void FoundLogin(String login){
			incr++; 
			//callFocus(incr,login);
	 	}

			 
			
	 	
	public void  setLogin(String loginGenerate)
	{
	login.setText(loginGenerate);
	}


	 private void getContry()
	 {
		 envoye="getContry";
			pDialog.setMessage(R.string.st_dialog_createCompt+"");
			pDialog.setCancelable(false);
		 
		try
		{  	 		
			jsonObjSend.put("pays_iso",pays_iso); 

			makeJsonObjReq(jsonObjSend,Const.urlContry+"getContry",""); 

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	 
	 }
	private void getListContry()
	{
		envoye="getListContry";
		showProgressDialog();

		try
		{
			jsonObjSend.put("pays_iso","");

			makeJsonObjReq(jsonObjSend,Const.urlContry+"getListContry","");

		} catch (JSONException e) {
			e.printStackTrace();
		}


	}
}
