/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive.page;

/**
 * 页面显示对象
 *
 * @author wangwei
 */
public class PageOut {

    private final Integer number;
    private final Integer count;
    private final String label;
    private final String url;
    private final Boolean active;
    
    public PageOut(Integer count,Integer number){
        this(count,number,"");
    }
    
    public PageOut(Integer count ,Integer number,String url){
        this(count,number,null, url);
    }
    
    public PageOut(Integer count ,Integer number,String url,boolean active){
        this(count,number,null, url,active);
    }
    
    public PageOut(Integer count,Integer number,String label,String url){
        this(count,number,label,url,Boolean.FALSE);
        
    }
    
    public PageOut(Integer count,Integer number,String label,String url,boolean active){
        this.count = count;
        this.number = number + 1;
        this.label = label;
        this.url = url;
        this.active = active;
    }
    
    public Integer getNumber() {
        return number;
    }

    public Integer getCount() {
        return count;
    }

    public String getLabel() {
        return label == null ? number.toString() : label;
    }

    public String getUrl() {
        return url;
    }
    
    public Boolean isActive(){
        return this.active;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((count == null) ? 0 : count.hashCode());
        result = prime * result + ((active == null) ? 0 : active.hashCode());
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PageOut other = (PageOut) obj;
        if (count == null) {
            if (other.count != null)
                return false;
        } else if (!count.equals(other.count))
            return false;
        if (active == null) {
            if (other.active != null)
                return false;
        } else if (!active.equals(other.active))
            return false;
        if (label == null) {
            if (other.label != null)
                return false;
        } else if (!label.equals(other.label))
            return false;
        if (number == null) {
            if (other.number != null)
                return false;
        } else if (!number.equals(other.number))
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PageOut [number=" + number + ", count=" + count + ", label="
                + label + ", url=" + url + ", current=" + active + "]";
    }
}
