package com.scm.tools.config;

import java.io.InputStream;
import java.util.*;

/**
 * @ClassName ScmInfo
 * @Description SCM配置信息
 * @Author yangqi
 * @Date 2022/4/27 16:59
 * @Version 1.0
 **/
public class ScmInfo {

    /**
     * 配置文件映射变量
     */
    private static String ADMIN_PWD;
    private static String USER_NAME;
    private static String USER_PWD;
    private static String WORKSPACE_NAME;
    private static String SYS_REF;
    private static SystemLevel SYS_LEVEL;
    private static Map<Environment,List<String>> URLS;
    private static List<Environment> EXE_ENV;
    private static String SCM_DIR;
    private static Boolean dirNameSameToLocal;
    private static String LOCAL_DIR;

    private static String META_DOMAIN;
    private static String META_SITE;
    private static List<String> DATA_DOMAINS;
    private static List<String> DATA_SITES;
    private static boolean ENABLE_DIRECTORY;
    private static List<String> DOMAIN_CLASS;

    private static String WORKSPACE_DESCRIPTION;
    private static boolean IS_RECURSION;

    static {//加载配置文件
        Properties properties = new Properties();
        InputStream in;
        try{//解析配置文件
            in = ScmInfo.class.getClassLoader().getResourceAsStream("scm-info.properties");
            //in = new FileInputStream("./scm-info.properties");
            properties.load(in);

            ADMIN_PWD = properties.getProperty("scm.admin");
            USER_NAME = properties.getProperty("scm.user");
            USER_PWD = properties.getProperty("scm.passwd");
            WORKSPACE_NAME = properties.getProperty("scm.workspace");
            SYS_REF = properties.getProperty("sys.ref");
            SYS_LEVEL = SystemLevel.valueOf(properties.getProperty("sys.level"));

            EXE_ENV = new ArrayList<>();
            List<String> envs = toList(properties.getProperty("exe.env"));
            if (envs.size() == 1&& "ALL".equals(envs.get(0))){
                EXE_ENV.add(Environment.DEV);
                EXE_ENV.add(Environment.SIT);
                EXE_ENV.add(Environment.UAT);
                EXE_ENV.add(Environment.TST);
                EXE_ENV.add(Environment.PRO);
                EXE_ENV.add(Environment.XC_DEV);
                EXE_ENV.add(Environment.XC_PRO);
                EXE_ENV.add(Environment.XC_SIT);
                EXE_ENV.add(Environment.XC_UAT);
                EXE_ENV.add(Environment.XC_TST);
            }else {
                for (int i = 0 ; i < envs.size();i++){
                    EXE_ENV.add(Environment.valueOf(envs.get(i)));
                }
            }
            SCM_DIR = properties.getProperty("scm.dir");
            LOCAL_DIR = properties.getProperty("local.dir");
            dirNameSameToLocal = Boolean.valueOf(properties.getProperty("dirNameSameToLocal"));
            URLS = new HashMap<>();
            URLS.put(Environment.DEV,toList(properties.getProperty("scm.dev.urls")));
            URLS.put(Environment.SIT,toList(properties.getProperty("scm.sit.urls")));
            URLS.put(Environment.UAT,toList(properties.getProperty("scm.uat.urls")));
            URLS.put(Environment.TST,toList(properties.getProperty("scm.tst.urls")));
            URLS.put(Environment.PRO,toList(properties.getProperty("scm.pro.urls")));

            URLS.put(Environment.XC_DEV,toList(properties.getProperty("scm.xc.dev.urls")));
            URLS.put(Environment.XC_SIT,toList(properties.getProperty("scm.xc.sit.urls")));
            URLS.put(Environment.XC_UAT,toList(properties.getProperty("scm.xc.uat.urls")));
            URLS.put(Environment.XC_TST,toList(properties.getProperty("scm.xc.tst.urls")));
            URLS.put(Environment.XC_PRO,toList(properties.getProperty("scm.xc.pro.urls")));

            WORKSPACE_DESCRIPTION = properties.getProperty("scm.workspace.description");
            IS_RECURSION = Boolean.valueOf(properties.getProperty("scm.download.isRecursion"));
            META_DOMAIN = properties.getProperty("scm.meta.domain");
            META_SITE = properties.getProperty("scm.meta.site");
            DATA_DOMAINS = toList(properties.getProperty("scm.data.domains"));
            DATA_SITES = toList(properties.getProperty("scm.data.sites"));
            ENABLE_DIRECTORY = Boolean.valueOf(properties.getProperty("scm.workspace.enableDirectory"));
            DOMAIN_CLASS = toList(properties.getProperty("scm.data.domains.class"));
        }catch (Exception e){
            System.out.println("an exception occur when reading config doc");
            e.printStackTrace();
        }



    }

    public static List<String> getDomainClass() {
        return DOMAIN_CLASS;
    }

    public static String getMetaDomain() {
        return META_DOMAIN;
    }

    public static String getMetaSite() {
        return META_SITE;
    }

    public static List<String> getDataDomains() {
        return DATA_DOMAINS;
    }

    public static List<String> getDataSites() {
        return DATA_SITES;
    }

    public static boolean isEnableDirectory() {
        return ENABLE_DIRECTORY;
    }

    public static String getWorkspaceDescription(){
        return WORKSPACE_DESCRIPTION;
    }
    public static boolean isIsRecursion(){
        return IS_RECURSION;
    }

    public static List<String> toList(String url){
        List<String> urls = new ArrayList<String>();
        String[] u = url.split(",");
        for (String gateway:u){
            urls.add(gateway);
        }
        return urls;
    }

    public static List<String> getUrlsByEnv(Environment environment){
        return URLS.get(environment);
    }

    public static String getAdminPwd() {
        return ADMIN_PWD;
    }

    public static String getUserName() {
        return USER_NAME;
    }

    public static String getUserPwd() {
        return USER_PWD;
    }

    public static String getWorkspaceName() {
        return WORKSPACE_NAME;
    }

    public static String getSysRef() {
        return SYS_REF;
    }

    public static SystemLevel getSysLevel() {
        return SYS_LEVEL;
    }

    public static List<Environment> getExeEnv() {
        return EXE_ENV;
    }

    public static String getScmDir() {
        return SCM_DIR;
    }

    public static Boolean getDirNameSameToLocal() {
        return dirNameSameToLocal;
    }

    public static String getLocalDir() {
        return LOCAL_DIR;
    }
}
