package com.dream.net.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dream.net.mobilesafe.R;
import com.dream.net.setting.HomeGridContent;

/**
 * Created by sunlongxiao on 10/14/14.
 */
public class HomeGridViewAdapter extends BaseAdapter {

    private Context context;

    public HomeGridViewAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return HomeGridContent.names.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    //返回位置对应的view对象
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =  View.inflate(context, R.layout.grid_home_item,null);
        ImageView iv_home_icon = (ImageView)view.findViewById(R.id.iv_home_icon);
        TextView tv_home_name = (TextView)view.findViewById(R.id.tv_home_name);
        tv_home_name.setText(HomeGridContent.names[position]);
        iv_home_icon.setImageResource(HomeGridContent.icons[position]);
        return view;
    }
}
