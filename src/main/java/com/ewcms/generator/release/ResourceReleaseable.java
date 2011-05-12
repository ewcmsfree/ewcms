/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.generator.release;

import com.ewcms.generator.dao.GeneratorDAOable;

/**
 *
 * @author wangwei
 */
public interface ResourceReleaseable {

    public void release(GeneratorDAOable dao,int siteId)throws ReleaseException;

    public void releaseSingle(GeneratorDAOable dao,int id)throws ReleaseException;
}
