package com.home.shaakya.material;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class LogoAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public Integer[] mThumbIds = { R.mipmap.log,R.mipmap.log2,R.drawable.samsung,
            R.drawable.vodafone,R.drawable.htc,R.drawable.oppo,R.drawable.gionee,R.drawable.nokia,
            R.drawable.sony,R.drawable.huawei
           };


    public LogoAdapter(Context c){
        mContext = c;
    }


    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(100, 100));

        Picasso.with(mContext).load(mThumbIds[position]).into(imageView);
        return imageView;
    }
}