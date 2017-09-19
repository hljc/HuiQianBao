package com.ceq.app_xinli_onekey.core.bootstrap.interfaces;

import com.ceq.app.core.bean.Bean_WelcomeOptions;
import com.ceq.app_core.bean.Bean_Banner;
import com.ceq.app_xinli_onekey.core.modules.abstracts.Abstract_OkModule_Fragment;
import com.ceq.app_xinli_onekey.core.props.beans.Bean_OKProp_Module;
import com.ceq.app_xinli_onekey.core.props.beans.Bean_OKProp_Oem;
import com.ceq.app_xinli_onekey.core.props.beans.Bean_OKProp_Share;

import java.util.List;

/**
 * Created by Administrator on 2017/8/25 0025.
 */

public interface Inter_OneKeyBootstrap {
    void initOneKeyProps(Bean_OKProp_Oem oem, Bean_OKProp_Module module, Bean_OKProp_Share share);

    void initOneKeyModules(List<Abstract_OkModule_Fragment> fragmentList);

    void initOneKeyWelcome(List<Bean_Banner> bannerList, Bean_WelcomeOptions welcomeOptions);
}
