package com.dream.net.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.dream.net.ui.anim.ShowByBehavior;


public class Setup3Activity extends SliderGestureActivity {


    private Button bt_setup3_next;
    private Button bt_setup3_previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);

        setupGesture();

        bt_setup3_next = (Button)findViewById(R.id.bt_setup3_next);
        bt_setup3_previous = (Button)findViewById(R.id.bt_setup3_previous);

        bt_setup3_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNext();
            }
        });

        bt_setup3_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPre();
            }
        });


    }


    @Override
    protected void showPre() {
        ShowByBehavior.rightToLeftTranslate(Setup3Activity.this,Setup2Activity.class);

    }

    @Override
    protected void showNext() {
        ShowByBehavior.leftToRightTranslate(Setup3Activity.this, Setup4Activity.class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setup3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
