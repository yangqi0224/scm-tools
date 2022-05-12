package com.scm.tools.duty.impl;

import com.scm.tools.common.SessionFactory;
import com.scm.tools.config.InfoHandle;
import com.scm.tools.config.ScmInfo;
import com.scm.tools.duty.DutyBase;
import com.scm.tools.pojo.ScmPojo;
import com.scm.tools.pojo.WorkSpacePojo;
import com.sequoiacm.client.core.*;
import com.sequoiacm.client.element.bizconf.ScmDataLocation;
import com.sequoiacm.client.element.bizconf.ScmSdbDataLocation;
import com.sequoiacm.client.element.bizconf.ScmSdbMetaLocation;
import com.sequoiacm.client.element.bizconf.ScmWorkspaceConf;
import com.sequoiacm.client.element.privilege.ScmPrivilegeType;
import com.sequoiacm.client.element.privilege.ScmResourceFactory;
import com.sequoiacm.client.exception.ScmException;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName InitSysDuty
 * @Description 为新系统创建工作区、用户、角色。
 * @Author yangqi
 * @Date 2022/5/9 15:14
 * @Version 1.0
 **/
public class InitSysDuty implements DutyBase {

    private ScmPojo scmPojo;
    private ScmSession scmSession;
    private int STATUS = 0;

    public InitSysDuty(ScmPojo scmPojo) {
        this.scmPojo = scmPojo;
    }

    @Override
    public Object runTask() {
        System.out.println("开始初始化。。。。");
        try {
            if (!createWorkSpace()) {
                fallBack();
                return false;
            }
            STATUS++;
            System.out.println("工作区创建成功");
            if (!createUser()){
                fallBack();
                return false;
            }
            STATUS++;
            System.out.println("工作区用户创建成功");
            if (!createRole()){
                fallBack();
                return false;
            }
            STATUS++;
            System.out.println("工作区角色创建成功");

            if (!grantPrivileges()){
                fallBack();
                return false;
            }
            System.out.println("工作区用户授权创建成功");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            STATUS = 0;
        }
        return true;
    }

    private boolean grantPrivileges() {
        try {
            ScmFactory.User.alterUser(scmSession,ScmFactory.User.getUser(scmSession,scmPojo.getUserName()),new ScmUserModifier().
                    addRole(ScmFactory.Role.getRole(scmSession,"ROLE_ADMINS_"+scmPojo.getWorkSpaceName())));
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
        return true;
    }

    private boolean createWorkSpace() {

        try {
            WorkSpacePojo workSpacePojo = InfoHandle.wsInfoHandle();
            ScmFactory.Workspace.createWorkspace(scmSession, workSpacePojo.buildWsConf());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean createRole() {
        String WS_NAME = scmPojo.getWorkSpaceName();
        try {

            ScmRole admins = ScmFactory.Role.createRole(scmSession, "ADMINS_" + WS_NAME, "管理员角色");
            ScmFactory.Role.grantPrivilege(scmSession,admins, ScmResourceFactory.createWorkspaceResource(WS_NAME), ScmPrivilegeType.ALL);
            ScmRole users = ScmFactory.Role.createRole(scmSession,"USERS_"+WS_NAME,"业务用户角色");
            ScmFactory.Role.grantPrivilege(scmSession,users, ScmResourceFactory.createWorkspaceResource(WS_NAME), ScmPrivilegeType.READ);
            ScmFactory.Role.grantPrivilege(scmSession,users, ScmResourceFactory.createWorkspaceResource(WS_NAME), ScmPrivilegeType.CREATE);
            ScmFactory.Role.grantPrivilege(scmSession,users, ScmResourceFactory.createWorkspaceResource(WS_NAME), ScmPrivilegeType.UPDATE);
            ScmRole connects = ScmFactory.Role.createRole(scmSession,"CONNECTS_"+WS_NAME,"连接用户角色");
            ScmFactory.Role.grantPrivilege(scmSession,connects, ScmResourceFactory.createWorkspaceResource(WS_NAME), ScmPrivilegeType.READ);
            ScmFactory.Role.grantPrivilege(scmSession,connects, ScmResourceFactory.createWorkspaceResource(WS_NAME), ScmPrivilegeType.CREATE);
            ScmFactory.Role.grantPrivilege(scmSession,connects, ScmResourceFactory.createWorkspaceResource(WS_NAME), ScmPrivilegeType.UPDATE);
            ScmFactory.Role.grantPrivilege(scmSession,connects, ScmResourceFactory.createWorkspaceResource(WS_NAME), ScmPrivilegeType.DELETE);
            ScmRole visitors = ScmFactory.Role.createRole(scmSession,"VISITORS_"+WS_NAME,"临时用户角色");
            ScmFactory.Role.grantPrivilege(scmSession,visitors, ScmResourceFactory.createWorkspaceResource(WS_NAME), ScmPrivilegeType.READ);
        } catch (Exception e) {

            e.printStackTrace();
            return false;

        }
        return true;
    }

    private boolean createUser() {
        ScmUser user = null;
        try {
            user = ScmFactory.User.createUser(scmSession,scmPojo.getUserName(), ScmUserPasswordType.LOCAL,scmPojo.getUserPwd());
        } catch (ScmException e) {
            e.printStackTrace();
        }
        return user!=null;
    }

    @Override
    public DutyBase beforeTask() throws Exception {
        this.scmPojo = InfoHandle.scmInfoHandle(scmPojo);
        this.scmSession = SessionFactory.getAdminSession(scmPojo);
        return this;
    }

    @Override
    public Object afterTask() {
        scmSession.close();
        return null;
    }


    private  void fallBack() throws ScmException {
        if (STATUS == 3){
            fallBackCreateRole();
            fallBackCreateUser();
            fallBackCreateWS();
        }
        if (STATUS == 2){
            fallBackCreateUser();
            fallBackCreateWS();
        }
        if (STATUS == 1){
            fallBackCreateWS();
        }
    }

    private  void fallBackCreateUser() throws ScmException {
        ScmFactory.User.deleteUser(scmSession,scmPojo.getUserName());

    }

    private  void fallBackCreateWS() throws ScmException{
        ScmFactory.Workspace.deleteWorkspace(scmSession,scmPojo.getWorkSpaceName());
    }

    private void fallBackCreateRole() throws ScmException {
        ScmFactory.Role.deleteRole(scmSession,"ADMINS_" + scmPojo.getWorkSpaceName());
        ScmFactory.Role.deleteRole(scmSession,"USERS_" + scmPojo.getWorkSpaceName());
        ScmFactory.Role.deleteRole(scmSession,"CONNECTS_" + scmPojo.getWorkSpaceName());
        ScmFactory.Role.deleteRole(scmSession,"VISITORS_" + scmPojo.getWorkSpaceName());
    }

    @Override
    public void run() {

        try {
            runTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
