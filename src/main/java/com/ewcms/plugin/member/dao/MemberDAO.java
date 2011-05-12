/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.plugin.member.dao;

import com.ewcms.comm.jpa.dao.JpaDAO;
import com.ewcms.plugin.member.model.Member;

import org.springframework.stereotype.Repository;

/**
 *
 * @author wangwei
 */
@Repository
public class MemberDAO  extends JpaDAO<String,Member>{

}
