package net.gusakov.testprojectexample.adapters;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.gusakov.testprojectexample.R;

/**
 * Created by hasana on 10/6/2016.
 */

public class GalleryAdapter extends BaseAdapter {

    private Context mContext;
    private  int[] mImage = null;
    int screenWidth;

    public GalleryAdapter(Context ctx,int[] imageResourceArray){
        mContext=ctx;
        mImage=imageResourceArray;
    }

    @Override
    public int getCount() {
        return mImage.length;
    }

    @Override
    public Object getItem(int position) {
        return mImage[position];
    }

    @Override
    public long getItemId(int position) {
        return  mImage[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        int imageWidth=(int)(screenWidth*0.7);
        ImageView view=(ImageView)convertView;
        if(convertView==null) {
            view = new ImageView(mContext);
        }
        view.setImageResource(mImage[position]);
        view.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        return view;
    }
}
