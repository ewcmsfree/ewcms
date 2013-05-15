/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.online;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.citizen.model.Citizen;
import com.ewcms.plugin.citizen.service.CitizenServiceable;
import com.ewcms.plugin.online.model.Advisor;
import com.ewcms.plugin.online.model.Matter;
import com.ewcms.plugin.online.model.WorkingBody;
import com.ewcms.plugin.online.service.AdvisorServiceable;
import com.ewcms.plugin.online.service.MatterServiceable;
import com.ewcms.plugin.online.service.WorkingBodyServiceable;
import com.ewcms.web.vo.TreeNode;

/**
 *
 * @author 吴智俊
 */
@Service
public class OnlineFac implements OnlineFacable {

    @Autowired
    private WorkingBodyServiceable workingBodyService;
    @Autowired
    private MatterServiceable matterService;
    @Autowired
    private CitizenServiceable citizenService;
    @Autowired
    private AdvisorServiceable advisorService;

    @Override
    public Integer addMatter(Matter matter, List<String> filePaths, List<String> legends) {
        return matterService.addMatter(matter, filePaths, legends);
    }

    @Override
    public List<Integer> addMatterToWorkingBody(Integer workingBodyId, List<Integer> matterIds, Integer channelId) {
        return workingBodyService.addMatterToWorkingBody(workingBodyId, matterIds, channelId);
    }

    @Override
    public Integer addWorkingBody(Integer parentId, String name, Integer channelId) {
        return workingBodyService.addWorkingBody(parentId, name, channelId);
    }

    @Override
    public void delMatter(Integer matterId) {
        matterService.delMatter(matterId);
    }

    @Override
    public void delWorkingBody(Integer workingBodyId, Integer channelId) {
        workingBodyService.delWorkingBody(workingBodyId, channelId);
    }

    @Override
    public void downMatter(Integer matterId) {
        matterService.downMatter(matterId);
    }

    @Override
    public void downWorkingBody(Integer parentId, Integer workingBodyId, Integer channelId) {
        workingBodyService.downWorkingBody(parentId, workingBodyId, channelId);
    }

    @Override
    public Matter getMatter(Integer matterId) {
        return matterService.getMatter(matterId);
    }

    @Override
    public List<Matter> getMatterAllOrderBySort() {
        return matterService.getMatterAllOrderBySort();
    }

    @Override
    public WorkingBody getWorkingBody(Integer workingBodyId, Integer channelId) {
        return workingBodyService.getWorkingBody(workingBodyId, channelId);
    }

    @Override
    public TreeNode getWorkingBodyWindowTree(Integer channelId, Boolean isMatter) {
        return workingBodyService.getWorkingBodyWindowTree(channelId, isMatter);
    }

    @Override
    public void removeMatterFromWorkingBody(Integer workingBodyId, Integer matterId, Integer channelId) {
        workingBodyService.removeMatterFromWorkingBody(workingBodyId, matterId, channelId);
    }

    @Override
    public Integer renameWorkingBody(Integer workingBodyId, String name, Integer channelId) {
        return workingBodyService.renameWorkingBody(workingBodyId, name, channelId);
    }

    @Override
    public void upMatter(Integer matterId) {
        matterService.upMatter(matterId);
    }

    @Override
    public void upWorkingBody(Integer parentId, Integer workingBodyId, Integer channelId) {
        workingBodyService.upWorkingBody(parentId, workingBodyId, channelId);
    }

    @Override
    public Integer updMatter(Matter matter, List<Integer> matterAnnexIds, List<String> filePaths, List<String> legends) {
        return matterService.updMatter(matter, matterAnnexIds, filePaths, legends);
    }

    @Override
    public void addWorkingBodyRoot(Integer channelId) {
        workingBodyService.addWorkingBodyRoot(channelId);
    }

    @Override
    public String addOrganToMatter(Integer matterId, Integer organId) {
        return matterService.addOrganToMatter(matterId, organId);
    }

    @Override
    public String addOrganToWorkingBody(Integer workingBodyId, List<Integer> organIds) {
        return workingBodyService.addOrganToWorkingBody(workingBodyId, organIds);
    }

    @Override
    public String removeOrganFromMatter(Integer matterId) {
        return matterService.removeOrganFromMatter(matterId);
    }

    @Override
    public String removeOrganFromWorkingBody(Integer workingBodyId) {
        return workingBodyService.removeOrganFromWorkingBody(workingBodyId);
    }

    @Override
    public String addCitizenToMatter(Integer matterId, List<Integer> citizenIds) {
        return matterService.addCitizenToMatter(matterId, citizenIds);
    }

    @Override
    public String removeCitizenFromMatter(Integer matterId) {
        return matterService.removeCitizenFromMatter(matterId);
    }

    @Override
    public List<Citizen> getAllCitizen() {
        return citizenService.getAllCitizen();
    }

    @Override
    public void advisorReplay(Integer id, String replay) {
        advisorService.replay(id, replay);
    }

    @Override
    public Advisor getAdvisor(Integer id) {
        return advisorService.get(id);
    }

    @Override
    public void releaseAdvisor(Integer id,boolean pub) {
        advisorService.release(id,pub);
    }

	@Override
	public void deleteAdvisor(List<Integer> ids) {
		advisorService.deleteAdvisor(ids);
	}
}
