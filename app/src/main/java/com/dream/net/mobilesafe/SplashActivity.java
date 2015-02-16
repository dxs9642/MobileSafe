package com.dream.net.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.net.bean.UpdateInfo;
import com.dream.net.engine.UpdateInfoParser;
import com.dream.net.setting.State;
import com.dream.net.utils.DownloadUtil;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class SplashActivity extends Activity {




    private TextView tv_splash_version;
    private UpdateInfo updateInfo;
    private String TAG = "S plashActivity---------->";
    private ProgressDialog pd;//下载的进度条


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case State.PARSE_XML_ERROR:
                    Toast.makeText(getApplicationContext(),"解析xml失败",Toast.LENGTH_SHORT).show();
                    gotoMainActivity();
                    break;
                case State.SERVER_ERROR:
                    Toast.makeText(getApplicationContext(),"服务器连接错误",Toast.LENGTH_SHORT).show();
                    gotoMainActivity();
                    break;
                case State.URL_SYNTAX_ERROR:
                    Toast.makeText(getApplicationContext(),"程序内部错误（内部url格式错误）",Toast.LENGTH_SHORT).show();
                    gotoMainActivity();
                    break;
                case State.URL_CONNECT_ERROR:
                    Toast.makeText(getApplicationContext(),"连接网络失败，可能是服务器不在线，如果是本地的问题请检查你的网络",Toast.LENGTH_SHORT).show();
                    gotoMainActivity();
                    break;
                case State.DOWNLOAD_FILE_SUCCESS:
                    Toast.makeText(getApplicationContext(),"文件下载成功，正在进入安装程序......",Toast.LENGTH_SHORT).show();
                    File file = (File)msg.obj;
                    Log.i(TAG,"文件路径："+file.getAbsolutePath());
                    installNewAPK(file);
                    SplashActivity.this.finish();
                    break;
                case State.DOWNLOAD_FILE_FAILED:
                    Toast.makeText(getApplicationContext(),"文件下载失败，请检查你的网络连接",Toast.LENGTH_SHORT).show();
                    gotoMainActivity();
                    break;
                case State.PARSE_XML_SUCCESS:
                    if(appVersion().equals(updateInfo.getVersion())){
                        //版本号相同进入主界面
                        Log.i(TAG,"版本号相同");
                        gotoMainActivity();

                    }else{
                        //版本号不同询问更新
                        Log.i(TAG,"版本号不相同");
                        showUpdateDialog();

                    }

            }

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv_splash_version = (TextView)findViewById(R.id.tv_splash_version);
        tv_splash_version.setText(this.appVersion());
        new Thread(new CheckVersionTask()).start();

        AlphaAnimation aa = new AlphaAnimation(0.1f,1.0f);//定义开始的透明度和结束的透明度
        aa.setDuration(3000);
        findViewById(R.id.rl_splash).startAnimation(aa);
    }

    private class DownloadNewVersionTask implements Runnable{
        @Override
        public void run() {
            File file = new File(Environment.getExternalStorageDirectory(),DownloadUtil.getFileName(updateInfo.getApkurl()));
            File downloadFile = DownloadUtil.download(updateInfo.getApkurl(), file.getAbsolutePath(),pd);
            Message msg = Message.obtain();
            if(downloadFile!=null){
                //下载成功
                pd.dismiss();
                msg.what = State.DOWNLOAD_FILE_SUCCESS;
                msg.obj = file;
            }else{
                //下载失败
                msg.what = State.DOWNLOAD_FILE_FAILED;
            }
            handler.sendMessage(msg);





        }
    }


    private class CheckVersionTask implements Runnable{

        @Override
        public void run() {
            //记录开始时间
            long startTime = System.currentTimeMillis();

            //android子线程默认提示信息是不能写成吐司（Toast）的，因此需要添加handler，原因件见最下方
            Message msg = Message.obtain();

            try {
                URL url = new URL(getResources().getString(R.string.update_address));
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);
                int code = conn.getResponseCode();
                if(code==200){
                    InputStream is = conn.getInputStream();
                    updateInfo = UpdateInfoParser.getUpdateInfo(is);
                    if(updateInfo==null){
                        //解析失败
                        msg.what = State.PARSE_XML_ERROR;
                    }else{
                        //成功
                        msg.what = State.PARSE_XML_SUCCESS;
                    }
                }else{
                    //连接失败
                    msg.what = State.SERVER_ERROR;

                }

            }catch(MalformedURLException e){
                msg.what = State.URL_SYNTAX_ERROR;
                e.printStackTrace();
            }catch (IOException e){
                msg.what= State.URL_CONNECT_ERROR;
                e.printStackTrace();
            }finally {
                long endTime = System.currentTimeMillis();
                long dTime = endTime - startTime;
                if(dTime<3000){
                    try {
                        Thread.sleep(3000 - dTime);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }

                handler.sendMessage(msg);
            }

        }
    }


    private String appVersion(){
        PackageManager pm = getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            return pi.versionName;
        }catch (PackageManager.NameNotFoundException e){
            //there is no way to go into here
            e.printStackTrace();
            return "";
        }
    }


    private void gotoMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void showUpdateDialog(){
        //弹出升级的对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("升级提示");
        builder.setMessage(updateInfo.getDescription());
        builder.setPositiveButton("确定升级",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pd = new ProgressDialog(SplashActivity.this);
                pd.setTitle("升级操作");
                pd.setMessage("文件正在下载");
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pd.show();
                String apkurl = updateInfo.getApkurl();
                Log.i(TAG,"开始下载");
                //这里使用sd卡进行下载，如果sd卡不可用，则不能进行下载
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    new Thread(new DownloadNewVersionTask()).start();
                }else{
                    Toast.makeText(getApplicationContext(),"未装载sd卡，无法完成下载",Toast.LENGTH_SHORT).show();
                    gotoMainActivity();
                }





            }
        });
        builder.setNegativeButton("取消升级",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gotoMainActivity();
            }
        });
        builder.show();


    }
//该方法接受file来安装apk
    private void installNewAPK(File file){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        startActivity(intent);



    }

    @Override
    public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas) {

        return super.onCreateThumbnail(outBitmap, canvas);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


/*
因为Toast在service和activity中都可以执行。所以开始就认为和ui线程没有有太大的关系，而现在子线程Toast竟然报错！无 奈之下，花了半天的时间看了一下Handler，Looper，Toast的源码，终于搞定了。（这个效率..本人愚钝啊）----->的确和UI 线程没有关系
记录下来，希望对遇上同样问题的同学有所帮助。下面正题
1、错误的关键位置在于Toast初始化的时候，这句
public class Toast {final Handler mHandler = new Handler();….}
2、其实在别的地方也看到过，普通线程不能直接new一个Handler
原因：
public Handler(){
             mLooper = Looper.myLooper();

        if (mLooper == null) {

            throw new RuntimeException(

                "Can't create handler inside thread that has not called Looper.prepare()");

        }

}
3、而Looper中
public static final Looper myLooper() {
        //这个方法是从当前线程的ThreadLocal中拿出设置的looper
        return (Looper)sThreadLocal.get();
}

而事实上子线程只是一个普通的线程，其ThreadLoacl中没有设置过Looper，所以会抛出异常
 */