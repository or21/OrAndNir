package com.main.divvyapp;

import helpeMethods.DealObj;
import helpeMethods.ListDealsAdapter;

import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import serverComunication.DataTransfer;
import serverComunication.ServerAsyncParent;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MatchSummary extends Activity implements OnClickListener{
	
	private Context context;
	private SharedPreferences pref;
	private String claimedBy;
	private String uid;
	private String chatid;
	private String dealName;
	private String deadLine;
	private String storeId;
	private String category;
	private String picture;
	private String dealId;
	private String userNameClaimed;
	private String city;
	private String otherUser;
	private TextView countdown;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_summary);
		
		// Menu bar coloring
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#73bd90")));
		bar.setTitle("Match Summary");
		
		dealId = getIntent().getExtras().getString("dealId");
		pref = getSharedPreferences(LoginPage.class.getSimpleName(), MODE_PRIVATE);
		storeId = getIntent().getExtras().getString("storeId");
		category = getIntent().getExtras().getString("category");
		picture = getIntent().getExtras().getString("picture");
		dealName = getIntent().getExtras().getString("dealName");
		userNameClaimed = getIntent().getExtras().getString("userNameClaimed");
		chatid = getIntent().getExtras().getString("chatid");
		uid = getIntent().getExtras().getString("uid");
		context = getApplicationContext();
		deadLine = getIntent().getExtras().getString("deadLine");

		
		final ArrayList<DealObj> fillMaps = new ArrayList<DealObj>();
		final DealObj deal = new DealObj(dealId, storeId, category, "0", picture, "0", dealName, city, userNameClaimed);
		fillMaps.add(deal);


		ListView dealList = (ListView) findViewById(R.id.summary);
		ListDealsAdapter adapter = new ListDealsAdapter(this, fillMaps);
		dealList.setAdapter(adapter);
		
		Button clickToChat = (Button) findViewById(R.id.ClickToChat);
		clickToChat.setOnClickListener(this);
		
		TextView userName = (TextView) findViewById(R.id.otherUser);
		userName.setText(userNameClaimed);
		
		int miliDeadLine = CompleteMatch.calcMili(deadLine);

		// if the deal is finishing right now giving the option to return to DealsPage
				if (miliDeadLine <= 600) {
					countdown = (TextView) findViewById(R.id.countdown);
					Typeface type = Typeface.createFromAsset(getAssets(),"fonts/AGENCY-FB.ttf"); 
					countdown.setTypeface(type);

					countdown.setText("Uh-Oh " + deal.getUserNameClaimed() + " left!");

					Button completeMatch = (Button) findViewById(R.id.completeDeal);
					completeMatch.setText("Back to Deals");
					completeMatch.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent = new Intent(context, StorePage.class);
							intent.putExtra("filter", "all");
							startActivity(intent);
							finish();
						}
					});

					// otherwise - start the countDown
				} else {
					// launching the countDown
					CountDownTimer cT =  new CountDownTimer(miliDeadLine, 1000) {

						public void onTick(long millisUntilFinished) {
							countdown = (TextView) findViewById(R.id.countdown);
							int vh = (int)( (millisUntilFinished / (1000*60*60)) % 24);
							int vm = (int)( (millisUntilFinished / 60000) % 60);
							int vs = (int)( (millisUntilFinished / 1000) % 60);
							String format = String.format("%02d",vh)+":"+String.format("%02d",vm);
							countdown.setText(String.format("%02d",vh)+":"+String.format("%02d",vm));
						}

						public void onFinish() {
							countdown.setText("Uh-Oh " + deal.getUserNameClaimed() + " left!");
							Button completeMatch = (Button) findViewById(R.id.completeDeal);
							completeMatch.setText("Back to Deals");
							completeMatch.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									Intent intent = new Intent(context, StorePage.class);
									intent.putExtra("filter", "all");
									startActivity(intent);
									finish();
								}
							});
						}
					};
					cT.start();
		
				}
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
