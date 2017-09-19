package com.ceq.app_core.java;

import com.alibaba.fastjson.JSON;
import com.ceq.app_core.annotation.Annotation_Language;
import com.ceq.app_core.constants.Constants_File$SharePreferences;
import com.ceq.app_core.constants.Constants_International;
import com.ceq.app_core.interfaces.Inter_Global_Language;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Encrypt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class Java_Language extends Constants_International implements Constants_File$SharePreferences {
    public static void main(String[] args) throws Exception {
        System.out.println("文件已生成！路径：" + useTranslateToFile(Constants_International.class, true));
    }

    private static File useTranslateToFile(Class<?> internationalObj, boolean isAS) throws Exception {
        File assetsFile = new File(new File(Java_Language.class.getSimpleName().concat(".java")).getAbsoluteFile().getParentFile(), isAS ? "/app/src/main/assets" : "/assets");
        if (!assetsFile.exists())
            assetsFile.mkdirs();
        System.out.println("正在生成：" + assetsFile);
        System.out.println("==============================================================================");
        File filePath;
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(filePath = new File(assetsFile, LANGUAGE_FILENAME)), "utf-8"), true);
        String line = "变量名";
        for (Field field : Inter_Global_Language.class.getDeclaredFields()) {
            String annotation = field.getAnnotation(Annotation_Language.class).toString();
            String language = annotation.substring(annotation.lastIndexOf("value="), annotation.lastIndexOf(")"));
            line += "\t" + language.substring(0, language.lastIndexOf("("));
        }
        pw.println(line);
        for (Field field : internationalObj.getDeclaredFields()) {
            int count = 0;
            line = "";
            String value = (String) field.get(internationalObj);
            if (field.getType().getSimpleName().equals("String") && !Util_Empty.isEmpty(value)) {
                line += field.getName() + "\t" + value;
                for (Field field2 : Inter_Global_Language.class.getDeclaredFields()) {
                    if (count++ == 0)
                        continue;
                    String annotation = field2.getAnnotation(Annotation_Language.class).toString();
                    line += "\t" + useTranslateByBaidu(
                            annotation.substring(annotation.lastIndexOf("(") + 1, annotation.indexOf(")")), value);
                }
                pw.println(line);
                System.out.println(line);
            }
        }
        pw.close();
        System.out.println("==============================================================================");
        return filePath;
    }

    private static String useTranslateByBaidu(String toLanguage, String text) throws Exception {
        String result = "";
        int salt = 1;
        String baiduTranslateUrl = "http://api.fanyi.baidu.com/api/trans/vip/translate";
        String appId = "20160406000017866", secret = "TMndJaQlTB6o6B9i6pyJ";
        String sign = appId + text + salt + secret;
        String md5 = Util_Encrypt.md5(sign, false);
        String url = baiduTranslateUrl + "?q=" + URLEncoder.encode(text, "UTF-8") + "&from=zh&to=" + toLanguage
                + "&appid=20160406000017866&salt=" + salt + "&sign=" + md5;
        HttpURLConnection huc = (HttpURLConnection) new URL(url).openConnection();
        huc.setRequestMethod("GET");
        if (huc.getResponseCode() == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream(), "utf-8"));
            result = br.readLine();
            if (result != null && result.contains("trans_result")) {
                result = JSON.parseObject(result).getJSONArray("trans_result").getJSONObject(0).getString("dst");
            }
            br.close();
        }
        return result;
    }


}
