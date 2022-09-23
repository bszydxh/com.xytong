package com.xytong;

import android.content.Context;
import android.util.Log;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.xytong.model.dto.AccessRequestDTO;
import com.xytong.utils.Access;
import com.xytong.utils.DataChecker;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DataCheckerTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.xytong", appContext.getPackageName());
    }

    @Test
    public void getTokenTest() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AccessRequestDTO accessRequestDTO =
                DataChecker.getToken(context, "bszydxh",
                        Access.md5Salt("bszydxh", "1357924680"));
        Log.e("json", accessRequestDTO.toString());
        assertNotNull(accessRequestDTO);
    }
}