/*
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
var pubsub = {
    connection : false,
    iframediv : false,

    initialize : function(url) {
        if ($.browser.msie) {
            pubsub.connection = new ActiveXObject("htmlfile");
            pubsub.connection.open();
            pubsub.connection.write("<html>");
            pubsub.connection.write("<script>document.domain = '" + document.domain + "'");
            pubsub.connection.write("</html>");
            pubsub.connection.close();
            pubsub.iframediv = pubsub.connection.createElement("div");
            pubsub.connection.appendChild(pubsub.iframediv);
            pubsub.connection.parentWindow.pubsub = pubsub;
            pubsub.iframediv.innerHTML = "<iframe id='pubsub_iframe' src='" + url + "'></iframe>";
        } else if ($.browser.safari) {
            pubsub.connection = document.createElement('iframe');
            pubsub.connection.setAttribute('id', 'pubsub_iframe');
            pubsub.connection.setAttribute('src', url);
            with (pubsub.connection.style) {
                position = "absolute";
                left = top = "-100px";
                height = width = "1px";
                visibility = "hidden";
            }
            document.body.appendChild(pubsub.connection);
        } else {
            pubsub.connection = document.createElement('iframe');
            pubsub.connection.setAttribute('id', 'pubsub_iframe');
            with (pubsub.connection.style) {
                left = top = "-100px";
                height = width = "1px";
                visibility = "hidden";
                display = 'none';
            }
            pubsub.iframediv = document.createElement('iframe');
            pubsub.iframediv.setAttribute('src', url);
            pubsub.connection.appendChild(pubsub.iframediv);
            document.body.appendChild(pubsub.connection);
        }
    },
    onUnload : function() {
        if (pubsub.connection) {
            pubsub.connection = false;
        }
    }
}