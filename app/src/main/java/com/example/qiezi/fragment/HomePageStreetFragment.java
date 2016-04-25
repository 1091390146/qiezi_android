package com.example.qiezi.fragment;


import android.app.Fragment;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qiezi.R;

/**
 * Created by 潘 on 2016/3/14.
 */
public class HomePageStreetFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInsatanceState){
        View view = inflater.inflate(R.layout.fragment_homepagestreet,container,false);
        return  view;
        // return inflater.inflate(R.layout.fragment_diary, container, false);

    }
}
