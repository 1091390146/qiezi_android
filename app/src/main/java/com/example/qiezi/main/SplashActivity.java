package com.example.qiezi.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qiezi.R;
import com.example.qiezi.adapter.ViewPagerAdapter;
import com.example.qiezi.utils.IntentUtils;

import java.awt.font.TextAttribute;
import java.util.ArrayList;


/**
 * Created by 潘 on 2016/2/23.
 */
public class SplashActivity extends Activity implements View.OnClickListener,ViewPager.OnPageChangeListener{
    private  int UID;
    private Context mContext;
    private TextView tv_login;
    //引导图片资源
    private static final int[] pics ={ R.drawable.we_1, R.drawable.we_1,R.drawable.we_1, R.drawable.we_1};
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
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //找出布局
        final View view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);
        mContext = this;
        initView();
        //判断是否进行过登录 未曾登陆过：
        initData();
        //登陆过
       // LoginToWxt();

    }



    private void initView() {
        // 实例化ArrayList对象
        views = new ArrayList<View>();
        // 实例化ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager_flash);
        // 实例化ViewPager适配器
        vpAdapter = new ViewPagerAdapter(views);
        viewPager.setVisibility(View.GONE);
        ll_point = (LinearLayout) findViewById(R.id.ll_flash);
        ll_point.setVisibility(View.GONE);
        tv_login = (TextView) findViewById(R.id.tv_flash);
        tv_login.setVisibility(View.GONE);
        tv_login.setOnClickListener(this);

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
            ImageView iv = new ImageView(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_flash:
                //判断是否登陆过
 //                   try {
//                    AppInfo appInfo = new AppInfo();
//                    PackageManager pm = mContext.getPackageManager();
//                    PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
//                    String currentVersion = "" + pi.versionCode;
//                    appInfo.setLastVersionCode(currentVersion);
//                    appInfo.writeData(mContext);
//                    UserInfo user = new UserInfo();
//                    user.readData(mContext);
//                    if (user.isLogined()) {// 已登录
//                        LoginToWxt();
//                    } else {// 未登录
//                        IntentUtils.getIntent(SplashActivity.this, LoginActivity.class);
//                        finish();
//                    }
//
//                } catch (Exception e) {
//
//                }
//                IntentUtils.getIntent(SplashActivity.this, LoginActivity.class);
//                finish();
                IntentUtils.getIntent(SplashActivity.this,LoginActivity.class);
                break;
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
        if (positon == pics.length - 1) {
            ll_point.setVisibility(View.GONE);
            tv_login.setVisibility(View.VISIBLE);
        } else {
            tv_login.setVisibility(View.GONE);
            ll_point.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 新的页面被选中时调用
     */
    @Override
    public void onPageSelected(int arg0) {
        // 设置底部小点选中状态
        setCurDot(arg0);

    }
//    private void LoginToWxt() {
//        UserInfo user = new UserInfo();
//        user.readData(mContext);
//        UID = WxtApplication.UID;
//        Log.e("login", String.valueOf(UID));
//        if (user.isLogined()) {// 曾经登录过
//            IntentUtils.getIntent(SplashActivity.this, MainActivity.class);
//            finish();
//        }else{
//            IntentUtils.getIntent(SplashActivity.this, LoginActivity.class);
//            finish();
//        }
//        }else{//已经登陆
//            IntentUtils.getIntent(SplashActivity.this, MainActivity.class);
//            finish();
//        }


    @Override
    public void onPageScrollStateChanged(int state) {

    }



}
