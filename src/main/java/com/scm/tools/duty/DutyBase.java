package com.scm.tools.duty;

import com.sequoiacm.client.exception.ScmException;

/**
 * @ClassName DutyBase
 * @Description 任务类统一接口
 * @Author yangqi
 * @Date 2022/5/9 13:52
 * @Version 1.0
 **/
public interface DutyBase extends Runnable {
    /**
     * 统一接口
     * @param
     * @return
     */
    Object runTask();

    /**
     * 前置准备
     * @param objects
     * @throws Exception
     * @return
     */
    DutyBase beforeTask() throws Exception;

    /**
     * 后置处理
     * @param objects
     * @return
     */
    Object afterTask();
}
