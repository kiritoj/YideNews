package com.Blinger.YiDeNews.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Blinger.YiDeNews.App;
import com.Blinger.YiDeNews.R;
import com.Blinger.YiDeNews.config.Constant;
import com.Blinger.YiDeNews.model.NewTypeBean;
import com.Blinger.YiDeNews.model.UserTailBean;
import com.Blinger.YiDeNews.presenter.MainPresenter;
import com.Blinger.YiDeNews.ui.MyView.BannerView;
import com.Blinger.YiDeNews.ui.MyView.FragmentAdapter;
import com.Blinger.YiDeNews.ui.fragment.AboutFragment;
import com.Blinger.YiDeNews.ui.fragment.NewFragment;
import com.Blinger.YiDeNews.utils.SharePreferencesUtil;
import com.Blinger.YiDeNews.utils.ToastUtil;
import com.Blinger.base.BooleanEvent;
import com.Blinger.base.base.BaseActivity;
import com.Blinger.base.base.BasePresenter;
import com.Blinger.base.base.BaseView;
import com.Blinger.base.utils.LogUtils;
import com.Blinger.base.utils.NightModeUtil;
import com.Blinger.base.utils.SpUtils;
import com.Blinger.base.utils.StatusBarUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * 作者：310Lab
 * 时间：2020/4/1 21:16
 * 邮箱：1760567382@qq.com
 * 功能：
 */

/**
 * modified by 逃课 on 2020/11/17
 */
