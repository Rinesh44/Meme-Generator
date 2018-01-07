package com.home.shaakya.material;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

public class TakeCameraImage {
	Activity mActivity;
	public TakeCameraImage(Activity activity){
		this.mActivity = activity;
	}
	
	
	public void openCamera() {
		MainActivity.gallery = false;
		

		 File image = new File(appFolderCheckandCreate(), "img" + getTimeStamp() + ".jpg");
         Uri uriSavedImage = Uri.fromFile(image);
         MainActivity.cameraImagePath = image.getAbsolutePath();
         Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		 i.putExtra("android.intent.extras.CAMERA_FACING", 1);
         i.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
         i.putExtra("return-data", true);
         mActivity.startActivityForResult(i,  MainActivity.RESULT_LOAD_IMAGE);
	}
							
	private String appFolderCheckandCreate(){

	    String appFolderPath="";
	    File externalStorage = Environment.getExternalStorageDirectory();

	    if (externalStorage.canWrite())
	    {
	        appFolderPath = externalStorage.getAbsolutePath() + "/Meme_Generator";
	        File dir = new File(appFolderPath);

	        if (!dir.exists()) 
	        {
	              dir.mkdirs();
	        }

	    }
	    else
	    {

	    }

	    return appFolderPath;
	}



	 private String getTimeStamp() {

	    final long timestamp = new Date().getTime();

	    final Calendar cal = Calendar.getInstance();
	                   cal.setTimeInMillis(timestamp);

	    final String timeString = new SimpleDateFormat("HH_mm_ss_SSS").format(cal.getTime());


	    return timeString;
	}
}
