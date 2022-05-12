package com.scm.tools.util;

import com.scm.tools.config.ScmInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName GenInfoForScm
 * @Description 初始化对接系统时生成信息
 * @Author yangqi
 * @Date 2022/5/9 16:02
 * @Version 1.0
 **/
public class GenInfoForScm {

    private static String sysRef = ScmInfo.getSysRef();
    public static String generatePassword(){
        String ref = sysRef.replace("_","");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String format = sdf.format(d);
        String userPwd = firstToUp(ref) + "@" + format;
        System.out.println("generate password for "+ sysRef +" is successful,user password: "+userPwd);
        return userPwd;
    }

    public static String generateUserName(){
        return sysRef.replace("_","")+"admin";
    }

    public static String firstToUp(String name){
        char[] chars = name.toCharArray();
        chars[0] -= 32 ;
        return String.valueOf(chars);
    }
    public static String generateWorkspaceName(){
        return "ws_"+sysRef;
    }
}
