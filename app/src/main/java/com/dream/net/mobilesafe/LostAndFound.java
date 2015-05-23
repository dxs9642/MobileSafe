package com.dream.net.mobilesafe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.dream.net.receiver.SMSContentObserver;


public class LostAndFound extends Activity {




    private Button bt_lost_and_found_reGuide;
    private TextView tv_laf_safe_num;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found);

        bt_lost_and_found_reGuide = (Button)findViewById(R.id.bt_lost_and_found_reGuide);
        tv_laf_safe_num = (TextView)findViewById(R.id.tv_laf_safe_num);

        sp = this.getSharedPreferences("config", Context.MODE_PRIVATE);
        String num = sp.getString("safeNumber", "");
        if (!TextUtils.isEmpty(num)){
            tv_laf_safe_num.setText(num);
        }

        bt_lost_and_found_reGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LostAndFound.this,Setup1Activity.class);
                startActivity(intent);
                LostAndFound.this.finish();

            }
        });



        SMSContentObserver content = new SMSContentObserver(this,new Handler());
        this.getContentResolver().registerContentObserver(
                Uri.parse("content://sms/"), true, content);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lost_and_found, menu);
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
