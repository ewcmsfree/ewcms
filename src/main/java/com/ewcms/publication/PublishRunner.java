/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.core.site.model.Site;
import com.ewcms.publication.service.SitePublishServiceable;
import com.ewcms.publication.task.TaskException;
import com.ewcms.publication.task.TaskRegistryable;
import com.ewcms.publication.task.Taskable;
import com.ewcms.publication.task.publish.SiteMultiPublish;
import com.ewcms.publication.task.publish.SitePublish;
import com.ewcms.publication.task.publish.SitePublishable;

public class PublishRunner implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(PublishRunner.class);
    
    private final Map<Integer,SitePublishable> sitePublishs = 
        Collections.synchronizedMap(new HashMap<Integer,SitePublishable>());
    private final SitePublishServiceable siteService;
    private final  Semaphore semaphore;
    private final TaskRegistryable registry;
    private final boolean multi;
    
    
    public PublishRunner(
            SitePublishServiceable siteService,
            TaskRegistryable registry,int maxSite){
        
        this(siteService,registry,maxSite,false);
    }
    
    public PublishRunner(
            SitePublishServiceable siteService,
            TaskRegistryable registry,int maxSite,boolean multi){
        
        this.siteService = siteService;
        this.semaphore = new Semaphore(maxSite);
        this.registry = registry;
        this.multi = multi;
    }
    
    @Override
    public void run() {
        for(Integer siteId = registry.getWaitSite();true;siteId = registry.getWaitSite()){
            try {
                semaphore.acquire();
                Taskable task = registry.getTaskOfSite(siteId);
                SitePublishable sitePublish = getSitePublish(siteId);
                if(task == null || sitePublish == null){
                    semaphore.release();
                    continue;
                }
                Thread t = new Thread(new SiteTaskRunner(sitePublish,task,semaphore));
                t.start();
            } catch (InterruptedException e) {
                logger.error("Site's task is fail of {}:{}",siteId,e);
            }
        }
    }

   private SitePublishable getSitePublish(Integer siteId){
       
       synchronized(sitePublishs){
           SitePublishable sitePublish = sitePublishs.get(siteId);
           if(sitePublish != null){
               return sitePublish;
           }
           Site site = siteService.getSite(siteId);
           if(site ==null){
               logger.warn("Site is not exist,Site's id is {}",siteId);
               return null;
           }
           if(multi){
               sitePublish = new SitePublish(site.getSiteServer());
           }else{
               sitePublish = new SiteMultiPublish(site.getSiteServer());
           }
           sitePublishs.put(siteId, sitePublish);
           
           return sitePublish;
       }
   }
   
   public void remove(Integer siteId){
       synchronized(sitePublishs){
           sitePublishs.remove(siteId);
       }
   }
   
   class SiteTaskRunner implements Runnable{
       private final SitePublishable sitePublish;
       private final Taskable task;
       private final  Semaphore semaphore;
       
       SiteTaskRunner(SitePublishable sitePublish,Taskable task,Semaphore semaphore){
           this.sitePublish = sitePublish;
           this.task = task;
           this.semaphore = semaphore;
       }
       
       @Override
       public void run() {
            try {
                sitePublish.publish(task);
            } catch (TaskException e) {
                logger.debug("Site's task is fail:{}",e.toString());
            }finally{
                try {
                    task.close();
                } catch (TaskException e) {
                    logger.debug("Task closed  fail:{}",e.toString());
                }
                semaphore.release();
            }
       }
   }
}
