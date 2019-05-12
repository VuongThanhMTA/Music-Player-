package com.vuongthanh.t3h.musicplayer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;

import com.bumptech.glide.Glide;
import com.vuongthanh.t3h.musicplayer.databinding.ItemSongBinding;

import java.util.ArrayList;
import java.util.Collection;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> implements Filterable {
    // private ItemSongBinding binding;
    private ArrayList<Song> arrSong;
    private ArrayList<Song> arrSongDisplay;
    private LayoutInflater inflater;
    private ArrayList<Album> albums;
    private ItemClickListener itemClickListener;
    private MFilter mFilter = new MFilter();

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public SongAdapter(ArrayList<Song> arrSong, ArrayList<Album> albums, Context context) {
        this.arrSong = arrSong;
        this.albums = albums;
        this.inflater = LayoutInflater.from(context);
        this.arrSongDisplay = arrSong;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemSongBinding binding = ItemSongBinding.inflate(inflater, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.binding.setItemSong(arrSong.get(i));
      //  viewHolder.binding.txvTitle.setSelected(true);
        Animation rotate = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(),R.anim.rotate_anim);
        viewHolder.binding.imgSong.startAnimation(rotate);
        String linkImg = albums.get(arrSong.get(i).getIdAlbum() - 1).getImageAlbum();
        arrSong.get(i).setImage(albums.get(arrSong.get(i).getIdAlbum() - 1).getImageAlbum());
        if (arrSong.get(i).getImage()==null) {
            //Glide.with(viewHolder.itemView.getContext()).load(R.drawable.ic_queue_music).into(viewHolder.binding.imgSong);
        } else
            Glide.with(viewHolder.itemView.getContext()).load(arrSong.get(i).getImage()).into(viewHolder.binding.imgSong);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(arrSong.indexOf(arrSongDisplay.get(i)));
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrSong.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemSongBinding binding;

        public ViewHolder(ItemSongBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public interface ItemClickListener {
        void onItemClick(int position);

    }


    private class MFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            //arrSongDisplay.clear();
            ArrayList<Song> arrayList = new ArrayList<>();
            for (Song s : arrSong) {
                if (s.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                    arrayList.add(s);
                }
            }

            FilterResults results = new FilterResults();
            results.count = arrayList.size();
            results.values = arrayList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrSongDisplay.clear();
            arrSongDisplay.addAll((Collection<? extends Song>) results.values);
            notifyDataSetChanged();
        }
    }
}
