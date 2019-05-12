package com.vuongthanh.t3h.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vuongthanh.t3h.musicplayer.databinding.DetailAlbumBinding;

import java.util.ArrayList;

public class DetailAlbum extends Activity implements View.OnClickListener, SongAdapter.ItemClickListener {

    private DetailAlbumBinding binding;
    private ArrayList<Song> songsAlbum;
    private MyMediaStore mediaStore;
    private SongAdapter adapter;
    private ArrayList<Album> albums;
    private int id;
    private String frag;
    private ArrayList<Song> allSong;
    private int result = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.detail_album);
        getDataIntent();
        initView();
    }

    private void initView() {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rcDetailA.setLayoutManager(manager);
        mediaStore = new MyMediaStore(this);

        if (frag.equalsIgnoreCase("album"))
            songsAlbum = mediaStore.getSongByAlbum(id);
        else
            songsAlbum = mediaStore.getSongByArtist(id);

        albums = mediaStore.getAlbumData();
        adapter = new SongAdapter(songsAlbum, albums, this);
        adapter.setItemClickListener(this);
        binding.rcDetailA.setAdapter(adapter);
        binding.imvBackA.setOnClickListener(this);
        Glide.with(this).load(R.drawable.backgroundtemp).into(binding.imvDetailA);
        //  mp3Player = new MP3Player(arrSong, getContext());
        //  adapter.setItemClickListener(this);
    }

    private void getDataIntent() {

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra("ID", 1);
            frag = intent.getStringExtra("FRAGMENT");

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_back_A:
                setResult(result);
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        //allSong = mediaStore.getAllSong();
        //  int index = allSong.indexOf(songsAlbum.get(position));
        FragmentSong.getInstance().setSongsService(songsAlbum);
        FragmentSong.getInstance().getService().getMp3Player().Create(position);

        Intent intent_DetailSong = new Intent(this, DetailSong.class);
        startActivityForResult(intent_DetailSong,MainActivity.REQUEST_INTENT_SONG);
        //FragmentSong.getInstance().
        overridePendingTransition(R.anim.translate_anim, R.anim.alpha_anim);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.REQUEST_INTENT_SONG) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this,"request song",Toast.LENGTH_LONG).show();
                result = RESULT_OK;
               // ShowPlay();
               // Log.e(this.getClass().getName(), "request song modelview");

            }
        }
    }


}
