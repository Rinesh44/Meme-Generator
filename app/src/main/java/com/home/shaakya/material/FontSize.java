package com.home.shaakya.material;

import com.home.shaakya.material.CreateMemeActivity;
import com.home.shaakya.material.R;
import com.home.shaakya.material.HandleMemeWriteBounds;
import com.home.shaakya.material.PaintStyle;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class FontSize extends Dialog {
	private int currentSize = 20;
	private SeekBar setSize;
	Activity activity;

	public FontSize(Activity activity) {
		super(activity);
		this.activity = activity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.font_size);

		setSize = (SeekBar) findViewById(R.id.seekbar);
		setSize.setMax(70);
		setSize.setProgress(currentSize);
		setSize.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {					
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				PaintStyle.FONT_SIZE =progress;
				setFontSize();
			}
		});

	}

	private void setFontSize() {
		CreateMemeActivity.handleMemeWriteBounds = new HandleMemeWriteBounds(CreateMemeActivity.meme_image,CreateMemeActivity.logo,CreateMemeActivity.logo2,CreateMemeActivity.font_style);
		CreateMemeActivity.ALTERED_BITMAP = CreateMemeActivity.handleMemeWriteBounds
				.generateImage(CreateMemeActivity.TOP_TEXT,
						CreateMemeActivity.BOTTOM_TEXT);
		CreateMemeActivity.meme_generated_img
				.setImageBitmap(CreateMemeActivity.ALTERED_BITMAP);
	}
}
