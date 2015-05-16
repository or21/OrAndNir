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
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

public class FindMeMatch extends Activity implements OnClickListener, ServerAsyncParent {

	private TimePicker deadLine;
	private String dealId;
	private SharedPreferences pref;
	private String storeId;
	private String category;
	private String picture;
	private String dealName;
	private String city;
	private String userNameClaimed;
	private String newDeadLine;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_me_match);

		// Menu bar coloring
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#73bd90")));
		bar.setTitle("Requesting a Match");

		pref = getSharedPreferences(LoginPage.class.getSimpleName(), MODE_PRIVATE);
		dealId = getIntent().getExtras().getString("dealid");
		storeId = getIntent().getExtras().getString("storeId");
		category = getIntent().getExtras().getString("category");
		picture = getIntent().getExtras().getString("picture");
		dealName = getIntent().getExtras().getString("dealName");
		city = getIntent().getExtras().getString("city");
		userNameClaimed = getIntent().getExtras().getString("userNameClaimed");
		context = getApplicationContext();

		deadLine = (TimePicker) findViewById(R.id.deadLine);
		deadLine.setBackground(new ColorDrawable(Color.parseColor("#ececed")));
		Calendar cal = Calendar.getInstance();
		int min = cal.get(Calendar.MINUTE);
		deadLine.setCurrentMinute(min + 10);

		final ArrayList<DealObj> fillMaps = new ArrayList<DealObj>();
		DealObj deal = new DealObj(dealId, storeId, category, "0", picture, "0", dealName, city, userNameClaimed);
		fillMaps.add(deal);

		ListView dealList = (ListView) findViewById(R.id.dealList);
		ListDealsAdapter adapter = new ListDealsAdapter(this, fillMaps);
		dealList.setAdapter(adapter);

		Button requestMatch = (Button) findViewById(R.id.claimDeal);
		requestMatch.setOnClickListener(this);
	}

	public void sendUpadte(View v) {
		int m_dealId = Integer.parseInt(dealId);
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("deadLine", newDeadLine));
		params.add(new BasicNameValuePair("dealid", "" + m_dealId));
		params.add(new BasicNameValuePair("uidNew", pref.getString("uid", "error")));
		params.add(new BasicNameValuePair("chatid", "request"));
		params.add(new BasicNameValuePair("uid", ""));
		new DataTransfer(this, params, DataTransfer.METHOD_POST).execute("http://nir.milab.idc.ac.il/php/milab_send_deal_update.php");
	}

	@Override
	public void onClick(View v) {
		newDeadLine = deadLine.getCurrentHour().toString() + ":" + deadLine.getCurrentMinute().toString();
		int desiredTimeInMilis = CompleteMatch.calcMili(newDeadLine);
		if (desiredTimeInMilis <= 600000 || desiredTimeInMilis > 82800000) {
			CharSequence text = "Invalid time";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			View view = toast.getView();
			view.setBackground(new ColorDrawable(Color.parseColor("#71bd90")));
			toast.show();
		}
		else {
			sendUpadte(v);
		}
	}

	@Override
	public void doOnPostExecute(JSONObject jObj) {
		Intent intent = new Intent(this, StorePage.class);
		intent.putExtra("filter", "all");
		startActivity(intent);
		finish();
	}
}
