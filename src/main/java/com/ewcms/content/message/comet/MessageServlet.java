package com.ewcms.content.message.comet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.CometEvent;
import org.apache.catalina.CometProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ewcms.content.message.dao.MsgReceiveDAO;
import com.ewcms.content.message.model.MsgReceive;

public class MessageServlet extends HttpServlet implements CometProcessor {

	private static final long serialVersionUID = 8941875437286407922L;

	private static final Logger logger = LoggerFactory.getLogger(MessageServlet.class);

	protected ArrayList<HttpServletResponse> connections = new ArrayList<HttpServletResponse>();
	private MessageSender messageSender = null;
	private int newsMsgCount = 0;
	private static final Integer TIMEOUT = 60 * 1000;

	private MsgReceiveDAO msgReceiveDAO;
	
	@Override
	public void destroy() {
		logger.info("destory");
		connections.clear();
		messageSender.stop();
		messageSender = null;
	}

	public void init() throws ServletException {
		logger.info("init");
		ServletContext application = getServletContext(); 
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(application);
		msgReceiveDAO = (MsgReceiveDAO)wac.getBean("msgReceiveDAO");
		messageSender = new MessageSender();
		Thread messageSenderThread = new Thread(messageSender, "MessageSender["	+ getServletContext().getContextPath() + "]");
		messageSenderThread.setDaemon(true);
		messageSenderThread.start();
	}

	@Override
	public void event(CometEvent event) throws IOException, ServletException {
		HttpServletRequest request = event.getHttpServletRequest();
		HttpServletResponse response = event.getHttpServletResponse();
		if (event.getEventType() == CometEvent.EventType.BEGIN) {
			logger.info("Begin for session: " + request.getSession(true).getId());
			request.setAttribute("org.apache.tomcat.comet.timeout", TIMEOUT);
			messageSender.setConnection(response);
			
			NewsMessage newsMessage = new NewsMessage(request);
			newsMessage.start();
		} else if (event.getEventType() == CometEvent.EventType.ERROR) {
			logger.info("Error for session: " + request.getSession(true).getId());
			event.close();
		} else if (event.getEventType() == CometEvent.EventType.READ) {
			logger.info("Read....");
			throw new UnsupportedOperationException("This servlet does not accept data");
		} 
	}

	private class NewsMessage {
		private HttpServletRequest request;
		
		public NewsMessage(HttpServletRequest request){
			this.request = request;
		}
		
		public void start() {
			Runnable r = new Runnable() {
				@Override
				public void run() {
					int i = 0;
					while (i >= 0) {
						try {
							HttpSession session = request.getSession();
							SecurityContext securityContext = (SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT");
							String userName = "";
							if (securityContext != null){
								UserDetails userDetails = (UserDetails)securityContext.getAuthentication().getPrincipal();
								userName = userDetails.getUsername();
							}

							if (userName.equals("")){
								 wait();
							}else{
								List<MsgReceive> msgReceives = msgReceiveDAO.findMsgReceiveByUserNameAndUnRead(userName);
								int unReadMsg = msgReceives.size();
								if (unReadMsg != newsMsgCount){
									newsMsgCount = unReadMsg;
									messageSender.send(entryToHtml(msgReceives.size()));
								}
								Thread.sleep(30000L);
							}
						}catch(Exception e){
						}
						i++;
					}
				}
				
			};
			Thread t = new Thread(r);
			t.start();
		}
		
		private String entryToHtml(Integer count){
			StringBuilder html = new StringBuilder();
			if (count > 0){
				html.append("<a href='javascript:void(0);' onclick='javascript:_home.addTab(\"个人消息\",\"message/index.do\");return false;' onfocus='this.blur();' style='color:red;font-size:13px;text-decoration:none;'>【<img src='./ewcmssource/image/msg/msg_new.gif'/>新消息(" + count + ")】</a>");
			}else{
				html.append("");
			}
			return html.toString();
		}
	}
}
