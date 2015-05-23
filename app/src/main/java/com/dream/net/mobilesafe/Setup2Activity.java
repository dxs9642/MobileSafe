package com.dream.net.mobilesafe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.dream.net.ui.Setup2ItemView;
import com.dream.net.ui.anim.ShowByBehavior;

import java.sql.BatchUpdateException;


public class Setup2Activity extends SliderGestureActivity {



    private Button bt_setup2_next;
    private Button bt_setup2_previous;
    private SharedPreferences sp;
    private TelephonyManager manager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);

        manager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);

        setupGesture();

        bt_setup2_next = (Button)findViewById(R.id.bt_setup2_next);
        bt_setup2_previous = (Button)findViewById(R.id.bt_setup2_previous);


        bt_setup2_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNext();
            }
        });

        bt_setup2_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPre();
            }
        });





    }

    @Override
    protected void showPre() {
        simBind();

        ShowByBehavior.rightToLeftTranslate(Setup2Activity.this,Setup1Activity.class);

    }

    @Override
    protected void showNext() {

        simBind();

        ShowByBehavior.leftToRightTranslate(Setup2Activity.this, Setup3Activity.class);

    }

    private void simBind(){

        sp = this.getSharedPreferences("config", Context.MODE_PRIVATE);

        boolean status = sp.getBoolean("simBinding", false);

        if (status){
            String sim = manager.getSimSerialNumber();
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("sim",sim);
            editor.commit();
        }else{
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("sim","");
            editor.commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setup2, menu);
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
