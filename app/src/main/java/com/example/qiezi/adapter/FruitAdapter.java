package com.example.qiezi.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.qiezi.R;
import com.example.qiezi.main.Fruit;

import java.util.List;

/**
 * Created by 潘 on 2016/3/9.
 */
public class FruitAdapter extends ArrayAdapter<Fruit>{
    private int resourceId;
    public FruitAdapter(Context context , int textViewResourceId,List<Fruit> objects){
        super(context,textViewResourceId,objects);
        resourceId  = textViewResourceId;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        Fruit fruit = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView fruitImage = (ImageView)view.findViewById(R.id.fruit_image);
        TextView fruitName = (TextView)view.findViewById(R.id.fruit_name);
        fruitImage.setImageResource(fruit.getImageId());
        fruitName.setText(fruit.getName());
        return view;
    }
}
