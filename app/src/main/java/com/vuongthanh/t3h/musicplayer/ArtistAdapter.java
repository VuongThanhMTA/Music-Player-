package com.vuongthanh.t3h.musicplayer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.vuongthanh.t3h.musicplayer.databinding.ItemAlbumBinding;
import com.vuongthanh.t3h.musicplayer.databinding.ItemArtistBinding;

import java.util.ArrayList;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {
    private ArrayList<Artist> arrArtist;
    private LayoutInflater inflater;
    private ArrayList<Album> albums;
    private onItemClickListener listener;

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public ArtistAdapter(ArrayList<Artist> arrArtist, Context context) {
        this.arrArtist = arrArtist;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemArtistBinding binding = ItemArtistBinding.inflate(inflater, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.binding.setItemArtist(arrArtist.get(i));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onItemAlbumClick(i);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return arrArtist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemArtistBinding binding;

        public ViewHolder(ItemArtistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface onItemClickListener {
        void onItemAlbumClick(int position);
    }
}
