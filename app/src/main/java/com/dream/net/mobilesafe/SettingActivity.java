package com.dream.net.mobilesafe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import com.dream.net.ui.SettingItemView;

/**
 * Created by sunlongxiao on 2/17/15.
 */
public class SettingActivity extends Activity {


    private SettingItemView siv_setting_itemUp;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        siv_setting_itemUp = (SettingItemView)findViewById(R.id.siv_setting_itemUp);


    }
}
