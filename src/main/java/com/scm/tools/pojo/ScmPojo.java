package com.scm.tools.pojo;

import com.scm.tools.config.Environment;
import com.scm.tools.config.SystemLevel;

import java.util.List;

/**
 * @ClassName ScmPojo
 * @Description SCM信息抽象
 * @Author yangqi
 * @Date 2022/5/5 14:59
 * @Version 1.0
 **/
public class ScmPojo {
    private String adminPwd;
    private String userName;
    private String userPwd;
    private Environment envName;
    private List<String> gateWays;
    private SystemLevel sysLevel;
    private String workSpaceName;

    public WorkSpacePojo getWorkSpacePojo() {
        return workSpacePojo;
    }

    public void setWorkSpacePojo(WorkSpacePojo workSpacePojo) {
        this.workSpacePojo = workSpacePojo;
    }

    private WorkSpacePojo workSpacePojo;

    public String getWorkSpaceName() {
        return workSpaceName;
    }

    public void setWorkSpaceName(String workSpaceName) {
        this.workSpaceName = workSpaceName;
    }

    public String getAdminPwd() {
        return adminPwd;
    }

    public void setAdminPwd(String adminPwd) {
        this.adminPwd = adminPwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public Environment getEnvName() {
        return envName;
    }

    public void setEnvName(Environment envName) {
        this.envName = envName;
    }

    public List<String> getGateWays() {
        return gateWays;
    }

    public void setGateWays(List<String> gateWays) {
        this.gateWays = gateWays;
    }

    public SystemLevel getSysLevel() {
        return sysLevel;
    }

    public void setSysLevel(SystemLevel sysLevel) {
        this.sysLevel = sysLevel;
    }
}
