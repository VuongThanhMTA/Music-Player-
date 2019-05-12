package com.vuongthanh.t3h.musicplayer.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.vuongthanh.t3h.musicplayer.MP3Player;

public class AppViewModel extends AndroidViewModel {

    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<String> nameArtist = new MutableLiveData<>();
    private MutableLiveData<String> image = new MutableLiveData<>();
    private MutableLiveData<Long> duration = new MutableLiveData<>();
    private MutableLiveData<Boolean> ipause = new MutableLiveData<>();
    private MutableLiveData<Long> currentPosition = new MutableLiveData<>();
    private AsyncTask<Void, Void, Void> asyncTask;

    public AppViewModel(@NonNull Application application) {
        super(application);
    }


    public void update(final MP3Player mp3Player) {
        if (asyncTask != null) {
          //  Log.e(this.getClass().getName(), " cancel aynctask : ");
         //   asyncTask.cancel(true);
            asyncTask = null;
        }
        //if (asyncTask==null) {
            asyncTask = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                 //   Log.e(this.getClass().getName(), " new aynctask : ");
                    while (true) {
                        publishProgress();// sẽ gọi update
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                protected void onProgressUpdate(Void... values) {
                    super.onProgressUpdate(values);
                    image.postValue(mp3Player.getImage());
                    ipause.postValue(mp3Player.isPause());
                    name.postValue(mp3Player.getNameSong());
                    nameArtist.postValue(mp3Player.getNameArtist());
                    duration.postValue((long) mp3Player.getDuration());
                    currentPosition.postValue((long) mp3Player.getCurrentPosition());
                 //   Log.e(this.getClass().getName(), " update : " + mp3Player.getNameSong());
                 //   Log.e(this.getClass().getName(), " update : " + String.valueOf(mp3Player.getCurrentPosition()));

                }
            };
            asyncTask.execute();
       // }

    }

    public MutableLiveData<Boolean> getIpause() {
        return ipause;
    }

    public MutableLiveData<String> getImage() {
        return image;
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<Long> getDuration() {
        return duration;
    }

    public MutableLiveData<Long> getCurrentPosition() {
        return currentPosition;
    }

    public MutableLiveData<String> getNameArtist() {
        return nameArtist;
    }
}
