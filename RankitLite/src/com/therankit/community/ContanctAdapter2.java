package com.therankit.community;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.load.img.ImageLoaderProfil;
import com.therankit.rankitlite.R;

import java.util.List;

public class ContanctAdapter2 extends ArrayAdapter<ContactBean> {

	private Activity activity;
	private List<ContactBean> items;
	private int row;
	private ContactBean objBean;
    public ImageLoaderProfil imageLoader;
    public int emotion_nbre;

	public ContanctAdapter2(Activity act, int row, List<ContactBean> items) {
		super(act, row, items);
        this.emotion_nbre=2;
		this.activity = act;
		this.row = row;
		this.items = items;
        imageLoader=new ImageLoaderProfil(activity.getApplicationContext());

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(row, null);

			holder = new ViewHolder();
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		if ((items == null) || ((position + 1) > items.size()))
			return view;

		objBean = items.get(position);

		holder.tvname = (TextView) view.findViewById(R.id.tvname);
		holder.tvPhoneNo = (TextView) view.findViewById(R.id.tvphone);
		holder.tvprofil = (ImageView) view.findViewById(R.id.tvprofil);
		holder.tvStatut = (ImageView) view.findViewById(R.id.tvStatut);
		holder.emotion = (ImageView) view.findViewById(R.id.emotion);
		holder.tvStatut2 = (TextView) view.findViewById(R.id.tvStatut2);
		//holder.linegcmMsgText = (LinearLayout) view.findViewById(R.id.linegcmMsgText);
		//holder.line2 = (LinearLayout) view.findViewById(R.id.line2);
		//holder.gcmMsgText = (TextView) view.findViewById(R.id.gcmMsgText);
		/*
		if(objBean.getStatut())
		{ 
		
				if(objBean.getNumbersMsg()==0)
				{
					holder.linegcmMsgText.setVisibility(view.GONE);
					holder.line2.setVisibility(view.VISIBLE);	 
				}
				else 
				{
		 
					holder.linegcmMsgText.setVisibility(view.VISIBLE);
					holder.line2.setVisibility(view.GONE);
					holder.gcmMsgText.setText(objBean.getNumbersMsg());		 
				}
				  
		}
		 */
		if (holder.tvname != null && null != objBean.getName()
				&& objBean.getName().trim().length() > 0) {
			holder.tvname.setText(Html.fromHtml(""+objBean.getName()));
		}
		if (holder.tvPhoneNo != null && null != objBean.getPhoneNo()
				&& objBean.getPhoneNo().trim().length() > 0) {
			holder.tvPhoneNo.setText(Html.fromHtml(objBean.getPhoneNo()));
		}
 
		if (objBean.getStatut()) {
			holder.tvStatut.setVisibility(view.VISIBLE); 
			holder.tvStatut2.setVisibility(view.VISIBLE); 
            
		}
		else if (!objBean.getStatut())
		{
			holder.tvStatut.setVisibility(view.GONE); 
			holder.tvStatut2.setVisibility(view.GONE); 
		}
      if(emotion_nbre%2==0)
      {
    	  holder.emotion.setImageResource(R.drawable.ic_happy);
    	  emotion_nbre=3;
      }
      else if(emotion_nbre%3==0)
      {
    	  holder.emotion.setImageResource(R.drawable.ic_angry);
    	  emotion_nbre=5;
      }
      else if(emotion_nbre%5==0)
      {
    	  holder.emotion.setImageResource(R.drawable.ic_neutral);
    	  emotion_nbre=2;
      }
		imageLoader.DisplayImage(items.get(position).getPhotos(), holder.tvprofil); 
		return view;
	}

	public class ViewHolder {
		public TextView tvname, tvPhoneNo,tvStatut2,gcmMsgText;
		public ImageView tvprofil, tvStatut,emotion;
		public LinearLayout linegcmMsgText,line2;
	}

}
