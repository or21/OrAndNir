package com.main.divvyapp;

import java.util.ArrayList;

import helpeMethods.FilterAdatpter;
import helpeMethods.FilterObj;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Switch;

public class Filter extends Activity {

	private Context context;
	private Switch loc;
	private final String[] categories = {"All", "Children\'s Apparel", "Men\'s Apparel", "Women\'s Apparel",
			"Women\'s Shoes", "Men\'s Shoes", "Health & Beauty", "Home Furnishing Decor", 
			"Jewellery & Accessories", "Technology","Baby & Infants", "Beauty & Fragrance"};

	private int[] images = {R.drawable.filter_icon, R.drawable.ca_icon, R.drawable.ma_icon, R.drawable.wa_icon, R.drawable.ws_icon, R.drawable.ms_icons,
			R.drawable.hf_icon, R.drawable.hdf_icons, R.drawable.ja_icon, R.drawable.tei_con, R.drawable.ba_icons, R.drawable.bf_icon};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);

		// Menu bar coloring
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#73bd90")));
		bar.setTitle("Categories");

		context = getApplicationContext();

		loc = (Switch) findViewById(R.id.switch1);
		loc.setChecked(StorePage.locationFlag);
		loc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StorePage.locationFlag = !StorePage.locationFlag;
			}
		});

		ArrayList<FilterObj> categoriesList= new ArrayList<FilterObj>();

		for (int i = 0; i < 12; i++) {
			categoriesList.add(new FilterObj(categories[i], images[i]));
		}

		FilterAdatpter adapter = new FilterAdatpter(this,R.layout.filter_list_item , categoriesList);
		ListView listview = (ListView) findViewById(R.id.listViewId);
		listview.setAdapter(adapter);

		listview.setClickable(true);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(context, StorePage.class);
				intent.putExtra("filter", categories[position]);
				startActivity(intent);
			}
		});

	}
}
