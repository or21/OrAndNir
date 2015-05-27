package com.main.divvyapp;

import helpeMethods.ClockObject;
import helpeMethods.DealObj;
import helpeMethods.ListDealsAdapter;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MatchSummary extends Activity implements OnClickListener {
	
	private String claimedBy;
	private String uid;
	private String chatid;
	private String deadLine;
	private String dealName;
	private String storeId;
	private String category;
	private String picture;
	private String dealId;
	private String userNameClaimed;
	private String city;
	private ClockObject clockObj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_summary);
		
		// Menu bar coloring
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#73bd90")));
		bar.setTitle("Deal In Motion");
		
		deadLine = getIntent().getExtras().getString("deadLine");
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
		
		clockObj = (ClockObject) findViewById(R.id.clock);
		
		int miliDeadLine = calcMili(deadLine);

		CountDownTimer cT =  new CountDownTimer(miliDeadLine, 1000) {

			public void onTick(long millisUntilFinished) {
				int vh = (int)( (millisUntilFinished / (1000*60*60)) % 24);
				int vm = (int)( (millisUntilFinished / 60000) % 60);
				int vs = (int)( (millisUntilFinished / 1000) % 60);
				clockObj.timeSetter(String.format("%02d",vh)+":"+String.format("%02d",vm)+":"+String.format("%02d",vs));
			}

			public void onFinish() {
//				Button completeMatch = (Button) findViewById(R.id.completeDeal);
//				completeMatch.setText("Back to Deals");

			}
		};
		cT.start();

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
	
	// returns the difference between current time given string (format HH:MM) in milliseconds 
	public static int calcMili(String timeToCalc) {

		// divides the time to hours and minutes
		final int hour = Integer.parseInt((String) timeToCalc.subSequence(0, timeToCalc.indexOf(':')));
		final int minutes = Integer.parseInt((String) timeToCalc.subSequence(timeToCalc.indexOf(':') + 1, timeToCalc.length()));

		// Gets the time from the device and calculates the difference
		Calendar c = Calendar.getInstance(); 

		int diffhour = (int) (hour - c.get(Calendar.HOUR_OF_DAY)) ;
		int diffminute = (int) (minutes - c.get(Calendar.MINUTE));

		// taking care of time calculations
		if (diffhour < 0) {
			diffhour = diffhour + 24;
		}

		if (diffminute < 0) {
			diffhour--;
			diffminute = diffminute + 60;
		}

		// sums the difference from dead line and convert to milliseconds
		return (diffhour * 3600000) + (diffminute * 60000);
	}
	
	
}