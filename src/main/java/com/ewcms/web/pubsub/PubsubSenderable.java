/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.pubsub;

import javax.servlet.http.HttpServletResponse;

public interface PubsubSenderable {

    void removeClient(HttpServletResponse connection);

    void addClient(HttpServletResponse connection);
    
    void setInitialDelay(long initialDelay);
    
    void setDelay(long delay);
    
    void start();
}
