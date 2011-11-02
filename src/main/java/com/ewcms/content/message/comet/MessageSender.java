package com.ewcms.content.message.comet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageSender implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);
	
    protected boolean running = true;  
    protected final ArrayList<String> messages = new ArrayList<String>();  
    private ServletResponse connection;  

    public synchronized void setConnection(ServletResponse connection){  
        this.connection = connection;  
        notify();  
    }  

    public void stop() {  
        running = false;  
    }  

    /** 
     * Add message for sending. 
     */  
	public void send(String message) {  
        synchronized (messages) {  
            messages.add(message);  
            logger.info("Message added #messages=" + messages.size());  
            messages.notify();  
        }  
    }  

    public void run() {  
        logger.info("start running!!!");  
        while (running) {  
            if (messages.size() == 0) {  
                try {  
                    synchronized (messages) {  
                    	logger.info("start waiting!!!");  
                        messages.wait();  
                    }  
                } catch (InterruptedException e) {  
                    // Ignore  
                }  
            }  
            String[] pendingMessages = null;  
            synchronized (messages) {  
                pendingMessages = messages.toArray(new String[0]);  
                messages.clear();  
            }  
            try {  
                if (connection == null){  
                    try{  
                        synchronized(this){  
                            wait();  
                        }  
                    } catch (InterruptedException e){  
                        // Ignore  
                    }  
                }  
                PrintWriter writer = connection.getWriter();  
                for (int j = 0; j < pendingMessages.length; j++) {  
                    final String result = pendingMessages[j] + "<br>";  
                    writer.println(result);  
                    logger.info("Writing:" + result);  
                }  
                writer.flush();  
                writer.close();  
                connection = null;  
                logger.info("Closing connection");  
            } catch (IOException e) {  
            	logger.error("IOExeption sending message", e);  
            }  
        }
    }
    
    
}
