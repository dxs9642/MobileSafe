package com.dream.net.ui.anim;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.dream.net.mobilesafe.R;
import com.dream.net.mobilesafe.Setup2Activity;

/**
 * Created by sunlongxiao on 5/20/15.
 */
public class ShowByBehavior {

    public static void leftToRightTranslate(Activity a, Class<?> b){
        Intent intent = new Intent(a,b);
        a.startActivity(intent);
        a.overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
        a.finish();
    }

    public static void rightToLeftTranslate(Activity a, Class<?> b){
        Intent intent = new Intent(a,b);
        a.startActivity(intent);
        a.overridePendingTransition(R.anim.pre_tran_in, R.anim.pre_tran_out);
        a.finish();
    }




}
