/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.online.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.core.site.dao.OrganDAO;
import com.ewcms.core.site.model.Organ;
import com.ewcms.plugin.citizen.dao.CitizenDAO;
import com.ewcms.plugin.citizen.model.Citizen;
import com.ewcms.plugin.online.dao.MatterAnnexDAO;
import com.ewcms.plugin.online.dao.MatterDAO;
import com.ewcms.plugin.online.model.Matter;
import com.ewcms.plugin.online.model.MatterAnnex;
import com.ewcms.plugin.online.util.FormatText;

/**
 *
 * @author 吴智俊
 */
@Service
public class MatterService implements MatterServiceable {

	@Autowired
	private MatterDAO matterDAO;
	@Autowired
	private MatterAnnexDAO matterAnnexDAO;
	@Autowired
	private OrganDAO organDAO;
	@Autowired
	private CitizenDAO citizenDAO;
	
	@Override
	public Integer addMatter(Matter matter, List<String> filePaths, List<String> legends) {
		Matter isMatter = matterDAO.findMatterByMatterName(matter.getName());
		if (isMatter != null) return null;
		Long max_sort = matterDAO.findMatterMaxSort() + 1;
		matter.setSort(max_sort);
		
		List<MatterAnnex> matterAnnexs = new ArrayList<MatterAnnex>();
		if (filePaths != null && filePaths.size() > 0){
			for (int i = 0; i < filePaths.size(); i++){
				MatterAnnex matterAnnex = new MatterAnnex();
				matterAnnex.setUrl(filePaths.get(i));
				matterAnnex.setLegend(legends.get(i));
				matterAnnex.setSort(i + 1);
				matterAnnexs.add(matterAnnex);
			}
		}
		matter.setMatterAnnexs(matterAnnexs);
		
		matterDAO.persist(matter);
		return matter.getId();
	}
	
	@Override
	public void delMatter(Integer matterId){
		Matter matterVo = matterDAO.get(matterId);
		Long sort = matterVo.getSort();
		List<Matter> matterVos = matterDAO.findAll();
		Integer size = matterVos.size();
		
		matterDAO.removeByPK(matterId);
		
		if (sort.intValue() != size.intValue()){
			sort = 1L;
			matterVos = matterDAO.findMatterAllOrderBySort();
			for (Matter matter : matterVos){
				matter.setSort(sort);
				sort++;
				matterDAO.merge(matter);
			}
		}
	}
	
	@Override
	public Matter getMatter(Integer matterId) {
		return matterDAO.get(matterId);
	}

	@Override
	public Integer updMatter(Matter matter, List<Integer> matterAnnexIds, List<String> filePaths, List<String> legends) {
		Matter isMatter = matterDAO.findMatterByMatterIdAndMatterName(matter.getId(), matter.getName());
		if (isMatter != null) return null;
		Matter matter_old = matterDAO.get(matter.getId());
		
		matter_old.setAcceptedCondition(matter.getAcceptedCondition());
		matter_old.setAcceptedWay(matter.getAcceptedWay());
		matter_old.setConsultingTel(matter.getConsultingTel());
		matter_old.setContactName(matter.getContactName());
		matter_old.setContactTel(matter.getContactTel());
		matter_old.setDeadline(matter.getDeadline());
		matter_old.setDepartment(matter.getDepartment());
		matter_old.setEmail(matter.getEmail());
		matter_old.setFees(matter.getFees());
		matter_old.setFeesBasis(matter.getFeesBasis());
		matter_old.setHandleBasis(matter.getHandleBasis());
		matter_old.setHandleCourse(matter.getHandleCourse());
		matter_old.setHandleSite(matter.getHandleSite());
		matter_old.setHandleWay(matter.getHandleWay());
		matter_old.setName(matter.getName());
		matter_old.setPetitionMaterial(matter.getPetitionMaterial());
		matter_old.setTimeLimit(matter.getTimeLimit());
		matter_old.setInfoUrl(matter.getInfoUrl());
		matter_old.setOnlinePayUrl(matter.getOnlinePayUrl());
		matter_old.setServiceObject(matter.getServiceObject());

		int order = 0;
		List<MatterAnnex> matterAnnexs = new ArrayList<MatterAnnex>();
		if (matterAnnexIds != null && !matterAnnexIds.isEmpty()){
			order = matterAnnexIds.size();
			for (int i = 0; i < matterAnnexIds.size(); i++){
				MatterAnnex matterAnnex = matterAnnexDAO.get(matterAnnexIds.get(i));
				if (matterAnnex == null) continue;
				if (filePaths.get(i) != null && filePaths.get(i).length() > 0)
					matterAnnex.setUrl(filePaths.get(i));
				if (legends.get(i) != null && legends.get(i).length() > 0)
					matterAnnex.setLegend(legends.get(i));
				matterAnnexs.add(matterAnnex);
			}
		}
		
		if (filePaths != null && !filePaths.isEmpty()){
			for (int i = order; i < filePaths.size(); i++){
				MatterAnnex matterAnnex = new MatterAnnex();
				matterAnnex.setUrl(filePaths.get(i));
				matterAnnex.setLegend(legends.get(i));
				matterAnnex.setSort(i + 1);
				matterAnnexs.add(matterAnnex);
			}
		}
		
		matter_old.setMatterAnnexs(matterAnnexs);
		
		matterDAO.merge(matter_old);
		return matter.getId();
	}

