package com.dream.net.mobilesafe;


import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import com.dream.net.ui.anim.ShowByBehavior;


public class Setup1Activity extends SliderGestureActivity{


    private Button bt_setup1_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);

        setupGesture();

        bt_setup1_next =  (Button)findViewById(R.id.bt_setup1_next);

        bt_setup1_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNext();
            }
        });
    }

    @Override
    protected void showPre() {
        ShowByBehavior.rightToLeftTranslate(Setup1Activity.this, LostAndFound.class);
    }

    @Override
    protected void showNext() {
        ShowByBehavior.leftToRightTranslate(Setup1Activity.this,Setup2Activity.class);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setup1, menu);
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
