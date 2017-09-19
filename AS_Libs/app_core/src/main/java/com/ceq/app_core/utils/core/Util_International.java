package com.ceq.app_core.utils.core;


import com.ceq.app_core.constants.Constants_File$SharePreferences;
import com.ceq.app_core.constants.Constants_International;
import com.ceq.app_core.framework.Framework_App;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/23.
 */
public class Util_International implements Constants_File$SharePreferences {
    public static void language(final int inter_global_language) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Framework_App.getInstance().getAssets().open(LANGUAGE_FILENAME), "utf-8"));
            String line = null;
            int x = 0;
            while ((line = br.readLine()) != null) {
                if (x++ == 0)
                    continue;
                String[] text = line.split("\t");
                Constants_International.class.getDeclaredField(text[0]).set(Constants_International.class, text[inter_global_language]);
            }
            br.close();
            Util_SharePreference.spToSet(SP_LANGUAGE_FILE_NAME, new Util_SharePreference.InitSP() {

                @Override
                public HashMap<String, Object> initParameters(HashMap<String, Object> hm_values) {
                    hm_values.put(SP_LANGUAGE_KEY_ID, inter_global_language);
                    return hm_values;
                }
            }, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
