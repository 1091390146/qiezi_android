package com.example.qiezi.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qiezi.R;
import com.example.qiezi.net.HttpTool;
import com.example.qiezi.utils.IntentUtils;
import com.example.qiezi.view.MyProgressDialog;
import com.example.qiezi.view.WxtApplication;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by wzh on 2016/1/24.
 */
public class ForgetPasswordActivity extends Activity implements View.OnClickListener {
    private int second = 30;
    private Button btn_next;
    private Button btn_sendVerifyCode;
    private EditText edit_password1;
    private EditText edit_password2;
    private EditText phone_num;
    private EditText sendVerifyCode;
    private String result_data;
    private MyProgressDialog mDialog;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    //设置按钮的状态不可用
                    //按钮开始倒计时
                    btn_sendVerifyCode.setClickable(false);
                    btn_sendVerifyCode.setText("请等待（"+   second+"s）" );
                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                    break;
                case 1:
                    btn_sendVerifyCode.setText("获取验证码");
                    btn_sendVerifyCode.setClickable(true);
                    break;
                case 2:
                    Toast.makeText(ForgetPasswordActivity.this, "获取验证码成功",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    //判断与服务器的验证码是否一致，如果一致则提示注册成功，跳转到主界面；否则提示验证码错误
                    try {
                        JSONObject json = new JSONObject(result_data);
                        String status = json.getString("status");
                        String data = json.getString("data");
                        if (status.equals("success")) {
                            //实力化缓存类
                            Toast.makeText(ForgetPasswordActivity.this, "找回密码成功！",Toast.LENGTH_SHORT).show();
                            IntentUtils.getIntent(ForgetPasswordActivity.this, MainActivity.class);
                        }
                        else {
                            Toast.makeText(ForgetPasswordActivity.this, data,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    break;
                default:
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setpassword);
        init();
    }

    private void init() {
        edit_password1 = (EditText) findViewById(R.id.edit_password1);
        edit_password2 = (EditText) findViewById(R.id.edit_password2);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_sendVerifyCode = (Button) findViewById(R.id.yanzhengma);
        phone_num = (EditText) findViewById(R.id.edit_phone_number);
        sendVerifyCode = (EditText) findViewById(R.id.edit_verifycode);
        btn_next.setOnClickListener(this);
        btn_sendVerifyCode.setOnClickListener(this);

    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.yanzhengma:
                VerifyCode();
                break;
            case R.id.btn_next:
                Next();
            default:
                break;
        }
    }




    //发送验证码的函数
    private void VerifyCode() {
        final String phonenumber = phone_num.getText().toString();
        if (phonenumber.length() == 11) {
//            Toast.makeText(ForgetPasswordActivity.this, "获取成功，请稍后！", Toast.LENGTH_SHORT).show();
            new Thread() {
                @Override
                public void run() {

                    HttpTool.SendMobileCode(phonenumber);  //让服务器发送验证码到手机
                    handler.sendEmptyMessage(2);
                    for (int i = 0; i < 30; i++) {
                        try {
                            handler.sendEmptyMessage(0);
                            Thread.sleep(1000);
                            second--;
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    handler.sendEmptyMessage(1);
                }
            }.start();
        }
        else

        {
            Toast.makeText(ForgetPasswordActivity.this, "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
        }



    }
    private void Next() {
        //获取手机号码的字符串形式，获取服务器返回的手机号和验证码值
        String phoneNum = phone_num.getText().toString();
        String verifyCode = sendVerifyCode.getText().toString();
        String firstPassword = edit_password1.getText().toString();
        String secondPassword = edit_password2.getText().toString();
        if (firstPassword.equals(secondPassword) ) {

            if ((phoneNum.length() == 11) && (verifyCode.length()) == 6) {
                //启动新线程
                new Thread() {
                    public void run() {
                        String firstPassword = edit_password1.getText().toString();
                        String phoneNum = phone_num.getText().toString();
                        String verifyCode = sendVerifyCode.getText().toString();
                        result_data = HttpTool.forgetpwd(phoneNum, verifyCode, firstPassword);
                        Log.e("result_data",result_data);
                        handler.sendEmptyMessage(3);
                    }

                }.start();
            } else {
                Toast.makeText(ForgetPasswordActivity.this, "请输入正确的验证码和手机号！！", Toast.LENGTH_SHORT).show();
            }
        }else
            Toast.makeText(ForgetPasswordActivity.this,"请检查输入的密码是否正确！",Toast.LENGTH_SHORT).show();


    }


}