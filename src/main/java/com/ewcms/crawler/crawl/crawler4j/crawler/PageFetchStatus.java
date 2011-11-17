/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler.crawl.crawler4j.crawler;

/**
 * @author Yasser Ganjisaffar <yganjisa at uci dot edu>
 */


public final class PageFetchStatus {
	
	public static final int OK = 1000;
	
	public static final int PageTooBig = 1001;
		
	public static final int UnknownDocument = 1002;
	
	public static final int URLnotAllowedByURLFilters = 1003;
	
	public static final int FatalProtocolViolation = 1004;
	
	public static final int FatalTransportError = 1005;
	
	public static final int UnknownError = 1006;
	
	public static final int PageLoadError = 1007;
	
	public static final int RequestForTermination = 1008;
	
	public static final int RedirectedPageIsSeen = 1010;
	
	public static final int NotInTextFormat = 1011;
	
	public static final int PageIsBinary = 1012;
	
	public static final int Moved = 1013;
	
	public static final int MovedToUnknownLocation = 1014;
	
	public boolean is2XXSuccess(int code) {
    	return code >= 200 && code < 300;
    }
	
	public static String fetchStatusCodesToString(int code){
        switch(code){
            case 100  : return "HTTP-100-Info-Continue";
            case 101  : return "HTTP-101-Info-Switching Protocols";
            case 200  : return "HTTP-200-Success-OK";
            case 201  : return "HTTP-201-Success-Created";
            case 202  : return "HTTP-202-Success-Accepted";
            case 203  : return "HTTP-203-Success-Non-Authoritative";
            case 204  : return "HTTP-204-Success-No Content ";
            case 205  : return "HTTP-205-Success-Reset Content";
            case 206  : return "HTTP-206-Success-Partial Content";
            case 300  : return "HTTP-300-Redirect-Multiple Choices";
            case 301  : return "HTTP-301-Redirect-Moved Permanently";
            case 302  : return "HTTP-302-Redirect-Found";
            case 303  : return "HTTP-303-Redirect-See Other";
            case 304  : return "HTTP-304-Redirect-Not Modified";
            case 305  : return "HTTP-305-Redirect-Use Proxy";
            case 307  : return "HTTP-307-Redirect-Temporary Redirect";
            case 400  : return "HTTP-400-ClientErr-Bad Request";
            case 401  : return "HTTP-401-ClientErr-Unauthorized";
            case 402  : return "HTTP-402-ClientErr-Payment Required";
            case 403  : return "HTTP-403-ClientErr-Forbidden";
            case 404  : return "HTTP-404-ClientErr-Not Found";
            case 405  : return "HTTP-405-ClientErr-Method Not Allowed";
            case 407  : return "HTTP-406-ClientErr-Not Acceptable";
            case 408  : return "HTTP-407-ClientErr-Proxy Authentication Required";
            case 409  : return "HTTP-408-ClientErr-Request Timeout";
            case 410  : return "HTTP-409-ClientErr-Conflict";
            case 406  : return "HTTP-410-ClientErr-Gone";
            case 411  : return "HTTP-411-ClientErr-Length Required";
            case 412  : return "HTTP-412-ClientErr-Precondition Failed";
            case 413  : return "HTTP-413-ClientErr-Request Entity Too Large";
            case 414  : return "HTTP-414-ClientErr-Request-URI Too Long";
            case 415  : return "HTTP-415-ClientErr-Unsupported Media Type";
            case 416  : return "HTTP-416-ClientErr-Requested Range Not Satisfiable";
            case 417  : return "HTTP-417-ClientErr-Expectation Failed";
            case 500  : return "HTTP-500-ServerErr-Internal Server Error";
            case 501  : return "HTTP-501-ServerErr-Not Implemented";
            case 502  : return "HTTP-502-ServerErr-Bad Gateway";
            case 503  : return "HTTP-503-ServerErr-Service Unavailable";
            case 504  : return "HTTP-504-ServerErr-Gateway Timeout";
            case 505  : return "HTTP-505-ServerErr-HTTP Version Not Supported";
            case OK:
            	return "OK";
            case PageTooBig:
            	return "Page too big";
            case UnknownDocument:
            	return "Unknown Document Type";
            case URLnotAllowedByURLFilters:
            	return "URL not allowed by filters";
            case FatalProtocolViolation:
            	return "Fatal Protocol Violation";
            case FatalTransportError:
            	return "Fatal Transport Error";
            case RequestForTermination:
            	return "Request for Termination";
            case UnknownError:
            	return "Unknown Error";            
            default : return Integer.toString(code);
        }
    }

}
