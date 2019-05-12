package com.vuongthanh.t3h.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vuongthanh.t3h.musicplayer.databinding.UiArtistBinding;
import com.vuongthanh.t3h.musicplayer.databinding.UiSongBinding;

import java.util.ArrayList;

public class FragmentArtist extends Fragment implements ArtistAdapter.onItemClickListener {
    private static FragmentArtist instance;
    private ArrayList<Artist> arrArtist;
    private ArtistAdapter adapter;
    private MyMediaStore mediaStore;
    private ArrayList<Album> albums;

    public static FragmentArtist getInstance() {
        if (instance == null) {
            instance = new FragmentArtist();
        }
        return instance;
    }

    private UiArtistBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = UiArtistBinding.inflate(inflater, container, false);
       initView();
        return binding.getRoot();
    }

    private void initView() {

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rcArtist.setLayoutManager(manager);
        mediaStore = new MyMediaStore(getContext());
        albums = mediaStore.getAlbumData();
        arrArtist = mediaStore.getArtistData();
        adapter = new ArtistAdapter(arrArtist, getContext());
        adapter.setListener(this);
        binding.rcArtist.setAdapter(adapter);
    }

    @Override
    public void onItemAlbumClick(int position) {

        Intent intent_Detail_album = new Intent(this.getContext(),DetailAlbum.class);
        intent_Detail_album.putExtra("FRAGMENT","artist");
        intent_Detail_album.putExtra("ID",arrArtist.get(position).getIdArtist());
        startActivity(intent_Detail_album);

    }
}
