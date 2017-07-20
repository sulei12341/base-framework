package cn.ydhl.framework.utils;

import org.apache.commons.lang3.time.DurationFormatUtils;


public class DateDurationUtilPlus extends DurationFormatUtils {

    /**
     * 获取两个日期之间的正差距
     *
     * @param startMillis 开始日期
     * @param endMillis 结束日期
     * @param dateDurationType 返回格式
     * @return 差距
     */
    public static int formatIntPeriod(final long startMillis, final long endMillis, final DateDurationType dateDurationType) {
        String period;
        if(startMillis <= endMillis){
            period = formatPeriod(startMillis, endMillis, dateDurationType.getFormat());
            return Integer.parseInt(period);
        }else{
            //period = formatPeriod(endMillis, startMillis, dateDurationType.getFormat());
            //return Integer.parseInt("-"+period);
            return 0;
        }
    }
}
