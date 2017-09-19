package com.ceq.app_core.utils.core;
/**
 * Created by Administrator on 2016/9/25.
 */
public class Util_Authorization {
    public static void initAuthorization(long expireTime) {
        if (expireTime == -1)
            return;
        if (expireTime< System.currentTimeMillis()) {
            System.exit(0);
        }
      /*  File authorization = new File(Environment.getExternalStorageDirectory(), "authorize");
        try {
            if (!authorization.exists()) {
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(authorization), "utf-8"), true);
                pw.println(expireTime);
                pw.close();
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(authorization), "utf-8"));
                String line=br.readLine();
                br.close();
                if (Long.parseLong(line) < System.currentTimeMillis()) {
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Util_Log.logE(e);
        }*/
    }
}
