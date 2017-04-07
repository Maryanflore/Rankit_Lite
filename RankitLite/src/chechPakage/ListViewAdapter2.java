package chechPakage;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.therankit.home.Flower;
import com.therankit.rankitlite.R;

import java.util.ArrayList;

public class ListViewAdapter2 extends RecyclerView.Adapter<ListViewAdapter2.ViewHolder> {


	   private ArrayList<BeanRow> liste;
	   private Activity activity;

	   private Holder holder;
	   private Context mContext;
	  private static MyClickListener myClickListener;
	public ListViewAdapter2(Context contexts,Activity activity, ArrayList<BeanRow> grpBr){
		super();
		this.mContext = contexts;
		this.activity=activity;

		this.liste = grpBr;
	}


	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.colmn_row2, parent, false);
		return new ViewHolder(itemView);
	}


	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {

		holder.beanRow=getItem(position);
		holder.gender.setText(liste.get(position).getLibelleService());

		holder.imgLike.setImageResource(R.drawable.like);
		holder.imgNoLike.setImageResource(R.drawable.dontlike);
		holder.annuler.setImageResource(R.drawable.annuler);
		holder.annuler.setVisibility(View.GONE);
		holder.setClickListener(new ItemClickListener() {
			@Override
			public void onClick(View view, int position, boolean isLongClick) {
				/*if (isLongClick) {
					Toast.makeText(mContext, "#" + position + " - " + liste.get(position).getLibelleService() + " (Long click)", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(mContext, "#" + position + " - " + liste.get(position).getLibelleService(), Toast.LENGTH_SHORT).show();
				}*/


		/*		switch (view.getId())

				{


					case R.id.gender:


						Toast.makeText(mContext, liste.get(position).getLibelleService(), Toast.LENGTH_SHORT).show();
						break;
					case R.id.img:
					liste.get(position).setVote(true);
					liste.get(position).setNoteService(1);
					Toast.makeText(mContext, "" +mContext.getString(R.string.like), Toast.LENGTH_SHORT).show();
						holder.annuler.setVisibility(View.VISIBLE);
						holder.imgNoLike.setVisibility(View.VISIBLE);
						holder.imgLike.setVisibility(View.GONE);
						break;

					case R.id.img2:
						liste.get(position).setVote(true);
						liste.get(position).setNoteService(-1);
						Toast.makeText(mContext, "" +mContext.getString(R.string.likenot), Toast.LENGTH_SHORT).show();
						holder.annuler.setVisibility(View.VISIBLE);
						holder.imgLike.setVisibility(View.VISIBLE);
						holder.imgNoLike.setVisibility(View.GONE);
						break;

					case R.id.annuler:
						liste.get(position).setVote(false);
						liste.get(position).setNoteService(0);
						holder.annuler.setVisibility(View.GONE);
						holder.imgLike.setVisibility(View.VISIBLE);
						holder.imgNoLike.setVisibility(View.VISIBLE);
						break;

				}
*/


			}

		});


		if(!liste.get(position).getVote())
		{

			holder.annuler.setVisibility(View.GONE);
			holder.imgLike.setVisibility(View.VISIBLE);
			holder.imgNoLike.setVisibility(View.VISIBLE);
		}

		else
		{
			if(liste.get(position).getNoteService()==1)
			{
				holder.imgLike.setVisibility(View.GONE);

			}
			else
			{
				holder.imgNoLike.setVisibility(View.GONE);

			}

			holder.annuler.setVisibility(View.VISIBLE);
		}

	}


	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}

	@Override
	public int getItemCount() {
		return liste.size();
	}



	public BeanRow getItem(int position) {
		// TODO Auto-generated method stub
		return liste.get(position);
	}
 
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}




	public void setOnItemClickListener(MyClickListener myClickListener) {
		this.myClickListener = myClickListener;
	}



	public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener{

		ImageView imgLike;
		ImageView imgNoLike;
		ImageView annuler;
		TextView  gender;
		BeanRow   beanRow;
		private ItemClickListener clickListener;

		public ViewHolder(View itemView) {
			super(itemView);

			imgLike = (ImageView) itemView.findViewById(R.id.img);
			imgNoLike = (ImageView) itemView.findViewById(R.id.img2);
			annuler = (ImageView) itemView.findViewById(R.id.annuler);
			gender = (TextView) itemView.findViewById(R.id.gender);
			itemView.setTag(itemView);
			itemView.setOnClickListener(this);
			itemView.setOnLongClickListener(this);
			gender.setTag(itemView);
			gender.setOnClickListener(this);
			gender.setOnLongClickListener(this);
			imgLike.setTag(itemView);
			imgLike.setOnClickListener(this);
			imgLike.setOnLongClickListener(this);
			imgNoLike.setTag(itemView);
			imgNoLike.setOnClickListener(this);
			imgNoLike.setOnLongClickListener(this);
			annuler.setTag(itemView);
			annuler.setOnClickListener(this);
			annuler.setOnLongClickListener(this);


		}

		public void setClickListener(ItemClickListener itemClickListener) {
			this.clickListener = itemClickListener;
		//	this.myClickListener = myClickListener;
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
