package com.vuongthanh.t3h.musicplayer;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.vuongthanh.t3h.musicplayer.Services.MP3Service;
import com.vuongthanh.t3h.musicplayer.ViewModel.AppViewModel;
import com.vuongthanh.t3h.musicplayer.ViewModel.AppViewModelFactory;
import com.vuongthanh.t3h.musicplayer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener, NavigationView.OnNavigationItemSelectedListener {

    public static final int REQUEST_INTENT_SONG = 1;
    public static final int REQUEST_INTENT_ALBUM = 2;
    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;// kích vào để đóng mở menu
    private PageAdapter adapter;
    private SearchView searchView;
    private AppViewModel viewModel;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private String[] LIST_PERMISSION = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (checkPermisson()) {
            setUpActionBar();
            initSliding();
            initPager();
        }
//        new MyMediaStore(this).getDataInternal();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (checkPermisson()) {
            setUpActionBar();
            initSliding();
            initPager();
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_main, menu);

        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        // searchView.setTextAlignment(getResources().getColor(R.color.colorAccent));
        EditText edtSearch = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        edtSearch.setTextColor(getResources().getColor(R.color.colorBlue));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (binding.mainLayout.myViewPager.getCurrentItem() == 0) {
                    FragmentSong.getInstance().getAdapter().getFilter().filter(s);
                }
                return false;
            }
        });
        return true;
    }

    private void initPager() {

        adapter = new PageAdapter(getSupportFragmentManager());
        binding.mainLayout.myViewPager.setAdapter(adapter);
//        binding.mainLayout.myViewPager.setCurrentItem(); // thay đôi page = code
        binding.mainLayout.myViewPager.addOnPageChangeListener(this);
        binding.mainLayout.tabLayout.setupWithViewPager(binding.mainLayout.myViewPager);
        binding.mainLayout.lnBottom.setVisibility(LinearLayout.GONE);

    }

    private void initSliding() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_songs);
        toggle = new ActionBarDrawerToggle(this,
                binding.drawerLayout,
                R.string.app_name,
                R.string.app_name);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();// đồng bộ trạng thái khi đóng mở drawerlayout với navi button

    }

    private void setUpActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // trên actionbar có nhiều item menu, khi click vào các item thì
        int id = item.getItemId();
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        int size = navigationView.getMenu().size();
        for (int j = 0; j < size; j++) {
            navigationView.getMenu().getItem(j).setChecked(false);
        }
        if (i == 0)
            navigationView.setCheckedItem(R.id.nav_songs);
        else if (i == 1) {
            navigationView.setCheckedItem(R.id.nav_Albums);
        } else if (i == 2) {
            navigationView.setCheckedItem(R.id.nav_Artist);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private boolean checkPermisson() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String p : LIST_PERMISSION) {
                if (checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(LIST_PERMISSION, 0);
                    return false;
                }
            }
        }
        return true;
    }

    private void initPlay() {

        binding.mainLayout.imPrev.setOnClickListener(this);
        binding.mainLayout.imNext.setOnClickListener(this);
        binding.mainLayout.imPlay.setOnClickListener(this);
        binding.mainLayout.lnBottom.setOnClickListener(this);

    }

    public void ShowPlay() {
        if (binding.mainLayout.lnBottom.getVisibility() != LinearLayout.VISIBLE) {
            initPlay();
            updateViewModel();
            binding.mainLayout.lnBottom.setVisibility(LinearLayout.VISIBLE);
        }
        Animation scroll = AnimationUtils.loadAnimation(this, R.anim.text_scroll_anim);
        binding.mainLayout.tvNameSong.startAnimation(scroll);
    }

    public void updateViewModel() {
        //  Log.e(this.getClass().getName(), "main update modelview");
        viewModel = ViewModelProviders.of(this,
                AppViewModelFactory.getInstace(getApplication())).get(AppViewModel.class);

        viewModel.getName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.mainLayout.tvNameSong.setText(s);
            }
        });//cập nhật
//        viewModel.getDuration().observe(this, new Observer<Long>() {
//            @Override
//            public void onChanged(@Nullable Long duration) {
//                AppBinding.setDuration(binding.mainLayout.tvTime, duration);
//                binding.mainLayout.sbPlay.setMax(duration.intValue());
//            }
//        });
//
//        viewModel.getCurrentPosition().observe(this, new Observer<Long>() {
//            @Override
//            public void onChanged(@Nullable Long current) {
//                binding.mainLayout.sbPlay.setProgress(current.intValue());
//            }
//        });
        viewModel.getIpause().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean ipause) {
                if (ipause) {
                    binding.mainLayout.imPlay.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                } else {
                    binding.mainLayout.imPlay.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);

                }
            }
        });
        viewModel.update(FragmentSong.getInstance().getService().getMp3Player());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_next:
                FragmentSong.getInstance().getService().getMp3Player().changeSong(MP3Player.NEXT);
                binding.mainLayout.imPlay.setSelected(false);
                break;
            case R.id.im_play:
                boolean ispause = FragmentSong.getInstance().getService().getMp3Player().isPause();
                if (ispause == true)
                    FragmentSong.getInstance().getService().getMp3Player().start();
                else
                    FragmentSong.getInstance().getService().getMp3Player().pause();

                binding.mainLayout.imPlay.setSelected(ispause);
                break;
            case R.id.im_prev:
                FragmentSong.getInstance().getService().getMp3Player().changeSong(MP3Player.PREV);
                binding.mainLayout.imPlay.setSelected(false);
                break;
            case R.id.ln_bottom:
                startActivityDetailSong();
                break;

        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            FragmentSong.getInstance().getService().getMp3Player().seek(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void translateAnim() {
        overridePendingTransition(R.anim.translate_anim, R.anim.translate_anim);
    }

    public void startActivityDetailSong() {
        Intent intent_DetailSong = new Intent(this, DetailSong.class);
        // intent_DetailSong.putExtra("position", position);
        startActivityForResult(intent_DetailSong, REQUEST_INTENT_SONG);
        overridePendingTransition(R.anim.translate_anim, R.anim.translate_anim);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateViewModel();
        if (requestCode == REQUEST_INTENT_SONG) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this,"request song",Toast.LENGTH_LONG).show();
             //   ShowPlay();
                Log.e(this.getClass().getName(), "request song ");
            }
        }
    }

    @Override
    protected void onDestroy() {
        //    unbindService(connection);
        super.onDestroy();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.e("MenuItem", String.valueOf(menuItem.getItemId()));
        switch (menuItem.getItemId()) {
            case R.id.nav_songs:
                binding.mainLayout.myViewPager.setCurrentItem(0);
                break;
            case R.id.nav_Albums:
                binding.mainLayout.myViewPager.setCurrentItem(1);
                break;
            case R.id.nav_Artist:
                binding.mainLayout.myViewPager.setCurrentItem(2);
                break;

        }
        // menuItem.setChecked(false);
        drawerLayout.closeDrawer(Gravity.START);
        return true;
    }
}
