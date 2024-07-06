package com.neu.buryingpoint.model;

import com.neu.buryingpoint.constants.Constants;
import com.neu.buryingpoint.process.ArgProcessor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Data
@Slf4j
public class RecordChain {
    Logger logger = LoggerFactory.getLogger(RecordChain.class);

    /**
     * 根据rpc链中是否存在sign来判断该链是否存在
     * @return
     */
    public boolean chainExist() {
        String value = getValueFromRpcContext(Constants.RECORDSIGN).orElse(Constants.NOTEXIST);
        if(Constants.NOTEXIST.equals(value)) {
            return true;
        }
        return false;
    }

    /**
     * 从rpc上下文中获取到标记值
     * @return
     */
    public static Optional<String> getValueFromRpcContext(String key) {
        String value = RpcContext.getServiceContext().getAttachment(key);
        return Optional.ofNullable(value);
    }


}
