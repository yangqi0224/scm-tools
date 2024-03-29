package com.scm.tools.option;

import com.scm.tools.duty.DutyBase;
import com.scm.tools.duty.impl.DownloadDuty;
import com.scm.tools.duty.impl.UploadDuty;
import com.scm.tools.pojo.ScmPojo;

/**
 * @ClassName UpgradeTest
 * @Description scm功能验证
 * @Author yangqi
 * @Date 2022/4/27 17:04
 * @Version 1.0
 **/
public class UpgradeTest {

    ScmPojo scmPojo;


    public DutyBase buildUpload(){
        UploadDuty uploadDuty = new UploadDuty(scmPojo);
        return uploadDuty.isLocal(true);
    }

    public DutyBase buildDownload(){
        DownloadDuty downloadDuty = new DownloadDuty(scmPojo);
        return downloadDuty.setDirVar("/");
    }

    public UpgradeTest setScmPojo(ScmPojo scmPojo){
        this.scmPojo = scmPojo;
        return this;
    }
}
