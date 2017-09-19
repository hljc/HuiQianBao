package com.ceq.app_xinli_onekey.core.modules.enums;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/25 0025.
 */

public enum Enum_OKModule_Template implements Serializable{
    芒果钱包("MGQB"),芒果钱包2("MGQB2"), 云付("YF"), 节付宝("JFB"), 得意钱付("DYQF"), 牛码付("NMF"),
    赚道钱包("ZDQB"), 赚道钱包2("ZDQB2"), 和易付("HYF"), 星星钱包("XXQB"), 星星钱包2("XXQB2"), 信掌柜("XZG"),
    元百百("YBB");
    public String template;

    Enum_OKModule_Template(String template) {
        this.template = template;
    }

}
