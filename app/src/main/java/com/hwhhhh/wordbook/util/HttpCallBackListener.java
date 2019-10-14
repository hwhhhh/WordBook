package com.hwhhhh.wordbook.util;

import java.io.InputStream;

public interface HttpCallBackListener {
    //访问完成
    void onFinish(InputStream inputStream);
    //访问出错
    void onError(Exception e);
}
