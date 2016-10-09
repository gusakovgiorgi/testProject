package net.gusakov.testprojectexample.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import net.gusakov.testprojectexample.R;
import net.gusakov.testprojectexample.adapters.ImageAdapter;

import java.util.NoSuchElementException;

import static android.R.attr.bitmap;

/**
 * Created by hasana on 10/6/2016.
 */

public class ListViewAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    Integer[] objects;
    private int screenWidth;
    private final Integer[] mImage = {R.drawable.tmp_poster1, R.drawable.tmp_poster2};


    public ListViewAdapter(Context context, Integer[] images) {
        ctx = context;
        objects = images;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth=size.x;

    }
    @Override
    public int getCount() {
        return objects.length;
    }

    @Override
    public Object getItem(int position) {
        return objects[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position==1){
            View galleryView =lInflater.inflate(R.layout.galerry_view,parent,false);
            LinearLayout linearLayout=(LinearLayout) galleryView.findViewById(R.id.containerId);
            for(int i=0;i<mImage.length;i++) {
                ImageView imageView=new ImageView(ctx);
                setScaledImage(imageView, mImage[i], 0.8f);
//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                linearLayout.addView(imageView);
            }
//            gallery.setAdapter(new ImageAdapter(ctx));
//            return galleryView;
            return galleryView;
        }else {
            ImageView view=null;
            if(convertView instanceof ImageView) {
                view = (ImageView) convertView;
            }
            if (view == null) {
                view = new ImageView(ctx);
            }

            setScaledImage(view,objects[position],1.0f);


            return view;
        }
    }

    private void setScaledImage(ImageView view,int drawableId,float scaleParameter) throws NoSuchElementException {
        // Get bitmap from the the ImageView.
        Bitmap bitmap = null;

        try {
            bitmap = BitmapFactory.decodeResource(ctx.getResources(), drawableId);

        } catch (NullPointerException e) {
            throw new NoSuchElementException("No drawable on given view");
        } catch (ClassCastException e) {
            // Check bitmap is Ion drawable
//            bitmap = Ion.with(view).getBitmap();
        }

        // Get current dimensions AND the desired bounding box
        int width = 0;

        try {
            width = bitmap.getWidth();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Can't find bitmap on given view/drawable");
        }


        int height = bitmap.getHeight();
        int bounding =(int)(screenWidth*scaleParameter);

        float xScale = ((float) bounding) / width;

        Matrix matrix = new Matrix();
        matrix.postScale(xScale, xScale);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        width = scaledBitmap.getWidth(); // re-use
        height = scaledBitmap.getHeight(); // re-use
        bitmap.recycle();
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);

        // Apply the scaled bitmap
        view.setImageDrawable(result);

        // Now change ImageView's dimensions to match the scaled image
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(width,height);
        view.setLayoutParams(params);
    }

//    private int dpToPx(int dp) {
//        float density = getApplicationContext().getResources().getDisplayMetrics().density;
//        return Math.round((float)dp * density);
//    }
}
