package com.hwhhhh.wordbook.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FanyiDemo {
    private static final String YOUDAO_URL = "https://openapi.youdao.com/api";
    private static final String APP_KEY = "5cb26503ae2880a9";
    private static final String APP_SECRET = "oIXMQ6amN35wtUsZL3rp6B4C1GYDX3Qo";
    private static final String TAG = "FanyiDemo";
    private Map<String, String> params;
    private String salt, curTime, signStr, sign;

    public FanyiDemo(String q) throws IOException{
        params = new HashMap<>();
        salt = String.valueOf(System.currentTimeMillis());
        params.put("from", "auto");
        params.put("to", "auto");
        params.put("signType", "v3");
        curTime = String.valueOf(System.currentTimeMillis() / 1000);
        params.put("curTime", curTime);
        signStr = APP_KEY + truncate(q) + salt + curTime + APP_SECRET;
        sign = getDigest(signStr);
        params.put("appKey", APP_KEY);
        params.put("q", q);
        params.put("salt", salt);
        params.put("sign", sign);
        requestForHttp(YOUDAO_URL, params);
    }

    /**
     * 生成加密字段
     */
    public static String getDigest(String string) {
        if (string == null) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String truncate(String q) {
        if (q == null) {
            return null;
        }
        int len = q.length();
        return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
    }

    public static void requestForHttp(final String url, final Map<String,String> params) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader bufferedReader = null;
                try {
                    URL mUrl = new URL(url);
                    connection = (HttpURLConnection) mUrl.openConnection();
                    connection.setRequestMethod("POST");
                    Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, String> en = it.next();
                        String key = en.getKey();
                        String value = en.getValue();
                        connection.setRequestProperty(key, value);
                    }
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream inputStream = connection.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
//                    StringBuilder response = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
//                        response.append(line);
                        Log.d(TAG, "run: " + line);
                    }
                } catch (Exception e) {
                    Log.d(TAG, "run: " + e.getMessage());
                }
            }
        }).start();
    }

//    /**
//     *
//     * @param result 音频字节流
//     * @param file 存储路径
//     */
//    private static void byte2File(byte[] result, String file) {
//        File audioFile = new File(file);
//        FileOutputStream fos = null;
//        try{
//            fos = new FileOutputStream(audioFile);
//            fos.write(result);
//
//        }catch (Exception e){
//            Log.d(TAG, "byte2File: " + e.getMessage());
//        }finally {
//            if(fos != null){
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


}
