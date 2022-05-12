package com.scm.tools.config;

/**
 * @author yangqi
 *
 * 环境枚举
 */

public enum Environment {

    DEV("开发环境"),SIT("sit环境"),UAT("uat环境"),TST("准生产环境"),
    XC_DEV("XC_开发环境"),XC_SIT("XC_sit环境"),XC_UAT("XC_uat环境"),XC_TST("XC_准生产环境"),
    PRO("生产环境"),XC_PRO("XC_生产环境"),
    ALL("所有环境");
    private String name;

    private Environment(String name){
        this.name = name;
    }
}
