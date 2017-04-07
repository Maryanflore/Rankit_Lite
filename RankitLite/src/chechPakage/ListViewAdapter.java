package chechPakage;


import com.therankit.rankitlite.R;

 
import java.util.ArrayList;

 
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ListViewAdapter extends BaseAdapter{
 
	
	   private ArrayList<BeanRow> liste;
	Activity activity;
	
	private OnClickListener click;
	private Holder holder;
	public ListViewAdapter(Activity activity,ArrayList<BeanRow> grpBr, OnClickListener onClick){
		super();
		this.activity=activity;
		this.click = onClick;
		this.liste = grpBr;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return liste.size();
	}


	@Override
	public BeanRow getItem(int position) {
		// TODO Auto-generated method stub
		return liste.get(position);
	}
 
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
 
 
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	
		
		
		LayoutInflater inflater=activity.getLayoutInflater();
		
		if(convertView == null){
			
			convertView=inflater.inflate(R.layout.colmn_row, null);
			holder = new Holder();
			//holder.txtFirst=(TextView) convertView.findViewById(R.id.name);
			holder.txtSecond=(TextView) convertView.findViewById(R.id.gender);
			//txtThird=(TextView) convertView.findViewById(R.id.age);
			holder.txtThird= (ImageView)convertView.findViewById(R.id.img);
			//txtFourth=(TextView) convertView.findViewById(R.id.status);
			holder.txtFourth= (ImageView)convertView.findViewById(R.id.img2);
			holder.annuler= (ImageView)convertView.findViewById(R.id.annuler);
			holder.txtSecond.setOnClickListener(click);
			holder.txtThird.setOnClickListener(click);
			holder.txtFourth.setOnClickListener(click);
			holder.annuler.setOnClickListener(click);
			holder.annuler.setVisibility(convertView.GONE); 
			//txtThird.setText(map.get(THIRD_COLUMN));
			//txtFourth.setText(map.get(FOURTH_COLUMN));
			convertView.setTag(holder);
			
			  
		  
		}
		   else {
               holder = (Holder) convertView.getTag();
       }
		
		
		if ( position % 2 == 0)
		  {
			  convertView.setBackgroundResource(R.drawable.listview_selector_even);
		      
		       holder.txtSecond.setTextColor(Color.parseColor("#ffffff"));
		      holder.txtThird.setImageResource(R.drawable.ic_action_thumb_up_blanc);
		      holder.txtFourth.setImageResource(R.drawable.ic_action_thumb_down_blanc);
		      holder.annuler.setImageResource(R.drawable.annuler_blanc);
	
	      }
		  else
		  {
			  convertView.setBackgroundResource(R.drawable.listview_selector_even2);
			  holder.txtSecond.setTextColor(Color.parseColor("#73C2FB"));
			  holder.txtThird.setImageResource(R.drawable.ic_action_thumb_up);
		      holder.txtFourth.setImageResource(R.drawable.ic_action_thumb_down);
		      holder.annuler.setImageResource(R.drawable.annuler);
		  }
		holder.position = position;
		//holder.txtFirst.setText(liste.get(position).getIdService()+"");
		
		holder.txtSecond.setText(liste.get(position).getLibelleService());
		
		if(!liste.get(position).getVote())
		{
			 /* AlphaAnimation anim = new AlphaAnimation(0.2f, 1);
		       anim.setDuration(0);
		       anim.setFillAfter(true);
		       convertView.startAnimation(anim);	*/
		       holder.annuler.setVisibility(convertView.GONE); 
		       holder.txtThird.setVisibility(convertView.VISIBLE); 
		       holder.txtFourth.setVisibility(convertView.VISIBLE); 
		}
	       
		else
		{
			/*AlphaAnimation anim = new AlphaAnimation(1, 0.2f);
            anim.setDuration(0);
            anim.setFillAfter(true);
            convertView.startAnimation(anim);*/
            if(liste.get(position).getNoteService()==1)
    		{
            	  holder.txtFourth.setVisibility(convertView.GONE); 
            	  
    		}
            else
            {
            	  holder.txtThird.setVisibility(convertView.GONE); 
            	
            }
            
            holder.annuler.setVisibility(convertView.VISIBLE); 
		}

		return convertView;
	}
 
}
