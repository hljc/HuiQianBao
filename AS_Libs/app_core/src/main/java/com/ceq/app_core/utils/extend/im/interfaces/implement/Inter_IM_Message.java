package com.ceq.app_core.utils.extend.im.interfaces.implement;

import com.ceq.app_core.utils.extend.im.enums.Enum_ChatWay;

import java.util.List;

/**
 * Created by Administrator on 2017/3/3.
 */

public interface Inter_IM_Message {
    void sendText(String imId, Enum_ChatWay enum_chatWay, String content);

    void sendAudio(String imId, Enum_ChatWay enum_chatWay, String filePath, int audioTime);

    void sendVideo(String imId, Enum_ChatWay enum_chatWay, String videoPath, String thumbPath, int videoTime);

    void sendPicture(String imId, Enum_ChatWay enum_chatWay, String imagePath, boolean isSendOriginal);

    void sendLocation(String imId, Enum_ChatWay enum_chatWay, double longitude, double latitude, String locationRemark);

    void sendFile(String imId, Enum_ChatWay enum_chatWay, String filePath);

    void sendCustomMessage();

    List<?> getAllMessageRecordById(String imId);

    int getUnReadCountById(String imId);

    void resetUnReadCountByAppointConversation(String imId);

    void resetUnReadCountByMessageId(String imId, String messageId);

    void resetUnReadCountByAllUnReadMessage(String imId);

    int getMessageTotalCountFromMemory(String imId);

    int getMessageTotalCountFromDB(String imId);

    Object getAllConversation();

    void deleteConversationById(String imId, boolean isDeleteRecord);

    void deleteRecordByMessageId(String imId, String massageId);

    void importMessageToDB(List<?> messages);
}
