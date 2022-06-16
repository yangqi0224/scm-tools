package com.scm.tools;

import com.scm.tools.common.SessionFactory;
import com.scm.tools.config.Environment;
import com.scm.tools.config.InfoHandle;
import com.scm.tools.config.ScmInfo;
import com.scm.tools.option.InitSys;
import com.scm.tools.option.UpgradeTest;
import com.scm.tools.pojo.ScmPojo;
import com.sequoiacm.client.common.ScmType;
import com.sequoiacm.client.core.ScmCursor;
import com.sequoiacm.client.core.ScmFactory;
import com.sequoiacm.client.core.ScmSession;
import com.sequoiacm.client.core.ScmWorkspace;
import com.sequoiacm.client.element.ScmFileBasicInfo;
import org.bson.BasicBSONObject;

import java.nio.channels.SelectionKey;

/**
 * @ClassName Test
 * @Description 请描述类的业务用途
 * @Author yangqi
 * @Date 2022/6/8 11:44
 * @Version 1.0
 **/
public class Test {
    public static void main(String[] args) throws Exception {
        /**
         * 工作区创建--测试代码
         */
        /*InitSys initSys = new InitSys();
        initSys.setScmPojo(InfoHandle.scmInfoHandle(Environment.UAT)).buildInit().beforeTask().run();*/


        /**
         * 清空工作区--测试代码
         */
        /*ScmPojo scmPojo = InfoHandle.scmInfoHandle(Environment.UAT);
        ScmSession userSession = SessionFactory.getUserSession(scmPojo);
        ScmWorkspace workspace = ScmFactory.Workspace.getWorkspace(scmPojo.getWorkSpaceName(),userSession);
        ScmCursor<ScmFileBasicInfo> scmFileBasicInfoScmCursor = ScmFactory.File.listInstance(workspace, ScmType.ScopeType.SCOPE_ALL, new BasicBSONObject());
        while (scmFileBasicInfoScmCursor.hasNext()){
            ScmFactory.File.deleteInstance(workspace,scmFileBasicInfoScmCursor.getNext().getFileId(),true);
        }*/


        /**
         * 功能测试--测试代码
         */
        UpgradeTest upgradeTest = new UpgradeTest();
        upgradeTest.setScmPojo(InfoHandle.scmInfoHandle(Environment.UAT));
        upgradeTest.buildUpload().beforeTask().run();
        upgradeTest.buildDownload().beforeTask().run();
    }
}
