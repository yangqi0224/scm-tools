package com.scm.tools.option;

import com.scm.tools.config.InfoHandle;
import com.scm.tools.config.ScmInfo;
import com.scm.tools.duty.impl.DownloadDuty;
import com.scm.tools.duty.impl.UploadDuty;
import com.scm.tools.pojo.ScmPojo;

import javax.sound.sampled.Line;

/**
 * @ClassName UpgradeTest
 * @Description scm功能验证
 * @Author yangqi
 * @Date 2022/4/27 17:04
 * @Version 1.0
 **/
public class UpgradeTest {
    public void upgradeTest(){
        ScmPojo scmPojo = InfoHandle.scmInfoHandle(ScmInfo.getExeEnv());
        UploadDuty uploadDuty = new UploadDuty(scmPojo);
        uploadDuty.isLocal(false).run();
        DownloadDuty downloadDuty = new DownloadDuty(scmPojo);
        downloadDuty.setDirVar("./").run();
    }

}
