package com.xytong;

import android.content.Context;
import android.util.Log;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.xytong.model.dto.access.AccessCheckResponseDTO;
import com.xytong.utils.AccessUtils;
import com.xytong.utils.LogUtils;
import com.xytong.utils.TokenDownloader;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TokenDownloaderTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.xytong", appContext.getPackageName());
    }
    @Test
    public void getTokenTest() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AccessCheckResponseDTO accessCheckResponseDTO =
                TokenDownloader.getTokenFromServer(context, "bszydxh",
                        AccessUtils.md5Salt("bszydxh", "1357924680"));
        assertNotNull(accessCheckResponseDTO);
        assertNotNull(accessCheckResponseDTO.getToken());
        assertNotEquals("", accessCheckResponseDTO.getToken().trim());
        Log.i(LogUtils.getLogTag(), accessCheckResponseDTO.toString());
        AccessCheckResponseDTO accessCheckResponseDTO_illegal =
                TokenDownloader.getTokenFromServer(context, "bszydxh",
                        AccessUtils.md5Salt("bszydxh", "1357924680_illegal"));
        assertNotNull(accessCheckResponseDTO_illegal);
        assertNull(accessCheckResponseDTO_illegal.getToken());
        Log.i(LogUtils.getLogTag(), accessCheckResponseDTO_illegal.toString());
    }
}