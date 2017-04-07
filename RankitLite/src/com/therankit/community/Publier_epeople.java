package com.therankit.community;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.app.AlertDialog;
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
import android.util.Log; 
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener; 
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner; 
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
import com.reponse.Reponse;
import com.therankit.rankitlite.R;
import com.url.AppController; 
import com.volley.Const;
import com.volley.JsonUTF8Request;

public class Publier_epeople extends Activity implements OnItemSelectedListener{
	MultipartEntity mEntity;
	 private static RequestQueue mQueue;
	
	 private String file_path;   

		
		private int incrConnexion=1;
		ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	int serverResponseCode = 0;
	private Spinner monnaie;
	private Spinner categuorie;
	private Spinner sous_categuorie; 
	private EditText descriptionArticle,prix,titreArticle;
	private Button envoyer;
	private Uri mCapturedImageURI = null;
	private static final int FILECHOOSER_RESULTCODE   = 2888;
	  
	private ProgressDialog pDialog;
	private List<String> list = new ArrayList<String>();
	private List<String> listCateguorie = new ArrayList<String>();
	private List<String> listSousCateguorie = new ArrayList<String>(); 
	private ImageView publierImage; 
	private String nameImage="";
	private String idUtilisateur;
	private String TitreText;
	private ImageView imageview;
	private String codeVue;
	private String []TabSou_Cat;
	private String TroncatDB;
	private String URLUpload;	
	private String TAG = Publier_epeople.class.getSimpleName();
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
private EditText publication;
	private LinearLayout hideShowLinearLayout;
	private LinearLayout hideShowLinearLayout2;
	private ImageView imagepublie;
	private JSONObject jsonObjSend;
	private ImageView publierImage2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_publi_epeople);  
	    mQueue = Volley.newRequestQueue(getApplicationContext());

		 jsonObjSend = new JSONObject();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); 

		// just to get the look of facebook (changing background color & hiding the icon)
		//getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#73C2FB")));
		//getSherlockActivity().getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		//getSupportActionBar().setIcon(R.drawable.ic_maps_directions_walk_blanc);
		//getSupportActionBar().setTitle("QUOI DE 9 ?");
    	pDialog = new ProgressDialog(this); 
    	Bundle extras=getIntent().getExtras(); 
    	idUtilisateur=new String(extras.getString("idUtilisateur"));

    	 publication = (EditText)findViewById(R.id.publication); 

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
			 
		
			envoyer = (Button) findViewById(R.id.publierButon);
			
			envoyer.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) { 
					 
						if((publication.getText().toString().equals("")))
						{
							Toast.makeText(getBaseContext(), "Veuillez renseigner tous les champs !", Toast.LENGTH_LONG).show();	
						}
						else
						{   
							if(tesConnection())							     
								{
								envoyer.setEnabled(false);
								 publier();
								}
							 else
								{
								Toast.makeText(getBaseContext(),""+getString(R.string.st_toast_error_connection), Toast.LENGTH_LONG).show();	
				                }
						 }
		       	  
				}
		       	    });
			
			 
	} 
	
	public boolean tesConnection()
	{
		boolean test=true; 
		 ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
         if ( cm.getActiveNetworkInfo() == null ) {
        	 test=false;
         } 
		return test;
	}
	
	public void  selectImage(){
		final CharSequence[] options = {getString(R.string.pictureChoice1),getString(R.string.pictureChoice2),getString(R.string.pictureChoice3) };
		AlertDialog.Builder builder = new AlertDialog.Builder(Publier_epeople.this);

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
                    
                 	File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "eyamo_pictures");
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
                //createDialog("picturePath",type+"--width="+width+"--height="+height);
               // imageview.setImageBitmap(thumbnail);
                
             	File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "eyamo_pictures");
                if (!imageStorageDir.exists()) {
                    imageStorageDir.mkdirs();
                }  
                /*
                String tab[]=picturePath.split("/");
                String tabEnd=tab[tab.length-1];
                */

                File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");               
                nameImage=file.getAbsolutePath();
                
                Log.w("path of image from gallery......******************.........", picturePath+"");
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
   //createDialog("nameImage", "-----"+nameImage);  
		try { 
			String TabPathImg []=nameImage.split("/"); 	
            String TabPathImgPoint=TabPathImg[TabPathImg.length-1].replace("  ", "");
            String TabPathImgSend=TabPathImgPoint.replace(" ", "");
            
			jsonObjSend.put("",publication.getText().toString().trim());				       			 
	       	jsonObjSend.put(Const.image,""+TabPathImgSend);
			jsonObjSend.put("",idUtilisateur);
	    	makeJsonObjReq(jsonObjSend,"",nameImage);	
		   } 
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
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
						}
						incrConnexion++;
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
	private void parseJsonFeed(JSONObject response,String  file) {
		try { 
				jsonObjSend = new JSONObject(response.toString()); 
				 if((jsonObjSend.getBoolean(Reponse.retourVerifUser))&&(!file.equals("")))
				 {
					//Toast.makeText(getBaseContext(), ""+jsonObjSend.getString(Reponse.retour), Toast.LENGTH_LONG).show();

					 file_path=file;
						Thread thread = new Thread(entityRunnable);
		                thread.start();
				 } 
				 publication.setText("");
                 imageview.setImageResource(R.drawable.icone_ajout);	
					Toast.makeText(getBaseContext(), ""+jsonObjSend.getString(Reponse.retour), Toast.LENGTH_LONG).show();

					 envoyer.setEnabled(true);
			hideProgressDialog(); 
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
				mEntity.addPart("file", new InputStreamBody(new FileInputStream(file),""+TabPathImgSend) );
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

    private void testImagePost(){
            MyImageRequest myImgRequest = new MyImageRequest(Const.ipUriUploadImage, myListener, myErrorListener);

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

     imageview.setImageResource(R.drawable.ic_action_add);
 Toast.makeText(getBaseContext(), ""+jObj.getString(Reponse.retour), Toast.LENGTH_LONG).show(); 
 envoyer.setEnabled(true);        
   }
   else
   {
   Toast.makeText(getBaseContext(), ""+jObj.getString(Reponse.retour), Toast.LENGTH_LONG).show();
   publication.setText("");   
   }
	 publication.setText(""); 
	 envoyer.setEnabled(true);
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
	 
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
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

 
/*

		    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			
			
			if(requestCode==FILECHOOSER_RESULTCODE)  
			{  
			 createDialog("nameImage",nameImage+"----"+data.getDataString());

				Bitmap bmp2 = BitmapFactory.decodeFile(data.getDataString()); 
				imageview.setImageBitmap(bmp2);    
	    		//compressImage(nameImage);
	    		
			   try{			           
				   		Uri uri = data.getData();
	 					String path = getPath(getBaseContext(), uri);
	 					Bitmap bmp = BitmapFactory.decodeFile(path); 
	 					imageview.setImageBitmap(bmp); 
					 
			    		nameImage=path;	
			    		compressImage(nameImage);
			        } 
			    
		        catch(Exception e)
		        {
		            //Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
					 createDialog("catchnameImage",""+data.getDataString());
		        }
		        
			  // Toast.makeText(getApplicationContext(), ""+nameImage, Toast.LENGTH_LONG).show();
		 }
	 
		 super.onActivityResult(requestCode, resultCode, data);
		}
 
		*/

 
		
}
