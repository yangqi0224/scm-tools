package com.scm.tools.duty.impl;

import com.scm.tools.common.SessionFactory;
import com.scm.tools.duty.DutyBase;
import com.scm.tools.pojo.ScmPojo;
import com.sequoiacm.apache.commons.codec.digest.DigestUtils;
import com.sequoiacm.client.common.ScmType;
import com.sequoiacm.client.core.*;
import com.sequoiacm.client.element.ScmFileBasicInfo;
import com.sequoiacm.client.element.ScmId;
import com.sequoiacm.client.exception.ScmException;
import org.bson.BasicBSONObject;

import java.io.File;
import java.io.FileInputStream;

/**
 * @ClassName DownloadDuty
 * @Description 批量下载
 * @Author yangqi
 * @Date 2022/5/11 15:45
 * @Version 1.0
 **/
public class DownloadDuty implements DutyBase {

    private ScmPojo scmPojo;
    private ScmSession session;
    private ScmWorkspace workspace;

    ScmCursor<ScmFileBasicInfo> cursor;
    private String scmDir;
    private boolean isRecursion = false;


    public DownloadDuty(ScmPojo scmPojo) {
        this.scmPojo = scmPojo;
    }


    /**
     * 是否下载子目录下的文件
     * 默认false
     * @param flag
     * @return
     */
    public DutyBase isRecursion(boolean flag){
        this.isRecursion = flag;
        return this;
    }
    public DutyBase setDirVar(String dir){
        this.scmDir = dir;
        return this;
    }


    @Override
    public Object runTask() {
        ScmId id = null;
        if (cursor == null){
            System.out.println("download cursor is null. ");
            return null;
        }
        File dirs = new File("./download" + scmDir);
        System.out.println(scmDir);
        System.out.println(dirs.getAbsoluteFile());
        //创建文件夹
        if (!dirs.isDirectory()){
            System.out.println("ready to create download dir : " + dirs.getPath());
            dirs.mkdirs();
        }
        try {
            //下载文件
            while (cursor.hasNext()){
                id = cursor.getNext().getFileId();
                downloadFileById(id,0);
                System.out.println("download file is successful, file id :"+id);
            }
        }catch (Exception e){
            System.out.println("download file failed , file id :"+ id );
            e.printStackTrace();
        }
        return this;
    }


    private boolean downloadFileById(ScmId id,int fileNo) throws ScmException {
        boolean result = false;
        ScmFile file = ScmFactory.File.getInstance(workspace,id);
        String fileName = file.getFileName();
        try {
            if (fileNo > 0){
                file.getContent("./download" + scmDir + fileName + "_" + fileNo);
            }else {
                file.getContent("./download" + scmDir + fileName);
            }
            result = true;
        }catch (ScmException e){
            if (e.getErrorCode() == -604){
                fileNo++;
                downloadFileById(id,fileNo);
            }
        }
        return result;
    }
    public ScmCursor<ScmFileBasicInfo> getCursor(String scmDir){
        ScmCursor<ScmFileBasicInfo> cursor = null;
        try {
            if (workspace.isEnableDirectory()){
                cursor = ScmFactory.Directory.getInstance(workspace, scmDir).listFiles(null);
            }else {
                cursor = ScmFactory.File.listInstance(workspace, ScmType.ScopeType.SCOPE_ALL,new BasicBSONObject());
            }

        } catch (ScmException e) {
            e.printStackTrace();
        }
        return cursor;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    public DutyBase beforeTask() throws Exception {
        /**
         * 创建下载目录
         */
        File dir = new File("./download/");
        if (!dir.isDirectory()){
            System.out.println("ready to create download dir: ./download" );
            dir.mkdirs();
        }
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
        this.cursor = getCursor(this.scmDir);
        runTask();
        if (isRecursion){
            try {
                ScmCursor<ScmDirectory> dirs = ScmFactory.Directory.
                        getInstance(workspace, scmDir).listDirectories(null);
                while (dirs.hasNext()){
                    this.setDirVar(dirs.getNext().getPath()).runTask();
                }
            } catch (ScmException e) {
                e.printStackTrace();
            }
        }
    }
}
