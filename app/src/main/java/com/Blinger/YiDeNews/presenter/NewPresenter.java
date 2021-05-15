package com.Blinger.YiDeNews.presenter;

import com.Blinger.base.base.BasePresenter;
import com.Blinger.base.base.BaseView;
import com.Blinger.YiDeNews.model.NewBean;
import com.Blinger.YiDeNews.model.UserInfoBean;
import com.Blinger.YiDeNews.service.ApiService;
import com.Blinger.YiDeNews.service.BaseObserver;
import com.Blinger.YiDeNews.service.RetrofitManager;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 作者：310Lab
 * 时间：2020/4/1 23:08
 * 邮箱：1760567382@qq.com
 * 功能：
 */

/**
 * modified by 逃课 on 2020/5/18
 * 增加了加载更多
 *
 */

public class NewPresenter extends BasePresenter<BaseView>

{
    public static int REFRESH = 0;
    public static int LOAD_MORE = 1;
    //加载类型：刷新还是加载更多
    public int requestFlag = REFRESH;
    public NewPresenter(BaseView view)
    {
        super(view);
    }


    public void getNewsList(String type,String userId,int index,int flag)
    {
        requestFlag = flag;
        //LogUtils.d(Constant.debugName+"NewPresenter   ",type);
        //获取头条信息
        if ("top".equals(type)){
            subscribe(
                    RetrofitManager.getManager().getRetrofit().create(ApiService.class)
                            .getMyNewsList(userId,index),
                    new BaseObserver<List<NewBean>>()
                    {
                        @Override
                        public void onSuccess(List<NewBean> list)
                        {
                            mView.showData(list);
                        }

                        @Override
                        public void onFailed(String msg)
                        {
                            mView.showError(msg);
                        }

                        @Override
                        public void onSubcribes(Disposable d)
                        {
                            add(d);
                        }
                    }
            );
        }else {
            subscribe
                    (
                            RetrofitManager.getManager().getRetrofit().create(ApiService.class)
                                    .getNewsList("toutiao".equals(type) ? "top" : type, userId,index),
                            new BaseObserver<List<NewBean>>()
                            {
                                @Override
                                public void onSuccess(List<NewBean> list)
                                {
                                    mView.showData(list);
                                }

                                @Override
                                public void onFailed(String msg)
                                {
                                    mView.showError(msg);
                                }

                                @Override
                                public void onSubcribes(Disposable d)
                                {
                                    add(d);
                                }
                            }
                    );
        }

    }




    //String newUid,@Query("userUniqueKey") String userId,@Query("feedbackContent") String feedbackContent
    public void postEnjoy(String newUid,String userId,String feedbackContent){
        subscribe
                (
                        RetrofitManager.getManager().getRetrofit().create(ApiService.class)
                                .postEnjoy(newUid, userId, feedbackContent),
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
