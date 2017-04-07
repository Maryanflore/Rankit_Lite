package com.therankit.community;


import java.util.ArrayList;  

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.load.img.ImageLoaderProfil;
import com.therankit.rankitlite.R;
import com.url.AppController; 
import com.volley.Const;
import com.volley.EncodeDecode;

import android.widget.LinearLayout;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener; 
import android.view.ViewGroup;
import android.widget.BaseAdapter; 
import android.widget.ImageView;
import android.widget.TextView; 
public class MySecondeAdapter extends BaseAdapter {

        private ArrayList<BeanRow> liste;
        private OnClickListener click;
        private LayoutInflater inflater;
        private int idView;
        private Holder holder; 
        private Activity activity; 
        public ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        public ImageLoaderProfil imageProfil;
        public MySecondeAdapter(Activity a, int rowTourne,ArrayList<BeanRow> grpBr, OnClickListener onClick) {
        		activity = a; 
                liste = grpBr;
                click = onClick; 
                idView = rowTourne;
                inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            	imageLoader = AppController.getInstance().getImageLoader();
            	imageProfil=new ImageLoaderProfil(a);
        }

        /**
         * Ici on recupere la vue ou on la cree
         */
        @SuppressWarnings({ "deprecation", "deprecation" })
		public View getView(int position, View view, ViewGroup parent) {
                if (view == null) {
                        holder = new Holder();
                        view = inflater.inflate(idView, null); 
                        holder.rowNomPublieur = (TextView) view.findViewById(R.id.name); 
                        holder.rowDate=(TextView)view.findViewById(R.id.timestamp); 
                        holder.rowTitre=(TextView)view.findViewById(R.id.txtStatusMsg);
                        holder.rowTaille=(TextView)view.findViewById(R.id.taille); 
                        holder.rowCountComment = (TextView)view.findViewById(R.id.nbre_comment);
                        holder.rowLibelePartatger = (TextView)view.findViewById(R.id.partager);
                		holder.rowImageProfil = (ImageView) view.findViewById(R.id.profilePic);
                		holder.rowImagePublier = (FeedImageView) view.findViewById(R.id.feedImage1);
                        holder.rowCommentbutton = (ImageView)view.findViewById(R.id.iconComment);
                        holder.rowSharebutton = (ImageView)view.findViewById(R.id.iconShare);
                        
                        holder.linearLayout = (LinearLayout)view.findViewById(R.id.dessus);
                        
                        //la vue etant cree, nous rajoutons le listener

                        holder.rowLibelePartatger.setOnClickListener(click);
                        holder.rowCountComment.setOnClickListener(click);
                        holder.rowImagePublier.setOnClickListener(click);
                        holder.rowCommentbutton.setOnClickListener(click);
                        holder.rowSharebutton.setOnClickListener(click); 
                         
                        view.setTag(holder);
                } else {
                        holder = (Holder) view.getTag();
                }
                /** je donne la position a holder, plus facile a recuperer dans le listener */
                holder.position = position;
                
                holder.rowCommentbutton.setVisibility(view.VISIBLE);
                //holder.rowSharebutton.setVisibility(view.VISIBLE);
                
                holder.rowNomPublieur.setText(liste.get(position).getNomPublieur()); 
                holder.rowTitre.setText(liste.get(position).getTitreArticle()); 
                holder.rowDate.setText(liste.get(position).getDate()); 
                holder.rowCountComment.setText(" "+liste.get(position).getIdCmde()); 

                //holder.rowImageProfil.setImageUrl(Const.ipUriImageUsers+liste.get(position).getImageProfil(), imageLoader);
                //imageLoader.get(Const.ipUriImage+liste.get(position).getImageProfil(),ImageLoader.getImageListener(holder.rowImageProfil, R.drawable.ic_launcher, R.drawable.ic_launcher));
                imageProfil.DisplayImage(Const.ipUriImage+liste.get(position).getImageProfil(), holder.rowImageProfil);
                //holder.rowImagePublier.setImageUrl(Const.ipUriImage+liste.get(position).getImagePublier(), imageLoader);
        		
        		if (!liste.get(position).getImagePublier().equals("")) {
        			holder.rowImagePublier.setImageUrl(Const.ipUriLogo+liste.get(position).getImagePublier(), imageLoader);
        			holder.rowImagePublier.setVisibility(View.VISIBLE);
        			holder.rowImagePublier.setResponseObserver(new FeedImageView.ResponseObserver() {
        						@Override
        						public void onError() {
        						}

        						@Override
        						public void onSuccess() {
        						}
        					});

        			holder.rowTaille.setVisibility(View.GONE);
        		} else {
        			holder.rowImagePublier.setVisibility(View.GONE);
        			holder.rowTaille.setVisibility(View.VISIBLE);
        		}
                
                return view;
        }

        private Drawable switchIcon(int pos) {
                Drawable icon = null;
                switch (pos) {
                case 0:
                        icon = activity.getResources().getDrawable(R.drawable.ic_launcher);
                        break;
                case 1:
                        icon = activity.getResources().getDrawable(R.drawable.ic_launcher);
                        break;
                }
                return icon;
        }

        public int getCount() {
                return liste.size();
        }

        public BeanRow getItem(int position) {
                return liste.get(position);
        }

        public long getItemId(int position) {
                return position;
        }

}
