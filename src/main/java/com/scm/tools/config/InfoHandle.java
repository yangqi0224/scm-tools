package com.scm.tools.config;

import com.scm.tools.config.Environment;
import com.scm.tools.config.ScmInfo;
import com.scm.tools.pojo.ScmPojo;
import com.scm.tools.pojo.WorkSpacePojo;
import com.scm.tools.util.GenInfoForScm;
import com.sequoiacm.client.element.bizconf.*;
import com.sequoiacm.client.exception.ScmInvalidArgumentException;
import com.sun.codemodel.internal.JCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName InfoHandle
 * @Description 请描述类的业务用途
 * @Author yangqi
 * @Date 2022/5/9 16:00
 * @Version 1.0
 **/
public class InfoHandle {

    public static ScmPojo scmInfoHandle(Environment env){
        ScmPojo scmPojo = new ScmPojo();
        scmPojo.setEnvName(env);
        scmPojo.setAdminPwd(ScmInfo.getAdminPwd());
        scmPojo.setGateWays(ScmInfo.getUrlsByEnv(env));
        if (ScmInfo.getUserName() != null){
            scmPojo.setUserName(ScmInfo.getUserName());
        }else {
            scmPojo.setUserName(GenInfoForScm.generateUserName());
        }
        if (ScmInfo.getUserPwd() != null){
            scmPojo.setUserPwd(ScmInfo.getUserPwd());
        }else {
            scmPojo.setUserPwd(GenInfoForScm.generatePassword());
        }
        if (ScmInfo.getWorkspaceName() != null){
            scmPojo.setWorkSpaceName(ScmInfo.getWorkspaceName());
        }else {
            scmPojo.setWorkSpaceName(GenInfoForScm.generateWorkspaceName());
        }

        return scmPojo;
    }

    public static ScmPojo scmInfoHandle(ScmPojo scmPojo){
        if (scmPojo.getUserName() == null){
            scmPojo.setUserName(GenInfoForScm.generateUserName());
        }
        if (scmPojo.getUserPwd() == null){
            scmPojo.setUserPwd(GenInfoForScm.generatePassword());
        }
        if (scmPojo.getWorkSpaceName() == null){
            scmPojo.setWorkSpaceName(GenInfoForScm.generateWorkspaceName());
        }
        return scmPojo;
    }

    public static WorkSpacePojo wsInfoHandle(){
        WorkSpacePojo wsInfo = new WorkSpacePojo();
        wsInfo.setWorkSpaceName(ScmInfo.getWorkspaceName());

        List<String> dataSites = ScmInfo.getDataSites();
        List<String> dataDomains = ScmInfo.getDataDomains();

        try {
            ScmMetaLocation scmMetaLocation = new ScmSdbMetaLocation(ScmInfo.getMetaSite(),ScmInfo.getMetaDomain());
            List<ScmDataLocation> list = new ArrayList<>();
            List<String> domainClass = ScmInfo.getDomainClass();
            for (int i = 0; i < dataDomains.size(); i++) {
                ScmDataLocation dataLocation = null;
                switch (domainClass.get(i)){
                    case "SDB":
                        dataLocation = new ScmSdbDataLocation(dataSites.get(i),dataDomains.get(i));
                        break;
                    case "HDFS":
                        dataLocation = new ScmHdfsDataLocation(dataSites.get(i));
                        break;
                    case "HBASE":
                        dataLocation = new ScmHbaseDataLocation(dataSites.get(i));
                        break;
                    case "CEPH":
                        dataLocation = new ScmCephSwiftDataLocation(dataSites.get(i));
                        break;
                    case "CEPHS3":
                        dataLocation = new ScmCephS3DataLocation(dataSites.get(i));
                        break;
                }
                list.add(dataLocation);
            }
            wsInfo.setDataLocation(list);
            wsInfo.setMetaLocation(scmMetaLocation);
            wsInfo.setEnableDirectory(ScmInfo.isEnableDirectory());
            wsInfo.setDescription("default description");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wsInfo;
    }
}
