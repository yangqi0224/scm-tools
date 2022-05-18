package com.scm.tools.option;

import com.scm.tools.common.SessionFactory;
import com.scm.tools.config.Environment;
import com.scm.tools.config.InfoHandle;
import com.scm.tools.config.ScmInfo;
import com.scm.tools.duty.DutyBase;
import com.scm.tools.duty.impl.InitSysDuty;
import com.scm.tools.pojo.ScmPojo;

/**
 * @ClassName InitSys
 * @Description 请描述类的业务用途
 * @Author yangqi
 * @Date 2022/5/16 16:18
 * @Version 1.0
 **/
public class InitSys {

    ScmPojo scmPojo;
    public DutyBase buildInit(){
        scmPojo.setWorkSpacePojo(InfoHandle.wsInfoHandle());
        InitSysDuty initSysDuty = new InitSysDuty(scmPojo);
        return initSysDuty;
    }

    public InitSys setScmPojo(ScmPojo scmPojo){
        this.scmPojo = scmPojo;
        return this;
    }
}
