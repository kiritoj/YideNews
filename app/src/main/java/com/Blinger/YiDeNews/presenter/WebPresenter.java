package com.Blinger.YiDeNews.presenter;

import android.util.Log;
import android.widget.Toast;

import com.Blinger.base.base.BasePresenter;
import com.Blinger.base.base.BaseView;
import com.Blinger.base.utils.LogUtils;
import com.Blinger.YiDeNews.config.Constant;
import com.Blinger.YiDeNews.model.CommentBean;
import com.Blinger.YiDeNews.model.UserTailBean;
import com.Blinger.YiDeNews.service.ApiService;
import com.Blinger.YiDeNews.service.BaseObserver;
import com.Blinger.YiDeNews.service.RetrofitManager;

import java.util.List;

import io.reactivex.disposables.Disposable;


/**
 * Created by 社会主义好
 */

public class WebPresenter extends BasePresenter<BaseView>{
    public static int requireType;

    public WebPresenter(BaseView view) {
        super(view);
    }

    public static int getRequireType() {
        return requireType;
    }

    public void postHistory(String UID, String newsID, String title, final String type, String time, String url){
        subscribe(
                RetrofitManager.getManager().getRetrofit().create(ApiService.class)
                        .postHistory(UID, newsID, title, type,url),
                new BaseObserver<List<UserTailBean>>() {
                    @Override
                    public void onSuccess(List<UserTailBean> userTailBean) {
                        //LogUtils.d(Constant.debugName,"into success");
                        //LogUtils.d(Constant.debugName,userTailBean.get(0).getInfo()+"success");
                        mView.showData(userTailBean);
                    }

                    @Override
                    public void onFailed(String msg) {
                        //LogUtils.d(Constant.debugName,msg+" failed");
                        requireType = 0;
                        mView.showError(msg);
                    }

                    @Override
                    public void onSubcribes(Disposable d) {
                        add(d);
                    }


                }
        );
    }

    public void postReview(String newsId, String reviewId, String reviewType, String reviewContent, String UID, String commentTime, String ip) {
        subscribe(RetrofitManager.getManager().getRetrofit().create(ApiService.class)
                        .postReview(newsId, reviewId, reviewType, reviewContent, UID, commentTime, ip),
                new BaseObserver<List<UserTailBean>>() {
                    @Override
                    public void onSuccess(List<UserTailBean> userTailBeans) {
                        //LogUtils.d(Constant.debugName+"postReview","into success");
                        mView.showData(userTailBeans);
                    }

                    @Override
                    public void onFailed(String msg) {
                        //LogUtils.d(Constant.debugName+"postReview","into failue");
                        requireType = 1;
                        mView.showError(msg);
                    }

                    @Override
                    public void onSubcribes(Disposable d) {
                        add(d);
                    }

                }
        );
    }

    public void getReviewList(String newsId,String userId){
        subscribe(RetrofitManager.getManager().getRetrofit().create(ApiService.class)
                        .getReviewList(newsId,userId),
                new BaseObserver<List<CommentBean>>() {
                    @Override
                    public void onSuccess(List<CommentBean> commentBeans) {
                        //LogUtils.d(Constant.debugName+"getReviewList","into success");
                        mView.showData(commentBeans);
                        int size = commentBeans.size();
                        Log.d("cnm", String.valueOf(size));
                        if (size > 0) {
                            if (commentBeans.get(0).getObject() != null) {
                                Log.d("cnm", String.valueOf(commentBeans.get(0).getObject().toString()));
                            }
                        }
                    }

                    @Override
                    public void onFailed(String msg) {
                        //LogUtils.d(Constant.debugName+"getReviewList","into failue");
                        requireType = 2;
                        mView.showError(msg);
                    }

                    @Override
                    public void onSubcribes(Disposable d) {
                        add(d);
                    }
                });
    }

    public void postAcclaim(String typeId,String userId,int type,int value){
        subscribe(RetrofitManager.getManager().getRetrofit().create(ApiService.class)
                        .postAcclaim(typeId, userId,type,value),
                new BaseObserver<List<UserTailBean>>() {
                    @Override
                    public void onSuccess(List<UserTailBean> userTailBeans) {
                        mView.showData(userTailBeans);
                    }

                    @Override
                    public void onFailed(String msg) {
                        requireType = 3;
                        mView.showError(msg);
                    }

                    @Override
                    public void onSubcribes(Disposable d) {
                        add(d);
                    }
                });
    }

    public void postCollect(String newsId,String userId,int value){
        subscribe(RetrofitManager.getManager().getRetrofit().create(ApiService.class)
                    .postCollect(newsId,userId,value),
                new BaseObserver<List<UserTailBean>>(){
                    @Override
                    public void onSuccess(List<UserTailBean> userTailBeans) {
                        mView.showData(userTailBeans);
                    }

                    @Override
                    public void onFailed(String msg) {
                        requireType = 4;
                        mView.showError(msg);
                    }

                    @Override
                    public void onSubcribes(Disposable d) {
                        add(d);
                    }
                });
    }
}
