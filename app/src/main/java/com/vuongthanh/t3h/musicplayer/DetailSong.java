package com.vuongthanh.t3h.musicplayer;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.animation.AnimationUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vuongthanh.t3h.musicplayer.ViewModel.AppViewModel;
import com.vuongthanh.t3h.musicplayer.ViewModel.AppViewModelFactory;
import com.vuongthanh.t3h.musicplayer.databinding.DetailSongBinding;

import java.util.ArrayList;

public class DetailSong extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private DetailSongBinding binding;
    private AppViewModel viewModel;
    private Animation translate;
    private MP3Player mp3Player;
    private int postionSong;
    private ArrayList<Song> songs;
    private String FROM_INTENT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.detail_song);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        listener();
        updateViewModel();

    }

    private void updateViewModel() {

        Log.e(this.getClass().getName(), "detail song update modelview");

        viewModel = ViewModelProviders.of(this,
                AppViewModelFactory.getInstace(getApplication())).get(AppViewModel.class);

        viewModel.getImage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Glide.with(DetailSong.this).load(s).into(binding.imvBackgroundSD);
            }
        });

        viewModel.getName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.tvNameSongSD.setText(s);
            }
        });

        viewModel.getNameArtist().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String nameArtist) {
                binding.tvNameArtistSD.setText(nameArtist);
            }
        });
        viewModel.getDuration().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long duration) {
                AppBinding.setDuration(binding.tvTimeDurationSD, duration);
                binding.sbPlaySD.setMax(duration.intValue());
            }
        });

        viewModel.getCurrentPosition().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long current) {
                AppBinding.setDuration(binding.tvTimeCurrentSD, current);
                binding.sbPlaySD.setProgress(current.intValue());
            }
        });
        viewModel.update(FragmentSong.getInstance().getService().getMp3Player());
        //viewModel.update(mp3Player);
    }


    private void getDataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            postionSong = intent.getIntExtra("position", 1);
            FROM_INTENT = intent.getStringExtra("TO_FRAGMENT");
        }
    }

    private void listener() {

        binding.imvBackSD.setOnClickListener(this);
        binding.imvLikeSD.setOnClickListener(this);
        binding.imvShareSD.setOnClickListener(this);
        binding.imShuffleSD.setOnClickListener(this);
        binding.imPrevSD.setOnClickListener(this);
        binding.imPlaySD.setOnClickListener(this);
        binding.imNextSD.setOnClickListener(this);
        binding.imReplaySD.setOnClickListener(this);
        binding.sbPlaySD.setOnSeekBarChangeListener(this);

    }

    private boolean clikPause = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_back_SD:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.imv_like_SD:
                break;
            case R.id.imv_share_SD:
                break;
            case R.id.im_next_SD:
                FragmentSong.getInstance().getService().getMp3Player().changeSong(MP3Player.NEXT);
                binding.imPlaySD.setSelected(false);
                break;
            case R.id.im_prev_SD:
                FragmentSong.getInstance().getService().getMp3Player().changeSong(MP3Player.PREV);
                binding.imPlaySD.setSelected(false);
                break;
            case R.id.im_play_SD:
                if (clikPause) {
                    clikPause = false;
                    FragmentSong.getInstance().getService().getMp3Player().start();
                } else {
                    clikPause = true;
                    FragmentSong.getInstance().getService().getMp3Player().pause();
                }
                binding.imPlaySD.setSelected(clikPause);
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {

            FragmentSong.getInstance().getService().getMp3Player().seek(progress);
            Log.e(this.getClass().getName(), "progress seek : " + progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
