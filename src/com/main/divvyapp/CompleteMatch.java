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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CompleteMatch extends Activity implements ServerAsyncParent {

	private Context context;
	private String dealId;
	private SharedPreferences pref;
	private TextView countdown;
	private String deadLine;
	private String claimedBy;
	private String uid;
	private String storeId;
	private String category;
	private String picture;
	private String dealName;
	private String city;
	private ProgressDialog dialog;
	private String chatid;
	private String userNameClaimed;

	final static String msg = "Click here to Divvy it up";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complete_match);

		// Menu bar coloring
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#71bd90")));
		bar.setTitle("");

		context = getApplicationContext();
		dialog = new ProgressDialog(this);
		pref = getSharedPreferences(LoginPage.class.getSimpleName(), MODE_PRIVATE);
		uid = pref.getString("uid", "error");

		dealId = getIntent().getExtras().getString("dealid");
		deadLine = getIntent().getExtras().getString("deadLine");
		claimedBy = getIntent().getExtras().getString("claimedBy");
		storeId = getIntent().getExtras().getString("storeId");
		category = getIntent().getExtras().getString("category");
		picture = getIntent().getExtras().getString("picture");
		dealName = getIntent().getExtras().getString("dealName");
		city = getIntent().getExtras().getString("city");
		userNameClaimed = getIntent().getExtras().getString("userNameClaimed");

		final ArrayList<DealObj> fillMaps = new ArrayList<DealObj>();
		final DealObj deal = new DealObj(dealId, storeId, category, claimedBy, picture, deadLine, dealName, city, userNameClaimed);
		fillMaps.add(deal);

		ListView dealList = (ListView) findViewById(R.id.complete_dealList);
		ListDealsAdapter adapter = new ListDealsAdapter(this, fillMaps);
		dealList.setAdapter(adapter);

		TextView textAboveCounter = (TextView) findViewById(R.id.textView1);
		textAboveCounter.setText("\u200E" + deal.getUserNameClaimed() + " will leave the mall in: ");

		int miliDeadLine = calcMili(deadLine);

		// if the deal is finishing right now giving the option to return to DealsPage
		if (miliDeadLine <= 600) {
			countdown = (TextView) findViewById(R.id.countdown);
			countdown.setText("Uh-Oh " + deal.getUserNameClaimed() + " left!");

			Button completeMatch = (Button) findViewById(R.id.completeDeal);
			completeMatch.setText("Back to Deals");
			completeMatch.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context, StorePage.class);
					intent.putExtra("filter", "all");
					sendClearDealUpadte(v);
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
					countdown.setText(String.format("%02d",vh)+":"+String.format("%02d",vm)+":"+String.format("%02d",vs));
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
							sendClearDealUpadte(v);
							startActivity(intent);
							finish();
						}
					});
				}
			};
			cT.start();

			// performing match - sets the claimedBy and deadLine fields in DB to 0
			Button completeMatch = (Button) findViewById(R.id.completeDeal);
			completeMatch.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (claimedBy.equals(uid)) {
						Toast toast = Toast.makeText(context, "You've already claimed this deal" , Toast.LENGTH_LONG);
						View view = toast.getView();
						view.setBackground(new ColorDrawable(Color.parseColor("#71bd90")));
						toast.show();
					} else {
						dialog.setMessage("Opening chat, please wait...");
						dialog.setProgressStyle(Color.parseColor("#71bd90"));
						dialog.show();

						sendUpadte(v);
					}
				}
			});
		}
	}

	public void sendUpadte(View v) {
		String claimer = claimedBy.substring(0, claimedBy.indexOf("-"));
		String completer = uid.substring(0, uid.indexOf("-"));
		String newMsg = msg + "chatid:" + uid;
		chatid = completer + claimer;

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("deadLine", null));
		params.add(new BasicNameValuePair("dealid",dealId));
		params.add(new BasicNameValuePair("uidNew", null));
		params.add(new BasicNameValuePair("chatid", chatid));
		params.add(new BasicNameValuePair("uid", completer));
		params.add(new BasicNameValuePair("msg", newMsg));
		params.add(new BasicNameValuePair("target", claimedBy));
		new DataTransfer(this, params, DataTransfer.METHOD_POST).execute("http://nir.milab.idc.ac.il/php/milab_send_deal_update.php");
	}

	public void sendClearDealUpadte(View v) {
		chatid = "clear";
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("deadLine", null));
		params.add(new BasicNameValuePair("dealid",dealId));
		params.add(new BasicNameValuePair("uidNew", null));
		params.add(new BasicNameValuePair("chatid", chatid));
		params.add(new BasicNameValuePair("uid", ""));
		new DataTransfer(this, params, DataTransfer.METHOD_POST).execute("http://nir.milab.idc.ac.il/php/milab_send_deal_update.php");
	}

	// This should change to class that will make the match by send phone number or anything like this
	@Override
	public void doOnPostExecute(JSONObject jObj) {
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
