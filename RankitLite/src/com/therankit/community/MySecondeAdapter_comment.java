package com.therankit.community;
import java.util.ArrayList; 

import com.load.img.ImageLoaderProfil;
import com.therankit.rankitlite.R;
import com.url.AppController;
import com.volley.Const;
import com.volley.EncodeDecode;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView; 
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
public class MySecondeAdapter_comment extends BaseAdapter {

        private ArrayList<BeanRow_comment> liste;
        private OnClickListener click;
        private LayoutInflater inflater;
        private int idView;
        private Holder_comment holder;
        private String image; 
        private Context activity;
        private ImageLoaderProfil imageLoaderProfil; 

        private ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        

        public MySecondeAdapter_comment(Context a, int rowTourne,ArrayList<BeanRow_comment> grpBr) {
        		activity = a; 
                liste = grpBr;  
                idView = rowTourne; 
                inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                imageLoaderProfil=new ImageLoaderProfil(activity);
        }

        /**
         * Ici on recupere la vue ou on la cree
         */ 
		public View getView(int position, View view, ViewGroup parent) {
                if (view == null) {
                        holder = new Holder_comment();
                        view = inflater.inflate(idView, null); 
                        holder.rowNomPublieur = (TextView) view.findViewById(R.id.nameCommenter); 
                        holder.rowDate=(TextView)view.findViewById(R.id.dateComment); 
                        holder.rowCommentaire=(TextView)view.findViewById(R.id.comment); 
                        holder.rowImageProfil=(ImageView)view.findViewById(R.id.imgCommenter); 
                          
                        view.setTag(holder);
                } else {
                        holder = (Holder_comment) view.getTag();
                }
                /** je donne la position a holder, plus facile a recuperer dans le listener */
                holder.position = position; 
         
               // holder.rowImageProfil.setImageUrl(Const.ipUriImageUsers+liste.get(position).getImageProfil(), imageLoader);               
                
                imageLoaderProfil.DisplayImage(Const.ipUriImageUsers+liste.get(position).getImageProfil(), holder.rowImageProfil);
                
                holder.rowNomPublieur.setText(liste.get(position).getNomPublieur());  
                holder.rowDate.setText(liste.get(position).getDate()); 
                holder.rowCommentaire.setText(liste.get(position).getDescription()); 
             
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

        public BeanRow_comment getItem(int position) {
                return liste.get(position);
        }

        public long getItemId(int position) {
                return position;
        }

}
