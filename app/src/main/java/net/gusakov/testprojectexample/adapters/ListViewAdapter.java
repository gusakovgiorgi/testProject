package net.gusakov.testprojectexample.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
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

import net.gusakov.testprojectexample.Gallery_activity;
import net.gusakov.testprojectexample.R;
import net.gusakov.testprojectexample.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Created by hasana on 10/6/2016.
 */

public class ListViewAdapter extends BaseAdapter {
    private DatabaseHelper mDatabse;
    private Context ctx;
    private LayoutInflater lInflater;
    private Integer[] objects;
    private int screenWidth;
    private int screenHeight;
    private float screenDensity;
    private ArrayList<Integer> mPosterImage =null;
    private ArrayList<Integer> mAlbumImageIds =null;
    public static final int KEY_IMAGE_VIEW_TAG = -100;
    private int previousViewWidth;
    private int previousViewHeight;
    public static final String KEY_GALLERY_ACTIVITY_INTENT_EXTRA_IMAGES_IDS_ARRAY = "GALLERY_ACTIVITY_INTENT_EXTRA_IMAGES_ARRAY";
    public static final String KEY_GALLERY_ACTIVITY_INTENT_EXTRA_IMAGE_POSITION = "GALLERY_ACTIVITY_INTENT_EXTRA_IMAGE_POSITION";
    public static final int ALBUM_IMAGE_FIRST_TEMPLATE_RESOURCE_ID=R.drawable.first_album_template;
    public static final int ALBUM_IMAGE_SECOND_TEMPLATE_RESOURCE_ID=R.drawable.second_album_template;
    boolean firstImageTemplateIsUsed=false;


    public ListViewAdapter(Context context, Integer[] images,DatabaseHelper databaseHelper) {
        ctx = context;
        objects = images;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        GetDisplayParameters();
        mDatabse=databaseHelper;
        mPosterImage =mDatabse.getPosterImagesIds();
        mAlbumImageIds =mDatabse.getAlbumImagesIds();

    }

    private void GetDisplayParameters() {
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
            return getHorizontalView(parent);
        }else if(position==6) {
            return getAnimateedImageView();
        }else if(position==7) {
            return getFooterView(parent);
        }else{
            return getScaledImageView(objects[position], convertView);
        }
    }

    @NonNull
    private ImageView getScaledImageView(Integer imageResourceID, View convertView) {
        ImageView view=null;
        if(convertView instanceof ImageView) {
            view = (ImageView) convertView;
        }
        if (view == null) {
            view = new ImageView(ctx);
        }


        setScaledImage(view, imageResourceID, 1.0f);
        return view;
    }

    @NonNull
    private View getFooterView(ViewGroup parent) {
        View view= lInflater.inflate(R.layout.footer_layout,parent,false);
        HorizontalScrollView horizontalScrollView=(HorizontalScrollView)view.findViewById(R.id.footerHorizontalViewId);
        horizontalScrollView.getLayoutParams().height=(int)(screenHeight/2.5f);
        LinearLayout linearLayout=(LinearLayout)view.findViewById(R.id.horizontalSVContainertId);
        for(int i = 0; i< mAlbumImageIds.size(); i++){
            linearLayout.addView(returnDrawedView(mDatabse.getAlbumsImage(mAlbumImageIds.get(i))));
        }
        return view;
    }

    @NonNull
    private ImageView getAnimateedImageView() {
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
    }

    @NonNull
    private View getHorizontalView(ViewGroup parent) {
        View galleryView =lInflater.inflate(R.layout.horizontal_view,parent,false);
        LinearLayout linearLayout=(LinearLayout) galleryView.findViewById(R.id.containerId);
//        Cursor cursor=mDatabse.getPostersImage();


            for(int imageInScrollViewPosition = 0; imageInScrollViewPosition< mPosterImage.size(); imageInScrollViewPosition++) {

                ImageView imageView = new ImageView(ctx);
                setScaledImage(imageView, mDatabse.getPostersImage(mPosterImage.get(imageInScrollViewPosition)), 0.8f);
                imageView.setTag(KEY_IMAGE_VIEW_TAG, imageInScrollViewPosition);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent galleryIntent = new Intent(ctx, Gallery_activity.class);
//                        galleryIntent.putExtra(KEY_GALLERY_ACTIVITY_INTENT_EXTRA_IMAGES_IDS_ARRAY, mPosterImage);
                        galleryIntent.putExtra(KEY_GALLERY_ACTIVITY_INTENT_EXTRA_IMAGE_POSITION, (int) v.getTag(KEY_IMAGE_VIEW_TAG));
                        ctx.startActivity(galleryIntent);
                        ((Activity) ctx).overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
                    }
                });

