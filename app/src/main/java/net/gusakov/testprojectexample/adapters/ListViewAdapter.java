package net.gusakov.testprojectexample.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

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
    private int screenHeight;
    private float screenDensity;
    private final int[] mImage = {R.drawable.tmp_poster1, R.drawable.tmp_poster2};
    private final int[] mAlbumImage={R.drawable.tmp_album_1,R.drawable.tmp_album_2};
    public static final int KEY_IMAGE_VIEW_TAG = -100;
    private int previousViewWidth;
    private int previousViewHeight;
    public static final String KEY_GALLERY_ACTIVITY_INTENT_EXTRA_IMAGES_ARRAY = "GALLERY_ACTIVITY_INTENT_EXTRA_IMAGES_ARRAY";
    public static final String KEY_GALLERY_ACTIVITY_INTENT_EXTRA_IMAGE_POSITION = "GALLERY_ACTIVITY_INTENT_EXTRA_IMAGE_POSITION";
    public static final int ALBUM_IMAGE_FIRST_TEMPLATE_RESOURCE_ID=R.drawable.first_album_template;
    public static final int ALBUM_IMAGE_SECOND_TEMPLATE_RESOURCE_ID=R.drawable.second_album_template;
    boolean firstImageTemplateIsUsed=false;


    public ListViewAdapter(Context context, Integer[] images) {
        ctx = context;
        objects = images;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dm=new DisplayMetrics();
        display.getMetrics(dm);
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight=size.y;
        screenDensity=dm.density;

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
                        ((Activity)ctx).overridePendingTransition( R.anim.slide_in_up,R.anim.stay);
                    }
                });
//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                linearLayout.addView(imageView);
            }
//            gallery.setAdapter(new GalleryAdapter(ctx));
//            return galleryView;
            return galleryView;
        }else if(position==6) {
            ImageView view=new ImageView(ctx);
                            AbsListView.LayoutParams params = new AbsListView.LayoutParams(getPreviousViewWidth(), getPreviousViewHeight());
            view.setBackgroundResource(R.drawable.metro_animation);
                view.setLayoutParams(params);
//                view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                    AnimationDrawable loadingAnimation = (AnimationDrawable) v.getBackground();

                    loadingAnimation.start();
                }

                @Override
                public void onViewDetachedFromWindow(View v) {
                }
            });
            return view;
        }else if(position==7) {
            View view= lInflater.inflate(R.layout.footer_layout,null,false);
            HorizontalScrollView horizontalScrollView=(HorizontalScrollView)view.findViewById(R.id.footerHorizontalViewId);
            horizontalScrollView.getLayoutParams().height=(int)(screenHeight/2.5f);
            LinearLayout linearLayout=(LinearLayout)view.findViewById(R.id.horizontalSVContainertId);
            for(int i=0;i<mAlbumImage.length;i++){
                linearLayout.addView(returnDrawedView(mAlbumImage[i]));
            }

            return view;
        }else{
            ImageView view=null;
            if(convertView instanceof ImageView) {
                view = (ImageView) convertView;
            }
            if (view == null) {
                view = new ImageView(ctx);
            }


                setScaledImage(view, objects[position], 1.0f);


            return view;
        }
    }

    private View returnDrawedView(int imageResourceId){
        int templateid;
        float angle;
        if(!firstImageTemplateIsUsed){
            templateid=ALBUM_IMAGE_FIRST_TEMPLATE_RESOURCE_ID;
            angle=3.0f;
            firstImageTemplateIsUsed=true;
        }else{
            templateid=ALBUM_IMAGE_SECOND_TEMPLATE_RESOURCE_ID;
            angle=2.0f;
            firstImageTemplateIsUsed=false;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(ctx
                .getResources(), templateid);
        Log.v("MyTag","dimensiond: "+bitmap.getWidth()+" x "+bitmap.getHeight());
        /* set other image top of the first icon */
        Bitmap tmpBitMap = BitmapFactory.decodeResource(ctx
                .getResources(), imageResourceId);
        Matrix matrix=new Matrix();
        matrix.setRotate(angle,tmpBitMap.getWidth()/2,tmpBitMap.getHeight()/2);
        Bitmap rotatedBitmap = Bitmap.createBitmap(tmpBitMap,0,0,tmpBitMap.getWidth(),tmpBitMap.getHeight(),matrix,true);
//        rotatedBitmap.eraseColor(Color.TRANSPARENT);

        Bitmap bmOverlay = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bmOverlay);
//        canvas.drawARGB(0x00, 0, 0, 0);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawBitmap(rotatedBitmap,40*screenDensity,90*screenDensity,null);
        BitmapDrawable dr = new BitmapDrawable(bmOverlay);
        dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());

        ImageView imageView=new ImageView(ctx);

//        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(bmOverlay.getWidth(),bmOverlay.getHeight());
        Log.v("MyTag","dimensiond: "+bmOverlay.getWidth()+" x "+bmOverlay.getHeight());
//        imageView.setLayoutParams(params);
                imageView.setImageDrawable(dr);
        Log.v("MyTag","imageView dimensions: "+imageView.getWidth()+" x "+imageView.getHeight());
        return imageView;
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

        saveDimensions(width,height);
        // Now change ImageView's dimensions to match the scaled image
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(width,height);
        view.setLayoutParams(params);
    }

    private void saveDimensions(int width, int height) {
        previousViewWidth=width;
        previousViewHeight=height;
    }
    private int getPreviousViewWidth(){
        return previousViewWidth;
    }
    private int getPreviousViewHeight(){
        return previousViewHeight;
    }

//    private int dpToPx(int dp) {
//        float density = getApplicationContext().getResources().getDisplayMetrics().density;
//        return Math.round((float)dp * density);
//    }
}
