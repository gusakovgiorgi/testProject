package net.gusakov.testprojectexample.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
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
import net.gusakov.testprojectexample.database.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by hasana on 10/6/2016.
 */

public class GalleryAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Integer> mImageIds = null;
    int screenWidth;
    DatabaseHelper mDatabase;

    public GalleryAdapter(Context ctx, DatabaseHelper db){
        mContext=ctx;
        mDatabase=db;
        mImageIds=mDatabase.getPosterImagesIds();
    }

    @Override
    public int getCount() {
        return mImageIds.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatabase.getPostersImage(mImageIds.get(position));
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        int imageWidth=(int)(screenWidth*0.7);
        ImageView view=(ImageView)convertView;
        if(convertView==null) {
            view = new ImageView(mContext);
        }
        view.setImageBitmap((Bitmap)getItem(position));
        view.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        return view;
    }
}
