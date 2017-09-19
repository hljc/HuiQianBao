package com.ceq.app_core.utils.extend.im.interfaces.implement;

import java.util.List;

/**
 * Created by Administrator on 2017/3/3.
 */

public interface Inter_IM_Friends {
    List<?> getFriendList();

    void addFriend(String imId, String addReason);

    void deleteFriend(String imId);

    void acceptFriend(String imId);

    void refuseFriend(String imId);

    List<?> getBlackListFromServer();

    List<?> getBlackListFromDB();

    void addFromBlackList(String imId);

    void removeFromBlackList(String imId);

}
