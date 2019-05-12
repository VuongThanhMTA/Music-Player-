package com.vuongthanh.t3h.musicplayer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.vuongthanh.t3h.musicplayer.databinding.ItemAlbumBinding;
import com.vuongthanh.t3h.musicplayer.databinding.UiAlbumsBinding;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private ArrayList<Album> arrAlbum;
    private LayoutInflater inflater;
    private onItemClickListener listener;

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public AlbumAdapter(ArrayList<Album> arrAlbum, Context context) {
        this.arrAlbum = arrAlbum;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemAlbumBinding binding = ItemAlbumBinding.inflate(inflater, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.binding.setItemAlbum(arrAlbum.get(i));
        Animation marquee = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.text_scroll_anim);
        viewHolder.binding.txvTitle.startAnimation(marquee);
        String linkImg = arrAlbum.get(i).getImageAlbum();
        if (linkImg == null) {
            Glide.with(viewHolder.itemView.getContext()).load(R.drawable.ic_album24dp).into(viewHolder.binding.imgAlbum);
        } else
            Glide.with(viewHolder.itemView.getContext()).load(linkImg).into(viewHolder.binding.imgAlbum);

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
        return arrAlbum.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemAlbumBinding binding;

        public ViewHolder(ItemAlbumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface onItemClickListener {
        void onItemAlbumClick(int position);
    }

}
