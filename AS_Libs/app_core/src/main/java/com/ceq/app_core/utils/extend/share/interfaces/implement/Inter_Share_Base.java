package com.ceq.app_core.utils.extend.share.interfaces.implement;

import android.view.View;

import com.ceq.app_core.utils.extend.share.enums.Enum_PlatformName_Share;

import java.util.List;

/**
 * Created by Administrator on 2017/1/18.
 */

public interface Inter_Share_Base {
    void share(String shareContent, String shareUrl, String shareImgName);

    void share(Enum_PlatformName_Share enum_platformName_share, String shareContent, String shareUrl, String shareImgName);

    void share(Enum_PlatformName_Share enum_platformName_share, String shareTitle, String shareContent, String shareUrl, String shareImgName);
    void share(View viewToken, List<Enum_PlatformName_Share> platformNameShares, String shareTitle, String shareContent, String shareUrl, String shareImgName);

}
