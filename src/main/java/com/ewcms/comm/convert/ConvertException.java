/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.comm.convert;

/**
 *
 * @author wangwei
 */
public class ConvertException extends Exception {

    public ConvertException(String string) {
        super(string);
    }

    public ConvertException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public ConvertException(Throwable thrwbl) {
        super(thrwbl);
    }
}
