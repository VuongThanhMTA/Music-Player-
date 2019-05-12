package com.vuongthanh.t3h.musicplayer.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RemoteViews;
import android.widget.Switch;
import android.widget.TextView;

import com.vuongthanh.t3h.musicplayer.MP3Player;
import com.vuongthanh.t3h.musicplayer.R;
import com.vuongthanh.t3h.musicplayer.Song;

public class MP3Service extends Service {

    private MP3Player mp3Player;
    private static final int NOTIFICATION_ID = 26;
    private static final String TAG = "ServiceMP3";
    private static final String CHANNEL = "MP3_CHANNEL";
    TextView tvNameNotify;

    private enum ACTIONS {
        ACION_PAUSE,
        ACION_NEXT,
        ACION_PREV,
        ACION_EXIT
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MP3Binder(this);
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTIONS.ACION_PREV.name());
        filter.addAction(ACTIONS.ACION_EXIT.name());
        filter.addAction(ACTIONS.ACION_PAUSE.name());
        filter.addAction(ACTIONS.ACION_NEXT.name());
        registerReceiver(receiver, filter);

        mp3Player = new MP3Player(this) {
            @Override
            public void Create(int index) {
                super.Create(index);
                putNotification(getSongs().get(index), R.drawable.ic_pause_circle24dp);
            }

            @Override
            public void start() {
                super.start();
                putNotification(getSongs().get(getCurrentIndex()), R.drawable.ic_pause_circle24dp);
            }

            @Override
            public void pause() {
                super.pause();
                putNotification(getSongs().get(getCurrentIndex()), R.drawable.ic_play_circle24dp);
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        mp3Player.Release();
    }

    public class MP3Binder extends Binder {
        private MP3Service service;

        public MP3Binder(MP3Service service) {
            this.service = service;
        }

        public MP3Service getService() {
            return service;
        }
    }

    private void putNotification(Song song, @DrawableRes int idRes) {

        RemoteViews views = new RemoteViews(getPackageName(), R.layout.notify_mp3);
        views.setTextViewText(R.id.tv_name_notify, song.getName());
        views.setOnClickPendingIntent(R.id.im_prev_notify, getPendingAction(ACTIONS.ACION_PREV));
        views.setOnClickPendingIntent(R.id.im_next_notify, getPendingAction(ACTIONS.ACION_NEXT));
        views.setOnClickPendingIntent(R.id.im_play_notify, getPendingAction(ACTIONS.ACION_PAUSE));
        views.setOnClickPendingIntent(R.id.im_exit_notify, getPendingAction(ACTIONS.ACION_EXIT));
        views.setImageViewResource(R.id.im_play_notify, idRes);
        //Animation marquee = AnimationUtils.loadAnimation(this, R.anim.text_scroll_anim);
        //startAnimation(marquee);
        //views.set

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL, CHANNEL,
                    NotificationManager.IMPORTANCE_MIN);
            notificationChannel.setSound(null, null);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL);
//        builder.setTicker(song.getName());
//        builder.setContentTitle(song.getName());
//        builder.setContentText(song.getArtist());
      //  builder.setSound(null);
       // builder.setSound(null);

        builder.setSmallIcon(R.drawable.ic_music_note_black_24dp);
        builder.setCustomContentView(views);

        startForeground(NOTIFICATION_ID, builder.build());
        builder.setSound(null);
    }

    private PendingIntent getPendingAction(ACTIONS action) {
        Intent intent = new Intent(action.name());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equalsIgnoreCase(ACTIONS.ACION_NEXT.name())) {
                mp3Player.changeSong(MP3Player.NEXT);
            } else if (action.equalsIgnoreCase(ACTIONS.ACION_PREV.name())) {
                mp3Player.changeSong(MP3Player.PREV);
            } else if (action.equalsIgnoreCase(ACTIONS.ACION_PAUSE.name())) {
                if (mp3Player.isPause()) {
                    mp3Player.start();
                } else {
                    mp3Player.pause();
                }
            } else if (action.equalsIgnoreCase(ACTIONS.ACION_EXIT.name())) {

                stopForeground(true);
            }
        }
    };

    public MP3Player getMp3Player() {
        return mp3Player;
    }

}
