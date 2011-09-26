/*!
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

var thumb = function(id,opts){
    this._id = id;
    this._opts = opts;
};

thumb.prototype.init=function(){
    var id = this._id;
    var opts = this._opts;
    
    $("#upload").uploadify({
        'uploader': opts.uploader,
        'expressInstall':opts.expressInstall,
        'cancelImg': opts.cancelImg,
        'script': opts.script,
        'queueID': 'upload_queue',
        'fileDataName': 'myUpload',
        'scriptData': {'id':id},
        'auto':true,
        'multi':false,
        'removeCompleted':false,
        'fileDesc': 'jpg/gif/jpeg/png/bmp',
        'fileExt' : '*.jpg;*.gif;*.jpeg;*.png;*.bmp'
    });
};
