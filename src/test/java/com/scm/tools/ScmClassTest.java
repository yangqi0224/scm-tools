package com.scm.tools;

import com.scm.tools.common.SessionFactory;
import com.scm.tools.config.Environment;
import com.scm.tools.config.InfoHandle;
import com.scm.tools.pojo.ScmPojo;
import com.sequoiacm.client.core.*;
import com.sequoiacm.client.element.ScmClassProperties;
import com.sequoiacm.client.element.metadata.ScmAttributeConf;
import com.sequoiacm.client.exception.ScmException;
import com.sequoiacm.common.AttributeType;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.HashMap;

/**
 * @author YangQ
 */
public class ScmClassTest {
    public static void main(String[] args) throws Exception {
        ScmPojo scmPojo = InfoHandle.scmInfoHandle(Environment.UAT);
        ScmSession session = SessionFactory.getUserSession(scmPojo);
        ScmWorkspace ws = ScmFactory.Workspace.getWorkspace(scmPojo.getWorkSpaceName(),session);
        ScmFile scmFile = ScmFactory.File.createInstance(ws);
        scmFile.setAuthor("yangqi");
        scmFile.setTitle("2021tt");
        scmFile.setFileName("2021tt");
        scmFile.setContent(new ByteArrayInputStream(new byte[1024]));
        //设置文件创建日期为实际生成日期
        Date date = new Date(new Long("1635838791322"));
        scmFile.setCreateTime(date);
        scmFile.addTag("标签测试");
        scmFile.addTag("标签测试1");
        scmFile.addTag("标签测试2");
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("自由属性","ss");
        objectObjectHashMap.put("自由属性1","ss1");
        objectObjectHashMap.put("自由属性2","ss2");
        scmFile.setCustomMetadata(objectObjectHashMap);

        //scmFile.setCustomMetadata();

        ScmClass test1 = createClassAndAttrIfNotExist(ws, "test1");

        ScmClassProperties scmClassProperties = new ScmClassProperties(test1.getId().toString());
        scmFile.setClassProperties(scmClassProperties);
        scmFile.setClassProperty("testAttr1","2022tt");
        scmFile.setClassProperty("testAttr2","2022tt");
        scmFile.setClassProperty("testAttr3","2022tt");

        scmFile.save();
        System.out.println("ok");
        session.close();

    }

    public static ScmAttribute buildAttrBuild(ScmAttributeConf conf,ScmWorkspace ws) throws ScmException {

        return  ScmFactory.Attribute.createInstance(ws,conf);
    }

    public static ScmClass createClassAndAttrIfNotExist(ScmWorkspace ws,String className) throws ScmException {
        ScmClass scmClass =null;
        try{
            scmClass = ScmFactory.Class.getInstanceByName(ws,className);
        }catch (ScmException e){
            e.printStackTrace();
            if (e.getErrorCode() == -503){//error=METADATA_CLASS_NOT_EXIST(-503): Class is not exist
                System.out.println("元数据模型不存在，创建。。");
                scmClass = ScmFactory.Class.createInstance(ws, className, "test");
                ScmAttributeConf scmAttributeConf = new ScmAttributeConf().setDescription("testAttr")
                        .setName("testAttr1")
                        .setRequired(true)
                        .setType(AttributeType.STRING)
                        .setDescription("测试属性");
                ScmAttributeConf scmAttributeConf1 = new ScmAttributeConf().setDescription("testAttr1")
                        .setName("testAttr2")
                        .setRequired(true)
                        .setType(AttributeType.STRING)
                        .setDescription("测试属性");
                ScmAttributeConf scmAttributeConf2 = new ScmAttributeConf().setDescription("testAttr2")
                        .setName("testAttr3")
                        .setRequired(true)
                        .setType(AttributeType.STRING)
                        .setDescription("测试属性");

                scmClass.attachAttr(buildAttrBuild(scmAttributeConf,ws).getId());
                scmClass.attachAttr(buildAttrBuild(scmAttributeConf1,ws).getId());
                scmClass.attachAttr(buildAttrBuild(scmAttributeConf2,ws).getId());
            }

        }
        return scmClass;
    }
}
