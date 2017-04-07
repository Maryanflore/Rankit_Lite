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
 
public class ListViewAdapterDetailNote extends BaseAdapter{
 
	
	   private ArrayList<BeanRow> liste;
	Activity activity;
	
	private OnClickListener click;
	private Holder holder;
	public ListViewAdapterDetailNote(Activity activity,ArrayList<BeanRow> grpBr, OnClickListener onClick){
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
			
			convertView=inflater.inflate(R.layout.colmn_row_note, null);
			holder = new Holder();
			holder.txtFirst=(TextView) convertView.findViewById(R.id.gender);
			holder.txtSecond=(TextView) convertView.findViewById(R.id.note_like);
			holder.rowLibelleService=(TextView) convertView.findViewById(R.id.note_nolike);
			holder.rowClient=(TextView) convertView.findViewById(R.id.rankeur);
			
			convertView.setTag(holder);
			
			  
		  
		}
		   else {
               holder = (Holder) convertView.getTag();
       }
		
		
		if ( position % 2 == 0)
		  {
			  convertView.setBackgroundResource(R.drawable.listview_selector_even);
		      
		       holder.txtSecond.setTextColor(Color.parseColor("#ffffff"));
		       holder.txtFirst.setTextColor(Color.parseColor("#ffffff"));
		       holder.rowLibelleService.setTextColor(Color.parseColor("#ffffff"));
		       holder.rowClient.setTextColor(Color.parseColor("#ffffff"));
			 
	
	      }
		  else
		  {
			  convertView.setBackgroundResource(R.drawable.listview_selector_even2);
			
			   holder.txtSecond.setTextColor(Color.parseColor("#73C2FB"));
		       holder.txtFirst.setTextColor(Color.parseColor("#73C2FB"));
		       holder.rowLibelleService.setTextColor(Color.parseColor("#73C2FB"));
		       holder.rowClient.setTextColor(Color.parseColor("#73C2FB"));
			 
		  }
		holder.position = position;
		
		
		
	    holder.txtFirst.setText(liste.get(position).getLibelleService());
	    holder.rowLibelleService.setText("13%");
	    holder.txtSecond.setText("10%");
	    holder.rowClient.setText("3");

		return convertView;
	}
 
}
