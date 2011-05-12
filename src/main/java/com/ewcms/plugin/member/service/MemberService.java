/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.plugin.member.service;

import com.ewcms.plugin.member.dao.MemberDAO;
import com.ewcms.plugin.member.model.Member;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author wangwei
 */
@Service
public class MemberService {

    @Autowired
    private MemberDAO memberDAO;

    public boolean hasMember(String username){
        Member member = memberDAO.get(username);
        return member != null;
    }

    public void addMember(Member member){
        memberDAO.persist(member);
    }

    public void updateMember(Member newMember){
        Member member = memberDAO.get(newMember.getUsername());

        member.setName(newMember.getName());
        member.setCppcc(newMember.getCppcc());
        member.setNccpc(newMember.getNccpc());
        member.setEnabled(newMember.isEnabled());
        if(StringUtils.isNotEmpty(newMember.getPassword())){
            member.setPassword(newMember.getPassword());
        }
        
        memberDAO.persist(member);
    }

    public void removeMember(String username){
        memberDAO.removeByPK(username);
    }

    public Member getMember(String username){
        return memberDAO.get(username);
    }

    public void setMemberDAO(MemberDAO dao){
        this.memberDAO = dao;
    }

    

}
