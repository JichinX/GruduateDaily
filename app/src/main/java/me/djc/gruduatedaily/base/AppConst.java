package me.djc.gruduatedaily.base;

/**
 * Des:GruduateDaily - me.djc.gruduatedaily.base
 *
 * @author xujichang
 * @date 2019-04-18 - 17:03
 * <p>
 * modify:
 */
public class AppConst {
    public static class OrderType {

        public static final int IMPORTANT = 1;
        public static final int URGENT = 2;
        public static final int IMPORTANT_URGENT = 3;
        public static final int NONE = 4;
    }

    public static final class Analysistype {
        public static final int TAG_DAY = 1;
        public static final int TAG_MONTH = 2;
        public static final int TAG_YEAY = 3;
        public static final int TAG_WEEK = 5;
    }

    public static final class Value {
        public static final String ANALYSIS_TYPE = "analysis_type";
        public static final String ORDER_TYPE = "order_type";
        public static final String DAY_TYPE = "day_type";
        public static final String DAY_TIME_MS = "day_time_ms";
    }
}
