package net.gusakov.testprojectexample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * Created by hasana on 10/6/2016.
 */

public class ImageAdapter extends BaseAdapter {

    private int mGalleryItemBackground;
    private Context mContext;
    private final Integer[] mImage = {R.drawable.tmp_poster1, R.drawable.tmp_poster2};

    public ImageAdapter(Context ctx){
        mContext=ctx;
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
        // TODO Auto-generated method stub
        ImageView view = new ImageView(mContext);
        view.setImageResource(mImage[position]);
        view.setPadding(20, 20, 20, 20);
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);

        return view;
    }
}
