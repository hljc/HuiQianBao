package com.ceq.app_core.utils.extend.im.interfaces.callback;

import java.util.List;

/**
 * Created by Administrator on 2017/1/14.
 */

public interface Inter_IM_Listener {
     interface ConnectStatusListener{
        void accountRemove();

        void accountOtherDeviceLogin();
    }
     interface MessageReciverListener{
         void onMessageReceived(List<?> messages);
    }
     interface MessageStatusListener{
         void onMessageStatus(List<?> messages);
    }

}