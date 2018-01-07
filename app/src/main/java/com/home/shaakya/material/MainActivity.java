package com.home.shaakya.material;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;


public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    private Toolbar toolbar;

    private ListView meme_img_listview;
    private ImageAdapter adapter;
    private List<MemeImages> memeList;

    public static int RESULT_LOAD_IMAGE = 1;
    public static boolean gallery = false;
    public static String cameraImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        onStartView();

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment navigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        navigationDrawerFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout)findViewById(R.id.drawer_layout),toolbar);

    }

    private void onStartView() {
        adapter=new ImageAdapter(this,memeList);
        meme_img_listview.setAdapter(adapter);
        meme_img_listview.setOnItemClickListener(this);


    }

    private void findViews(){
        initialiseList();
        meme_img_listview=(ListView)this.findViewById(R.id.meme_img_list);

    }

    private void initialiseList() {
        memeList=new ArrayList<MemeImages>();

        memeList.add(new MemeImages("Willy Wonka",R.drawable.meme1));
        memeList.add(new MemeImages("Candy Crash",R.drawable.meme2));
        memeList.add(new MemeImages("Flurty Dog",R.drawable.meme3));
        memeList.add(new MemeImages("Innocent Cat",R.drawable.meme4));
        memeList.add(new MemeImages("Annoying Childhood",R.drawable.meme5));
        memeList.add(new MemeImages("Hungry Bear",R.drawable.meme6));
        memeList.add(new MemeImages("Miss You Baby",R.drawable.meme7));
        memeList.add(new MemeImages("Techno Aunty",R.drawable.meme8));
        memeList.add(new MemeImages("Grumpy Cat",R.drawable.meme9));
        memeList.add(new MemeImages("Overly Attached Girlfriend",R.drawable.meme10));
        memeList.add(new MemeImages("Business Cat",R.drawable.meme11));
        memeList.add(new MemeImages("Most Interesting Men",R.drawable.meme12));
        memeList.add(new MemeImages("Crazy Duck",R.drawable.meme13));
        memeList.add(new MemeImages("Lord of Toy",R.drawable.meme14));
        memeList.add(new MemeImages("Come on Pussy",R.drawable.meme15));
        memeList.add(new MemeImages("Pussy T",R.drawable.meme16));
        memeList.add(new MemeImages("Mad in Love",R.drawable.meme17));
        memeList.add(new MemeImages("Annoying Facebook Girl",R.drawable.meme18));
        memeList.add(new MemeImages("Ridiculously Photogenic",R.drawable.meme19));
        memeList.add(new MemeImages("Confession boy",R.drawable.meme20));

    }

    @Override
      public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            this.fireCreateMemeActivity("home", arg2);

        }


    public void onFabClick2(View v){
        TakeCameraImage take = new TakeCameraImage(this);
        take.openCamera();
    }


    public void fireCreateMemeActivity(String action,int position){
        Intent i = new Intent(this,CreateMemeActivity.class);
        i.setAction(action);
        if(action.equals("home")){
            i.putExtra("id", memeList.get(position).getFile());
        }
        startActivity(i);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("request", requestCode+"  result  "+resultCode+"  intent  "+data);
        super.onActivityResult(requestCode, resultCode, data);
        if (gallery == true) {
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                    && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                Intent i = new Intent(MainActivity.this, CreateMemeActivity.class);
                i.setAction("gallery");
                i.putExtra("id", picturePath);
                startActivity(i);
            } else {
                this.finish();
                startActivity(new Intent(this, MainActivity.class));
            }
        } else if (gallery == false) {
            File file = new File(cameraImagePath);
            if (file.exists()) {
                Intent i = new Intent(MainActivity.this, CreateMemeActivity.class);
                i.setAction("gallery");
                i.putExtra("id", cameraImagePath);
                startActivity(i);
            } else {
                this.finish();
                startActivity(new Intent(this, MainActivity.class));
            }

        } else {
            this.finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action1) {
            adapter.notifyDataSetChanged();
            Collections.sort(memeList, new Comparator<MemeImages>() {
                @Override
                public int compare(MemeImages o1, MemeImages o2) {
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
            });
                       return true;

        } else {
            adapter.notifyDataSetChanged();
            Collections.sort(memeList, new Comparator<MemeImages>() {
                @Override
                public int compare(MemeImages o1, MemeImages o2) {
                    return o2.getName().compareToIgnoreCase(o1.getName());
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}

