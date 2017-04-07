package com.therankit.home;
 
 
import com.load.img.ImageLoaderProfil;
import com.therankit.rankitlite.R;

import android.content.Context; 
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
  

public class MenuListAdapter extends BaseAdapter{

	    private Context context; 
	    private String[] mtitle;
	    private String[] msubTitle;
	    private int[] micon;
	    private LayoutInflater inflater;
        public ImageLoaderProfil imageLoader;
		  
        
	 public  MenuListAdapter(Context context,String title[], String subtilte[], int icon[])
	 {
		 this.context=context;
		 this.mtitle=title;
		 this.msubTitle=subtilte;
		 this.micon=icon; 
		 this.imageLoader=new ImageLoaderProfil(context.getApplicationContext());
		 
	 }

			
	@Override
	public int getCount() {
		return mtitle.length;
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
 
	@Override
	public View getView(int position, View arg1, ViewGroup parent) {
	
		TextView title;
		TextView subtitle;
		ImageView icon;
		
		ImageView photos;
		TextView tel;
		TextView nomPrenm;
		TextView mail;
		LinearLayout linearLayout1;
		LinearLayout linearLayout2;
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.drawer_list_item, parent, false);
 
    	 
       // icon = (ImageView) itemView.findViewById(R.id.icon);
        linearLayout1 = (LinearLayout)itemView.findViewById(R.id.LinearLayout1);
        linearLayout2 = (LinearLayout)itemView.findViewById(R.id.LinearLayout2);
        
        if(mtitle[position].equals("Home"))
        {
 
        	linearLayout1.setVisibility(View.VISIBLE);             	

        	tel = (TextView) itemView.findViewById(R.id.telephone);
        	mail = (TextView) itemView.findViewById(R.id.mail);
        	nomPrenm = (TextView) itemView.findViewById(R.id.nomPrenon);
        	photos = (ImageView) itemView.findViewById(R.id.photos);
        	
        	String tab[]=msubTitle[position].split("###");

        	mail.setText(tab[3]);
        	mail.setTextColor(Color.parseColor("#000000")); 
        	
        	
        	tel.setText(tab[2]);
        	tel.setTextColor(Color.parseColor("#666666"));
        	
            nomPrenm.setText(tab[1]);
            nomPrenm.setTextColor(Color.parseColor("#000000")); 
            
            imageLoader.DisplayImage(tab[0],photos);
   
        }
        else
        {

        	linearLayout1.setVisibility(View.GONE); 
        	linearLayout2.setVisibility(View.VISIBLE); 
        	

            title = (TextView) itemView.findViewById(R.id.title);
            subtitle = (TextView) itemView.findViewById(R.id.subtitle); 
            icon = (ImageView) itemView.findViewById(R.id.icon);
            
            title.setText(mtitle[position]);
            subtitle.setText(msubTitle[position]); 
            icon.setImageResource(micon[position]);
        }
        
 
        return itemView;
		
	}

 
}
