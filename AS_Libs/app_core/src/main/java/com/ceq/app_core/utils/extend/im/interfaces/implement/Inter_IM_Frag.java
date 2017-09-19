package com.ceq.app_core.utils.extend.im.interfaces.implement;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2017/3/3.
 */

public interface Inter_IM_Frag {
    Fragment getChatFragment(String imId);

    Fragment getConversationListFragment();

    Fragment getContactListListFragment();
}