	@Override
	public void downMatter(Integer matterId) {
		if (matterId == null) return;
		Matter matter = matterDAO.get(matterId);
		if (matter == null) return;
		Long sort = matter.getSort();
		Long maxSort = matterDAO.findMatterMaxSort();
		if (sort == null || sort.intValue() >= maxSort.intValue()) return;
		Matter matter_next = matterDAO.findMatterBySort(sort + 1);
		if (matter_next == null) return;
		matter.setSort(sort + 1);
		matterDAO.merge(matter);
		matter_next.setSort(sort);
		matterDAO.merge(matter_next);
	}

	@Override
	public void upMatter(Integer matterId) {
		if (matterId == null) return;
		Matter matter = matterDAO.get(matterId);
		if (matter == null) return;
		Long sort = matter.getSort();
		if (sort == null || sort.intValue() <= 1) return;
		Matter matter_prv = matterDAO.findMatterBySort(sort - 1);
		if (matter_prv == null) return;
		matter.setSort(sort - 1);
		matterDAO.merge(matter);
		matter_prv.setSort(sort);
		matterDAO.merge(matter_prv);
	}
	
	@Override
	public List<Matter> getMatterAllOrderBySort() {
		return matterDAO.findMatterAllOrderBySort();
	}
	
	@Override
	public String addOrganToMatter(Integer matterId, Integer organId){
		if (matterId == null || organId == null) return "";
		Matter matter = matterDAO.get(matterId);
		Organ organ = organDAO.get(organId);
		matter.setOrgan(organ);
		matterDAO.merge(matter);
		
		return tranformMatterText(matter);
	}
	
	@Override
	public String removeOrganFromMatter(Integer matterId){
		if (matterId == null) return "";
		Matter matter = matterDAO.get(matterId);
		matter.setOrgan(null);
		matterDAO.merge(matter);
		
		return tranformMatterText(matter);
	}
	
	private String tranformMatterText(Matter matter){
		String organName = "";
		Organ organ = matter.getOrgan();
    	if (organ != null) {
    		organName = FormatText.ORGAN_NAME_BEGIN + organ.getName() + FormatText.ORGAN_NAME_END;
    	}
        
        String citizenName = "";
        List<Citizen> citizens = matter.getCitizens();
        if (citizens != null && !citizens.isEmpty()){
	        for (Citizen citizen : citizens){
	        	citizenName = citizenName + "," + citizen.getName();
	        }
	        citizenName = citizenName.substring(1);
	        citizenName = FormatText.CITIZEN_NAME_BEGIN + citizenName + FormatText.CITIZEN_NAME_END;
        }
        
        return matter.getName() + organName + citizenName;
    }
	
	@Override
	public String addCitizenToMatter(Integer matterId, List<Integer> citizenIds){
    	if (matterId == null) return "";
    	if (citizenIds == null || citizenIds.isEmpty()) return "";
    	Matter matter = matterDAO.get(matterId);
    	List<Citizen> citizens = new ArrayList<Citizen>();
    	for (Integer citizenId : citizenIds){
    		Citizen citizen = citizenDAO.get(citizenId);
    		citizens.add(citizen);
    	}
    	matter.setCitizens(citizens);
    	matterDAO.merge(matter);
    	
    	return tranformMatterText(matter);
	}
	
	@Override
	public String removeCitizenFromMatter(Integer matterId){
		if (matterId == null) return "";
		Matter matter = matterDAO.get(matterId);
		matter.setCitizens(null);
		matterDAO.merge(matter);
		
		return tranformMatterText(matter);
	}
}
