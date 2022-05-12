package com.scm.tools.pojo;

import com.sequoiacm.client.element.bizconf.ScmDataLocation;
import com.sequoiacm.client.element.bizconf.ScmMetaLocation;
import com.sequoiacm.client.element.bizconf.ScmWorkspaceConf;
import com.sequoiacm.client.exception.ScmInvalidArgumentException;

import java.util.List;

/**
 * @ClassName WorkSpacePojo
 * @Description 请描述类的业务用途
 * @Author yangqi
 * @Date 2022/5/9 14:19
 * @Version 1.0
 **/
public class WorkSpacePojo {
    private String workSpaceName;
    private ScmMetaLocation metaLocation;
    private List<ScmDataLocation> dataLocations;
    private boolean enableDirectory;
    private String description;

    public String getWorkSpaceName() {
        return workSpaceName;
    }

    public void setWorkSpaceName(String workSpaceName) {
        this.workSpaceName = workSpaceName;
    }

    public ScmMetaLocation getMetaLocation() {
        return metaLocation;
    }

    public void setMetaLocation(ScmMetaLocation metaLocation) {
        this.metaLocation = metaLocation;
    }

    public List<ScmDataLocation> getDataLocation() {
        return dataLocations;
    }

    public void setDataLocation(List<ScmDataLocation> dataLocations) {
        this.dataLocations = dataLocations;
    }

    public boolean isEnableDirectory() {
        return enableDirectory;
    }

    public void setEnableDirectory(boolean enableDirectory) {
        this.enableDirectory = enableDirectory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ScmWorkspaceConf buildWsConf(){
        ScmWorkspaceConf conf = new ScmWorkspaceConf();
        try {
            conf.setDescription(this.description);
            conf.setMetaLocation(this.metaLocation);
            conf.setDataLocations(this.dataLocations);
            conf.setName(this.workSpaceName);
            conf.setEnableDirectory(enableDirectory);
        } catch (ScmInvalidArgumentException e) {
            e.printStackTrace();
        }
        return conf;
    }
}
