package com.npace.snake.framework.impl;

import java.io.IOException;

import com.npace.snake.framework.Music;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class AndroidMusic implements Music, OnCompletionListener {
    MediaPlayer mediaPlayer;
    boolean isPrepared = false;

    /**
     * @param assetDescriptor
     */
    public AndroidMusic(AssetFileDescriptor assetDescriptor) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(),
                    assetDescriptor.getStartOffset(), assetDescriptor.getLength());
            mediaPlayer.prepare();
            isPrepared = true;
            mediaPlayer.setOnCompletionListener(this);
        } catch (Exception e){
            throw new RuntimeException("Couldn't load music");
        }
    }

    @Override
    public void play() {
        if (mediaPlayer.isPlaying()) return;
        try {
            synchronized(this){
                if (!isPrepared) mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        mediaPlayer.stop();
        synchronized (this) {
            isPrepared = false;
        }
    }

    @Override
    public void pause() {
        if (mediaPlayer.isPlaying()) mediaPlayer.pause();
    }

    @Override
    public void setLooping(boolean looping) {
        mediaPlayer.setLooping(looping);
    }

    @Override
    public void setVolume(float volume) {
        mediaPlayer.setVolume(volume, volume);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public boolean isStopped() {
        return !isPrepared;
    }

    @Override
    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    @Override
    public void dispose() {
        if (mediaPlayer.isPlaying()) mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        synchronized (this) {
            isPrepared = false;
        }
    }

}
