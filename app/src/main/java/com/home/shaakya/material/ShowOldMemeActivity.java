package com.home.shaakya.material;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;





public class ShowOldMemeActivity extends AppCompatActivity {

	private String path;
	private Toolbar toolbar;
	private ImageView old_meme_image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_meme_layout);

		path = getIntent().getStringExtra("imagePath");
		old_meme_image = (ImageView) findViewById(R.id.img_show_meme);
		old_meme_image.setImageURI(Uri.parse(path));

		toolbar = (Toolbar)findViewById(R.id.app_bar);
		setSupportActionBar(toolbar);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.share_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_share:
			shareImage();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void shareImage() {
		Uri imageUri = Uri.parse("file://" + path);
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/png");
		intent.putExtra(Intent.EXTRA_STREAM, imageUri);
		startActivity(Intent.createChooser(intent, "Share"));
	}


}
