package com.therankit.product;


import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.load.img.ImageLoaderProfil;
import com.therankit.rankitlite.R;
import com.therankit.services.AnonceRow;
import com.therankit.services.FeedImageView;
import com.therankit.services.HolderAnonce;
import com.url.AppController;
import com.volley.Const;

import java.util.ArrayList;

import chechPakage.ItemClickListener;

public class AnonceAdapter2 extends  RecyclerView.Adapter<AnonceAdapter2.ViewHolder>{

private ArrayList<AnonceRow>liste;

private HolderAnonce holder;
private Activity activity;
public ImageLoader imageLoader=AppController.getInstance().getImageLoader();
public ImageLoaderProfil imageProfil;
private static MyClickListener myClickListener;
public AnonceAdapter2(Activity a, ArrayList<AnonceRow>grpBr){
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
        holder.rowTitre.setText(liste.get(position).getTitreArticle());
        holder.rowDate.setText(liste.get(position).getDate());
        holder.rowCountComment.setText(" "+liste.get(position).getNbre_comment());
       imageProfil.DisplayImage(Const.ipUriImage+liste.get(position).getImageProfil(), holder.rowImageProfil);
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
    holder.rowCommentbutton.setVisibility(View.VISIBLE);


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




    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener, View.OnLongClickListener {
    TextView rowNomPublieur;
    TextView rowDescription;
    TextView rowDate;
    TextView rowTitre;
    TextView rowTaille;

    TextView rowPrixVente;
    TextView rowCountComment;
    TextView rowLibelePartatger;
    ImageView rowImageProfil;
    ImageView barr_vote;
    FeedImageView rowImagePublier;
    ImageView rowCommentbutton;
    ImageView rowSharebutton;
    private ItemClickListener clickListener;

    public ViewHolder(View itemView) {
        super(itemView);
        rowNomPublieur = (TextView) itemView.findViewById(R.id.name);
        rowDate = (TextView) itemView.findViewById(R.id.timestamp);
        rowTitre = (TextView) itemView.findViewById(R.id.txtStatusMsg);
        rowTaille = (TextView) itemView.findViewById(R.id.taille);
        rowCountComment = (TextView) itemView.findViewById(R.id.nbre_comment);
        rowLibelePartatger = (TextView) itemView.findViewById(R.id.partager);
        rowImageProfil = (ImageView) itemView.findViewById(R.id.profilePic);
        rowImagePublier = (FeedImageView) itemView.findViewById(R.id.feedImage1);
        rowCommentbutton = (ImageView) itemView.findViewById(R.id.iconComment);
        rowSharebutton = (ImageView) itemView.findViewById(R.id.iconShare);
        barr_vote = (ImageView) itemView.findViewById(R.id.barr_vote);


        itemView.setTag(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        rowNomPublieur.setTag(itemView);
        rowNomPublieur.setOnClickListener(this);
        rowNomPublieur.setOnLongClickListener(this);
        rowLibelePartatger.setTag(itemView);
        rowLibelePartatger.setOnClickListener(this);
        rowLibelePartatger.setOnLongClickListener(this);
        rowCountComment.setTag(itemView);
        rowCountComment.setOnClickListener(this);
        rowCountComment.setOnLongClickListener(this);
        rowImagePublier.setTag(itemView);
        rowImagePublier.setOnClickListener(this);
        rowImagePublier.setOnLongClickListener(this);
        rowCommentbutton.setTag(itemView);
        rowCommentbutton.setOnClickListener(this);
        rowCommentbutton.setOnLongClickListener(this);
        rowSharebutton.setTag(itemView);
        rowSharebutton.setOnClickListener(this);
        rowSharebutton.setOnLongClickListener(this);
        rowImageProfil.setTag(itemView);
        rowImageProfil.setOnClickListener(this);
        rowImageProfil.setOnLongClickListener(this);
        barr_vote.setTag(itemView);
        barr_vote.setOnClickListener(this);
        barr_vote.setOnLongClickListener(this);

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
