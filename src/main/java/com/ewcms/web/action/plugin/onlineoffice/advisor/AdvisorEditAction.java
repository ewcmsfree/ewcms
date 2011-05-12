/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.web.action.plugin.onlineoffice.advisor;

import com.ewcms.plugin.onlineoffice.OnlineOfficeFacable;
import com.ewcms.plugin.onlineoffice.model.Advisor;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author wangwei
 */
@Controller
public class AdvisorEditAction extends ActionSupport {

    private String replay;
    private Advisor advisor;
    private Integer id;
    private Boolean checked;
    private Boolean success = false;
    @Autowired
    private OnlineOfficeFacable fac;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getReplay() {
        return replay;
    }

    public void setReplay(String replay) {
        this.replay = replay;
    }

    public Advisor getAdvisor() {
        return advisor;
    }

    public Boolean getSuccess() {
        return success;
    }

    @Override
    public String execute() {
        if(checked != null){
            fac.releaseAdvisor(id, checked);
            success = true;
        }
        if (replay != null && replay.trim().length() > 0) {
            fac.advisorReplay(id, replay);
            advisor = new Advisor();
            replay = "";
            id = null;
            success = true;
        }
        advisor = fac.getAdvisor(id);
        replay = advisor.getReplay();
        checked = advisor.isChecked();

        return SUCCESS;
    }
}
