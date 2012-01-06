/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.deploy;

import com.ewcms.core.site.model.SiteServer;
import com.ewcms.publication.deploy.provider.FtpDeployOperator;
import com.ewcms.publication.deploy.provider.FtpsDeployOperator;
import com.ewcms.publication.deploy.provider.LocalDeployOperator;
import com.ewcms.publication.deploy.provider.SftpDeployOperator;

/**
 * 发布资源工厂类
 * <br>
 * 通过发布类型（ftp,sftp等），得到具体发布操作对象
 * 
 * @author wangwei
 */
public class DeployOperatorFactory{

    private DeployOperatorFactory(){
        //隐藏构造行数，防止创建OutputFactory对象
    }

    public static DeployOperatorable factory(SiteServer server){
        switch(server.getOutputType()){
            case FTP:
                return newFtp(server);
            case FTPS :
                return newFtps(server);
            case LOCAL :
                return newLocal(server);
            case SFTP :
                return newSftp(server);
            default:
                return newLocal(server);
        }
    }
    
    private static DeployOperatorable newFtp(SiteServer server){
        return new FtpDeployOperator
                .Builder(server.getHostName(), server.getPath())
                .setPort(server.getPort())
                .setUsername(server.getUserName())
                .setPassword(server.getPassword())
                .build();
    }
    
    private static DeployOperatorable newFtps(SiteServer server){
        return new FtpsDeployOperator
                .Builder(server.getHostName(), server.getPath())
                .setPort(server.getPort())
                .setUsername(server.getUserName())
                .setPassword(server.getPassword())
                .build();
    }
  
    private static DeployOperatorable newLocal(SiteServer server){
        return new LocalDeployOperator
                .Builder(server.getPath())
                .build();
    }
    
    private static DeployOperatorable newSftp(SiteServer server){
        return new SftpDeployOperator
                .Builder(server.getHostName(), server.getPath())
                .setPort(server.getPort())
                .setUsername(server.getUserName())
                .setPassword(server.getPassword())
                .build();
    }
}
