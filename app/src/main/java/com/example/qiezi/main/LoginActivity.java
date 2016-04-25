package com.example.qiezi.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Selection;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiezi.R;
import com.example.qiezi.bean.find.UserInfo;
import com.example.qiezi.net.HttpTool;
import com.example.qiezi.utils.IntentUtils;
import com.example.qiezi.utils.KeyBoardUtils;
import com.example.qiezi.utils.ToastUtils;
import com.example.qiezi.view.WxtApplication;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by mac on 16/1/23.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private static String UID;
    private String result_data, token;
    private Button btn_login;
    private Button btn_close;
    private EditText tx_phonenumber;
    private EditText tx_vertifycode;
    private String phone = null;
    private String password = null;
    private TextView tv_register;
    private TextView tv_forget;
    private Context mContext;
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
                        JSONObject item = new JSONObject(data);
                        Log.e("hou_uid", item.getString("id"));
                        user.setUserId(item.getString("id"));
                        user.writeData(mContext);;
                        Toast.makeText(LoginActivity.this, "登陆成功",
                                Toast.LENGTH_SHORT).show();
                        IntentUtils.getIntent(LoginActivity.this, MainActivity.class);
                        finish();
//                        mDialog.dismiss();
                    } else {
                        Toast.makeText(LoginActivity.this, data,
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
            case 1:
                ToastUtils.ToastShort(mContext, "手机号或密码输入错误！");
            default:
                break;
        }
    }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        mContext = this;
        init();
    }
    private void init() {
        Log.e("login","init");
        // TODO Auto-generated method stub
//        mDialog = MyProgressDialog.createDialog(LoginActivity.this);
        btn_login = (Button) findViewById(R.id.login_login);
        tx_phonenumber = (EditText) findViewById(R.id.login_phonenum);
        tx_vertifycode = (EditText) findViewById(R.id.login_Password);
        btn_login =(Button)findViewById(R.id.login_login);
        tv_register = (TextView)findViewById(R.id.tv_login_register);
        tv_forget = (TextView)findViewById(R.id.tv_login_forgetpassword);
        tv_register = (TextView)findViewById(R.id.tv_login_register);
        tv_register.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        user = new UserInfo();
        user.readData(this);
        if (!user.getUserPhone().equals("")) {
            tx_phonenumber.setText(user.getUserPhone());
        }
        // 切换后将EditText光标置于末尾
        CharSequence charSequence = tx_phonenumber.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
        KeyBoardUtils.showKeyBoardByTime(tx_phonenumber, 300);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login:
                getLogin();
                break;
            case R.id.tv_login_register:// 注册
                IntentUtils.getIntent(LoginActivity.this, RegisterActivity.class);
                break;
            case R.id.tv_login_forgetpassword:// 忘记密码
                IntentUtils.getIntent(LoginActivity.this, ForgetPasswordActivity.class);
                break;
        }
    }

    private void getLogin() {
//            mDialog.show();
            phone = tx_phonenumber.getText().toString();
            password = tx_vertifycode.getText().toString();
            user.setUserPhone(phone);
            user.setUserPassword(password);
            user.writeData(mContext);
            Log.e("111","111");
        //线程
            new Thread() {
                public void run() {
                    //1、获取输入的手机号码
                    phone = tx_phonenumber.getText().toString();
                    password = tx_vertifycode.getText().toString();
                    if (phone.length()==11 ){
                        if (HttpTool.isConnnected(mContext)){
                            String phoneNum = tx_phonenumber.getText().toString();
                            String password = tx_vertifycode.getText().toString();
                            result_data = HttpTool.Login(phoneNum,password);
                            Log.e("login",result_data);
                            //调用服务器登录函数
                            handler.sendEmptyMessage(3);
                        }else {
                            //输出：网络连接有问题！
                            handler.sendEmptyMessage(2);
                        }

                    }else {
                        //输出：手机号或密码不正确！
                        handler.sendEmptyMessage(1);
                    }

                }

            }.start();
    }
}
