package com.dream.net.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.dream.net.adapter.HomeGridViewAdapter;

/**
 * Created by sunlongxiao on 10/11/14.
 {33eecb5f-1a64-4b9f-a852-a4ef7327e0f5} */
public class MainActivity extends Activity{
    private GridView gv_home_item;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gv_home_item = (GridView)findViewById(R.id.gv_home_item);
        gv_home_item.setAdapter(new HomeGridViewAdapter(this));
        gv_home_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 8:
                        Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                        startActivity(intent);
                        break;
                }


            }
        });
    }
}
