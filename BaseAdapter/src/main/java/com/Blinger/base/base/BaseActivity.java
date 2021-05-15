package com.Blinger.base.base;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.Blinger.base.BooleanEvent;
import com.Blinger.base.utils.NightModeUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.Blinger.base.BaseApplication;
import com.Blinger.base.R;
import com.Blinger.base.utils.AppManager;
import com.Blinger.base.utils.DialogLoading;
import com.Blinger.base.utils.DialogUtils;
import com.Blinger.base.utils.StatusBarUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * 作者：310 Group
 * 时间：2020/4/1
 * 邮箱：1760567382@qq.com
 * 功能：
 */

public abstract class BaseActivity<T extends BasePresenter> extends RxAppCompatActivity
{

    protected T mPresenter;
    protected Bundle savedInstanceState;
    protected Activity mActivity;

    /**
     * 初始化Presenter
     * @return
     */
    protected abstract T createPresenter();

    /**
     * 获取xml文件资源ID
     * @return
     */
    protected abstract int getResourceId();

    @Override
    public  void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        EventBus.getDefault().register(this);
        //初始化的时候将其添加到集合中
        AppManager.getActivityManager().addActivity(this);
        //设置Activity不允许横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mPresenter = createPresenter();
        setContentView(getResourceId());
        StatusBarUtils.setColorNoTranslucent(this, Color.WHITE);
        //隐藏自带的actionbar，因为主题要继承夜间模式主题，这里通过代码来隐藏actiobbar
//        getActionBar().hide();
        ButterKnife.bind(this);
        initView(savedInstanceState);
        initData();

        //夜间模式
        SharedPreferences sharedPreferences = getSharedPreferences("init", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.getBoolean("nightMode",false)){
            NightModeUtil.reduceTransparency(0.5f,this);
        }else{
            NightModeUtil.resetTransparency(this);
        }

    }


    public void initView(Bundle savedInstanceState)
    {
    }

    public void initData()
    {
        if (BaseApplication.isIsNetConnect())
        {
            netWork();
        }
    }


    protected void netWork()
    {

    }


    /**
     * Toast消息
     * @param msg 消息内容
     */
    public void alert(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    public void doFinish()
    {
        this.finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        DialogLoading.dissDialog();
        DialogUtils.dissDialog();
        EventBus.getDefault().unregister(this);
        AppManager.getActivityManager().finishActivity(this);
        if (mPresenter != null)
        {
            mPresenter.cancleAll();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveNightMode(BooleanEvent event){
        if (event.getTag().equals("nightMode")){
            if (event.getaBoolean()){
                NightModeUtil.reduceTransparency(0.5f,this);
            }else{
                NightModeUtil.resetTransparency(this);
            }
        }

    }


}
