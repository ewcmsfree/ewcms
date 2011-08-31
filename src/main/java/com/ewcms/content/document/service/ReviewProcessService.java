/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.document.dao.ReviewProcessDAO;
import com.ewcms.content.document.model.ReviewProcess;

@Service
public class ReviewProcessService implements ReviewProcessServiceable {

	@Autowired
	private ReviewProcessDAO reviewProcessDAO;
	
	@Override
	public Long addReviewProcess(Integer channelId, ReviewProcess reviewProcess) {
		List<ReviewProcess> vos = reviewProcessDAO.findReviewProcessByChannel(channelId);
		reviewProcess.setChannelId(channelId);
		if (vos == null){
			reviewProcess.setPrevProcess(null);
			reviewProcessDAO.persist(reviewProcess);
		}else{
			ReviewProcess lastVo = reviewProcessDAO.findLastReviewProcessByChannel(channelId);
			reviewProcess.setPrevProcess(lastVo);
			reviewProcess.setNextProcess(null);
			lastVo.setNextProcess(reviewProcess);
			
			reviewProcessDAO.merge(lastVo);
		}
		return reviewProcess.getId();
	}

	@Override
	public void delReviewProcess(Long reviewProcessId) {
		ReviewProcess vo = reviewProcessDAO.get(reviewProcessId);
		if (vo.getPrevProcess() == null){
			if (vo.getNextProcess() != null){ 
				ReviewProcess nextVo = reviewProcessDAO.get(vo.getNextProcess().getId());
				nextVo.setPrevProcess(null);
				reviewProcessDAO.merge(nextVo);
			}
		}else{
			if (vo.getNextProcess() == null){
				ReviewProcess prevVo = reviewProcessDAO.get(vo.getPrevProcess().getId());
				prevVo.setNextProcess(null);
				reviewProcessDAO.merge(prevVo);
			}else{
				ReviewProcess prevVo = reviewProcessDAO.get(vo.getPrevProcess().getId());
				ReviewProcess nextVo = reviewProcessDAO.get(vo.getNextProcess().getId());
				prevVo.setNextProcess(nextVo);
				nextVo.setPrevProcess(prevVo);
			
				reviewProcessDAO.merge(nextVo);
				reviewProcessDAO.merge(prevVo);
			}
		}
		reviewProcessDAO.removeByPK(reviewProcessId);
	}

	@Override
	public void downReviewProcess(Integer channelId, Long reviewProcessId) {
		ReviewProcess vo = reviewProcessDAO.get(reviewProcessId);
		if (vo.getNextProcess() != null){
			ReviewProcess prevVo = vo.getPrevProcess();
			ReviewProcess nextVo = vo.getNextProcess();
			
			ReviewProcess nextnextVo = nextVo.getNextProcess();
			if (nextnextVo != null){
				nextnextVo.setPrevProcess(vo);
				reviewProcessDAO.merge(nextnextVo);
			}
			if (prevVo != null){
				prevVo.setNextProcess(nextVo);
				reviewProcessDAO.merge(prevVo);
			}
			
			vo.setPrevProcess(nextVo);
			vo.setNextProcess(nextVo.getNextProcess());
			
			reviewProcessDAO.merge(vo);
			
			nextVo.setPrevProcess(prevVo);
			nextVo.setNextProcess(vo);
			
			reviewProcessDAO.merge(nextVo);
		}
	}

	@Override
	public void upReivewProcess(Integer channelId, Long reviewProcessId) {
		ReviewProcess vo = reviewProcessDAO.get(reviewProcessId);
		if (vo.getPrevProcess() != null){
			ReviewProcess prevVo = vo.getPrevProcess();
			ReviewProcess nextVo = vo.getNextProcess();
			
			ReviewProcess prevprevVo = prevVo.getPrevProcess();
			if (prevprevVo != null){
				prevprevVo.setNextProcess(vo);
				reviewProcessDAO.merge(prevprevVo);
			}
			
			if (nextVo != null){
				nextVo.setPrevProcess(prevVo);
				reviewProcessDAO.merge(nextVo);
			}
			
			vo.setPrevProcess(prevVo.getPrevProcess());
			vo.setNextProcess(prevVo);
			
			reviewProcessDAO.merge(vo);

			prevVo.setPrevProcess(vo);
			prevVo.setNextProcess(nextVo);
			
			reviewProcessDAO.merge(prevVo);
		}
	}

	@Override
	public Long updReviewProcess(ReviewProcess reviewProcess) {
		reviewProcessDAO.merge(reviewProcess);
		return reviewProcess.getId();
	}

	@Override
	public ReviewProcess findReviewProcess(Long reviewProcessId) {
		return reviewProcessDAO.get(reviewProcessId);
	}

	@Override
	public List<ReviewProcess> findReviewProcessByChannel(Integer channelId) {
		return reviewProcessDAO.findReviewProcessByChannel(channelId);
	}

	@Override
	public ReviewProcess findFirstReviewProcessByChannel(Integer channelId) {
		return reviewProcessDAO.findFirstReviewProcessByChannel(channelId);
	}

	@Override
	public Long findReviewProcessCountByChannel(Integer channelId) {
		return reviewProcessDAO.findReviewProcessCountByChannel(channelId);
	}

}
