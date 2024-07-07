package com.neu.buryingpoint.constants;

public class RecordConstants {
    /**
     * 加盐防冲突
     */
    public static final String SALT = "0721";
    /**
     * 是否已存在的标记
     */
    public static final String RECORDSIGN = "exist chain" + SALT;
    /**
     * 不存在统一标识
     */
    public static final String NOTEXIST = "not exist" + SALT;
}
