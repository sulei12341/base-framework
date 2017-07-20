package cn.ydhl.framework.utils;


public enum DateDurationType {
    YEAR("y"),

    MONTH("M"),

    DAY("d"),

    HOUR("H"),

    MINUTE("m"),

    SECOND("s"),

    MILLISECOND("S");

    private String format;

    DateDurationType(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
