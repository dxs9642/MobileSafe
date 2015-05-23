package com.dream.net.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by sunlongxiao on 5/20/15.
 */
public class PhoneStartedReceiver extends BroadcastReceiver{

    private SharedPreferences sp;
    private TelephonyManager manager;


    @Override
    public void onReceive(Context context, Intent intent) {


        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean status = sp.getBoolean("simBinding", false);
        manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);

        if (status){
            String oldSim = sp.getString("sim","");
            if (oldSim.isEmpty()){
                return;
            }

            String newSim = manager.getSimSerialNumber();
            if (!newSim.equals(oldSim)){
                Toast.makeText(context,"sim卡不匹配",Toast.LENGTH_SHORT).show();
                Log.d("show------------------>","显示");

            }


        }



    }


}
