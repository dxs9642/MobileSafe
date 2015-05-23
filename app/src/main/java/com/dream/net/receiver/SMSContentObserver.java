package com.dream.net.receiver;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sunlongxiao on 5/23/15.
 */
public class SMSContentObserver extends ContentObserver {
    private Context mContext;
    String[] projection = new String[] { "address", "body", "date", "type", "read" };

    public SMSContentObserver(Context context, Handler handler) {
        super(handler);
        mContext = context;
    }

    @Override
    public void onChange(boolean selfChange) {
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor c = mContext.getContentResolver().query(uri, null, null, null, "date desc");
        if (c != null) {
            while (c.moveToNext()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date d = new Date(c.getLong(c.getColumnIndex("date")));
                String date = dateFormat.format(d);
                StringBuilder sb = new StringBuilder();
                sb.append("发件人手机号码: " + c.getString(c.getColumnIndex("address")))
                        .append("信息内容: " + c.getString(c.getColumnIndex("body")))
                        .append(" 是否查看: " + c.getInt(c.getColumnIndex("read")))
                        .append(" 类型： " + c.getInt(c.getColumnIndex("type"))).append(date);
                Log.i("xxx", sb.toString());
            }
            c.close();
        }
    }

}
