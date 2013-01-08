
package com.npace.snake.framework.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.npace.snake.framework.FileIO;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.preference.PreferenceManager;

public class AndroidFileIO implements FileIO {
    Context mContext;
    AssetManager assMan;
    String externalStoragePath;

    public AndroidFileIO(Context context) {
        mContext = context;
        assMan = mContext.getAssets();
        externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    @Override
    public InputStream readAsset(String fileName) throws IOException {
        return assMan.open(fileName);
    }

    @Override
    public InputStream readFile(String fileName) throws IOException {
        return new FileInputStream(externalStoragePath + fileName);
    }

    @Override
    public OutputStream writeFile(String fileName) throws IOException {
        return new FileOutputStream(externalStoragePath + fileName);
    }

    public SharedPreferences getPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }
}
