package com.therankit.rankitlite;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.google.android.gcm.GCMBaseIntentService;
import com.sqlLite.MySQLiteGestion;
import com.therankit.home.HomeGrid;
import com.therankit.services.detailService;
import com.url.AppController;
import com.volley.Const;
import com.volley.JsonUTF8Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class GCMIntentService extends GCMBaseIntentService
{
	private static final String LOG_TAG = "GCMIntentService";
	private static final String PROJECT_ID = "226989288654";
	private static int count=0,incr=0;
	private String tag_json_obj;
	private SharedPreferences settings;
	private JSONObject jsonObjSend;
	private String idGCM;
	MySQLiteGestion livreBdd;  
	private  Date d;
	private SimpleDateFormat formater;
	private String envoye="";
	private NotificationCompat.Builder notificationBuilder;

	NotificationCompat.Builder builder;
	Context context;
	Bitmap icon;
	PendingIntent contentIntent;

	/**
	 * Constructeur
	 */
	public GCMIntentService()
	{
		//Définition du Project ID
		super(PROJECT_ID);
	}
 
	/**
	 * Réception d'une erreur lors d'un enregistrement ou d'une déconnexion au service
	 *
	 * @param context Contexte de l'application
	 * //@param Identifiant de l'erreur retourné par le service GCM
	 */
	@Override
	protected void onError(Context context, String errorId)
	{
		Log.d(LOG_TAG, "Erreur reçue : " + errorId);
	}
 
	/**
	 * Réception d'un message
	 *
	 * @param context Contexte de l'application
	 * @param intent Intent Contenant le message
	 */
	@Override
	protected void onMessage(Context context, Intent intent)
	{
		Log.d(LOG_TAG, "Message r��u");
		livreBdd = new MySQLiteGestion(this);     
 		 livreBdd.open();
 		d=new Date();
		new Locale("FR","fr");
		formater = new SimpleDateFormat("EEE hh:mm", Locale.getDefault());
		formater.format(d);
		  settings = getSharedPreferences(Const.PREFRENCES_NAME, Context.MODE_PRIVATE);
		  idGCM=settings.getString("idGCM", ""); 
		
	    	if(intent.getStringExtra("projet").equals("sendNotes"))
	   	 	{
	    		
	    	
	    		
	    	/*
	    	 * 
	    	 * condiction permettant de signaler une notification lorqu'on est pas entrain
	    	 * 
	    	 * de causer avec l'emeteur
	    	 */
	    			
	    			 NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
	    		    	
		  					 
                 generateNotification(notificationManager,context,intent.getStringExtra("message"), intent.getStringExtra("randkiteUser_id"), intent.getStringExtra("entreprise_id"),intent.getStringExtra("entreprise_logo"),intent.getStringExtra("entreprise_name"),intent.getStringExtra("secteur_id"),intent.getStringExtra("randkiteUser_name"),intent.getStringExtra("randkiteUser_picture"));








			}

		if(intent.getStringExtra("projet").equals("anonce")) {
			NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

			new generatePictureStyleNotification(context,   intent.getStringExtra("image_url"),notificationManager, intent.getStringExtra("entreprise_id"),intent.getStringExtra("entreprise_logo"),intent.getStringExtra("entreprise_name"),intent.getStringExtra("secteur_id")).execute();


		}
			/**
             *
             *     debut bloc traitant  les messages rexu par l'expecditeur
             */
	   if(intent.getStringExtra("projet").equals("etalkReponseRecption"))
	   	 	{
		   
			   try {
				livreBdd.updateStatutRexu(Integer.parseInt(intent.getStringExtra("idMobile")), 
						intent.getStringExtra("DateRecp"), "2");
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			   sendGCMIntentMsgRecu(context,Integer.parseInt(intent.getStringExtra("idMobile")));
		   
	   	 	}
	   
	   
	   /*informe le correspondant que l'autre effectue une saisis*/
	   else if(intent.getStringExtra("projet").equals("infosEtalkSaisi"))
	   {
		   sendGCMIntentInfosSaisis(context,Integer.parseInt(intent.getStringExtra("idContact")));   
	   }

	   /*informe le correspondant que l'autre est en ligne*/
	   else if(intent.getStringExtra("projet").equals("infosEtalkStatut"))
	   {
		   RetourReponseconnection(Integer.parseInt(intent.getStringExtra("idWriter")));
	   }

	   /*traitement de la reponse en provenant du corespodant pour voir si oui ou non il est en ligne*/
	   else if(intent.getStringExtra("projet").equals("infosEtalkReponseStatut"))
	   {
		   sendGCMIntentInfosReponseSaisis(context);
	   }
	   

	   /*traitement des messages qui on �t� vu par le receipteur du message*/
	   else if(intent.getStringExtra("projet").equals("infosReceiptMsgSend"))
	   {
		   sendGCMIntentInfosReceiptMsgSend(context,Integer.parseInt(intent.getStringExtra("idMobileMsg")));
	   }
	   /**
		 * 
		 *     fin bloc traitant les notifications gerant les messages rexu par l'expecditeur	
		 */
 livreBdd.close();
	}
	
	private void RetourReponseconnection(int idWriter)
	{
		envoye="EnvoieStatut";
		JSONObject json=new JSONObject();
		try { 
			json.put("idWriter", idWriter);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		makeJsonObjReq(json,"");
	}

	
private void sendGCMIntentInfosReceiptMsgSend(Context ctx,int idMobile) {
		
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("GCM_RECEIVED_ACTION");
		broadcastIntent.putExtra("TypeMsg", "reponseReceiptMsg");
		broadcastIntent.putExtra("idMobileMsg",idMobile);  
		ctx.sendBroadcast(broadcastIntent);
		
	}



private void sendGCMIntentInfosReponseSaisis(Context ctx ) {
		
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("GCM_RECEIVED_ACTION");
		broadcastIntent.putExtra("TypeMsg", "reponseStatut"); 
		ctx.sendBroadcast(broadcastIntent);
		
	}


private void sendGCMIntent(Context ctx,int idMobile,int idWeb, String message,int idE,int idR,String DateEnv,String DateRecp,String imageEmetteur) {
		
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("GCM_RECEIVED_ACTION");
		broadcastIntent.putExtra("TypeMsg", "MsgEmetteur");
		broadcastIntent.putExtra("idMobile", idMobile);
		broadcastIntent.putExtra("idWeb", idWeb);
		//broadcastIntent.putExtra("message",EncodeDecode.DecodeString(message) );
		broadcastIntent.putExtra("message", message);
		broadcastIntent.putExtra("idE", idE);
		broadcastIntent.putExtra("idR", idR);
		broadcastIntent.putExtra("DateEnv", DateEnv);
		broadcastIntent.putExtra("DateRecp", DateRecp);
		broadcastIntent.putExtra("imageEmetteur", imageEmetteur);
		
		ctx.sendBroadcast(broadcastIntent);
		
	}

private void sendGCMIntentInfosSaisis(Context ctx,int idContact) {
	
	Intent broadcastIntent = new Intent();
	broadcastIntent.setAction("GCM_RECEIVED_ACTION");
	broadcastIntent.putExtra("TypeMsg", "infoSaisi"); 
	broadcastIntent.putExtra("idContact",idContact);    
	
	ctx.sendBroadcast(broadcastIntent);
	
}

private void sendGCMIntentMsgRecu(Context ctx,int idMobile) {
	
	Intent broadcastIntent = new Intent();
	broadcastIntent.setAction("GCM_RECEIVED_ACTION");
	broadcastIntent.putExtra("TypeMsg", "MsgRexu");
	broadcastIntent.putExtra("idMobile", idMobile);
	
	ctx.sendBroadcast(broadcastIntent);
	
}

/*
 * 
 * fin methode permettant d'envoyer le message dans l'activite de etalk
 * 
 * 
 */

	/**
	 * Enregistrement au service GCM
	 *
	 * @param context Contexte de l'application
	 * @param registrationId Identifiant d'enregistrement
	 */
	@Override
	protected void onRegistered(Context context, String registrationId)
	{
		Log.d(LOG_TAG, "Enregistrement au service GCM avec identifiant " + registrationId);
	}
 
	/**
	 * Déconnexion du service GCM
	 *
	 * @param context Contexte de l'application
	 * @param registrationId Identifiant d'enregistrement
	 */
	@Override
	protected void onUnregistered(Context context, String registrationId)
	{
		Log.d(LOG_TAG, "Déconnexion du service GCM de l'identifiant "+ registrationId);
	} 
	/**
	 * Génération de la notification
	 *
	 * @param context Contexte de l'application
	 * @param message Message reçu
	 */
	@SuppressWarnings("deprecation")
	private  void generateNotification(NotificationManager notificationManager,Context context, String message, String randkiteUser_id,String entreprise_id,String entreprise_logo,String entreprise_name,String secteur_id,String name_user,String randkiteUser_picture)
	{
		//Récupération du notification Manager
    	
    	//Création de la notification avec spécification de l'icone de la notification et le texte qui apparait à la création de la notfication
    	// Notification notification = new Notification(R.drawable.ic_launcher,  "Vous avez reçu un nouveau message", System.currentTimeMillis());
    	 
    	   Intent intent=new Intent(context, detailService.class);
    	 
    	   if(message.equals("0"))
    	   {
    		   message="";
    	   }
    	    settings.edit() .putString("sourceNoteS","1") .commit();
    	    settings.edit() .putString("profilSendNote",randkiteUser_picture) .commit();
    	    settings.edit() .putString("messageService",message) .commit();
    		intent.putExtra("randkiteUser_id",Integer.parseInt(randkiteUser_id));
    		intent.putExtra("entreprise_id",Integer.parseInt(entreprise_id));
    		intent.putExtra("entreprise_logo",entreprise_logo);
    		intent.putExtra("entreprise_name",entreprise_name);
    		intent.putExtra("secteur_id",secteur_id);
    		
    		//Intent.FLAG_ACTIVITY_CLEAR_TOP
    	//D�finition de la redirection au moment du clic sur la notification. Dans notre cas la notification redirige vers notre application 
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
    	
    	//Récupération du titre et description de la notfication 
    	 String notificationTitle =name_user;
    	 String notificationDesc = message; 
    	 //Value indicates the current number of events represented by the notification
    //	notification.number=++count;

		//Prepare Notification Builder
		notificationBuilder = new NotificationCompat.Builder(this)
				.setContentTitle(notificationTitle).setSmallIcon(R.drawable.ic_launcher).setContentIntent(pendingIntent)
				.setContentText(notificationDesc)
		         .setAutoCancel(true);
				 //.addAction(R.drawable.ic_launcher, "Call", pendingIntent)
				 //.addAction(R.drawable.ic_launcher, "More", pendingIntent);
		//add sound
		Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		notificationBuilder.setSound(uri);
		//vibrate
		long[] v = {500,1000};
		notificationBuilder.setVibrate(v);
		notificationManager.notify(1, notificationBuilder.build());

        
	}


    private void deleteNotification(){
    	count=0;
    	final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    	//la suppression de la notification se fait grâce a son ID
    	notificationManager.cancel(2015);
    	
    }
	public class generatePictureStyleNotification extends AsyncTask<String, Void, Bitmap> {

		private Context mContext;
		private String title, message, imageUrl,randkiteUser_id,entreprise_id,entreprise_logo,entreprise_name,secteur_id;
		private NotificationManager notificationManager;
		public generatePictureStyleNotification(Context context,  String imageUrl,NotificationManager notificationManager, String entreprise_id,String entreprise_logo,String entreprise_name,String secteur_id) {
			super();
			this.mContext = context;

			this.imageUrl = imageUrl;
			this.notificationManager=notificationManager;
this.title=entreprise_name;
			this.message=entreprise_name;
			this.entreprise_id=entreprise_id;
			this.entreprise_logo=entreprise_logo;
			this.entreprise_name=entreprise_name;
			this.secteur_id=secteur_id;


		}

		@Override
		protected Bitmap doInBackground(String... params) {

			InputStream in;
			try {
				URL url = new URL(this.imageUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoInput(true);
				connection.connect();
				in = connection.getInputStream();
				Bitmap myBitmap = BitmapFactory.decodeStream(in);
				return myBitmap;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}


		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);



            /*contentIntent = PendingIntent.getActivity(mContext, 0,
                    new Intent(mContext, MainActivity.class).putExtra("is_from_notification", true), 0);*/

			settings.edit()
					.putString("sen_rotation","1")
					.commit();
			Intent intent = new Intent(mContext, HomeGrid.class);
	PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);




			notificationBuilder = new NotificationCompat.Builder(mContext)
					.setSmallIcon(R.drawable.ic_launcher).setContentIntent(pendingIntent)
					.setContentTitle(title)
					.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(result))
					.setContentText(message
					)
					.setAutoCancel(true);

			Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			notificationBuilder.setSound(uri);
			//vibrate
			long[] v = {500,1000};
			notificationBuilder.setVibrate(v);
			notificationManager.notify(1, notificationBuilder.build());


		}
	}
    
    /*****
     * 
     * debut partie traitant les messages pour signaler au recpteur la recpetion de son msg		 
     */
    		 
    		
    		 
    /**
	 * Making json object request
	 * */
	private void makeJsonObjReq(JSONObject json,String url) { 
		JsonUTF8Request jsonObjReq = new JsonUTF8Request(Method.POST,
				url, json,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, response.toString());
						
						if(envoye.equals("Enre_etalkNewmsg")){						
								try {
									 JSONObject jsonObjSend = null;
										
											jsonObjSend = new JSONObject(response.toString());
										
									
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} 
								
						}
						
					}
					
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						/*
						if(id_end.equals("0"))
						{
							Toast.makeText(getBaseContext(), getString(R.string.st_connection_failled), Toast.LENGTH_LONG).show();												
						}
						*/
						//createDialog("Error",error.getMessage());
						 
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
    		 
	
	
	
	 public void SetMsgRexu(int idWeb,int idMobile,int idEmeteur,String dateRecpt)
	 {
		 envoye="Enre_etalkNewmsg";
		 jsonObjSend = new JSONObject();
			try {  	 
				jsonObjSend.put("",idWeb);
				jsonObjSend.put("",idMobile); 
				jsonObjSend.put("",idEmeteur); 
				jsonObjSend.put("",dateRecpt);
				//makeJsonObjReq(jsonObjSend,"http://www.e-yamo.com/mobile_gestion_compte/connexion/dologin");
				makeJsonObjReq(jsonObjSend,"");
		        			
			} catch (JSONException e) {
				e.printStackTrace();
			}
	 }
    		 
    		 /*****
    		     * 
    		     * fin partie traitant les messages pour signaler au recpteur la recpetion de son msg		 
    		     */
    		    		 
}