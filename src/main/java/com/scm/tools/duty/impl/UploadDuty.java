package com.scm.tools.duty.impl;

import com.scm.tools.common.SessionFactory;
import com.scm.tools.duty.DutyBase;
import com.scm.tools.pojo.ScmPojo;
import com.sequoiacm.client.core.*;
import com.sequoiacm.client.element.ScmFileBasicInfo;
import com.sequoiacm.client.element.ScmId;
import com.sequoiacm.client.exception.ScmException;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @ClassName UploadDuty
 * @Description 上传单个文件
 * @Author yangqi
 * @Date 2022/5/9 13:57
 * @Version 1.0
 **/
public class UploadDuty implements DutyBase {

    private ScmPojo scmPojo;
    private String filePath;
    private String scmDir;

    private ScmSession session;
    private ScmWorkspace workspace;

    private boolean isLocal;

    public UploadDuty(ScmPojo scmPojo) {
        this.scmPojo = scmPojo;
        this.filePath = "";
        this.scmDir = "/";
    }

    @Override
    public Object runTask() {

        ScmFile instance = null;
        InputStream in;
        String fileName;
        try {
            if (isLocal){
                in = new ByteArrayInputStream(new byte[1*1024*1024]);
                fileName = UUID.randomUUID().toString();
            }else {
                in = new FileInputStream(filePath);
                String[] split = filePath.split("/");
                fileName = split[split.length-1];
            }
            instance = ScmFactory.File.createInstance(workspace);
            ScmDirectory directory = ScmFactory.Directory.getInstance(workspace,scmDir);
            instance.setContent(in);
            instance.setDirectory(directory);

            ScmCursor<ScmFileBasicInfo> scmFileBasicInfoScmCursor = directory.listFiles(null);
            int fileNo = 0;
            while (scmFileBasicInfoScmCursor.hasNext()) {
                if (scmFileBasicInfoScmCursor.getNext().getFileName().equals(fileName)){
                    fileName += ++fileNo;
                    scmFileBasicInfoScmCursor = directory.listFiles(null);
                }
            }
            if (fileNo > 0){
                System.out.println("file: " + filePath + "'s name has " + fileNo + " same to this. rename to : " + fileName);
            }
            instance.setFileName(fileName);

            System.out.println("ready to upload file : " + filePath + "to scm directory: " + scmDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    public DutyBase isLocal(boolean flag){
        this.isLocal = flag;
        return this;
    }
    public UploadDuty setFileVar(String filePath,String scmDir){
        this.filePath = filePath;
        this.scmDir = scmDir;
        try {
            if (!ScmFactory.Directory.isInstanceExist(workspace,scmDir)) {
                ScmFactory.Directory.createInstance(workspace,scmDir);
            }
        } catch (ScmException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     *
     * @return
     * @throws ScmException
     */
    @Override
    public DutyBase beforeTask() throws ScmException {

        this.session = SessionFactory.getUserSession(scmPojo);
        this.workspace = ScmFactory.Workspace.getWorkspace(scmPojo.getWorkSpaceName(),session);

        return this;
    }

    @Override
    public Object afterTask() {
        session.close();
        return null;
    }

    @Override
    public void run() {
        ScmFile file = (ScmFile) runTask();
        try {
            ScmId save = file.save();
            System.out.println("upload file is successful, file id" + save);
        } catch (ScmException e) {
            e.printStackTrace();
        }
    }
}
