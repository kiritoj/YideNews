package com.Blinger.YiDeNews.presenter;

import android.util.Log;

import com.Blinger.base.base.BasePresenter;
import com.Blinger.base.base.BaseView;
import com.Blinger.YiDeNews.model.UserInfoBean;
import com.Blinger.YiDeNews.service.ApiService;
import com.Blinger.YiDeNews.service.BaseObserver;
import com.Blinger.YiDeNews.service.RetrofitManager;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by 社会主义好
 */

public class SplashPresenter extends BasePresenter<BaseView>{
    public SplashPresenter(BaseView view) {
        super(view);
    }

    public void postNewUser(String UID,String name,int imageType,double latitude,double longitude){
        subscribe(
                RetrofitManager.getManager().getRetrofit().create(ApiService.class)
                        .postNewUser(UID,name,imageType,latitude,longitude),
                new BaseObserver <List <UserInfoBean>>() {
                    @Override
                    public void onSuccess(List<UserInfoBean> userInfoBeans) {
                        mView.showData(userInfoBeans);
                    }

                    @Override
                    public void onFailed(String msg) {
                        mView.showError(msg);
                        Log.d("sakura","注册失败" + msg);
                    }

                    @Override
                    public void onSubcribes(Disposable d) {
                        add(d);
                    }

                }
        );
    }
}
