package com.load.img;
  
import com.therankit.rankitlite.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private String[] Tabdate;
    private String[] TabTitre;
    private String[] TabDescrip;
    private String[] files;
 
    
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    
    public LazyAdapter(Activity a,String[] titre) {
    	notifyDataSetChanged();
        activity = a; 
        TabTitre=titre; 
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }
    
    public LazyAdapter(Activity a, String[] img, String[] titre, String[] description, String[] date) {
    	notifyDataSetChanged();
        activity = a;
        files=img;
        TabTitre=titre;
        TabDescrip=description;
        Tabdate=date;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return TabTitre.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    
    public void clearAdapter()
    {
    	
        notifyDataSetChanged();
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        notifyDataSetChanged();
        if(convertView==null)
            vi = inflater.inflate(R.layout.affichagenews, null);

        TextView titre=(TextView)vi.findViewById(R.id.titre);
       // TextView description=(TextView)vi.findViewById(R.id.description);
        // TextView date=(TextView)vi.findViewById(R.id.date); 
        TextView source=(TextView)vi.findViewById(R.id.source); 
        ImageView image=(ImageView)vi.findViewById(R.id.img);
        
        titre.setText(TabDescrip[position]);
        //description.setText(TabDescrip[position]);
        //date.setText(Tabdate[position]);
        source.setText(TabTitre[position]);
        
        imageLoader.DisplayImage(files[position], image);
        return vi;
    }
}