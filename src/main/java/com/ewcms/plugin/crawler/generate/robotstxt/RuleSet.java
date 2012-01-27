/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.crawler.generate.robotstxt;

import java.util.SortedSet;
import java.util.TreeSet;

public class RuleSet extends TreeSet<String> {

	private static final long serialVersionUID = -3232259659058346633L;

	@Override
	public boolean add(String str) {
		SortedSet<String> sub = headSet(str);
		if (!sub.isEmpty() && str.startsWith(sub.last())) {
			// no need to add; prefix is already present
			return false;
		}
		boolean retVal = super.add(str);
		sub = tailSet(str + "\0");
		while (!sub.isEmpty() && sub.first().startsWith(str)) {
			// remove redundant entries
			sub.remove(sub.first());
		}
		return retVal;
	}
	
	public boolean containsPrefixOf(String s) {
		SortedSet<String> sub = headSet(s);
		// because redundant prefixes have been eliminated,
		// only a test against last item in headSet is necessary
		if (!sub.isEmpty() && s.startsWith(sub.last())) {
			return true; // prefix substring exists
		} 
		// might still exist exactly (headSet does not contain boundary)
		return contains(s); 
	}
}
