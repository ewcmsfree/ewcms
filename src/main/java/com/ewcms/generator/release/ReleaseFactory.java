/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.generator.release;

/**
 *
 * @author wangwei
 */
public class ReleaseFactory {

    private ReleaseFactory(){}

    public static Releaseable articleRelease(){
        return new ArticleRelease();
    }

    public static Releaseable homeListRelease(){
        return new HomeListRelease();
    }

    public static Releaseable homeRelease(){
        return new HomeRelease();
    }

    public static Releaseable listRelease(){
        return new ListRelease();
    }

    public static ResourceReleaseable resourceRelease(){
        return new ResourceRelease();
    }
    
    public static ResourceReleaseable resourceTPLRelease(){
        return new ResourceTPLRelease();
    }
}
