package chechPakage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;
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
public class MyAdapterMarket2 extends  RecyclerView.Adapter<MyAdapterMarket2.ViewHolder>
{

    private LayoutInflater inflater;
    private int i;
    private Activity activity;
    private int userId;
    private SharedPreferences settings;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Context context;
    private String  mtype_secteur;
    private static MyClickListener myClickListener;

    private ArrayList<ItemAdapter> items;

    public MyAdapterMarket2(Activity activityM,Context context, ArrayList<ItemAdapter> mItems,int Id,String type_sectuer)
    {
        inflater = LayoutInflater.from(context);
        items = new ArrayList<ItemAdapter>();
        this.activity=activityM;
        this.userId=Id;
        this.settings =  activityM.getSharedPreferences(Const.PREFRENCES_NAME, Context.MODE_PRIVATE);
        this.context=context;
        this.items=mItems;
        this.mtype_secteur=type_sectuer;


    }





    @Override
    public int getItemCount() {
        return items.size();
    }


    public Object getItem(int i)
    {
        return items.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public MyAdapterMarket2.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_item2, viewGroup, false);
       // int height = viewGroup.getMeasuredHeight() / 4;
       // view.setMinimumHeight(height);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapterMarket2.ViewHolder viewHolder, int position) {
        final ItemAdapter item2 = items.get(position);
        if(mtype_secteur.equals(Const.TYPE_SERVICE))
        {
            viewHolder.picture.setImageUrl(Const.ipUriLogo2+item2.getLogo(), imageLoader);
        }
        else
        {
            viewHolder.picture.setImageUrl(Const.ipUriProduitPicture+item2.getLogo(), imageLoader);

        }

        //Picasso.with(getActivity()).load(Const.ipUriImage+item.UriImage).placeholder(R.drawable.load).error(R.drawable.ic_market).into(holder.picture);
        //Picasso.with(context).load(Const.ipUriLogo+item2.logo).resize(240, 120).into(viewHolder.picture);
        viewHolder.name.setText(item2.getNameEntreprise());
        viewHolder.prix.setText(item2.getNameEntreprise());

        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

              /*  Date d=new Date();

                    Intent i = new Intent(activity, detailService.class);

                    i.putExtra("entreprise_id",item2.getIdEntreprise());
                    i.putExtra("entreprise_logo",item2.getLogo());
                    i.putExtra("entreprise_name",item2.getNameEntreprise());
                    i.putExtra("secteur_id",item2.getIdSecteur()+"");
                    i.putExtra("randkiteUser_id",userId);

               context.startActivity(i);
                  */







            }

        });
    }


    /** Filter Logic**/
    public void animateTo(ArrayList<ItemAdapter> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);

    }
    private void applyAndAnimateRemovals(ArrayList<ItemAdapter> newModels) {

        if(items.size()>0)
        {
            for (int i = items.size() - 1; i >= 0; i--) {
                final ItemAdapter model = items.get(i);
                if (!newModels.contains(model)) {
                    removeItem(i);
                }
            }
        }


    }

    private void applyAndAnimateAdditions(List<ItemAdapter> newModels) {
        if(items.size()>0) {
            for (int i = 0, count = newModels.size(); i < count; i++) {
                final ItemAdapter model = newModels.get(i);
                if (!items.contains(model)) {
                    addItem(i, model);
                }
            }
        }
    }

    private void applyAndAnimateMovedItems(List<ItemAdapter> newModels) {
        if(items.size()>0) {
            for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
                final ItemAdapter model = newModels.get(toPosition);
                final int fromPosition = items.indexOf(model);
                if (fromPosition >= 0 && fromPosition != toPosition) {
                    moveItem(fromPosition, toPosition);
                }
            }
        }
    }

    public ItemAdapter removeItem(int position) {
        final ItemAdapter model = items.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, ItemAdapter model) {
        items.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final ItemAdapter model = items.remove(fromPosition);
        items.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        FeedImageView picture;
        TextView name;
        TextView prix;
        private ItemClickListener clickListener;
        public ViewHolder(View view) {
            super(view);

            name=(TextView)view.findViewById(R.id.text);
            prix=(TextView)view.findViewById(R.id.textprix);
            picture=(FeedImageView)view.findViewById(R.id.picture);
            view.setTag(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {

            clickListener.onClick(view, getPosition(), false);
            myClickListener.onItemClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}

