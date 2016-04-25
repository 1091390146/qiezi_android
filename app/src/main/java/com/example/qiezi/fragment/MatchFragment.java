package com.example.qiezi.fragment;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.SupportMapFragment;
import com.example.qiezi.R;
import com.example.qiezi.main.MapActivity;
import com.example.qiezi.utils.IntentUtils;
import com.example.qiezi.view.WxtApplication;

import java.util.List;

/**
 * Created by 潘 on 2016/2/24.
 */
public class MatchFragment extends Fragment implements View.OnClickListener {

    private Button btn_map;
    private MapView mapView;
    private BaiduMap baiduMap;
    private Context mContext;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.btn_map:
//                IntentUtils.getIntent((Activity) mContext,MapActivity.class);
            }

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInsatanceState){
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_match,null);
        mapView = (MapView) view.findViewById(R.id.map_view1);
        baiduMap = mapView.getMap();
        return  view;

    }
    @Override
    public void onActivityCreated(Bundle savedInstatanceState){
        super.onActivityCreated(savedInstatanceState);


//        btn_map = (Button)getView().findViewById(R.id.btn_map);
//        btn_map.setOnClickListener(this);

    }




    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }


}


