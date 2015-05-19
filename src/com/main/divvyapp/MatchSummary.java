package com.main.divvyapp;

import helpeMethods.DealObj;
import helpeMethods.ListDealsAdapter;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MatchSummary extends Activity implements OnClickListener {
	
	private Context context;
	private SharedPreferences pref;
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
	private String otherUser;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_summary);
		
		// Menu bar coloring
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#73bd90")));
		bar.setTitle("Match Summary");
		
		pref = getSharedPreferences(LoginPage.class.getSimpleName(), MODE_PRIVATE);
		storeId = getIntent().getExtras().getString("storeId");
		category = getIntent().getExtras().getString("category");
		picture = getIntent().getExtras().getString("picture");
		dealName = getIntent().getExtras().getString("dealName");
		userNameClaimed = getIntent().getExtras().getString("userNameClaimed");
		chatid = getIntent().getExtras().getString("userNameClaimed");
		context = getApplicationContext();
		
		final ArrayList<DealObj> fillMaps = new ArrayList<DealObj>();
		DealObj deal = new DealObj(dealId, storeId, category, "0", picture, "0", dealName, city, userNameClaimed);
		fillMaps.add(deal);


		ListView dealList = (ListView) findViewById(R.id.summary);
		ListDealsAdapter adapter = new ListDealsAdapter(this, fillMaps);
		dealList.setAdapter(adapter);
		
		Button clickToChat = (Button) findViewById(R.id.ClickToChat);
		clickToChat.setOnClickListener(this);
		
		TextView userName = (TextView) findViewById(R.id.otherUser);
		userName.setText(userNameClaimed);
		

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.match_summary, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);

	}
}
