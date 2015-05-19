package com.main.divvyapp;

import helpeMethods.DealObj;
import helpeMethods.ListDealsAdapter;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MatchSummary extends Activity implements OnClickListener {
	
	private String claimedBy;
	private String uid;
	private String chatid;
	private String dealName;
	private String storeId;
	private String category;
	private String picture;
	private String dealId;
	private String userNameClaimed;
	private String city;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_summary);
		
		// Menu bar coloring
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#73bd90")));
		bar.setTitle("Deal In Motion");
		
		storeId = getIntent().getExtras().getString("storeId");
		category = getIntent().getExtras().getString("category");
		picture = getIntent().getExtras().getString("picture");
		dealName = getIntent().getExtras().getString("dealName");
		userNameClaimed = getIntent().getExtras().getString("userNameClaimed");
		chatid = getIntent().getExtras().getString("chatid");
		
		final ArrayList<DealObj> fillMaps = new ArrayList<DealObj>();
		DealObj deal = new DealObj(dealId, storeId, category, "0", picture, "0", dealName, city, userNameClaimed);
		fillMaps.add(deal);

		ListView dealList = (ListView) findViewById(R.id.summary);
		ListDealsAdapter adapter = new ListDealsAdapter(this, fillMaps, R.layout.in_motion_layout);
		dealList.setAdapter(adapter);
		
		Button clickToChat = (Button) findViewById(R.id.ClickToChat);
		clickToChat.setOnClickListener(this);
		
//		TextView userName = (TextView) findViewById(R.id.txt_claimedBy);
//		userName.setText(userNameClaimed);
		// TODO: Add timer here
	}
	
	public void onClick(View v) {
		if (!chatid.equals("clear")) {
			Intent intent = new Intent(this, ChatAfterMatch.class);
			Bundle extras = new Bundle();
			extras.putString("claimedBy", claimedBy);
			extras.putString("uid", uid);
			extras.putString("chatid", chatid);

			intent.putExtras(extras);
			startActivity(intent);
		}
		finish();
	}
}
