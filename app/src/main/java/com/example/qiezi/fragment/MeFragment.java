package com.example.qiezi.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiezi.R;
import com.example.qiezi.app.ExitApplication;
import com.example.qiezi.bean.find.UserInfo;
import com.example.qiezi.main.LoginActivity;
import com.example.qiezi.utils.IntentUtils;
import com.example.qiezi.utils.LogUtils;
import com.example.qiezi.view.WxtApplication;

/**
 * Created by 潘 on 2016/2/24.
 */
public class MeFragment extends Fragment implements View.OnClickListener {
    private Button quit1 ;
    private Activity mContext;
    private SharedPreferences sp;
    private UserInfo user;

    private void getDialog() {
        AlertDialog.Builder dialog = new  AlertDialog.Builder(mContext);
        dialog.setTitle("退出登录");
        dialog.setMessage("确认退出？");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                user.clearDataExceptPhone(mContext);
//                SharedPreferences.Editor e = sp.edit();
//                LogUtils.e("删除前", e.toString());
//                e.clear();
//                e.commit();
//                WxtApplication.UID = 0;
//                LogUtils.e("删除后", e.toString());
                IntentUtils.getIntent(mContext, LoginActivity.class);
//                mContext.finish();
//                ExitApplication.getInstance().exit();

            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        dialog.show();
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInsatanceState){
        View view = inflater.inflate(R.layout.fragment_me,container,false);
        return  view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        sp = mContext.getSharedPreferences("list", mContext.MODE_PRIVATE);
        quit1 = (Button)getView().findViewById(R.id.quit);
        quit1.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.quit:
               LogUtils.e("111","111");
                getDialog();
                break;
        }
    }
}