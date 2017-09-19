package com.ceq.app_xinli_onekey.core.props.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public class Bean_OneKeyProps implements Serializable {
    Bean_OKProp_Module bean_okProp_module =new Bean_OKProp_Module();
    Bean_OKProp_Oem bean_okProp_oem =new Bean_OKProp_Oem();
    Bean_OKProp_Share bean_okProp_share =new Bean_OKProp_Share();

    public Bean_OKProp_Module getBean_okProp_module() {
        return bean_okProp_module;
    }

    public Bean_OKProp_Oem getBean_okProp_oem() {
        return bean_okProp_oem;
    }

    public Bean_OKProp_Share getBean_okProp_share() {
        return bean_okProp_share;
    }
}
