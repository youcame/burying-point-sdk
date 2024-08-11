package com.neu.buryingpoint.util;

import com.neu.buryingpoint.model.SystemConfig;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@NoArgsConstructor
public class SysArgsUtil {

    @Resource
    private SystemConfig systemConfig;

    public void getArgsFromStarter() {

    }

}
