package com.example.qiezi.fragment;


import android.app.Activity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;


import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qiezi.R;
import com.example.qiezi.main.MainActivity;
import com.example.qiezi.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 潘 on 2016/2/24.
 */
public class HomePageFragment extends Fragment implements View.OnClickListener  {
    private ImageView pic_head;
    private Context mContext;
    private TextView[] mTabs;
    private HomePageSchoolFragment homePageSchoolFragment;
    private HomePageStreetFragment homePageStreetFragment;
    private Fragment[] fragments;
    //测试内容
    private List<Fragment> list = new ArrayList<Fragment>();
    private int gapWidth;
    private int bmWidth;
    int beforeItem = 0;

    //测试内容结束
    private int index;
    // 当前fragment的index
    private int currentTabIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInsatanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homePageSchoolFragment = new HomePageSchoolFragment();
        homePageStreetFragment = new HomePageStreetFragment();
        fragments = new Fragment[]{homePageSchoolFragment, homePageStreetFragment};
////        FragmentManager fragmentManager = getChildFragmentManager();
////        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.add(R.id.fragment_container,homePageSchoolFragment);
//        transaction.commit();
        mContext=getActivity();
        init();
        getChildFragmentManager().beginTransaction().add(R.id.fragment_container, homePageSchoolFragment).commit();
    }

    private void init() {
        mTabs = new TextView[2];
        mTabs[0] = (TextView) getView().findViewById(R.id.school);
        mTabs[1] = (TextView) getView().findViewById(R.id.street);
        mTabs[0].setOnClickListener(this);
        mTabs[1].setOnClickListener(this);
        // 把第一个tab设为选中状态
        mTabs[0].setSelected(true);
        registerForContextMenu(mTabs[0]);
        pic_head=(ImageView)getView().findViewById(R.id.pic_head);
        pic_head.setOnClickListener((View.OnClickListener) mContext);
    }

    @Override
    public void onClick(View view) {
        Log.e("dddd", String.valueOf(view.getId()));
        switch (view.getId()) {
            case R.id.school:
                Log.e("111", "111");
                index = 0;
                break;
            case R.id.street:
                Log.e("222", "222");
                index = 1;
                break;
            case R.id.pic_head:
                LogUtils.e("333","333");
                DrawerLayout drawer = (DrawerLayout) getView().findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
        }

        if (currentTabIndex != index) {
//            FragmentTransaction trx = getChildFragmentManager().beginTransaction();
//            trx.hide(fragments[currentTabIndex]);
            //       FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
//                trx.add(R.id.fragment_container, fragments[index]);
                transaction.add(R.id.fragment_container, fragments[index]);
            }
//            trx.show(fragments[index]).commit();
            transaction.show(fragments[index]);
            transaction.commit();
        }

        mTabs[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }



}





