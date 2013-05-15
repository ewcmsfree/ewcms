/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.contribute;

import java.util.List;

import com.ewcms.plugin.contribute.model.Contribute;

/**
 *
 * @author wangwei
 */
public interface ContributeFacable {

    public Contribute getContribute(Long id);
    
    public void contributeChecked(Long id,Boolean checked);
    
    public void deleteContribute(List<Long> ids);

}
