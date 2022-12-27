package com.xytong;

import android.content.Context;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.xytong.dao.SettingDao;
import com.xytong.utils.AccessUtils;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class AccessUtilsTest {
    @Test
    public void loginTest() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SettingDao.setAccessUrl(context, "http://xytong.top/access");
        String username = "bszydxhqweqwe";
        String pwd = "1357924680";
        AccessUtils.login(context, username, pwd, new AccessUtils.StatusListener() {
            @Override
            public void onStart(Context context) {
            }

            @Override
            public void onDone(Context context) {
            }

            @Override
            public void onError(Context context, int errorFlag) {
                fail();
            }
        });
    }
}
