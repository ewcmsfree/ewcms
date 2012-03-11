/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.crawler.generate.fetcher;

import org.apache.http.HttpStatus;

public class CustomFetchStatus {
	public static final int PageTooBig = 1001;
	public static final int FatalTransportError = 1005;
	public static final int UnknownError = 1006;

	public static String getStatusDescription(int code) {
		switch (code) {
		case HttpStatus.SC_OK:
			return "OK";
		case HttpStatus.SC_CREATED:
			return "Created";
		case HttpStatus.SC_ACCEPTED:
			return "Accepted";
		case HttpStatus.SC_NO_CONTENT:
			return "No Content";
		case HttpStatus.SC_MOVED_PERMANENTLY:
			return "Moved Permanently";
		case HttpStatus.SC_MOVED_TEMPORARILY:
			return "Moved Temporarily";
		case HttpStatus.SC_NOT_MODIFIED:
			return "Not Modified";
		case HttpStatus.SC_BAD_REQUEST:
			return "Bad Request";
		case HttpStatus.SC_UNAUTHORIZED:
			return "Unauthorized";
		case HttpStatus.SC_FORBIDDEN:
			return "Forbidden";
		case HttpStatus.SC_NOT_FOUND:
			return "Not Found";
		case HttpStatus.SC_INTERNAL_SERVER_ERROR:
			return "Internal Server Error";
		case HttpStatus.SC_NOT_IMPLEMENTED:
			return "Not Implemented";
		case HttpStatus.SC_BAD_GATEWAY:
			return "Bad Gateway";
		case HttpStatus.SC_SERVICE_UNAVAILABLE:
			return "Service Unavailable";
		case HttpStatus.SC_CONTINUE:
			return "Continue";
		case HttpStatus.SC_TEMPORARY_REDIRECT:
			return "Temporary Redirect";
		case HttpStatus.SC_METHOD_NOT_ALLOWED:
			return "Method Not Allowed";
		case HttpStatus.SC_CONFLICT:
			return "Conflict";
		case HttpStatus.SC_PRECONDITION_FAILED:
			return "Precondition Failed";
		case HttpStatus.SC_REQUEST_TOO_LONG:
			return "Request Too Long";
		case HttpStatus.SC_REQUEST_URI_TOO_LONG:
			return "Request-URI Too Long";
		case HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE:
			return "Unsupported Media Type";
		case HttpStatus.SC_MULTIPLE_CHOICES:
			return "Multiple Choices";
		case HttpStatus.SC_SEE_OTHER:
			return "See Other";
		case HttpStatus.SC_USE_PROXY:
			return "Use Proxy";
		case HttpStatus.SC_PAYMENT_REQUIRED:
			return "Payment Required";
		case HttpStatus.SC_NOT_ACCEPTABLE:
			return "Not Acceptable";
		case HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED:
			return "Proxy Authentication Required";
		case HttpStatus.SC_REQUEST_TIMEOUT:
			return "Request Timeout";
		case PageTooBig:
			return "Page size was too big";
		case FatalTransportError:
			return "Fatal transport error";
		case UnknownError:
			return "Unknown error";
		default:
			return "(" + code + ")";
		}
	}
}
