package com.vuongthanh.t3h.musicplayer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class SongAlbumAdapter  {


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_NameSong;
        private TextView tv_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_NameSong = itemView.findViewById(R.id.tv_name_song_DA);
            tv_time = itemView.findViewById(R.id.tv_time_DA);
        }

        public void binData(Song song){
            tv_NameSong.setText(song.getName());
            AppBinding.setDuration(tv_time, song.getDuration());
        }
    }
}
