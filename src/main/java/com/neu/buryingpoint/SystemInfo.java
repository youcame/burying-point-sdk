package com.neu.buryingpoint;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SystemInfo {
    /**
     * 发送埋点系统的id
     */
    String sysId;

    /**
     * 发送埋点系统的token
     */
    String token;

    /**
     * 发送埋点系统的描述
     */
    String sysDescription;

    public SystemInfo(String sysId, String token, String sysDescription) {
        this.sysId = sysId;
        this.token = token;
        this.sysDescription = sysDescription;
    }
}
