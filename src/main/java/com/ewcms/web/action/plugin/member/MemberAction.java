/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.web.action.plugin.member;

import com.ewcms.plugin.member.model.Member;
import com.ewcms.plugin.member.service.MemberService;
import com.ewcms.web.action.CrudBaseAction;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author wangwei
 */
@Controller
public class MemberAction extends CrudBaseAction<Member, String> {

    private String eventOP = "add";
    
    private Boolean cppcc;
    private Boolean nccpc;
    
    @Autowired
    private MemberService memberService;

    public void setSelections(List<String> list){
        super.setOperatorPK(list);
    }

    public List<String> getSelections(){
        return super.getOperatorPK();
    }

    public Member getMember() {
        return super.getVo();
    }

    public void setMember(Member member) {
        super.setVo(member);
    }

    public void setEventOP(String eventOP){
        this.eventOP = eventOP;
    }

    public String getEventOP(){
        return this.eventOP;
    }

    public Boolean getCppcc() {
        return cppcc;
    }

    public void setCppcc(Boolean cppcc) {
        this.cppcc = cppcc;
    }

    public Boolean getNccpc() {
        return nccpc;
    }

    public void setNccpc(Boolean nccpc) {
        this.nccpc = nccpc;
    }

    public void setMemberService(MemberService service) {
        this.memberService = service;
    }

    @Override
    protected String getPK(Member vo) {
        return vo.getUsername();
    }

    @Override
    protected Member getOperator(String pk) {
        return memberService.getMember(pk);
    }

    @Override
    protected void deleteOperator(String pk) {
        memberService.removeMember(pk);
    }

    @Override
    public String save() throws Exception {
        if (eventOP.equals("add")) {
            if(memberService.hasMember(getVo().getUsername())){
                this.addActionError("用户已经存在");
                return SUCCESS;
            }
            operatorState = OperatorState.ADD;
            String id = saveOperator(vo, false);
            operatorPK.add(id);
        } else {
            operatorState = OperatorState.UPDATE;
            saveOperator(vo, true);
            operatorPK.remove(0);
        }
        saveAfter();
        return SUCCESS;
    }

    @Override
    protected String saveOperator(Member vo, boolean isUpdate) {
        if (isUpdate) {
            memberService.updateMember(vo);
        } else {
            memberService.addMember(vo);
        }
        return vo.getUsername();
    }

    @Override
    protected Member createEmptyVo() {
        Member member = new Member();
        member.setCppcc(cppcc);
        member.setNccpc(nccpc);
        return member;
    }
}
