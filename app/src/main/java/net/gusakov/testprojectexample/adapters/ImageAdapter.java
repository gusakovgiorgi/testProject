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

public class ImageAdapter extends BaseAdapter {

    private int mGalleryItemBackground;
    private Context mContext;
    private final Integer[] mImage = {R.drawable.tmp_poster1, R.drawable.tmp_poster2};
    int screenWidth;

    public ImageAdapter(Context ctx){
        mContext=ctx;
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
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
        int imageWidth=(int)(screenWidth*0.7);
        ImageView view=(ImageView)convertView;
        if(convertView==null) {
            view = new ImageView(mContext);
        }
        view.setImageResource(mImage[position]);
        view.setLayoutParams(new Gallery.LayoutParams(imageWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        return view;
    }
}
