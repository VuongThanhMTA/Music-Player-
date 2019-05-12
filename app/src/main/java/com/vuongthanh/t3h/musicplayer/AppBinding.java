package com.vuongthanh.t3h.musicplayer;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;

public class AppBinding {
    @BindingAdapter("app:size")
    public static void setSize(TextView tv, long size) {
        if (size <= 0) {
            tv.setText("0");
        }
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        tv.setText(new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups]);
    }

    @BindingAdapter("app:duration")
    public static void setDuration(TextView tv, long duration) {
        int m = (int) ((duration / 1000) / 60);
        int s = (int) ((duration / 1000) % 60);
        String d = m + ":" + s;
        tv.setText(d);
    }
}
