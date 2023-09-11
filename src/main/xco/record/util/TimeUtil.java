package xco.record.util;

import java.util.Date;

public class TimeUtil {
    private TimeUtil() {}
    
    public static String getTimeInterval(long startTime) {
        String str = null;
        try {
            long l = new Date().getTime() - startTime;
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            str = "" + day + "天" + hour + "小时" + min + "分" + s + "秒";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
