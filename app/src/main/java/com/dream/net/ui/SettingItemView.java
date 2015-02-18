package com.dream.net.ui;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.dream.net.mobilesafe.R;

/**
 * Created by sunlongxiao on 2/18/15.
 */
public class SettingItemView extends RelativeLayout {

    private CheckBox cb_setting_up;
    private TextView tv_setting_up_descript;
    private SharedPreferences sp;


    public SettingItemView(Context context) {
        super(context);
        initView(context);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context){
        View.inflate(context, R.layout.relative_setting_item,this);
        cb_setting_up = (CheckBox) this.findViewById(R.id.cb_setting_up);
        tv_setting_up_descript = (TextView)this.findViewById(R.id.tv_setting_up_descript);

        sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);

        boolean status = sp.getBoolean("update", true);

        if(status){
            cb_setting_up.setChecked(true);
            tv_setting_up_descript.setText("自动更新已开启");

        }else{
            cb_setting_up.setChecked(false);
            tv_setting_up_descript.setText("自动更新已关闭");
        }

        final SharedPreferences.Editor editor = sp.edit();

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_setting_up.isChecked()){
                    cb_setting_up.setChecked(false);
                    tv_setting_up_descript.setText("自动更新已关闭");
                    editor.putBoolean("update",false);
                }else{
                    cb_setting_up.setChecked(true);
                    tv_setting_up_descript.setText("自动更新已开启");
                    editor.putBoolean("update",true);
                }
                editor.commit();

            }
        });
    }

    public boolean getStatus(){


        return cb_setting_up.isChecked();


    }

}
