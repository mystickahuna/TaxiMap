package com.example.taximap.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.taximap.*;

public class ListViewActivity extends Activity implements
		android.view.View.OnClickListener {
	private static Activity context;
	private static String sortField="name";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_list_layout);
		((Button) findViewById(R.id.sort_by_name)).setOnClickListener(this);
		((Button) findViewById(R.id.sort_by_company)).setOnClickListener(this);
		((Button) findViewById(R.id.sort_by_rating)).setOnClickListener(this);
		((Button) findViewById(R.id.sort_by_distance)).setOnClickListener(this);
		context=this;
	}

	public void onResume() {
		super.onResume();
		createList();
	}

	public static void createList() {
		// bind ListView and use it as the container for listitem
		if(context==null){
			return;
		}
		ListView list = (ListView) context.findViewById(R.id.listview);
		list.setAdapter(null);
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		// sort DriverList by name default
		if (MapViewActivity.markerType.equals("driver")) {
			if (MapViewActivity.driverLst == null) {
				return;
			}
			if (sortField.equals("name")) {
				Collections.sort(MapViewActivity.driverLst,
						new Comparator<Driver>() {
							public int compare(Driver o1, Driver o2) {
								int compResult = o1.name.compareTo(o2.name);
								return compResult;
							}
						});
			} else if (sortField.equals("company")) {
				Collections.sort(MapViewActivity.driverLst,
						new Comparator<Driver>() {
							public int compare(Driver o1, Driver o2) {
								int compResult = o1.company
										.compareTo(o2.company);
								return compResult;
							}
						});
			}
			if (sortField.equals("rating")) {
				Collections.sort(MapViewActivity.driverLst,
						new Comparator<Driver>() {
							public int compare(Driver o1, Driver o2) {
								return o1.rating < o2.rating ? 1 : -1;
							}
						});
			}
			if (sortField.equals("distance")) {
				Collections.sort(MapViewActivity.driverLst,
						new Comparator<Driver>() {
							public int compare(Driver o1, Driver o2) {
								return o1.distance > o2.distance ? 1 : -1;
							}
						});
			}
			for (Driver d : MapViewActivity.driverLst) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("itemtitle", d.name);
				map.put("itemtext", d.snippet());
				mylist.add(map);
			}
			SimpleAdapter adapter = new SimpleAdapter(context, mylist, // data
																	// source
					R.layout.listviewitem, // ListItem XML
					// key correspondence
					new String[] { "itemtitle", "itemtext" }, new int[] {
							R.id.itemtitle, R.id.itemtext });
			// add and display
			list.setAdapter(adapter);
/*			Toast.makeText(this, Integer.toString(list.getCount()),
					Toast.LENGTH_SHORT).show();*/
			
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					for(int i=0;i<parent.getChildCount();i++){
						parent.getChildAt(i).setBackgroundColor(0x000000);
					}
					parent.getChildAt(position).setBackgroundColor(context.getResources().getColor(R.color.Yellow));
					/*for(View v:parent.get)
			    	view.setBackgroundColor(color)
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					MapViewActivity.driverLst.get(pos).marker.hideInfoWindow();*/
				}
				
			});
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.sort_by_name:
			sortField="name";
			createList();
			break;
		case R.id.sort_by_company:
			sortField="company";
			createList();
			break;
		case R.id.sort_by_rating:
			sortField="rating";
			createList();
			break;
		case R.id.sort_by_distance:
			sortField="distance";
			createList();
			break;
		}

	}
}