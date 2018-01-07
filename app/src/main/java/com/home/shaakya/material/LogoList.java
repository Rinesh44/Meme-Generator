package com.home.shaakya.material;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LogoList extends AppCompatActivity {
    private Toolbar toolbar;
    private GridView logo_list;
    private LogoAdapter adapter;
    private List<String> path;
    private ImageView image;
    public static boolean topleft;
    public static boolean topright;
    public static boolean bottomleft;
    public static HandleMemeWriteBounds handleMemeWriteBounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_list);
        path = new ArrayList<String>();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        logo_list = (GridView) findViewById(R.id.list_of_logo);

        adapter = new LogoAdapter(this);
        logo_list.setAdapter(adapter);


        logo_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                CreateMemeActivity.meme_generated_logo = (ImageView) logo_list.getChildAt(arg2);
                Toast.makeText(LogoList.this, "The logo has been selected", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logo_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

        @Override
        public boolean onOptionsItemSelected (MenuItem item) {
            switch (item.getItemId()){
                case android.R.id.home:
                onBackPressed();
                break;

                case R.id.position_topleft:
                    topleft = true;
                    return topleft;

                case R.id.position_topright:
                    topright = true;
                    break;

                case R.id.position_bottomleft:
                    bottomleft = true;
                    break;
            }
            return super.onOptionsItemSelected(item);
        }
    }