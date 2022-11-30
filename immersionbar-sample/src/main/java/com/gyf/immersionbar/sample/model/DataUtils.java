package com.gyf.immersionbar.sample.model;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.gyf.immersionbar.sample.R;
import com.gyf.immersionbar.sample.bean.FunBean;

import java.util.ArrayList;

/**
 * @author geyifeng
 * @date 2019/4/19 3:19 PM
 */
public class DataUtils {
    public static ArrayList<FunBean> getMainData(Context context) {
        ArrayList<FunBean> funBeans = new ArrayList<>();
        funBeans.add(new FunBean(context.getString(R.string.text_params) + "[展示OK]", ContextCompat.getDrawable(context, R.mipmap.icon_1)));
        funBeans.add(new FunBean(context.getString(R.string.text_params_kotlin) + "[不再展示]", ContextCompat.getDrawable(context, R.mipmap.icon_2)));
        funBeans.add(new FunBean(context.getString(R.string.text_pic_color) + "[展示OK]", ContextCompat.getDrawable(context, R.mipmap.icon_3)));
        funBeans.add(new FunBean(context.getString(R.string.text_pic) + "[展示OK]", ContextCompat.getDrawable(context, R.mipmap.icon_4)));
        funBeans.add(new FunBean(context.getString(R.string.text_color) + "[不再展示]", ContextCompat.getDrawable(context, R.mipmap.icon_5)));
        funBeans.add(new FunBean(context.getString(R.string.text_shape) + "[展示OK]", ContextCompat.getDrawable(context, R.mipmap.icon_6)));
        funBeans.add(new FunBean(context.getString(R.string.text_swipe_back) + "[展示OK]", ContextCompat.getDrawable(context, R.mipmap.icon_7)));
        funBeans.add(new FunBean(context.getString(R.string.text_fragment) + "[请参考navigation-edge-sample工程适配单Activity多Fragment]", ContextCompat.getDrawable(context, R.mipmap.icon_8)));
        funBeans.add(new FunBean(context.getString(R.string.text_dialog) + "[此框架暂未特别提供Dialog的适配， 不是特别懂这方面需求; 可参考XPopup对复杂（全屏）Dialog的适配]", ContextCompat.getDrawable(context, R.mipmap.icon_9)));
        funBeans.add(new FunBean(context.getString(R.string.text_popup) + "[注意：PopupWindow和Dialog不同，前者是针对Activity的window 后者是Dialog本身的window, 因此使用PopupWindow不需要另外设置Window本身的属性 只需要适配PopupWindow内部的Padding和Margin等； 考虑到这个需求场景少、我不熟悉，这里未进行代码演示]", ContextCompat.getDrawable(context, R.mipmap.icon_10)));
        funBeans.add(new FunBean(context.getString(R.string.text_drawer), ContextCompat.getDrawable(context, R.mipmap.icon_11)));
        funBeans.add(new FunBean(context.getString(R.string.text_coordinator) + "[动态布局有点麻烦、 还没搞定]", ContextCompat.getDrawable(context, R.mipmap.icon_12)));
        funBeans.add(new FunBean(context.getString(R.string.text_tab) + "[展示OK]", ContextCompat.getDrawable(context, R.mipmap.icon_13)));
        funBeans.add(new FunBean(context.getString(R.string.text_tab_two) + "[结合CoordinatorLayout使用有点复杂、还未搞定]", ContextCompat.getDrawable(context, R.mipmap.icon_14)));
        funBeans.add(new FunBean(context.getString(R.string.text_web), ContextCompat.getDrawable(context, R.mipmap.icon_15)));
        funBeans.add(new FunBean(context.getString(R.string.text_action_bar), ContextCompat.getDrawable(context, R.mipmap.icon_16)));
        funBeans.add(new FunBean(context.getString(R.string.text_flyme), ContextCompat.getDrawable(context, R.mipmap.icon_17)));
        funBeans.add(new FunBean(context.getString(R.string.text_over) + "[存在方案，不再展示]", ContextCompat.getDrawable(context, R.mipmap.icon_18)));
        funBeans.add(new FunBean(context.getString(R.string.text_key_board), ContextCompat.getDrawable(context, R.mipmap.icon_19)));
        funBeans.add(new FunBean(context.getString(R.string.text_all_edit), ContextCompat.getDrawable(context, R.mipmap.icon_20)));
        funBeans.add(new FunBean(context.getString(R.string.text_login) + "[展示OK]", ContextCompat.getDrawable(context, R.mipmap.icon_21)));
        funBeans.add(new FunBean(context.getString(R.string.text_white_bar) + "[使用状态栏前景色API，不再展示]", ContextCompat.getDrawable(context, R.mipmap.icon_22)));
        funBeans.add(new FunBean(context.getString(R.string.text_auto_status_font) + "[存在调节颜色API，但是并未实现自动计算颜色值，不再展示]", ContextCompat.getDrawable(context, R.mipmap.icon_23)));
        funBeans.add(new FunBean(context.getString(R.string.text_status_hide) + "[已存在方案，不再展示]", ContextCompat.getDrawable(context, R.mipmap.icon_24)));
        funBeans.add(new FunBean(context.getString(R.string.text_navigation_hide) + "[已存在方案，不再展示]", ContextCompat.getDrawable(context, R.mipmap.icon_25)));
        funBeans.add(new FunBean(context.getString(R.string.text_bar_hide) + "[已存在方案，不再展示]", ContextCompat.getDrawable(context, R.mipmap.icon_26)));
        funBeans.add(new FunBean(context.getString(R.string.text_bar_show) + "[已存在方案，不再展示]", ContextCompat.getDrawable(context, R.mipmap.icon_27)));
        funBeans.add(new FunBean(context.getString(R.string.text_full) + "[已存在方案，不再展示]", ContextCompat.getDrawable(context, R.mipmap.icon_28)));
        funBeans.add(new FunBean(context.getString(R.string.text_bar_font_dark) + "[已存在方案，不再展示]", ContextCompat.getDrawable(context, R.mipmap.icon_29)));
        funBeans.add(new FunBean(context.getString(R.string.text_bar_font_light) + "[已存在方案，不再展示]", ContextCompat.getDrawable(context, R.mipmap.icon_30)));
        funBeans.add(new FunBean("仿微信键盘动画[展示OK]", ContextCompat.getDrawable(context, R.mipmap.icon_30)));


        return funBeans;
    }
}
