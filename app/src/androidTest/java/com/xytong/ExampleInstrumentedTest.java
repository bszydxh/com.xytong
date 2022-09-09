package com.xytong;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.xytong.data.sharedPreferences.UserSP;
import com.xytong.data.UserData;

import org.junit.Test;
import org.junit.runner.RunWith;

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
        assertEquals("com.xytong", appContext.getPackageName());
    }

    @Test
    public void UserSPTest() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserData userData = new UserData();
        userData.setName("bszydxh");
        UserSP.setUser(context, userData);
        assertEquals("bszydxh", UserSP.getUser(context).getName());
        UserSP.setPwd(context, "1234");
        assertEquals("1234", UserSP.getPwd(context));
        UserSP.setToken(context, "1234");
        assertEquals("1234", UserSP.getToken(context));
    }
}