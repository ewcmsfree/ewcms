/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.output;

import java.util.HashMap;
import java.util.Map;

import com.ewcms.core.site.model.OutputType;
import com.ewcms.publication.output.provider.FtpOutput;
import com.ewcms.publication.output.provider.FtpsOutput;
import com.ewcms.publication.output.provider.LocalOutput;
import com.ewcms.publication.output.provider.SftpOutput;
import com.ewcms.publication.output.provider.SmbOutput;

/**
 * 发布资源工厂类
 * <br>
 * 通过发布类型（ftp,sftp等），得到具体发布操作对象
 * 
 * @author wangwei
 */
public class OutputFactory{

    private static final Map<OutputType,Outputable> outputs = initOutputs(); 
    
    private OutputFactory(){
        //隐藏构造行数，防止创建OutputFactory对象
    }
    
    public static Outputable factory(OutputType type){
        return outputs.get(type);
    }
  
    private static Map<OutputType,Outputable> initOutputs(){
        Map<OutputType,Outputable> map = new HashMap<OutputType,Outputable>();
        
        map.put(OutputType.LOCAL, new LocalOutput());
        map.put(OutputType.FTP, new FtpOutput());
        map.put(OutputType.FTPS, new FtpsOutput());
        map.put(OutputType.SFTP, new SftpOutput());
        map.put(OutputType.SMB, new SmbOutput());
        
        return map;
    }
}
