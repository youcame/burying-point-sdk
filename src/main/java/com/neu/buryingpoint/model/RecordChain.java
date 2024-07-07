package com.neu.buryingpoint.model;

import com.neu.buryingpoint.constants.RecordConstants;
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

    private String step;

    private String traceId;

    private String processName;

    private String processDesc;

    public RecordChain(String step, String traceId, String processName, String processDesc) {
        this.step = step;
        this.traceId = traceId;
        this.processName = processName;
        this.processDesc = processDesc;
    }

    /**
     * 根据rpc链中是否存在sign来判断该链是否存在
     * @return
     */
    public boolean chainExist() {
        String value = getValueFromRpcContext(RecordConstants.RECORDSIGN).orElse(RecordConstants.NOTEXIST);
        if(RecordConstants.NOTEXIST.equals(value)) {
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

    /**
     * 如果step为纯数字，结果为数值step+1, 如5->6<br>
     * 如果step以下划线结尾，结果为step+"1",如6_->6_1<br>
     * 如果step以数字与下划线间隔(可能有多个数字多个下划线)且最后为数字，则将最后一个下划线后的数字加1，如6_1_1_1->6_1_1_2<br>
     * 如果step不以数字或下划线结尾，结果为原字符串，如6a->6a<br>
     * 如果step为负数返回UNKNOWN, 如-100->UNKNOWN<br>
     *
     * @param step
     * @return
     */
    public static String increaseStep(String step) {
        if (step == null) {
            return RecordConstants.NOTEXIST;
        }
        if (step.matches("\\d+")) {
            return String.valueOf(Integer.parseInt(step) + 1);
        }
        if (step.matches("\\d+(_\\d+)*_")) {
            return step + "1";
        }
        if (step.matches("\\d+(_\\d+)*_\\d+")) {
            String[] split = step.split("_");
            split[split.length - 1] = String.valueOf(Integer.parseInt(split[split.length - 1]) + 1);
            return String.join("_", split);
        }
        return RecordConstants.NOTEXIST;
    }



    public static void main(String[] args) {
        System.out.println(increaseStep("1"));
    }


}
