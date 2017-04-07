package com.therankit.services;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.load.img.ImageLoaderProfil;
import com.therankit.rankitlite.R;
import com.url.AppController;
import com.volley.Const;

import java.util.ArrayList;

import chechPakage.BeanRow;
import chechPakage.ItemClickListener;

public class AnonceAdapter2 extends  RecyclerView.Adapter<AnonceAdapter2.ViewHolder>{

private ArrayList<AnonceRow>liste;

private HolderAnonce holder;
private Activity activity;
public ImageLoader imageLoader=AppController.getInstance().getImageLoader();
public ImageLoaderProfil imageProfil;
private static MyClickListener myClickListener;
public AnonceAdapter2(Activity a,ArrayList<AnonceRow>grpBr){
        activity=a;
        liste=grpBr;

        imageLoader=AppController.getInstance().getImageLoader();
        imageProfil=new ImageLoaderProfil(a);
        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView=LayoutInflater.from(parent.getContext())
        .inflate(R.layout.anonce_service_item,parent,false);
        return new ViewHolder(itemView);
        }

@Override
public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.rowNomPublieur.setText(liste.get(position).getNomPublieur());
        holder.rowNomPublieur.setText(liste.get(position).getNomPublieur());
        holder.description.setText(liste.get(position).getTitreArticle());
        holder.rowDate.setText(liste.get(position).getDate());
        holder.rowCountComment.setText(" "+liste.get(position).getNbre_comment());
        holder.nbre_jaime_pas.setText(" "+liste.get(position).getNbreJaimePas());
        holder.mark_search_text.setText(" "+liste.get(position).getNbreReaction());
        holder.nbre_jaime.setText(" "+liste.get(position).getNbreJaime());
    if (!liste.get(position).getEtudeAnonceExist()) {
        holder.mark_search_img.setVisibility(View.GONE);
        holder.mark_search_text.setVisibility(View.GONE);
    }
       imageProfil.DisplayImage(Const.ipUriLogo2+liste.get(position).getImageProfil(), holder.rowImageProfil);
    if (!liste.get(position).getImagePublier().equals("")) {
        holder.rowImagePublier.setImageUrl(Const.ipUriImageAnonce+liste.get(position).getImagePublier(), imageLoader);
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
    holder.iconComment.setVisibility(View.VISIBLE);


    holder.setClickListener(new ItemClickListener() {
        @Override
        public void onClick(View view, int position, boolean isLongClick) {


        }

    });


        }

    @Override
    public int getItemCount() {
        return liste.size();
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    TextView rowNomPublieur;
    TextView rowDate;
    TextView description;
    TextView rowTaille;

    TextView rowCountComment;
    TextView nbre_jaime;
    TextView nbre_jaime_pas;
    TextView mark_search_text;
    ImageView rowImageProfil;
    ImageView barr_vote;
    FeedImageView rowImagePublier;
    ImageView iconComment;
    ImageView iconJaime;
    ImageView iconJaime_pas;
    ImageView mark_search_img;
    private ItemClickListener clickListener;

    public ViewHolder(View itemView) {
        super(itemView);
        rowNomPublieur = (TextView) itemView.findViewById(R.id.name);
        rowDate = (TextView) itemView.findViewById(R.id.timestamp);
        description = (TextView) itemView.findViewById(R.id.description);
        rowTaille = (TextView) itemView.findViewById(R.id.taille);
        rowCountComment = (TextView) itemView.findViewById(R.id.nbre_comment);
        nbre_jaime = (TextView) itemView.findViewById(R.id.nbre_jaime);
        nbre_jaime_pas = (TextView) itemView.findViewById(R.id.nbre_jaim_pas);
        rowImageProfil = (ImageView) itemView.findViewById(R.id.profilePic);
        rowImagePublier = (FeedImageView) itemView.findViewById(R.id.feedImage1);
        iconComment = (ImageView) itemView.findViewById(R.id.iconComment);
        iconJaime = (ImageView) itemView.findViewById(R.id.iconJaime);
        iconJaime_pas = (ImageView) itemView.findViewById(R.id.iconJaime_pas);
        barr_vote = (ImageView) itemView.findViewById(R.id.barr_vote);
        mark_search_text = (TextView) itemView.findViewById(R.id.nbre_market_search);
        mark_search_img =(ImageView) itemView.findViewById(R.id.market_search);

        itemView.setTag(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        rowNomPublieur.setTag(itemView);
        rowNomPublieur.setOnClickListener(this);
        rowNomPublieur.setOnLongClickListener(this);
        nbre_jaime.setTag(itemView);
        nbre_jaime.setOnClickListener(this);
        nbre_jaime.setOnLongClickListener(this);
        rowCountComment.setTag(itemView);
        rowCountComment.setOnClickListener(this);
        rowCountComment.setOnLongClickListener(this);
        rowImagePublier.setTag(itemView);
        rowImagePublier.setOnClickListener(this);
        rowImagePublier.setOnLongClickListener(this);
        iconComment.setTag(itemView);
        iconComment.setOnClickListener(this);
        iconComment.setOnLongClickListener(this);
        iconJaime.setTag(itemView);
        iconJaime.setOnClickListener(this);
        iconJaime.setOnLongClickListener(this);

        iconJaime_pas.setTag(itemView);
        iconJaime_pas.setOnClickListener(this);
        iconJaime_pas.setOnLongClickListener(this);

        rowImageProfil.setTag(itemView);
        rowImageProfil.setOnClickListener(this);
        rowImageProfil.setOnLongClickListener(this);
        barr_vote.setTag(itemView);
        barr_vote.setOnClickListener(this);
        barr_vote.setOnLongClickListener(this);

        mark_search_text.setTag(itemView);
        mark_search_text.setOnClickListener(this);
        mark_search_text.setOnLongClickListener(this);

        mark_search_img.setTag(itemView);
        mark_search_img.setOnClickListener(this);
        mark_search_img.setOnLongClickListener(this);

        nbre_jaime_pas.setTag(itemView);
        nbre_jaime_pas.setOnClickListener(this);
        nbre_jaime_pas.setOnLongClickListener(this);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
        //this.myClickListener = myClickListener;
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


        public AnonceRow getItem(int position) {
                return liste.get(position);
        }

        public long getItemId(int position) {
                return position;
        }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
