package com.lxl.pbpclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lxl.network.api.AuthenticateApiService;
import com.lxl.network.base.BaseObserver;
import com.lxl.network.base.BaseRequest;
import com.lxl.network.base.BaseResponse;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.bt_hello);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseRequest.getService(AuthenticateApiService.class)
                        .hello()
                        .compose(BaseRequest.applyScheduler(new BaseObserver<String>() {
                            @Override
                            protected void onSuccess(BaseResponse<String> response) {
                                Log.d("TAG", "onSuccess: "+response.getResults());
                            }

                            @Override
                            protected void onFailed(Throwable e) {
                                Log.d("TAG", "onFailed: "+e);
                            }
                        }));
            }
        });
    }
}