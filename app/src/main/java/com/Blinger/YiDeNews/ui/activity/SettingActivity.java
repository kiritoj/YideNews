package com.Blinger.YiDeNews.ui.activity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.Blinger.base.BooleanEvent;
import com.Blinger.base.base.BaseActivity;
import com.Blinger.base.base.BasePresenter;
import com.Blinger.YiDeNews.R;
import com.Blinger.base.utils.NightModeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.tv_title_setting)
    TextView mTvTitle;
    @Bind(R.id.night_sw)
    Switch mSwitch;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_setting;
    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    @OnClick(R.id.img_finish_setting)
    public void onViewClicked() {
        doFinish();
    }
    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mTvTitle.setText("设置");

        SharedPreferences sharedPreferences = getSharedPreferences("init", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        mSwitch.setChecked(sharedPreferences.getBoolean("nightMode",false));
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EventBus.getDefault().post(new BooleanEvent(isChecked,"nightMode"));
                editor.putBoolean("nightMode",isChecked);
                editor.apply();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
