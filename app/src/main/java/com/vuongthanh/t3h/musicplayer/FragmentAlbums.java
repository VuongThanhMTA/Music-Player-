package com.vuongthanh.t3h.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vuongthanh.t3h.musicplayer.databinding.UiAlbumsBinding;
import com.vuongthanh.t3h.musicplayer.databinding.UiSongBinding;

import java.util.ArrayList;

public class FragmentAlbums extends Fragment implements AlbumAdapter.onItemClickListener {
    private static FragmentAlbums instance;
    private ArrayList<Album> arrAlbum;
    private AlbumAdapter adapter;
    private MyMediaStore mediaStore;

    public static FragmentAlbums getInstance() {
        if (instance == null) {
            instance = new FragmentAlbums();
        }
        return instance;
    }

    private UiAlbumsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = UiAlbumsBinding.inflate(inflater, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        binding.rcAlbums.setLayoutManager(manager);
        mediaStore = new MyMediaStore(getContext());
        arrAlbum = mediaStore.getAlbumData();
        adapter = new AlbumAdapter(arrAlbum, getContext());
        adapter.setListener(this);
        binding.rcAlbums.setAdapter(adapter);
    }

    @Override
    public void onItemAlbumClick(int position) {
        Intent intent_DetailAlbum = new Intent(this.getContext(),DetailAlbum.class);
        intent_DetailAlbum.putExtra("FRAGMENT","album");
        intent_DetailAlbum.putExtra("ID",arrAlbum.get(position).getIdAlbum());
        startActivityForResult(intent_DetailAlbum,MainActivity.REQUEST_INTENT_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == MainActivity.REQUEST_INTENT_ALBUM) {
       //     Toast.makeText(getContext(),"request album",Toast.LENGTH_LONG).show();
            if (resultCode == getActivity().RESULT_OK) {
               // Toast.makeText(getContext(),"request album show",Toast.LENGTH_LONG).show();
              MainActivity activity = (MainActivity) getActivity();
              activity.ShowPlay();
                Log.e(this.getClass().getName(), "request album ");
            }
        }
    }
}
