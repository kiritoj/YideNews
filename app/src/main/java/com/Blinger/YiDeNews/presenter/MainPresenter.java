package com.Blinger.YiDeNews.presenter;

import com.Blinger.YiDeNews.model.UserInfoBean;
import com.Blinger.YiDeNews.service.ApiService;
import com.Blinger.YiDeNews.service.BaseObserver;
import com.Blinger.YiDeNews.service.RetrofitManager;
import com.Blinger.base.base.BasePresenter;
import com.Blinger.base.base.BaseView;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by tk
 */
public class MainPresenter extends BasePresenter<BaseView> {
    public MainPresenter(BaseView view) {
        super(view);
    }
    public void postNewUsername(String userID,String newUsername){
        subscribe
                (
                        RetrofitManager.getManager().getRetrofit().create(ApiService.class)
                                .postNewUsername(userID, newUsername),
                        new BaseObserver<List<UserInfoBean>>() {
                            @Override
                            public void onSuccess(List<UserInfoBean> userInfoBeans) {
                                mView.showData(userInfoBeans);
                            }

                            @Override
                            public void onFailed(String msg) {
                                mView.showError(msg);
                            }

                            @Override
                            public void onSubcribes(Disposable d) {
                                add(d);
                            }
                        }
                );
    }
}
