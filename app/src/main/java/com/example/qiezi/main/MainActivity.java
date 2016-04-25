package com.example.qiezi.main;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;

import com.example.qiezi.R;
import com.example.qiezi.fragment.HomePageFragment;
import com.example.qiezi.fragment.MatchFragment;
import com.example.qiezi.fragment.MeFragment;
import com.example.qiezi.fragment.MessageFragment;
import com.example.qiezi.fragment.SquareFragment;
import com.example.qiezi.utils.IntentUtils;
import com.example.qiezi.utils.LogUtils;
import com.example.qiezi.view.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener ,NavigationView.OnNavigationItemSelectedListener{
    private Button[] mTabs;
    private HomePageFragment homePageFragment;
    private SquareFragment squareFragment;
    private MatchFragment matchFragment;
    private MessageFragment messageFragment;
    private MeFragment meFragment;
    private Fragment[] fragments;
    private int index;
    // 当前fragment的index
    private int currentTabIndex;
    private Context mContext;
    private Button btn_data;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        homePageFragment = new HomePageFragment();
        squareFragment = new SquareFragment();
        matchFragment = new MatchFragment();
        meFragment = new MeFragment();
        messageFragment = new MessageFragment();
        fragments = new  Fragment[]{homePageFragment , squareFragment , matchFragment ,messageFragment,meFragment};
        ////        添加显示第一个fragment
        //
//         getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, homePageFragment).add(R.id.fragment_container, squareFragment).hide(squareFragment).show(homePageFragment)
//                .commit();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container,homePageFragment);
        transaction.commit();

    }

    @Override
    protected void onNavigationItemSelected(View v) {
        switch (v.getId()){
            case R.id.btn_data:
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
        }
    }

    private void initView() {
        //初始化底部5个按钮
        mTabs = new Button[5];
        mTabs[0] = (Button)findViewById(R.id.btn_homepage);
        mTabs[1] = (Button)findViewById(R.id.btn_square);
        mTabs[2] = (Button)findViewById(R.id.btn_match);
        mTabs[3] = (Button)findViewById(R.id.btn_message);
        mTabs[4] = (Button)findViewById(R.id.btn_me);
        // 把第一个tab设为选中状态
        mTabs[0].setSelected(true);
        registerForContextMenu(mTabs[0]);


//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, pic_head, "Open navigation drawer", "Close navigation drawer");
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

       
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        btn_data=(Button)headerView.findViewById(R.id.btn_data);
        btn_data.setOnClickListener(this);


    }
    /**
     * button点击事件
     *
     * @param view
     */

    public void onTabClicked(View view) {
        Log.e("dddd", String.valueOf(view.getId()));
        switch (view.getId()) {
            case R.id.btn_homepage:
                index = 0;
                break;
            case R.id.btn_square:
                index = 1;
                break;
            case R.id.btn_match:
                index = 2;
                break;
            case R.id.btn_message:
                index = 3;
                break;
            case R.id.btn_me:
                index = 4;
                break;
        }
        if (currentTabIndex != index) {
//            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
//            trx.hide(fragments[currentTabIndex]);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
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
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pic_head:
                LogUtils.e("111","111");
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.btn_data:
                drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                IntentUtils.getIntent(this,WebClickEditActivity.class);

        }


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }else if(id ==R.id.btn_data){
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            IntentUtils.getIntent(this,WebClickEditActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}