//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                linearLayout.addView(imageView);
            }
        return galleryView;

    }

    private View returnDrawedView(Bitmap tmpBitMap){
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

//        Bitmap bitmap = BitmapFactory.decodeResource(ctx
//                .getResources(), imageResourceId);
        Matrix matrix=new Matrix();
        matrix.setRotate(angle,tmpBitMap.getWidth()/2,tmpBitMap.getHeight()/2);

        Bitmap rotatedBitmap = Bitmap.createBitmap(tmpBitMap,0,0,tmpBitMap.getWidth(),tmpBitMap.getHeight(),matrix,true);

        tmpBitMap.recycle();


        Bitmap bmOverlay = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bmOverlay);

        canvas.drawBitmap(bitmap, 0, 0, null);
        bitmap.recycle();

        canvas.drawBitmap(rotatedBitmap,30*screenDensity,65*screenDensity,null);

        rotatedBitmap.recycle();

        BitmapDrawable dr = new BitmapDrawable(bmOverlay);
        dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());

        ImageView imageView=new ImageView(ctx);

        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams((int)(bmOverlay.getWidth()/1.5),(int)(bmOverlay.getHeight()/1.5));
        imageView.setLayoutParams(params);

                imageView.setImageDrawable(dr);
        return imageView;
    }
    private void setScaledImage(ImageView view,int drawableId,float scaleParameter) throws NoSuchElementException {
        // Get bitmap from the the ImageView.
        Bitmap bitmap = getBitmap(drawableId);
        scaleAndSetBitmapToView(view, scaleParameter, bitmap);


    }
    private void setScaledImage(ImageView view,Bitmap bitmap,float scaleParameter) throws NoSuchElementException {
        // Get bitmap from the the ImageView.
//        Bitmap bitmap = getBitmap(bitmapDrawable);
//        view.setImageBitmap(bitmap);
//        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        view.setLayoutParams(params);
        scaleAndSetBitmapToView(view, scaleParameter, bitmap);


    }

    private void scaleAndSetBitmapToView(ImageView view, float scaleParameter, Bitmap bitmap) {
        // Get current dimensions AND the desired bounding box
        int width;

        try {
            width = bitmap.getWidth();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Can't find bitmap on given view/drawable");
        }


        int height = bitmap.getHeight();
        int bounding =(int)(screenWidth*scaleParameter);

        float ScaleParameter = ((float) bounding) / width;

        Matrix matrix = new Matrix();
        matrix.postScale(ScaleParameter, ScaleParameter);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        width = scaledBitmap.getWidth(); // re-use
        height = scaledBitmap.getHeight(); // re-use
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);

        // Apply the scaled bitmap
        view.setImageDrawable(result);

        saveDimensions(width,height);
        // Now change ImageView's dimensions to match the scaled image
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(width,height);
        view.setLayoutParams(params);

        bitmap.recycle();
//        scaledBitmap.recycle();
    }

    @Nullable
    private Bitmap getBitmap(int drawableId) {
        Bitmap bitmap = null;

        try {
            bitmap = BitmapFactory.decodeResource(ctx.getResources(), drawableId);

        } catch (NullPointerException e) {
            throw new NoSuchElementException("No drawable on given view");
        } catch (ClassCastException e) {
            // Check bitmap is Ion drawable
//            bitmap = Ion.with(view).getBitmap();
        }
        return bitmap;
    }

    @Nullable
    private Bitmap getBitmap(BitmapDrawable bitmapDrawable) {
        Bitmap bitmap = null;

        try {
            bitmap = bitmapDrawable.getBitmap();

        } catch (NullPointerException e) {
            throw new NoSuchElementException("No drawable on given view");
        } catch (ClassCastException e) {
            // Check bitmap is Ion drawable
//            bitmap = Ion.with(view).getBitmap();
        }
        return bitmap;
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

}
