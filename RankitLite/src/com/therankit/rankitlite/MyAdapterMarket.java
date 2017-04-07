package com.therankit.rankitlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.url.AppController;
import com.volley.Const;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterMarket extends BaseAdapter
{
   
    private LayoutInflater inflater; 
	private int i;   
    private List<Integer> tabGridIdEntreprise;
    private List<String> tabGridNameEntreprise;
    private List<Integer> tabGridIdSecteur;
    private List<String> tabGridUriLogo;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    
	private ArrayList<Item> items;

    public MyAdapterMarket(Context activity,List<Integer> tabGridIdEntreprise,
    		List<String> tabGridNameEntreprise,List<Integer> tabGridIdSecteur
    		,List<String> tabGridUriLogo)
    { 
        inflater = LayoutInflater.from(activity);
        items = new ArrayList<Item>();
        this.tabGridIdEntreprise= new ArrayList<Integer>() ;
        this.tabGridNameEntreprise= new ArrayList<String>() ;	
        this.tabGridIdSecteur= new ArrayList<Integer>() ;	
        this.tabGridUriLogo= new ArrayList<String>() ;


        
       
            for(i=0;i<tabGridIdEntreprise.size();i++)
            {
            	items.add(new Item(i,tabGridIdEntreprise.get(i),tabGridNameEntreprise.get(i),tabGridIdSecteur.get(i),tabGridUriLogo.get(i)));
            	
            	this.tabGridIdEntreprise.add(tabGridIdEntreprise.get(i));
            	this.tabGridNameEntreprise.add(tabGridNameEntreprise.get(i));
            	this.tabGridIdSecteur.add(tabGridIdSecteur.get(i));
            	this.tabGridUriLogo.add(tabGridUriLogo.get(i));
;

            	
            	
            }                    	
        }
        
        



	@Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i)
    {
        return items.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }
    public class ViewHolder
    {

        //ImageView picture;
        FeedImageView picture;
        TextView name;
        TextView prix;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        View row = convertView;
        ViewHolder holder = null;

        if(row == null)
        {
           holder = new ViewHolder();
           row = inflater.inflate(R.layout.element_item, viewGroup, false);

           holder.name=(TextView)row.findViewById(R.id.text);
           holder.prix=(TextView)row.findViewById(R.id.textprix);
           holder.picture=(FeedImageView)row.findViewById(R.id.picture);
          // holder.picture=(ImageView)row.findViewById(R.id.picture);

           row.setTag (holder);
        }
        else
        {
            /* We recycle a View that already exists */
            holder = (ViewHolder) row.getTag(); 
        } 	


        final Item item = (Item)getItem(position); 
        if(item != null) 
        {
holder.picture.setImageUrl(Const.ipUriImage+item.logo, imageLoader); 
//Picasso.with(getActivity()).load(Const.ipUriImage+item.UriImage).placeholder(R.drawable.load).error(R.drawable.ic_market).into(holder.picture);
        	  
              holder.name.setText(item.idEntreprise);
              holder.prix.setText(item.nameEntreprise);
             	
        }
     /*  
        *//**
         * On Click event for Single Gridview Item
         * *//*
        gridViewMarket.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) { 
                Intent i = new Intent(getActivity(), detailMarket.class);
                i.putExtra("position", position);
                i.putExtra("idProduit",tabGridIdEntreprise.get(position));
                i.putExtra("image",tabGridUriLogo.get(position));
                i.putExtra("titre",tabGridNameEntreprise.get(position));
                i.putExtra("monnaie",tabGridIdSecteur.get(position));
               
              
             

               
                startActivity(i);
 
            } 
        });*/
        return row;
    }

    public class Item
    {
        final int pos;
        final int idEntreprise;
        final String nameEntreprise;
        final int idSecteur;
        final String logo;
  
    	
        Item(int pos, int idEntreprise, String nameEntreprise, int idSecteur, String logo)
        {
            this.pos = pos;
            this.idEntreprise = idEntreprise;
            this.nameEntreprise = nameEntreprise;
            this.idSecteur = idSecteur;
            this.logo = logo;
           
        }
    }
}

