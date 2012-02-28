package com.ewcms.web.pubsub;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ewcms.content.document.DocumentFacable;
import com.ewcms.core.site.model.Channel;
import com.ewcms.plugin.message.manager.MessageFacable;
import com.ewcms.plugin.message.model.MsgReceive;
import com.ewcms.plugin.message.model.MsgSend;
import com.ewcms.plugin.notes.manager.NotesFacable;
import com.ewcms.plugin.notes.model.Memoranda;
import com.ewcms.security.manage.service.GroupServiceable;
import com.ewcms.web.util.EwcmsContextUtil;
import com.ewcms.web.util.JSONUtil;

public class MessageSender extends PubsubSender {

	private MessageFacable messageFac;
	private NotesFacable notesFac;
	private DocumentFacable documentFac;
	private String userName;
	private String clientTime;
	private Long unReadCount = 0L;
	private Integer popCount = 0;
	private Integer noticeCount = 0;
	private Integer subCount = 0;
	private Integer beApprovalCount = 0;
	private List<String> groupNames = new ArrayList<String>();
	private Boolean isFirst = true;

	public MessageSender(String path, ServletContext context) {
		super(path, context);
	}

	@Override
	protected void init(String path, ServletContext context) {
		messageFac = getMessageFac(context);
		notesFac = getNotesFac(context);
		documentFac = getDocumentFac(context);
		getPathValues(path);
		//groupNames = getUserGroups(userName);
	}

	@Override
	protected String constructFirstOutput(){
		isFirst = true;
//		groupNames = (List<String>)EwcmsContextUtil.getGroupnames();
		return constructOutput();
	}
	
	@Override
	protected String constructOutput() {

		StringBuilder builder = new StringBuilder();

		builder.append(" <script type=\"text/javascript\">\n");
		
		Long count = messageFac.findUnReadMessageCountByUserName(userName);
		if (isFirst || unReadCount.longValue() != count.longValue()){
			unReadCount = count;
			builder.append("      parent._home.getUnReadMessage(" + count.longValue() + ",true);\n");
		}else{
			builder.append("      parent._home.getUnReadMessage(0,false);\n");
		}

		List<Memoranda> popMessages = notesFac.getMemorandaFireTime(userName, clientTime);
		if (isFirst || popMessages.size() != popCount){
			popCount = popMessages.size();
			builder.append("      parent._home.getPopMessage('{\"pops\":").append(JSONUtil.toJSON(popMessages)).append("}', true);\n");
		}else{
			builder.append("      parent._home.getPopMessage('', false);\n");
		}
		
		List<MsgSend> noticeMessages = messageFac.findMsgSendByNotice(10);
		if (isFirst || noticeMessages.size() != noticeCount){
			noticeCount = noticeMessages.size();
			builder.append("      parent._home.getNoticeMessage('{\"notices\":").append(JSONUtil.toJSON(noticeMessages)).append("}', true);\n");
		}else{
			builder.append("      parent._home.getNoticeMessage('', false);\n");
		}

		List<MsgSend> subMessages = messageFac.findMsgSendBySubscription(10);
		if (isFirst || subMessages.size() != subCount){
			subCount = subMessages.size();
			builder.append("      parent._home.getSubscription('{\"subs\":").append(JSONUtil.toJSON(subMessages)).append("}', true);\n");
		}else{
			builder.append("      parent._home.getSubscription('', false);\n");
		}
		
		builder.append(" </script>");

//		Map<Channel, Long> map = documentFac.findBeApprovalArticleMain(userName, groupNames);
//		if ((map != null && !map.isEmpty()) && (isFirst || map.size() != beApprovalCount)){
//			beApprovalCount = map.size();
//		}else{
//			map = new HashMap<Channel, Long>();
//		}
//		builder.append("      parent._home.getBeApproval('{\"beapprovals\":[");
//		Set<Channel> channels = map.keySet();
//		for (Channel channel : channels){
//			builder.append("{");
//	        builder.append("\"channelId\":").append(channel.getId());
//	        builder.append(",\"channelName\":\"").append(channel.getName()).append("\"");
//	        builder.append(",\"articleCount\":").append(map.get(channel));
//	        builder.append("},");
//		}
//		builder.append("      ]}');\n");
		
		if (isFirst) isFirst = false;
		
		return builder.toString();
		
	}

	private MessageFacable getMessageFac(ServletContext context) {
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
		Assert.notNull(applicationContext, "Can not get spring's context");

		MessageFacable fac = applicationContext.getBean("messageFac",MessageFacable.class);
		Assert.notNull(fac, "Can not get messageFac");

		return fac;
	}
	
	private NotesFacable getNotesFac(ServletContext context){
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
		Assert.notNull(applicationContext, "Can not get spring's context");

		NotesFacable fac = applicationContext.getBean("notesFac",NotesFacable.class);
		Assert.notNull(fac, "Can not get notesFac");

		return fac;
	}
	
	private DocumentFacable getDocumentFac(ServletContext context){
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
		Assert.notNull(applicationContext, "Can not get spring's context");

		DocumentFacable fac = applicationContext.getBean("documentFac",DocumentFacable.class);
		Assert.notNull(fac, "Can not get documentFac");

		return fac;
	}
	
	private List<String> getUserGroups(String userName){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> names = new ArrayList<String>();
        
        UserDetails user = userDetails;
        if(user == null){
            return names;
        }
        
        Collection<GrantedAuthority> authorites = user.getAuthorities();
        for(GrantedAuthority auth: authorites){
            if(StringUtils.startsWith(auth.getAuthority(),GroupServiceable.GROUP_NAME_PERFIX)){
                names.add(auth.getAuthority());
            }
        }
        
        return names;

	}

	private void getPathValues(String path) {
		String[] s = StringUtils.split(path, "/");
		String pathValue = s[s.length - 1];
		if (pathValue.startsWith("?")){
			int i = pathValue.indexOf("?");
			userName = pathValue.substring(0, i - 1);
			clientTime = pathValue.substring(i);
		}else{
			userName = pathValue;
			clientTime = (new Date(Calendar.getInstance().getTime().getTime())).toString();
		}
	}
}