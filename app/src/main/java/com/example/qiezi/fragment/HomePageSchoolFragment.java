package com.example.qiezi.fragment;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;


import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qiezi.R;
import com.example.qiezi.adapter.ViewPagerAdapter;

import java.util.ArrayList;

/**
 * Created by 潘 on 2016/3/14.
 */
public class HomePageSchoolFragment extends Fragment implements View.OnClickListener,ViewPager.OnPageChangeListener{
    private Context mContext;
    //引导图片资源
    private static final int[] pics ={ R.drawable.homepage_pic1, R.drawable.homepage_pic2,R.drawable.homepage_pic3, R.drawable.homepage_pic4};
    //定义ViewPager对象
    private ViewPager viewPager;
    // 定义ViewPager适配器
    private ViewPagerAdapter vpAdapter;
    // 定义一个ArrayList来存放View
    private ArrayList<View> views;
    private LinearLayout ll_point;
    // 底部小点的图片
    private ImageView[] points;
    // 记录当前选中位置
    private int currentIndex;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInsatanceState){
        View view = inflater.inflate(R.layout.fragment_homepageschool,container,false);
        return  view;
        // return inflater.inflate(R.layout.fragment_diary, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initView();
        initData();

    }

    /**
     * 初始化数据
     */
    @SuppressWarnings("deprecation")
    private void initData() {
// 定义一个布局并设置参数 layout参数
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        // 初始化引导图片列表
        for (int i = 0; i < pics.length; i++) {
            ImageView iv = new ImageView(mContext);
            iv.setLayoutParams(mParams);
            // 防止图片不能填满屏幕 图片不安比例扩大缩小到View大小
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            // 加载图片资源
            iv.setImageResource(pics[i]);
            views.add(iv);
        }
        viewPager.setVisibility(View.VISIBLE);
        // 设置数据
        viewPager.setAdapter(vpAdapter);
        // 设置监听
        viewPager.setOnPageChangeListener(this);
        // 初始化底部小点
        initPoint();
    }

    /**
     * 初始化底部小点
     */
    private void initPoint() {
        points = new ImageView[pics.length];
        ll_point.setVisibility(View.VISIBLE);
        // 循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            // 得到一个LinearLayout下面的每一个子元素
            points[i] = (ImageView) ll_point.getChildAt(i);
            // 默认都设为灰色
            points[i].setEnabled(true);
            // 给每个小点设置监听
            points[i].setOnClickListener(this);
            // 设置位置tag，方便取出与当前位置对应
            points[i].setTag(i);
        }

        // 设置当面默认的位置
        currentIndex = 0;
        // 设置为白色，即选中状态
        points[currentIndex].setEnabled(false);
    }

    private void initView() {
        // 实例化ArrayList对象
        views = new ArrayList<View>();
        // 实例化ViewPager
        viewPager = (ViewPager) getView().findViewById(R.id.viewpager_homepage);
        // 实例化ViewPager适配器
        vpAdapter = new ViewPagerAdapter(views);
        viewPager.setVisibility(View.GONE);
        ll_point = (LinearLayout)getView().findViewById(R.id.ll_flash);
        ll_point.setVisibility(View.GONE);

    }

    @Override
       public void onClick(View v) {
        switch (v.getId()){
            default:
                int position = (Integer) v.getTag();
                setCurView(position);
                setCurDot(position);
                break;
        }
    }
    /**
     * 设置当前页面的位置
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }
        viewPager.setCurrentItem(position);
    }

    /**
     * 设置当前的小点的位置
     */
    private void setCurDot(int positon) {
        if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
            return;
        }
        points[positon].setEnabled(false);
        points[currentIndex].setEnabled(true);
        currentIndex = positon;
//        if (positon == pics.length - 1) {
//            ll_point.setVisibility(View.GONE);
//        } else {
//            ll_point.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 设置底部小点选中状态
        setCurDot(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
