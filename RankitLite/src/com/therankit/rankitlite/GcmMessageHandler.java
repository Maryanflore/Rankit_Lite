package com.therankit.rankitlite;

/**
 * Created by nirav on 28-10-2014.
 */

import android.app.IntentService;
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
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.therankit.services.detailService;
import com.volley.Const;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class GcmMessageHandler extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    Context context;
    Bitmap icon;
    PendingIntent contentIntent;
    private NotificationCompat.Builder notificationBuilder;
    private SharedPreferences settings;
    private static final String PROJECT_ID = "226989288654";

    public GcmMessageHandler() {

        super(PROJECT_ID);
        //super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        this.context = this;
        final Bundle extras = intent.getExtras();
        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle

            sendNotification(extras, this);
            settings = getSharedPreferences(Const.PREFRENCES_NAME, Context.MODE_PRIVATE);
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
       // GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(final Bundle response, final Context context) {

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        new generatePictureStyleNotification(this, response.getString("randkiteUser_name"), response.getString("message"), response.getString("image_url"), notificationManager,response.getString("randkiteUser_id"),response.getString("entreprise_id"),response.getString("entreprise_logo"),response.getString("entreprise_name"),response.getString("secteur_id"),response.getString("randkiteUser_name"),response.getString("randkiteUser_picture")).execute();
    }


    public class generatePictureStyleNotification extends AsyncTask<String, Void, Bitmap> {

        private Context mContext;
        private String title, message, imageUrl,randkiteUser_id,entreprise_id,entreprise_logo,entreprise_name,secteur_id,name_user,randkiteUser_picture;
        private NotificationManager notificationManager;
        public generatePictureStyleNotification(Context context, String title, String message, String imageUrl,NotificationManager notificationManager, String randkiteUser_id,String entreprise_id,String entreprise_logo,String entreprise_name,String secteur_id,String name_user,String randkiteUser_picture) {
            super();
            this.mContext = context;
            this.title = title;
            this.message = message;
            this.imageUrl = imageUrl;
            this.notificationManager=notificationManager;
            this.randkiteUser_id=randkiteUser_id;
            this.entreprise_id=entreprise_id;
            this.entreprise_logo=entreprise_logo;
            this.entreprise_name=entreprise_name;
            this.secteur_id=secteur_id;
            this.name_user=name_user;
            this.randkiteUser_picture=randkiteUser_picture;

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

            icon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_launcher);

            mNotificationManager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);

            /*contentIntent = PendingIntent.getActivity(mContext, 0,
                    new Intent(mContext, MainActivity.class).putExtra("is_from_notification", true), 0);*/
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
            builder = new NotificationCompat.Builder(context)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setLargeIcon(icon)
                    .setContentTitle(title)
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(result))
                    .setContentText(message
                    );
            //Intent.FLAG_ACTIVITY_CLEAR_TOP
            //D�finition de la redirection au moment du clic sur la notification. Dans notre cas la notification redirige vers notre application
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            //Récupération du titre et description de la notfication
            String notificationTitle =name_user;
            String notificationDesc = message;
            //Value indicates the current number of events represented by the notification
            //	notification.number=++count;

            //Prepare Notification Builder
            notificationBuilder = new NotificationCompat.Builder(mContext)
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
            builder.setContentIntent(contentIntent);
            mNotificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }
}