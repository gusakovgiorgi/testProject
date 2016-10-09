package net.gusakov.testprojectexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;

import net.gusakov.testprojectexample.adapters.GalleryAdapter;
import net.gusakov.testprojectexample.adapters.ListViewAdapter;

public class Gallery_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        int[] imageResources=getIntent().getIntArrayExtra(ListViewAdapter.KEY_GALLERY_ACTIVITY_INTENT_EXTRA_IMAGES_ARRAY);
        int selectedImagePosition=getIntent().getIntExtra(ListViewAdapter.KEY_GALLERY_ACTIVITY_INTENT_EXTRA_IMAGE_POSITION,0);

        ImageView backImageView=(ImageView)findViewById(R.id.backImageId);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView shareImageView=(ImageView)findViewById(R.id.shareImageId);
        shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO make share intent
            }
        });

        final Gallery gallery = (Gallery) findViewById(R.id.galleryId);
        gallery.setAdapter(new GalleryAdapter(this,imageResources));
        gallery.setSelection(selectedImagePosition);
    }
}
