package com.lxl.pbpclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.lxl.network.api.AuthenticateApiService;
import com.lxl.network.base.BaseObserver;
import com.lxl.network.base.BaseRequest;
import com.lxl.network.base.BaseResponse;
import com.lxl.pbpclient.MainActivity;
import com.lxl.pbpclient.R;

import org.apache.commons.lang3.StringUtils;

public class LoginActivity extends BaseActivity {

    private EditText loginAccount;
    private EditText loginPwd;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password);
        loginAccount = findViewById(R.id.et_login_account);
        loginPwd = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.bt_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = loginAccount.getText().toString().trim();
                String pwd = loginPwd.getText().toString().trim();
                login(account, pwd);
            }
        });
    }

    private void login(String account, String pwd) {
        if (StringUtils.isEmpty(account)) {
            showToastShort("请输入账号！");
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            showToastShort("请输入密码！");
            return;
        }
        BaseRequest.getService(AuthenticateApiService.class)
                .toLogin(account,pwd)
                .compose(BaseRequest.applyScheduler(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(BaseResponse<String> response) {
                        // 跳转界面
                        // showToastShortSync("登录成功");
                        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    protected void onFailed(Throwable e) {
                        // showToastShortSync("登录失败");
                        Log.d("TAG", "onFailed: "+e);
                    }
                }));
    }
}
