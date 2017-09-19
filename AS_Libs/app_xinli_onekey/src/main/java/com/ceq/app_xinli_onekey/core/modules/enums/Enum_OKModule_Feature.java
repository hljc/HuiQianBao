package com.ceq.app_xinli_onekey.core.modules.enums;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/25 0025.
 */

public enum Enum_OKModule_Feature implements Serializable{
    收款("Collection"), 钱包("Purse"), 分享("Share"), 我的("Mine"), 升级("Upgrade"), 收益("Income");
    public String feature;

    Enum_OKModule_Feature(String feature) {
        this.feature = feature;
    }

}

