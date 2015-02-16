package com.dream.net.engine;

import android.util.Xml;

import com.dream.net.bean.UpdateInfo;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

/**
 * Created by sunlongxiao on 10/11/14.
 */
public class UpdateInfoParser {

    public static UpdateInfo getUpdateInfo(InputStream is){
        UpdateInfo updateInfo = null;

        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(is, "utf-8");//一般来讲如果出异常在engine中要在方法上抛出异常，如果有返回值，通过返回值的结果来保证不同的异常
            int type = parser.getEventType();
            while (type != XmlPullParser.END_DOCUMENT){
            switch (type){
                case XmlPullParser.START_TAG:
                    if("info".equals(parser.getName())){
                        updateInfo = new UpdateInfo();
                    }
                    if("version".equals(parser.getName())){
                        updateInfo.setVersion(parser.nextText());
                    }
                    if("description".equals(parser.getName())){
                        updateInfo.setDescription(parser.nextText());
                    }
                    if("apkurl".equals(parser.getName())){
                        updateInfo.setApkurl(parser.nextText());
                    }
                    break;

            }

                type = parser.next();
            }
            return updateInfo;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
