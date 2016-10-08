package net.gusakov.testprojectexample;

import android.app.ActivityManager;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import net.gusakov.testprojectexample.adapters.ListViewAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
//
//        // Get a support ActionBar corresponding to this toolbar
//        ActionBar ab = getSupportActionBar();
//        ab.setDisplayShowTitleEnabled(false);
//
//        View bitMoreView=myToolbar.findViewById(R.id.bit_more_id);
//        bitMoreView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"bit more button clicked",Toast.LENGTH_SHORT).show();
//            }
//        });

        Integer[] mImage = {R.drawable.news,null, R.drawable.menu,R.drawable.main1,R.drawable.main2,R.drawable.main3
                ,R.drawable.main4};

        ListViewAdapter listViewAdapter = new ListViewAdapter(this, mImage);

        // настраиваем список
        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(listViewAdapter);

    }

}
