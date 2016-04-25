package com.example.qiezi.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiezi.R;
import com.example.qiezi.bean.find.UserInfo;
import com.example.qiezi.net.HttpTool;
import com.example.qiezi.utils.IntentUtils;
import com.example.qiezi.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by 潘 on 2016/4/24.
 */
public class WebClickEditActivity extends Activity implements View.OnClickListener {
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    private Context mContext;
    private ImageView btn_exit;
    private ImageView set_head_img;
    private TextView edit_finish;
    private EditText edit_name;
    private EditText edit_sex;
    private EditText edit_age;
    private EditText edit_city;
    private String result_data, token;
    private Uri imageUri;
    private UserInfo user;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 3:
                    try {
                        JSONObject json = new JSONObject(result_data);
                        String status = json.getString("status");
                        String data = json.getString("data");
                        if (status.equals("success")) {
                            Log.e("data",data);

                            Toast.makeText(mContext, "录入数据成功",
                                    Toast.LENGTH_SHORT).show();
                            //缓存数据
                            //设置年龄 城市
                            user.setUserCity(edit_city.getText().toString());
                            user.setuserAge(edit_age.getText().toString());
                            user.setUserName(edit_name.getText().toString());
                            user.setUserGender(edit_sex.getText().toString());
                            user.writeData(mContext);
                            Log.e("uesr", "录入数据成功");
                            IntentUtils.getIntent(WebClickEditActivity.this,MainActivity.class);
//                        mDialog.dismiss();
                        } else {
                            Toast.makeText(mContext, data,
                                    Toast.LENGTH_SHORT).show();
                            Log.e("hou", data);
//                        mDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    break;
                case 2:
                    ToastUtils.ToastShort(mContext, "网络问题，请稍后重试！");
                    break;
                case 4:
                    ToastUtils.ToastShort(mContext,"请输入正确年龄");

                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_teacher_edit);
        mContext = this;
        init();
    }

    private void init() {
        btn_exit = (ImageView)findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);
        edit_finish = (TextView)findViewById(R.id.edit_finish);
        edit_finish.setOnClickListener(this);
        //设置头像
        set_head_img = (ImageView) findViewById(R.id.set_head_img);
        set_head_img.setOnClickListener(this);
        edit_name = (EditText)findViewById(R.id.teacher_edit_name);
        edit_age=(EditText)findViewById(R.id.teacher_edit_age);
        edit_sex=(EditText)findViewById(R.id.teacher_edit_sex);
        edit_city=(EditText)findViewById(R.id.teacher_edit_city);
        user = new UserInfo();
        user.readData(this);
        if(!user.getUserCity().equals("")){
            edit_city.setText(user.getUserCity());
        }
        if(!user.getUserGender().equals("")){
            edit_sex.setText(user.getUserGender());
        }
        if(!user.getUserName().equals("")){
            edit_name.setText(user.getUserName());
        }
        if(!user.getuserAge().equals("")){
            edit_age.setText(user.getuserAge());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_head_img:
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setMessage("调用方法");
                dialog.setCancelable(true);
                dialog.setPositiveButton("相册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
                        try {
                            if (outputImage.exists()) {
                                outputImage.delete();
                            }
                            outputImage.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageUri = Uri.fromFile(outputImage);
                        //发送打开相册信息
                        Intent intent = new Intent("android.intent.action.GET_CONTENT");
                        intent.setType("image/*");
                        intent.putExtra("crop", "true");
                        intent.putExtra("scale", "true");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, CROP_PHOTO);

                    }
                });
                dialog.setNegativeButton("拍照", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
                        try {
                            if (outputImage.exists()) {
                                outputImage.delete();
                            }
                            outputImage.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageUri = Uri.fromFile(outputImage);
                        //发送打开摄像头
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, TAKE_PHOTO);

                    }
                });
                dialog.show();
                break;
            case R.id.btn_exit:
                finish();
                break;
            case R.id.edit_finish:
                update();
                break;
        }
    }

    private void update() {

        //线程
        new Thread() {
            public void run() {
              ;
                if(edit_age.getText().toString().length()<3) {
                    if (HttpTool.isConnnected(mContext)) {
                        String name = edit_name.getText().toString();
                        String age = edit_age.getText().toString();
                        String city = edit_city.getText().toString();
                        String sex = edit_sex.getText().toString();
                        String id = user.getUserId();
                        result_data = HttpTool.savepersonalinfo(id, name, sex, age, city);
                        Log.e("录入数据返回值", result_data);
                        //调用服务器登录函数
                        handler.sendEmptyMessage(3);
                    } else {
                        //输出：网络连接有问题！
                        handler.sendEmptyMessage(2);
                    }
                }else {
                    handler.sendEmptyMessage(4);
                }

                }


        }.start();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    //设置intent的data和Type属性。 打开文件
                    // intent.setDataAndType(/*uri*/Uri.fromFile(file), type);
                    //image/* 指的是image的任何类型  如JPG PNG
//                    intent.putExtra("crop", "true");//发送裁剪信号
//                    intent.putExtra("outputX", outputX);//裁剪区的宽
//                    intent.putExtra("outputY", outputY);//裁剪区的高
//                    intent.putExtra("aspectX", 1);//X方向上的比例
//                    intent.putExtra("aspectY", 1);//Y方向上的比例
//                    intent.putExtra("scale", scale);//是否保留比例
//                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//直接输出文件
//                    intent.putExtra("return-data", true); //是否返回数据
////        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//                    intent.putExtra("noFaceDetection", true); //关闭人脸检测

                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", "true");
                    //自定义剪裁位置
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    set_head_img.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }
}
