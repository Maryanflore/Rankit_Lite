package chechPakage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.therankit.rankitlite.FeedImageView;
import com.therankit.rankitlite.R;
import com.therankit.services.detailService;
import com.url.AppController;
import com.volley.Const;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Martin Pendja on 28/06/2016.
 */
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

    public MyAdapterMarket(Context activity, List<Integer> tabGridIdEntreprise,
                           List<String> tabGridNameEntreprise, List<Integer> tabGridIdSecteur
            , List<String> tabGridUriLogo)
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
            holder.picture.setImageUrl(Const.ipUriLogo+item.logo, imageLoader);
            //Picasso.with(getActivity()).load(Const.ipUriImage+item.UriImage).placeholder(R.drawable.load).error(R.drawable.ic_market).into(holder.picture);

            holder.name.setText(item.nameEntreprise);
            holder.prix.setText(item.nameEntreprise);

        }
	     /*
	        *//**
     * On Click event for Single Gridview Item
     * */

     /*   gridViewMarket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Date d=new Date();
                new Locale("FR","fr");
                formater = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                formater.format(d);
                verifNoteBdd.open();

                if(!verifNoteBdd.getVerification(tabGridIdEntreprise.get(position), formater.format(d)))
                {
                    Intent i = new Intent(getActivity(), detailService.class);

                    i.putExtra("entreprise_id",tabGridIdEntreprise.get(position));
                    i.putExtra("entreprise_logo",tabGridUriLogo.get(position));
                    i.putExtra("entreprise_name",tabGridNameEntreprise.get(position));
                    i.putExtra("secteur_id",tabGridIdSecteur.get(position)+"");
                    i.putExtra("randkiteUser_id",randkiteUser_id);
                    settings.edit() .putString("sourceNoteS","0") .commit();
                    startActivity(i);

                }
                else
                {
                    createDialog("", getActivity().getApplicationContext().getString(R.string.msg_error_vote));
                }
                verifNoteBdd.close();


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

