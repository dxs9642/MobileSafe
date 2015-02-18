package com.dream.net.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.dream.net.adapter.HomeGridViewAdapter;

/**
 * Created by sunlongxiao on 10/11/14.
 {33eecb5f-1a64-4b9f-a852-a4ef7327e0f5} */
public class MainActivity extends Activity{
    private GridView gv_home_item;
    private SharedPreferences sp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("config",MODE_PRIVATE);

        gv_home_item = (GridView)findViewById(R.id.gv_home_item);
        gv_home_item.setAdapter(new HomeGridViewAdapter(this));
        gv_home_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        showPhoneProtectDialog();
                        break;

                    case 8:
                        Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                        startActivity(intent);
                        break;
                }


            }
        });
    }


    public void showPhoneProtectDialog(){

        String hasSetted = sp.getString("PhoneProtectPassword","");
        if (TextUtils.isEmpty(hasSetted)){
            showCreatePwdDialog();
        }else{
            showInputPwdDialog();
        }

    }

    public void showCreatePwdDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = View.inflate(MainActivity.this, R.layout.linear_dialog_setup_pwd, null);
        builder.setView(view);
        builder.show();
    }


    public void showInputPwdDialog(){

    }

}
