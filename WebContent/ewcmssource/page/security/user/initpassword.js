/*!
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
 
var InitPassword = function(){
}

InitPassword.prototype.init = function(){
 
    $("#button-save").bind('click',function(){
        $('form').submit();
    });
    
    $('#button-cancel').bind('click',function(){
        parent.closeEditWindow();
    });
}
