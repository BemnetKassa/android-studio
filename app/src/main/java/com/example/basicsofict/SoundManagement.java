package com.example.basicsofict.utils;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {
    private static SoundManager instance;
    private MediaPlayer mediaPlayer;
    private boolean soundEnabled = true;

    private SoundManager() {}

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void playAchievementSound(Context context) {
        if (!soundEnabled) return;

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        // You'll need to add achievement_sound.mp3 to res/raw folder
        mediaPlayer = MediaPlayer.create(context, R.raw.achievement_sound);
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}