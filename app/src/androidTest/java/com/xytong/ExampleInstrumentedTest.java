package com.xytong;

import android.content.Context;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.xytong.dao.UserDao;
import com.xytong.model.dto.user.UserRequestDTO;
import com.xytong.model.dto.user.UserResponseDTO;
import com.xytong.model.vo.UserVO;
import com.xytong.utils.poster.Poster;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

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
        UserVO userVO = new UserVO();
        userVO.setName("bszydxh");
        UserDao.setUser(context, userVO);
        assertEquals("bszydxh", UserDao.getUser(context).getName());
        UserDao.setPwd(context, "1234");
        assertEquals("1234", UserDao.getPwd(context));
        UserDao.setToken(context, "1234");
        assertEquals("1234", UserDao.getToken(context));
    }

    @Test
    public void jacksonPostTest() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("bszydxh");
        Poster.jacksonPost("http://192.168.137.1/user", userRequestDTO, UserResponseDTO.class);
    }
}