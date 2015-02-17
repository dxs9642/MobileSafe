package com.dream.net.utils;

import android.app.ProgressDialog;

import org.apache.http.HttpConnection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sunlongxiao on 10/12/14.
 */
public class DownloadUtil {

    public static String getFileName(String path){
        return path.substring(path.lastIndexOf("/") + 1);
    }

//    public static File download(String serverPath,String savePath){
//        try {
//            URL url = new URL(serverPath);
//            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//            conn.setConnectTimeout(5000);
//            conn.setRequestMethod("GET");
//            int code  = conn.getResponseCode();
//            if(code==200){
//                InputStream is = conn.getInputStream();
//                File file = new File(savePath);
//                FileOutputStream fos = new FileOutputStream(file);
//                byte[] buffer  = new  byte[1024];
//
//                int len = 0;
//                while ((len=is.read(buffer))!=-1){
//                    fos.write(buffer,0,len);
//                }
//                fos.flush();
//                fos.close();
//                is.close();
//                return file;
//            }else{
//                return null;
//            }
//
//        }catch(Exception e){
//            e.printStackTrace();
//            return null;
//        }
//
//
//    }

    public static void download(String serverPath,String savePath,ProgressDialog pd){
        //为满足带有进度条的对话框而写的download方法，上面那个暂时弃用
        try {
            URL url = new URL(serverPath);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            int code  = conn.getResponseCode();
            if(code==200){
                pd.setMax(conn.getContentLength());
                InputStream is = conn.getInputStream();
                File file = new File(savePath);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer  = new  byte[1024];

                int len = 0;
                int total = 0;
                while ((len=is.read(buffer))!=-1){
                    fos.write(buffer,0,len);
                    total += len;
                    pd.setProgress(total);
                }
                fos.flush();
                fos.close();
                is.close();
            }

        }catch(Exception e){
            e.printStackTrace();
        }


    }



}
