package com.lxl.network;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.lxl.network.api.ApiService;
import com.lxl.network.base.BaseObserver;
import com.lxl.network.base.BaseRequest;
import com.lxl.network.base.BaseResponse;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.lxl.network.test", appContext.getPackageName());

        BaseRequest.getService(ApiService.class)
                .getInfo("1")
                //做线程切换的封装
                .compose(BaseRequest.applyScheduler(new BaseObserver<List<NewsChannelsBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse baseResponse) {

                    }

                    @Override
                    protected void onFailed(Throwable e) {

                    }
                }));

    }
}