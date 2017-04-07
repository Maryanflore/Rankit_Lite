package com.therankit.community;


import java.util.ArrayList;
import java.util.List;
 

import android.app.Activity; 
import android.content.Context; 
import android.text.Html;
import android.text.TextUtils; 
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener; 
import android.widget.BaseAdapter; 
import android.widget.TextView; 

import com.android.volley.toolbox.ImageLoader; 


import com.therankit.rankitlite.R;
import com.url.AppController;
import com.volley.Const; 

public class FeedListAdapter extends BaseAdapter {	
	private Activity activity;
	private LayoutInflater inflater;
	private List<FeedItem> feedItems;
    private Holder2 holder; 
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	private OnClickListener click;

	public FeedListAdapter(Activity activity, ArrayList<FeedItem> feedItems,OnClickListener onClick) {
		this.activity = activity;
		this.feedItems = feedItems; 
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = AppController.getInstance().getImageLoader();
        click = onClick;
	}

	@Override
	public int getCount() {
		return feedItems.size();
	}

	@Override
	public Object getItem(int location) {
		return feedItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
 
        if (convertView == null) 
        {
            holder = new Holder2();
			convertView = inflater.inflate(R.layout.enews_items, null); 
		holder.feedImageView = (FeedImageView) convertView.findViewById(R.id.feedImage1); 
		holder.url  = (TextView) convertView.findViewById(R.id.txtUrl);  
		holder.timestamp  = (TextView) convertView.findViewById(R.id.timestamp);
		holder.feedImageView2 = (FeedImageView) convertView.findViewById(R.id.feedImage2); 
		//holder.statusMsg  = (TextView) convertView.findViewById(R.id.txtStatusMsg); 
		convertView.setTag(holder);
        } 
        else 
        {
            holder = (Holder2) convertView.getTag();
    	} 
        holder.position = position;  
		final FeedItem item = feedItems.get(position); 
		//holder.statusMsg.setText(item.getStatus()); 
		//holder.statusMsg.setVisibility(View.GONE);
		// Feed image
		holder.feedImageView.setImageUrl(Const.ipUriUploadImageEnews+item.getImge(), imageLoader);
		holder.feedImageView2.setImageUrl(Const.ipUriUploadImageEnews+item.getImge(), imageLoader);
		holder.url.setText(item.getUrl());
		holder.timestamp .setText(item.getTimeStamp()); 
		holder.url.setOnClickListener(click);
		holder.feedImageView.setOnClickListener(click); 
		holder.feedImageView2.setOnClickListener(click); 

			if (!item.getImge().equals("")) { 
		 
			holder.feedImageView.setResponseObserver(new FeedImageView.ResponseObserver() {
						@Override
						public void onError() {
						}

						@Override
						public void onSuccess() {
						}
					});
		 
								if (!item.getPosition())
								{
									holder.feedImageView2.setVisibility(View.GONE);
									holder.feedImageView.setVisibility(View.VISIBLE);
								}
								else
								{					
									holder.feedImageView.setVisibility(View.GONE);
									holder.feedImageView2.setVisibility(View.VISIBLE);	
								}
		} else {
			holder.feedImageView.setVisibility(View.GONE); 
			holder.feedImageView2.setVisibility(View.GONE); 
		}

		return convertView;
	}
	 
}
