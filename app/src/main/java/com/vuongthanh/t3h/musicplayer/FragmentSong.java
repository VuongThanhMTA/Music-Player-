package com.vuongthanh.t3h.musicplayer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.SeekBar;

import com.vuongthanh.t3h.musicplayer.Services.MP3Service;
import com.vuongthanh.t3h.musicplayer.databinding.UiSongBinding;

import java.util.ArrayList;

public class FragmentSong extends Fragment implements SongAdapter.ItemClickListener, View.OnClickListener {
    private static FragmentSong instance;
    private ArrayList<Song> arrSong = new ArrayList<>();
    private SongAdapter adapter;

    private MP3Service service;

    public MP3Service getService() {
        return service;
    }

    public SongAdapter getAdapter() {
        return adapter;
    }

    private UiSongBinding binding;
    private MyMediaStore mediaStore;
    private ArrayList<Album> albums;
    private int currentPosition = -1;
    private MP3Player mp3Player;


    private Animation translate;

    public static FragmentSong getInstance() {
        if (instance == null) {
            instance = new FragmentSong();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = UiSongBinding.inflate(inflater, container, false);
        Intent service1 = new Intent(getContext(), MP3Service.class);
        getActivity().startService(service1);
        getActivity().bindService(service1, connection, getActivity().BIND_AUTO_CREATE);
        initView();
        return binding.getRoot();

    }

    private void initView() {

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rcSongs.setLayoutManager(manager);
        mediaStore = new MyMediaStore(getContext());
        arrSong = mediaStore.getAllSong();
        albums = mediaStore.getAlbumData();
        adapter = new SongAdapter(arrSong, albums, getContext());
        binding.rcSongs.setAdapter(adapter);


        adapter.setItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        MainActivity activity = (MainActivity) getActivity();
        if (currentPosition != position) {
            currentPosition = position;
            setSongsService(arrSong);
            service.getMp3Player().Create(position);
            activity.ShowPlay();
        } else {
            activity.startActivityDetailSong();
//            Intent intent_Song = new Intent(this.getContext(),DetailSong.class);
//            startActivityForResult(intent_Song,MainActivity.REQUEST_INTENT_SONG);
        }
    }

    public void showViewPlay(){
        MainActivity activity = (MainActivity) getActivity();
        activity.ShowPlay();
    }

    public void setSongsService(ArrayList<Song> arrSong) {
        service.getMp3Player().setSongs(arrSong);
    }

    @Override
    public void onClick(View v) {

    }

    public MP3Player getMp3Player() {
        return mp3Player;
    }

    public ArrayList<Song> getArrSong() {
        return arrSong;
    }


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            Log.e(getClass().getName(), "onServiceConnected");
            MP3Service.MP3Binder binder = (MP3Service.MP3Binder) iBinder;
            service = binder.getService();
            initView();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(getClass().getName(), "onServiceDisconnected");
        }
    };

    @Override
    public void onDestroy() {
        getActivity().unbindService(connection);
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
