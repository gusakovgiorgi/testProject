package net.gusakov.testprojectexample;

import android.content.Context;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * Created by hasana on 10/6/2016.
 */

public class ListViewAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    Integer[] objects;

    ListViewAdapter(Context context, Integer[] images) {
        ctx = context;
        objects = images;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            final Gallery gallery = (Gallery) galleryView.findViewById(R.id.gallery1);
            gallery.setAdapter(new ImageAdapter(ctx));
            return galleryView;
        }
        ImageView view=new ImageView(ctx);
        view.setImageResource(objects[position]);
        return view;
    }
}
