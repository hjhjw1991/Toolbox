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

    @Test
    public void xingjiabi_sort_isCorrect() throws Exception {
        XingjiabiActivity activity = new XingjiabiActivity();
        float[] price = {99, 100, 10, 0};
        int[] amount = {5, 10, -5, 1};
        float[] expect = {Integer.MAX_VALUE>>4, 10/100f, 5/99f, 0};
        for(int i=0;i<price.length;i++){
            XingjiabiActivity.Good good = activity.new Good("test Good"+i);
            good.setPrice(price[i]).setAmount(amount[i]).commit();
            activity.add(good);
        }
        for(int i=0;i<activity.getGoods().size();i++){
            XingjiabiActivity.Good g = activity.getGoods().get(i);
            assertThat(g.xingjiabi, is(equalTo(expect[i])));
            System.out.println("Good:" + g.name + " xingjiabi:" + g.xingjiabi);
        }
    }
}