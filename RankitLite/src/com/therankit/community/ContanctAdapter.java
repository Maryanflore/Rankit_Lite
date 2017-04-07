package com.therankit.community;

import java.util.List;

import com.load.img.ImageLoaderProfil;
import com.therankit.rankitlite.R;

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

public class ContanctAdapter extends ArrayAdapter<ContactBean> {

	private Activity activity;
	private List<ContactBean> items;
	private int row;
	private ContactBean objBean;
    public ImageLoaderProfil imageLoader; 
    public int emotion_nbre;

	public ContanctAdapter(Activity act, int row, List<ContactBean> items) {
		super(act, row, items);
        this.emotion_nbre=1;
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

		//holder.tvname = (TextView) view.findViewById(R.id.textView10);
		holder.tvPhoneNo = (TextView) view.findViewById(R.id.textView12);
		//holder.tvprofil = (ImageView) view.findViewById(R.id.tvprofil);
		//holder.tvStatut = (ImageView) view.findViewById(R.id.tvStatut);
		holder.emotion = (ImageView) view.findViewById(R.id.id_imag_num);
		//holder.tvStatut2 = (TextView) view.findViewById(R.id.tvStatut2);
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
	/*	if (holder.tvname != null && null != objBean.getName()
				&& objBean.getName().trim().length() > 0) {
			holder.tvname.setText(Html.fromHtml(""+objBean.getName()));
		}*/

		if (holder.tvPhoneNo != null && null != objBean.getPhoneNo()
				&& objBean.getPhoneNo().trim().length() > 0) {
			holder.tvPhoneNo.setText(Html.fromHtml(objBean.getPhoneNo()));
		}

      if(objBean.getId().equals("0"))
      {
    	  holder.emotion.setImageResource(R.drawable.numb1);
    	  //emotion_nbre=2;
      }
      else if(objBean.getId().equals("1"))
      {
    	  holder.emotion.setImageResource(R.drawable.numb2);

      }
      else if(objBean.getId().equals("2"))
      {
    	  holder.emotion.setImageResource(R.drawable.numb3);

      }
	  else if(objBean.getId().equals("3"))
	  {
		  holder.emotion.setImageResource(R.drawable.numb4);

	  }
		//imageLoader.DisplayImage(items.get(position).getPhotos(), holder.tvprofil);
		return view;
	}

	public class ViewHolder {
		public TextView tvname, tvPhoneNo,tvStatut2,gcmMsgText;
		public ImageView tvprofil, tvStatut,emotion;
		public LinearLayout linegcmMsgText,line2;
	}

}
