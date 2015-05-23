package com.dream.net.mobilesafe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.dream.net.ui.anim.ShowByBehavior;


public class Setup3Activity extends SliderGestureActivity {


    private Button bt_setup3_next;
    private Button bt_setup3_previous;
    private Button bt_setup3_cselect;
    private EditText et_setup3_cselect;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);

        sp = this.getSharedPreferences("config", Context.MODE_PRIVATE);


        setupGesture();

        bt_setup3_next = (Button)findViewById(R.id.bt_setup3_next);
        bt_setup3_previous = (Button)findViewById(R.id.bt_setup3_previous);
        bt_setup3_cselect = (Button)findViewById(R.id.bt_setup3_cselect);
        et_setup3_cselect = (EditText)findViewById(R.id.et_setup3_cselect);

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

        bt_setup3_cselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setup3Activity.this,ContactSelectActivity.class);
                Setup3Activity.this.startActivityForResult(intent, 0);
            }
        });


        String num = sp.getString("safeNumber","");
        if (TextUtils.isEmpty(num)){
            return;
        }
        et_setup3_cselect.setText(num);



    }


    @Override
    protected void showPre() {

        setupSafeNumber();
        ShowByBehavior.rightToLeftTranslate(Setup3Activity.this,Setup2Activity.class);

    }

    @Override
    protected void showNext() {
        setupSafeNumber();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode==0&&data!=null){

            String num = data.getStringExtra("num");

            et_setup3_cselect.setText(num);


        }

    }


    private  void setupSafeNumber(){

        String num = et_setup3_cselect.getText().toString();
        if (TextUtils.isEmpty(num)){
            return;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("safeNumber",num);
        editor.commit();
    }
}
