package com.home.shaakya.material;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import com.google.android.gms.ads.InterstitialAd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class MemeListActivity extends Activity {

	private List<String> path;
	private GridView meme_list;
	private OldMemeAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.old_meme_list);
		

		path = new ArrayList<String>();
		oldCreatedMeme();
		meme_list = (GridView) findViewById(R.id.list_of_meme);
		adapter = new OldMemeAdapter(this, path);
		meme_list.setAdapter(adapter);
		
		meme_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {				
				Intent open = new Intent(MemeListActivity.this, ShowOldMemeActivity.class);
				open.putExtra("imagePath",path.get(arg2));
				startActivity(open);
			}
		});
	}

	private void oldCreatedMeme() {
		File folder = new File(Environment.getExternalStorageDirectory()
				+ "/Meme_Generator");
		if (folder.isDirectory()) {
			File[] listOfFiles = folder.listFiles();
			for (File file : listOfFiles) {
				if (file.isFile()) {
					path.add(file.getAbsolutePath());
					Log.i("image path", file.getAbsolutePath());
				}
			}
		} else
			Toast.makeText(getBaseContext(), "First Save Meme",
					Toast.LENGTH_SHORT).show();

	}

}
