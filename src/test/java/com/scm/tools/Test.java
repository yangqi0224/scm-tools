package com.scm.tools;

import com.scm.tools.common.SessionFactory;
import com.scm.tools.config.Environment;
import com.scm.tools.config.InfoHandle;
import com.scm.tools.option.InitSys;

/**
 * @ClassName Test
 * @Description 请描述类的业务用途
 * @Author yangqi
 * @Date 2022/6/8 11:44
 * @Version 1.0
 **/
public class Test {
    public static void main(String[] args) throws Exception {
        InitSys initSys = new InitSys();
        initSys.setScmPojo(InfoHandle.scmInfoHandle(Environment.UAT)).buildInit().beforeTask().run();
    }
}
