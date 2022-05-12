package com.scm.tools.common;

import com.scm.tools.config.Environment;
import com.scm.tools.pojo.ScmPojo;
import com.sequoiacm.client.common.ScmType;
import com.sequoiacm.client.core.ScmConfigOption;
import com.sequoiacm.client.core.ScmFactory;
import com.sequoiacm.client.core.ScmSession;
import com.sequoiacm.client.core.ScmSessionMgr;
import com.sequoiacm.client.exception.ScmException;

import java.util.List;

/**
 * @ClassName SessionFactory
 * @Description 获取session
 * @Author yangqi
 * @Date 2022/4/27 17:06
 * @Version 1.0
 **/
public class SessionFactory {

    private static ScmSession session;

    public static ScmSession getAdminSession(ScmPojo scmPojo) throws ScmException {
        return getSession("admin",scmPojo.getAdminPwd(),scmPojo.getGateWays());
    }

    public static ScmSession getUserSession(ScmPojo scmPojo) throws ScmException{
        return getSession(scmPojo.getUserName(),scmPojo.getUserPwd(),scmPojo.getGateWays());
    }

    private static ScmSession getSession(String userName, String userPwd, List<String> urls) throws ScmException{
        ScmSessionMgr scmSessionMgr;
        ScmConfigOption option = new ScmConfigOption(urls,userName,userPwd);
        scmSessionMgr = ScmFactory.Session.createSessionMgr(option, 1*60*1000);
        session = scmSessionMgr.getSession(ScmType.SessionType.AUTH_SESSION);
        return session;
    }
}
