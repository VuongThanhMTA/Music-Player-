package com.vuongthanh.t3h.musicplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.IntDef;

import java.util.ArrayList;

public class MP3Player implements MediaPlayer.OnCompletionListener {
    private MediaPlayer mediaPlayer;
    private ArrayList<Song> songs;
    private int currentIndex;
    private Context context;

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public MP3Player(Context context) {
        this.context = context;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void Create(int index) {
        Release();
        this.currentIndex = index;
        Uri uri = Uri.parse(songs.get(index).getData());
        mediaPlayer = MediaPlayer.create(context, uri);
        mediaPlayer.setOnCompletionListener(this);
        start();
    }


    public boolean isPause() {
        if (mediaPlayer != null) {
            return !mediaPlayer.isPlaying();
        }
        return false;
    }

    public void Release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    public void start() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    // dua đến cuối bài hát và chạy bài tiếp
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void loop(boolean isLoop) {
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(isLoop);
        }
    }

    // tua đến vị trí
    public void seek(int duration) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(duration);
        }
    }

    public int getDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    public int getCurrentPosition() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    //////////skill
    // bắt buộc truyền giá trị @IntDef({})
    //
    public static final int NEXT = 1;
    public static final int PREV = -1;

    @IntDef({NEXT, PREV})
    @interface SongPosition {
    }//giàng buộc bởi @IntDef

    public void changeSong(@SongPosition int value) {
        currentIndex += value;
        if (currentIndex >= songs.size()) {
            currentIndex = 0;
        } else if (currentIndex < 0) {
            currentIndex = songs.size() - 1;
        }
        Create(currentIndex);
    }

    public void shuffleSong(int indexSong) {
        currentIndex = indexSong;
        if (currentIndex >= songs.size()) {
            currentIndex = 0;
        } else if (currentIndex < 0) {
            currentIndex = songs.size() - 1;
        }
        Create(currentIndex);
    }

    public String getNameSong() {
        if (songs != null && songs.size() > currentIndex) {
            return songs.get(currentIndex).getName();
        }
        return "";
    }

    public String getNameArtist() {
        if (songs != null && songs.size() > currentIndex) {
            return songs.get(currentIndex).getArtist();
        }
        return "";
    }

    public String getImage() {
        if (songs != null && songs.size() > currentIndex) {
            return songs.get(currentIndex).getImage();
        }
        return "sadf";
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        changeSong(NEXT);
    }
}
