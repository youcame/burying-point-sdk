package com.neu.buryingpoint;

import lombok.Data;

import java.util.Date;

@Data
public class RecordParams {
    private Long id;
    /**
     * 系统唯一id
     */
    private String sysId;
    /**
     * 请求的业务来源
     */
    private String source;
    /**
     * 系统描述
     */
    private String sysDescription;
    /**
     * 同一条调用链上的id相同，随机生成
     */
    private String recordId;
    /**
     * 操作人
     */
    private String pin;
    /**
     * 记录id
     */
    private String bid;
    /**
     * 操作描述
     */
    private String description;
    /**
     * 操作时间
     */
    private Date operateTime;

}
