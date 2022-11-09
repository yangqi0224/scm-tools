package com.scm.tools;

import com.scm.tools.common.SessionFactory;
import com.scm.tools.config.Environment;
import com.scm.tools.config.InfoHandle;
import com.scm.tools.pojo.ScmPojo;
import com.sequoiacm.client.core.*;
import com.sequoiacm.client.element.ScmId;
import com.sequoiacm.client.element.bizconf.ScmDataLocation;
import com.sequoiacm.client.element.bizconf.ScmSdbDataLocation;
import com.sequoiacm.client.element.bizconf.ScmSdbMetaLocation;
import com.sequoiacm.client.element.bizconf.ScmWorkspaceConf;
import com.sequoiacm.client.exception.ScmException;
import com.sequoiacm.common.ScmShardingType;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * @ClassName BatchTest
 * @Description 请描述类的业务用途
 * @Author yangqi
 * @Date 2022/11/9 14:32
 * @Version 1.0
 **/
public class BatchTest {
    public static void main(String[] args) throws ScmException {
        ScmPojo scmPojo = InfoHandle.scmInfoHandle(Environment.UAT);
        ScmSession userSession = SessionFactory.getUserSession(scmPojo);
        ScmWorkspace workspace = ScmFactory.Workspace.getWorkspace(scmPojo.getWorkSpaceName(),userSession);

        ScmFile scmFile = ScmFactory.File.createInstance(workspace);
        scmFile.setFileName("20221109");
        scmFile.setContent(new ByteArrayInputStream(new byte[1024]));

        ScmBatch scmBatch = ScmFactory.Batch.createInstance(workspace);
        scmBatch.setName("testBatch");
        scmBatch.addTag("百骑爱你");
        scmBatch.save();
        ScmId save = scmFile.save();
        scmBatch.attachFile(save);
        System.out.println("关联成功");



    }

    public static ScmWorkspace checkWorkSpace(String wsName, ScmSession session) throws ScmException {

        ScmWorkspace workspace = null;

        try {
            workspace = ScmFactory.Workspace.getWorkspace(wsName,session);
        }catch (ScmException e){
            ScmWorkspaceConf conf = new ScmWorkspaceConf();
            conf.setBatchShardingType(ScmShardingType.MONTH);
            ScmSdbDataLocation scmSdbDataLocation = new ScmSdbDataLocation("rootsite","SCM_SYS_DATA_DOMAIN");
            ArrayList<ScmDataLocation> objects = new ArrayList<>();
            objects.add(scmSdbDataLocation);
            conf.setDataLocations(objects)
                    .setMetaLocation(new ScmSdbMetaLocation("rootsite","SCM_SYS_META_DOMAIN"))
                    .setName(wsName);
            ScmFactory.Workspace.createWorkspace(session,conf);
        }

        return workspace;
    }
}
