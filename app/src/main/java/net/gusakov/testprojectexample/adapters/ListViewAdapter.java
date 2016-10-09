package net.gusakov.testprojectexample.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.gusakov.testprojectexample.Gallery_activity;
import net.gusakov.testprojectexample.R;

import java.util.NoSuchElementException;

/**
 * Created by hasana on 10/6/2016.
 */

public class ListViewAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    Integer[] objects;
    private int screenWidth;
    private final int[] mImage = {R.drawable.tmp_poster1, R.drawable.tmp_poster2};
    public static final int KEY_IMAGE_VIEW_TAG = -100;
    public static final String KEY_GALLERY_ACTIVITY_INTENT_EXTRA_IMAGES_ARRAY = "GALLERY_ACTIVITY_INTENT_EXTRA_IMAGES_ARRAY";
    public static final String KEY_GALLERY_ACTIVITY_INTENT_EXTRA_IMAGE_POSITION = "GALLERY_ACTIVITY_INTENT_EXTRA_IMAGE_POSITION";


    public ListViewAdapter(Context context, Integer[] images) {
        ctx = context;
        objects = images;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;

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
            View galleryView =lInflater.inflate(R.layout.horizontal_view,parent,false);
            LinearLayout linearLayout=(LinearLayout) galleryView.findViewById(R.id.containerId);
            for(int imageInScrollViewPosition=0;imageInScrollViewPosition<mImage.length;imageInScrollViewPosition++) {
                ImageView imageView=new ImageView(ctx);
                setScaledImage(imageView, mImage[imageInScrollViewPosition], 0.8f);
                imageView.setTag(KEY_IMAGE_VIEW_TAG,imageInScrollViewPosition);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent galleryIntent=new Intent(ctx, Gallery_activity.class);
                        galleryIntent.putExtra(KEY_GALLERY_ACTIVITY_INTENT_EXTRA_IMAGES_ARRAY,mImage);
                        galleryIntent.putExtra(KEY_GALLERY_ACTIVITY_INTENT_EXTRA_IMAGE_POSITION,(int)v.getTag(KEY_IMAGE_VIEW_TAG));
                        ctx.startActivity(galleryIntent);
                    }
                });
//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                linearLayout.addView(imageView);
            }
//            gallery.setAdapter(new GalleryAdapter(ctx));
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
