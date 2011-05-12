/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.plugin.leadingwindow.generator;

import com.ewcms.generator.release.ReleaseException;

/**
 *
 * @author wangwei
 */
public interface LeadingReleaseable {

    void release(int channelId)throws ReleaseException;

    void release(int channelId,int leaderId)throws ReleaseException;
}
