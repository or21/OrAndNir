package com.main.divvyapp;

import helpeMethods.DealObj;
import helpeMethods.ListDealsAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import serverComunication.ClietSideCommunicator;
import serverComunication.ServerAsyncParent;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.location.*;

public class StorePage extends Activity implements ServerAsyncParent{

	private String filter;
	private ListView dealList;
	private Context context;
	private ClietSideCommunicator cummunicator;
	private LocationManager mLocationManager;
	private Location myLocation;
	private String city;

	// Turn on location based filter
	public static boolean locationFlag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_page);

		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
		init();
	}

	private void init() {
		// Menu bar coloring
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#71bd90")));
		bar.setTitle("");
		context = getApplicationContext();

		// Location
		myLocation = getLastKnownLocation();
		if ((myLocation == null) || (findMyCity() == false)) {
			city = null;
		}

		filter = getIntent().getExtras().getString("filter");

		// initialize the main list of deals
		dealList = (ListView) findViewById(R.id.dealList);

		// Gets data from previous activity - not necessary
		Intent intent = getIntent();
		int id = intent.getIntExtra("id", -1);
		getDataFromServer(id);
	}

	private Location getLastKnownLocation() {
		mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
		List<String> providers = mLocationManager.getProviders(true);
		Location bestLocation = null;
		for (String provider : providers) {
			Location l = mLocationManager.getLastKnownLocation(provider);
			if (l == null) {
				continue;
			}
			if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
				// Found best last known location: %s", l);
				bestLocation = l;
			}
		}

		return bestLocation;
	}

	private boolean findMyCity(){
		Locale.getDefault();
		Geocoder gcd = new Geocoder(context, Locale.ENGLISH);
		double lat = myLocation.getLatitude();
		double lng = myLocation.getLongitude();
		List<Address> addresses = null;
		try {
			addresses = gcd.getFromLocation(lat, lng, 1);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		if (addresses.size() > 0 && addresses != null) {
			city = addresses.get(0).getLocality();	
			return true;
		}

		return false;
	}

	// Menu Bar
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_filter:
			Intent intent1 = new Intent(context, Filter.class);
			startActivity(intent1);
			finish();
			return true;
		case R.id.previous_chats:
			Intent intent2 = new Intent(context, ChatHistory.class);
			startActivity(intent2);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void getDataFromServer(int id) {
		// Sending GET request to server
		cummunicator = new ClietSideCommunicator();
		cummunicator.connectDealsTable(this);
	}

	public void setDataFromServer(JSONArray deals) {
		try {
			final ArrayList<DealObj> fillMaps = new ArrayList<DealObj>();
			for (int i = 0; i < deals.length(); i++) {
				JSONObject row = deals.getJSONObject(i);

				if ((filter + "\n").equals(row.getString("category")) || filter.equals(row.getString("category"))
						|| filter.equalsIgnoreCase("all")) {
					DealObj deal = new DealObj(
							row.getString("id"),
							row.getString("storeid"),
							row.getString("category"),
							row.getString("claimedBy"),
							row.getString("picture"),
							row.getString("deadLine"),
							row.getString("dealName"),
							row.getString("city"),
							row.getString("userName"));

					// if the flag is on - filter deals by location
					// if location unavailable - show all deals
					if (locationFlag) {
						if (deal.getCity().equalsIgnoreCase(city) || city.equalsIgnoreCase(null)
								|| deal.getCity().equalsIgnoreCase(null)) {
							fillMaps.add(deal);
						}
					} else{
						fillMaps.add(deal);
					}
				}
			}

			dealList = (ListView) findViewById(R.id.dealList);
			if (fillMaps.isEmpty()) {
				CharSequence text = "Nothing here yet";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				View view = toast.getView();
				view.setBackground(new ColorDrawable(Color.parseColor("#71bd90")));
				toast.show();
			}
			ListDealsAdapter adapter = new ListDealsAdapter(this, fillMaps, R.layout.layout_list_item);
			dealList.setAdapter(adapter);
			dealList.setClickable(true);
			dealList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					if (fillMaps.get(position).getClaimedBy().length() < 15) {
						Intent intent = new Intent(context, FindMeMatch.class);
						intent.putExtra("dealid", fillMaps.get(position).getId());
						intent.putExtra("storeId", fillMaps.get(position).getStoreId());
						intent.putExtra("category",fillMaps.get(position).getCategory());
						intent.putExtra("picture", fillMaps.get(position).getPicture());
						intent.putExtra("dealName", fillMaps.get(position).getDealName());
						intent.putExtra("city", fillMaps.get(position).getCity());
						intent.putExtra("userNameClaimed", fillMaps.get(position).getUserNameClaimed());
						startActivity(intent);

					} else {
						Intent intent = new Intent(context, CompleteMatch.class);
						intent.putExtra("dealid", fillMaps.get(position).getId());
						intent.putExtra("claimedBy", fillMaps.get(position).getClaimedBy());
						intent.putExtra("deadLine", fillMaps.get(position).getDeadLine());
						intent.putExtra("storeId", fillMaps.get(position).getStoreId());
						intent.putExtra("category",fillMaps.get(position).getCategory());
						intent.putExtra("picture", fillMaps.get(position).getPicture());
						intent.putExtra("dealName", fillMaps.get(position).getDealName());
						intent.putExtra("city", fillMaps.get(position).getCity());
						intent.putExtra("userNameClaimed", fillMaps.get(position).getUserNameClaimed());
						startActivity(intent);
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		if (filter.equals("all")) {
			new AlertDialog.Builder(this).setIcon(android.R.drawable.stat_sys_warning).setTitle("Exit")
			.setMessage("Are you sure you want to exit?")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
					System.exit(0);
				}
			}).setNegativeButton("No", null).show();
		} 
		else {
			Intent intent = new Intent(context, Filter.class);
			startActivity(intent);
			finish();
		}
	}

	public void doOnPostExecute(JSONObject jObj) {
		try {
			// Retrieving JSON array from server
			setDataFromServer(jObj.getJSONArray("deals"));

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}