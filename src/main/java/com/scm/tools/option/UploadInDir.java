package com.scm.tools.option;

import com.scm.tools.config.InfoHandle;
import com.scm.tools.config.ScmInfo;
import com.scm.tools.duty.DutyBase;
import com.scm.tools.duty.impl.UploadDuty;
import com.scm.tools.pojo.ScmPojo;

/**
 * @ClassName UploadInDir
 * @Description 请描述类的业务用途
 * @Author yangqi
 * @Date 2022/5/16 16:27
 * @Version 1.0
 **/
public class UploadInDir {

    ScmPojo scmPojo;

    public DutyBase buildUploadInDir(){
        UploadDuty uploadDuty = new UploadDuty(scmPojo);
        if (ScmInfo.getDirNameSameToLocal()){
            uploadDuty.setFileVar(ScmInfo.getLocalDir(),ScmInfo.getScmDir());
        }
        return uploadDuty.setFileVar(ScmInfo.getLocalDir(),
                ScmInfo.getScmDir()).
                isLocal(false);
    }

    public UploadInDir setScmPojo(ScmPojo scmPojo){
        this.scmPojo = scmPojo;
        return this;
    }
}
