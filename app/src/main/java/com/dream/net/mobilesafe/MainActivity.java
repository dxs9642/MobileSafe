package com.dream.net.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.dream.net.adapter.HomeGridViewAdapter;
import com.dream.net.utils.MD5Utils;

/**
 * Created by sunlongxiao on 10/11/14.
 {33eecb5f-1a64-4b9f-a852-a4ef7327e0f5} */
public class MainActivity extends Activity{
    private SharedPreferences sp;

    private GridView gv_home_item;

    private EditText et_dialog_setup_pwd;
    private EditText et_dialog_resetup_pwd;
    private Button bt_dialog_setup_OK_pwd;
    private Button bt_dialog_setup_cancel_pwd;


    private EditText et_dialog_login_pwd;
    private Button bt_dialog_login_OK_pwd;
    private Button bt_dialog_login_cancel_pwd;

    private AlertDialog dialog;


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
        dialog = builder.show();

        et_dialog_setup_pwd = (EditText)view.findViewById(R.id.et_dialog_setup_pwd);
        et_dialog_resetup_pwd = (EditText)view.findViewById(R.id.et_dialog_resetup_pwd);

        bt_dialog_setup_OK_pwd = (Button)view.findViewById(R.id.bt_dialog_setup_OK_pwd);
        bt_dialog_setup_cancel_pwd = (Button)view.findViewById(R.id.bt_dialog_setup_cancel_pwd);

        bt_dialog_setup_OK_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = et_dialog_setup_pwd.getText().toString().trim();//trim去掉空格
                String repwd = et_dialog_resetup_pwd.getText().toString().trim();
                if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(repwd) && pwd.equals(repwd)) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("PhoneProtectPassword", MD5Utils.encrypt(pwd));
                    editor.commit();
                    dialog.dismiss();

                    Intent intent = new Intent(MainActivity.this,Setup1Activity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(MainActivity.this,"密码为格式错误或者密码不匹配,请从新输入",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        bt_dialog_setup_cancel_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });






    }


    public void showInputPwdDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = View.inflate(MainActivity.this, R.layout.linear_dialog_login_pwd, null);
        //如果出现边框问题的话使用builder.setView(view,0,0,0,0)去掉边框
        builder.setView(view);
        dialog = builder.show();

        et_dialog_login_pwd = (EditText)view.findViewById(R.id.et_dialog_login_pwd);
        bt_dialog_login_OK_pwd = (Button)view.findViewById(R.id.bt_dialog_login_OK_pwd);
        bt_dialog_login_cancel_pwd = (Button)view.findViewById(R.id.bt_dialog_login_cancel_pwd);

        bt_dialog_login_OK_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = sp.getString("PhoneProtectPassword","");
                String inputPwd = et_dialog_login_pwd.getText().toString().trim();
                if (!TextUtils.isEmpty(pwd)){

                    if (MD5Utils.encrypt(inputPwd).equals(pwd)){
                        dialog.dismiss();
                        Log.i("MainActivity","密码输入正确");

                        Intent intent = new Intent(MainActivity.this,LostAndFound.class);
                        startActivity(intent);


                    }else{
                        Toast.makeText(MainActivity.this,"密码输入错误，请重新输入",Toast.LENGTH_SHORT).show();
                        et_dialog_login_pwd.setText("");
                    }

                }else{
                    Toast.makeText(MainActivity.this,"程序工作出现异常，请于作者联系一下",Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }


            }
        });

        bt_dialog_login_cancel_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
