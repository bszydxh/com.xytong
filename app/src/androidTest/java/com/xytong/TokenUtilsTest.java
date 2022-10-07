package com.xytong;

import android.content.Context;
import android.util.Log;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.xytong.dao.SettingDao;
import com.xytong.model.dto.AccessRequestDTO;
import com.xytong.utils.AccessUtils;
import com.xytong.utils.TokenUtils;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TokenUtilsTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.xytong", appContext.getPackageName());
    }
    @Test
    public void getTokenTest() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SettingDao.setAccessUrl(context,"http://xytong.top/access");
        AccessRequestDTO accessRequestDTO =
                TokenUtils.getToken(context, "bszydxh",
                        AccessUtils.md5Salt("bszydxh", "1357924680"));
        assertNotNull(accessRequestDTO);
        assertNotNull(accessRequestDTO.getToken());
        assertNotEquals("",accessRequestDTO.getToken().trim());
        Log.i("json", accessRequestDTO.toString());
        AccessRequestDTO accessRequestDTO_illegal =
                TokenUtils.getToken(context, "bszydxh",
                        AccessUtils.md5Salt("bszydxh", "1357924680_illegal"));
        assertNotNull(accessRequestDTO_illegal);
        assertNull(accessRequestDTO_illegal.getToken());
        Log.i("json", accessRequestDTO_illegal.toString());
    }
}