package com.Blinger.YiDeNews.ui.MyView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.Blinger.YiDeNews.R;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class BannerView extends FrameLayout {

    private View mContainer;
    private AutoHeightViewPager mViewPager;
    private LinearLayout mDotsContainer;
    private List<ImageView> mImageViews;
    private List<ImageView> mDots;
    private MyHandler mHandler;
    private int mPageCount;
    private int mImageIndex; //当前展示的图片下标
    private int mDotIndex;  //当前的小圆点下标
    private static final int MSG_WHAT = 0;

    public BannerView(@NonNull Context context) {
        this(context,null);
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        mContainer = LayoutInflater.from(context).inflate(R.layout.banner_container,this,true);
        mViewPager = mContainer.findViewById(R.id.view_pager);
        mDotsContainer = mContainer.findViewById(R.id.ll_idots);
        mHandler = new MyHandler();
        mDots = new ArrayList<>();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setAdapter(final BinnerAdapter adapter){
        mViewPager.setPageTransformer(true,new AlphaPageTransformer());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageMargin(20);
        mImageViews = adapter.getImageViewList();
        mPageCount = adapter.getRealCount();
        initDots(adapter.getRealCount());
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mImageIndex = position;
                mDotIndex = position % mPageCount;
                refreshDots();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        stopPlay();
                        break;
                    case MotionEvent.ACTION_UP:
                        autoPlay();
                        break;
                }
                //不消费事件，只停止handler继续发送消息
                return false;
            }
        });
        mViewPager.setCurrentItem(mPageCount * 500);
        autoPlay();
    }

    //开启自动轮播
    public void autoPlay(){
        mHandler.sendEmptyMessageDelayed(MSG_WHAT,3000);
    }

    //停止自动轮播
    public void stopPlay(){
        mHandler.removeMessages(MSG_WHAT);
    }

    //初始化底部的标志点位
    public void initDots(int count){
        for (int i = 0; i < count; i++) {
            ImageView dot = new ImageView(getContext());
            dot.setImageResource(R.drawable.selector_idot_select);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,0,10,0);
            mDots.add(dot);
            mDotsContainer.addView(dot,params);
        }
    }

    //刷新当前点
    public void refreshDots(){
        for (int i = 0; i < mPageCount; i++) {
            if (i == mDotIndex){
                mDots.get(mDotIndex).setSelected(true);
            }else{
                mDots.get(i).setSelected(false);
            }
        }
    }


    class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            sendEmptyMessageDelayed(MSG_WHAT,3000);
            mViewPager.setCurrentItem(mImageIndex + 1);
        }
    }

    //包装类的适配器，实现无限轮播
    public static class BinnerAdapter extends PagerAdapter {

        private List<ImageView> mImageViewList = new ArrayList<>();
        private static final int MULTIPLE_NUM = 1000;

        public BinnerAdapter(Context context, List<String> urls){
            if (urls != null){
                for (String url : urls) {
                    ImageView imageView = new ImageView(context);
                    Glide.with(context).load(url).into(imageView);
                    mImageViewList.add(imageView);
                }
            }
        }


        @Override
        public int getCount() {
            return mImageViewList.size() * MULTIPLE_NUM;
        }

        public int getRealCount(){
            return mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //container.removeView((View) object);
            //不自动移除，instantiateItem中手动移除
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            int realPosition = position % getRealCount();
            ViewGroup parent = (ViewGroup) mImageViewList.get(realPosition).getParent();
            if (parent != null){
                parent.removeView(mImageViewList.get(realPosition));
            }
            container.addView(mImageViewList.get(realPosition));
            return mImageViewList.get(realPosition);
        }

        public List<ImageView> getImageViewList() {
            return mImageViewList;
        }
    }


}