public class MainActivity extends BaseActivity<MainPresenter> implements BaseView {
    @Bind(R.id.toolBar)
    Toolbar mToolBar;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.navigationView)
    NavigationView mNavigationView;
    @Bind(R.id.drawer)
    DrawerLayout mDrawer;
    @Bind(R.id.main_tl)
    TabLayout mainTl;
    @Bind(R.id.fragment_vp)
    ViewPager fragmentVp;
    //用户选择的推荐引擎方式
    public static int index;
    ImageView avatar;
    TextView userName;
    TextView userSignature;
    Button modifyInfo;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected MainPresenter createPresenter() {

        return new MainPresenter(this);

    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        sharedPreferences = getSharedPreferences("init", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setSupportActionBar(mToolBar);
        StatusBarUtils.transparentBar(this);
        hideNavigationViewScrollBars();

        mainTl.setTabMode(TabLayout.MODE_SCROLLABLE);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentVp.setAdapter(fragmentAdapter);
        mainTl.setupWithViewPager(fragmentVp);



        //ToolBar点击事件
        mToolBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_opean:
                    mDrawer.openDrawer(Gravity.END);
                    break;
                default:
                    break;
            }
            return true;
        });
        initNavigation();
        setNavigationItemSelectedListener();

    }

    /**
     * 隐藏NavigationView的滚动条
     */
    private void hideNavigationViewScrollBars() {
        if (mNavigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) mNavigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
                navigationMenuView.setOverScrollMode(View.OVER_SCROLL_NEVER);
            }
        }
    }

    /**
     * 初始化侧滑菜单控件
     */
    public  void initNavigation(){
        View header = mNavigationView.getHeaderView(0);
        avatar = header.findViewById(R.id.avater_iv);
        userName = header.findViewById(R.id.user_name_tv);
        userSignature = header.findViewById(R.id.user_desc_tv);
        modifyInfo = header.findViewById(R.id.modify_info_bt);
        if(TextUtils.isEmpty(sharedPreferences.getString("avatar",""))){
            avatar.setImageResource(R.drawable.default_avater);
        }else{
            avatar.setImageBitmap(SharePreferencesUtil.getBitmap(this,"avatar"));
        }
        userName.setText(sharedPreferences.getString("name",""));
        userSignature.setText(sharedPreferences.getString("signature",""));

    }
    /**
     * NavagationView菜单点击事件
     */
    private void setNavigationItemSelectedListener() {
        //头部点击事件
        avatar.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                new RxPermissions(MainActivity.this).request(Manifest.permission.READ_PHONE_STATE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean){
                                    Matisse.from(MainActivity.this)
                                            .choose(MimeType.allOf())
                                            .countable(false)
                                            .maxSelectable(1)
                                            .capture(true)
                                            .captureStrategy(new CaptureStrategy(true, "com.Blinger.YiDeNews.fileprovider"))
                                            .imageEngine(new GlideEngine())
                                            .forResult(2);
                                }else{
                                    Toast.makeText(MainActivity.this,"拒绝授权无法正常使用本应用",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
        modifyInfo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_modify_username,null, false);
                EditText name = view.findViewById(R.id.user_name_edit);
                EditText signature = view.findViewById(R.id.user_signature_edit);
                Button cancle = view.findViewById(R.id.cancle_bt);
                Button save = view.findViewById(R.id.ok_bt);
                builder.setView(view);
                builder.setTitle("修改个人信息");
                AlertDialog dialog = builder.show();

                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newName = TextUtils.isEmpty(name.getText())?
                                sharedPreferences.getString("name","") :name.getText().toString();
                        String newSignature = TextUtils.isEmpty(signature.getText())?
                                sharedPreferences.getString("signature",""):signature.getText().toString();
                        editor.putString("signature",newSignature);
                        editor.putString("name",newName);
                        editor.apply();
                        userName.setText(newName);
                        userSignature.setText(newSignature);
                        dialog.dismiss();
                        mPresenter.postNewUsername(sharedPreferences.getString("uuid",""),newName);
                    }
                });


            }
        });


        //菜单点击事件
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    //收藏:
                    case R.id.menu_collection:
                        startActivity(new Intent(MainActivity.this, CollectionActivity.class));
                        break;
                    // 足迹
                    case R.id.menu_history:
                        startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                        break;
                    //切换引擎
                    case R.id.menu_recommend:
                        showSingleChoiceDialog();
                        break;
                    //关于
                    case R.id.menu_about:
                        AboutFragment about = new AboutFragment();
                        about.show(getSupportFragmentManager(), AboutFragment.class.getSimpleName());
                        break;
                    case R.id.menu_setting:
                        startActivity(new Intent(MainActivity.this,SettingActivity.class));
                        break;
                    default:
                        break;
                }
                mDrawer.closeDrawers();
                return true;
            }
        });
    }

    int yourChoice;

    //弹出改变推荐引擎dialog
    private void showSingleChoiceDialog() {

        //editor.putInt("recommend_type",0);
        index = sharedPreferences.getInt("recommend_type", 0);

        final String[] items = {"热点新闻模式", "智能个性化推荐模式", "自由推送模式"};
        yourChoice = -1;
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(MainActivity.this);

        singleChoiceDialog.setTitle("请选择推荐引擎模式");
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, index,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
                            editor.putInt("recommend_type", yourChoice);
                            editor.apply();
                            Toast toast = Toast.makeText(App.getContext(), null, Toast.LENGTH_SHORT);
                            toast.setText("你选择了" + items[yourChoice]);
                            toast.show();
                        }
                    }
                });
        singleChoiceDialog.show();
    }

    //点击打开侧滑菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            List<Uri> uris = Matisse.obtainResult(data);
            avatar.setImageURI(uris.get(0));
            Bitmap bitmap = ((BitmapDrawable)avatar.getDrawable()).getBitmap();
            SharePreferencesUtil.putBitmap(this,"avatar",bitmap);
        }
    }

    @Override
    public void showData(Object obj) {
        if (((List) obj).get(0) instanceof UserTailBean) {
            List<UserTailBean> list = (List<UserTailBean>) obj;
            LogUtils.d(Constant.debugName, list.get(0).toString());

        }
    }

    @Override
    public void showError(String msg) {
        LogUtils.d("更新用户名失败:"+msg);
    }




}


