
package com.npace.snake.framework.impl;

import java.io.IOException;

import com.npace.snake.framework.Audio;
import com.npace.snake.framework.Music;
import com.npace.snake.framework.Sound;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

public class AndroidAudio implements Audio {
    AssetManager assMan;
    SoundPool soundPool;

    public AndroidAudio(Activity activity) {
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        assMan = activity.getAssets();
        soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
    }

    @Override
    public Music newMusic(String fileName) {
        try {
            AssetFileDescriptor assetDescriptor = assMan.openFd(fileName);
            return new AndroidMusic(assetDescriptor);
        } catch (IOException e){
            throw new RuntimeException("Couldn't load music '" + fileName + "'");
        }
    }

    @Override
    public Sound newSound(String fileName) {
        try {
            AssetFileDescriptor assetDescriptor = assMan.openFd(fileName);
            int soundID = soundPool.load(assetDescriptor, 0);
            return new AndroidSound(soundPool, soundID);
        } catch (IOException e){
            throw new RuntimeException("Couldn't load sound '" + fileName + "'");
        }
    }

}
