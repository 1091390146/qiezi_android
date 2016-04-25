package com.example.qiezi.fragment;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qiezi.R;

/**
 * Created by 潘 on 2016/2/24.
 */
public class MessageFragment extends Fragment implements View.OnClickListener {
    @Override
    public void onClick(View v) {

    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInsatanceState){
        View view = inflater.inflate(R.layout.fragment_message,container,false);
        return  view;

    }
}
