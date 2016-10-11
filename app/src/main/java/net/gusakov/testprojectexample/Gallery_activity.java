package net.gusakov.testprojectexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;

import net.gusakov.testprojectexample.adapters.GalleryAdapter;
import net.gusakov.testprojectexample.adapters.ListViewAdapter;
import net.gusakov.testprojectexample.database.DatabaseHelper;

public class Gallery_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

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
        gallery.setAdapter(new GalleryAdapter(this,new DatabaseHelper(this)));
        gallery.setSelection(selectedImagePosition);
    }
}
