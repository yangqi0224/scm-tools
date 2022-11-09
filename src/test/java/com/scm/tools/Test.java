package com.scm.tools;

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
        /**
         * 工作区创建--测试代码
         */


        InitSys initSys = new InitSys();
        initSys.setScmPojo(InfoHandle.scmInfoHandle(Environment.UAT)).buildInit().beforeTask().run();


        /**
         * 清空工作区--测试代码
         */
        /*
        ScmSession session = ScmFactory.Session.createSession(ScmType.SessionType.AUTH_SESSION, new ScmConfigOption());
        ScmPojo scmPojo = InfoHandle.scmInfoHandle(Environment.UAT);
        ScmSession userSession = SessionFactory.getUserSession(scmPojo);
        ScmWorkspace workspace = ScmFactory.Workspace.getWorkspace(scmPojo.getWorkSpaceName(),userSession);
        ScmCursor<ScmFileBasicInfo> scmFileBasicInfoScmCursor = ScmFactory.File.listInstance(workspace, ScmType.ScopeType.SCOPE_ALL, new BasicBSONObject());
        while (scmFileBasicInfoScmCursor.hasNext()){
            ScmFactory.File.deleteInstance(workspace,scmFileBasicInfoScmCursor.getNext().getFileId(),true);
        }*/


        /*BasicBSONObject basicBSONObject = new BasicBSONObject();
        ScmCursor<ScmClassBasicInfo> scmClassBasicInfoScmCursor = ScmFactory.Class.listInstance(ws,basicBSONObject);

        while (scmClassBasicInfoScmCursor.hasNext()){
            System.out.println(scmClassBasicInfoScmCursor.getNext().getId().get());
        }*/
        /**
         * 功能测试--测试代码
         */


        /*
        UpgradeTest upgradeTest = new UpgradeTest();
        upgradeTest.setScmPojo(InfoHandle.scmInfoHandle(Environment.UAT));
        upgradeTest.buildUpload().beforeTask().run();
        upgradeTest.buildDownload().beforeTask().run();


        ScmSdbDataLocation scmSdbDataLocation = new ScmSdbDataLocation("ss", "ss");
        BasicBSONObject basicBSONObject = new BasicBSONObject();
        basicBSONObject.put("ReplSize",2);
        scmSdbDataLocation.setClOptions(basicBSONObject);

        BasicBSONObject obj = new BasicBSONObject();
        obj.put("LobPageSize",64*1024);
        scmSdbDataLocation.setCsOptions(obj);
        ScmSdbMetaLocation scmSdbMetaLocation = new ScmSdbMetaLocation("ss", "ss");*/

    }
}
