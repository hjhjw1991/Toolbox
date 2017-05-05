package net.hjhjw1991.toolbox;

import android.content.Context;

import net.hjhjw1991.toolbox.ui.XingjiabiActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(MockitoJUnitRunner.class)
public class JUnitTest {
    @Mock
    Context mMockContext; // mock the android context

    @Test
    public void xingjiabi_set_isCorrect() throws Exception {
        XingjiabiActivity activity = new XingjiabiActivity();
        XingjiabiActivity.Good good = activity.new Good("test Good");
        good.setPrice(100).setAmount(10).commit();
        assertThat(Math.abs(good.xingjiabi - 10/100f), is(equalTo(0f)));
        System.out.println("Good:"+good.name + " xingjiabi:" + good.xingjiabi);
    }
}