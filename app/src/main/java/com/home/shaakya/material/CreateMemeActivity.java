package com.home.shaakya.material;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


@SuppressLint({"DefaultLocale", "NewApi"})
public class CreateMemeActivity extends AppCompatActivity implements OnClickListener {

    public static String TOP_TEXT = "";
    public static String BOTTOM_TEXT = "";
    private EditText edt_meme_top_txt, edt_meme_bottom_txt;

    public static ImageView meme_generated_img;
    private static int RESULT_LOAD_IMAGE = 1;
    private String SELECTED_IMAGE_PATH;
    public static Bitmap meme_image,logo,logo2;
    public static ImageView meme_generated_logo,meme_generated_logo2;
    public static Bitmap ALTERED_BITMAP;
    public static HandleMemeWriteBounds handleMemeWriteBounds;
    public static Typeface font_style;
    private FontDialog font;
    private FontSize fsize;
    private Toolbar toolbar;

    @TargetApi(Build.VERSION_CODES.N)
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_approach);


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getOverflowMenu();

        font = new FontDialog(this);
        fsize = new FontSize(this);
        if (getIntent().getAction().equals("home")) {
            findViews(BitmapFactory.decodeResource(getResources(), getIntent()
                    .getIntExtra("id", -1)));
        } else {
            findViews(BitmapFactory
                    .decodeFile(getIntent().getStringExtra("id")));
        }

    }


    @SuppressLint("DefaultLocale")
    public void findViews(Bitmap bmp) {
        meme_generated_img = (ImageView) this
                .findViewById(R.id.meme_generated_image);
        meme_image = scaleImage(bmp);
        meme_generated_img.setImageBitmap(meme_image);



        meme_generated_logo = (ImageView)findViewById(R.id.logo1);

        meme_generated_logo2 = (ImageView)findViewById(R.id.logo2);




        edt_meme_top_txt = (EditText) this.findViewById(R.id.edt_meme_top_txt);
        edt_meme_bottom_txt = (EditText) this
                .findViewById(R.id.edt_meme_bottom_txt1);

        handleMemeWriteBounds = new HandleMemeWriteBounds(meme_image,logo,logo2,font_style);

        ALTERED_BITMAP = handleMemeWriteBounds.generateImage(TOP_TEXT,
                BOTTOM_TEXT);
        meme_generated_img.setImageBitmap(ALTERED_BITMAP);

        edt_meme_top_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                TOP_TEXT = s.toString();
                ALTERED_BITMAP = handleMemeWriteBounds.generateImage(TOP_TEXT,
                        BOTTOM_TEXT);
                meme_generated_img.setImageBitmap(ALTERED_BITMAP);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edt_meme_bottom_txt.addTextChangedListener(new TextWatcher()

        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                BOTTOM_TEXT = s.toString();
                ALTERED_BITMAP = handleMemeWriteBounds.generateImage(TOP_TEXT,
                        BOTTOM_TEXT);
                meme_generated_img.setImageBitmap(ALTERED_BITMAP);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // adding exprot.xml file for menu option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meme_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }


    // perform action on menu choice for export, share and open
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // export contacts list in excel file
            case android.R.id.home:
                this.finish();
                break;
            case R.id.menu_share:
                saveImage(true);
                break;
            case R.id.menu_save:
                saveImage(false);
                break;
            case R.id.menu_old_meme_list:
                Intent intent = new Intent(CreateMemeActivity.this,
                        MemeListActivity.class);
                startActivity(intent);
                break;
            case R.id.select_logo:
                Intent intent_logo = new Intent(CreateMemeActivity.this,LogoList.class);
                startActivity(intent_logo);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveImage(boolean isShare) {
        try {
            if (ALTERED_BITMAP != null) {

                String path = getPath();
                FileOutputStream out = new FileOutputStream(new File(path));
                ALTERED_BITMAP.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
                if (isShare) {
                    shareImage(path);
                } else {

                    CustomToast.showSavedDialog(this, "Image saved at \n"
                            + path);

                }


            } else
                Toast.makeText(this, "enter texts first!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
        }

    }

    public String getPath() {
        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory(), "Meme_Generator");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "pic_" + timeStamp + ".jpg");
        return mediaFile.toString();
    }

    public void shareImage(String result) {
        Uri imageUri = Uri.parse("file://" + result);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(intent, "Share"));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            SELECTED_IMAGE_PATH = cursor.getString(columnIndex);
            cursor.close();
            this.findViews(BitmapFactory.decodeFile(SELECTED_IMAGE_PATH));
        } else {
            Toast.makeText(getBaseContext(), "Select an Image",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public Bitmap scaleImage(Bitmap bmp) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int displayWidth = metrics.widthPixels;
        float imageWidth = bmp.getWidth();
        float imageHeight = bmp.getHeight();
        float aspectRatio = imageWidth / imageHeight;
        int finalWidth = 0;
        if (imageWidth > displayWidth) {
            finalWidth = displayWidth;
        } else {
            finalWidth = (int) imageWidth;
        }
        float finalHeight = finalWidth / aspectRatio;

        Bitmap bitmap = Bitmap.createScaledBitmap(bmp, finalWidth,
                (int) finalHeight, true);
        return bitmap;
    }

    @Override
    public void onClick(View v) {

    }

    private void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        ALTERED_BITMAP = handleMemeWriteBounds.generateImage(TOP_TEXT, BOTTOM_TEXT);
        meme_generated_img.setImageBitmap(ALTERED_BITMAP);
        super.onResume();
    }
}
