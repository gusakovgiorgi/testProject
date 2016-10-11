package net.gusakov.testprojectexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.gusakov.testprojectexample.adapters.ListViewAdapter;
import net.gusakov.testprojectexample.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper mDatabaseHelper;
    private Integer[] imageDrawables = {R.drawable.news, null, R.drawable.menu, R.drawable.main1, R.drawable.main2, R.drawable.main3
            , R.drawable.metro_animation, null};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeDatabse();


        initializeListView(imageDrawables);


    }

    private void initializeListView(Integer[] imageDrawablesId) {
        ListViewAdapter listViewAdapter = new ListViewAdapter(MainActivity.this, imageDrawablesId, mDatabaseHelper);
        ListView listView = (ListView) findViewById(R.id.lvMain);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void InitializeDatabse() {
        mDatabaseHelper = new DatabaseHelper(this, "picture_database.db", null, 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseHelper.close();
    }
}
