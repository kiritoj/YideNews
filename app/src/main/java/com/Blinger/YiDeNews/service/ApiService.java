package com.Blinger.YiDeNews.service;

import com.Blinger.YiDeNews.model.BaseBean;
import com.Blinger.YiDeNews.model.CommentBean;
import com.Blinger.YiDeNews.model.DataBean;
import com.Blinger.YiDeNews.model.NewBean;
import com.Blinger.YiDeNews.model.UserInfoBean;
import com.Blinger.YiDeNews.model.UserTailBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 作者：310Lab
 * 时间：2020/4/2 10:56
 * 邮箱：1760567382@qq.com
 * 功能：接口文档
 */


public interface ApiService
{
    //获取分类的新闻
    @Headers({"url_name:other"})
    @GET("/ydkb/news/content/other")
    Observable<BaseBean<DataBean<List<NewBean>>>> getNewsList(@Query("type") String type
            , @Query("userUniqueKey") String userId
            ,@Query("recommendType") int index);

    //获取头条新闻
    @Headers({"url_name:other"})
    @GET("/ydkb/news/content/top")
    Observable<BaseBean<DataBean<List<NewBean>>>> getMyNewsList(@Query("userUniqueKey") String userId
            ,@Query("recommendType") int index);

    //反馈不喜欢原因
    @Headers({"url_name:other"})
    @GET("/ydkb/news/distaste")
    Observable<BaseBean<DataBean<List<UserInfoBean>>>> postEnjoy(@Query("newsUniqueKey") String newUid,@Query("userUniqueKey") String userId,@Query("feedbackContent") String feedbackContent);

    //上传用户历史浏览记录
    @Headers({"url_name:other"})
    @GET("/ydkb/news/history")
    Observable<BaseBean<DataBean<List<UserTailBean>>>>  postHistory(
            @Query("userUniqueKey") String UID
            , @Query("newsUniqueKey") String newsID
            ,@Query("newsTitle") String title
            ,@Query("newsType") String type
            ,@Query("newsUrl") String url);

    //用户注册
    @Headers({"url_name:other"})
    @GET("/ydkb/news/register")
    Observable<BaseBean<DataBean<List <UserInfoBean>>>> postNewUser(@Query("uniqueKey") String UID,@Query("userName") String name,@Query("imageType") int imageType,@Query("latitude") double latitude,@Query("longitude") double longitude);//

    //添加评论
    @Headers({"url_name:other"})
    @POST("/ydkb/news/comment/update               ")
    @FormUrlEncoded
    Observable<BaseBean<DataBean<List<UserTailBean>>>> postReview(
            @Field("newsUniqueKey") String newsId
            , @Field("commentUniqueKey") String reviewId
            , @Field("composeType") String reviewType
            , @Field("content") String reviewContent
            , @Field("userUniqueKey") String UID
            , @Field("commentTime") String commentTime
            , @Field("ip") String ip);

    //获取新闻评论列表
    @Headers({"url_name:other"})
    @GET("/ydkb/news/comment/get")
    Observable<BaseBean<DataBean<List<CommentBean>>>> getReviewList(@Query("newsUniqueKey") String newsUid,@Query("userUniqueKey") String userId);

    //更新文章点赞数
    @Headers({"url_name:other"})
    @GET("/ydkb/news/acclaim")
    Observable<BaseBean<DataBean<List<UserTailBean>>>> postAcclaim(
            @Query("typeId")String typeId //新闻id
            ,@Query("userUniqueKey") String userId //用户id
            ,@Query("acclaimType") int type //默认为1
            ,@Query("value") int value); //默认为1

    //上传用户收藏新闻
    @Headers({"url_name:other"})
    @GET("/ydkb/news/collect")
    Observable<BaseBean<DataBean<List<UserTailBean>>>> postCollect(
            @Query("newsUniqueKey") String newsId
            ,@Query("userUniqueKey")String userId
            ,@Query("value") int value
    );
    //修改用户昵称
    @Headers({"url_name:other"})
    @POST("/ydkb/news/changeUsername")
    @FormUrlEncoded
    Observable<BaseBean<DataBean<List<UserTailBean>>>> postNewUsername(
            @Field("userUniqueKey") String userId
            ,@Field("newUsername") String newUsername
    );
}

