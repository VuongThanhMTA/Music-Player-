package com.vuongthanh.t3h.musicplayer.ViewModel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class AppViewModelFactory implements ViewModelProvider.Factory {

    //có tạo ra nhiều hay 1 view model không
    private AppViewModel appViewModel;
    private static AppViewModelFactory instance;

    public static AppViewModelFactory getInstace(Application application) {
        if (instance == null) {
            instance = new AppViewModelFactory();
            instance.appViewModel = new AppViewModel(application);

        }
        return instance;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) appViewModel;
    }

}
