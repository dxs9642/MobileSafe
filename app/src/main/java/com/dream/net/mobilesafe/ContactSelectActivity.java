package com.dream.net.mobilesafe;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ContactSelectActivity extends Activity {


    private ListView lv_contact_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_select);

        final List<Map<String,String>> data = getContactInfo();

        lv_contact_select = (ListView)findViewById(R.id.lv_contact_select);

        lv_contact_select.setAdapter(new SimpleAdapter(this,data,R.layout.layout_cselect_item,new String[]{"name","num"},new int[]{R.id.tv_cselect_name,R.id.tv_cselect_name_num}));

        lv_contact_select.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String num = data.get(i).get("num");
                Intent back = new Intent();
                back.putExtra("num",num);
                setResult(0,back);
                finish();
            }
        });


    }


    private List<Map<String,String>> getContactInfo(){
        //得到内容解析器
        ContentResolver resolver = getContentResolver();

        List<Map<String,String>> list = new ArrayList<Map<String,String>>();

        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri uriData = Uri.parse("content://com.android.contacts/data");

        Cursor cursor = resolver.query(uri, new String[]{"contact_id"}, null, null, null);

        while (cursor.moveToNext()){
            String contact_id = cursor.getString(0);
            if (contact_id != null){
                Cursor dataCursor = resolver.query(uriData, new String[]{"mimetype", "data1"}, "contact_id=?", new String[]{contact_id}, null);

                Log.i("contact_id",contact_id);


                String name = "";
                String num = "";

                while (dataCursor.moveToNext()){

                    String firStr = dataCursor.getString(0);
                    String secStr = dataCursor.getString(1);
                    Log.i("firStr",firStr);

                    if (firStr.contains("name")){
                        name = secStr;
                    }

                    if (firStr.contains("phone")){
                        num = secStr;
                    }
                }

                dataCursor.close();

                if (!name.isEmpty()){
                    Map<String,String> map = new HashMap<String, String>();
                    map.put("name",name);
                    map.put("num",num);

                    list.add(map);
                }


            }


        }

        cursor.close();
        return list;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_select, menu);
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
