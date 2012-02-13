/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.resource.model.Resource;
import com.ewcms.publication.task.Taskable;

/**
 * 发布服务测试，该测试主要测试集成后发布是否成功和性能。
 * 
 * 该测试涉及到多线程测试，而junit对多线程测试不支持，所以使用了应用程序测试。
 * 
 * @author wangwei
 */
public class PublishIntegratedTest {
    private final static Logger logger = LoggerFactory.getLogger(PublishIntegratedTest.class);
    
    private final static ApplicationContext context;
    private  static String SPACE;
    
    static{
        context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        SPACE = "";
        for(int i = 0 ; i < 10 ; i ++){
            SPACE = SPACE + "----------";    
        }
    }
    
    private JpaTemplate createJpaTemplate() {
        EntityManagerFactory entityManagerFactory = (EntityManagerFactory)context.getBean("entityManagerFactory");
        return new JpaTemplate(entityManagerFactory);
    }
    
    private PublishServiceable getPublishService(){
        return (PublishServiceable)context.getBean("publishService");
    }
    
    private void updateTemplateSourceStutas(){
        JpaTemplate template = createJpaTemplate();
        template.execute(new JpaCallback<Object>() {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
                em.getTransaction().begin();
                String hql = "Update TemplateSource o Set o.release= ?1 Where o.release = ?2";
                Query query = em.createQuery(hql);
                query.setParameter(1, Boolean.FALSE);
                query.setParameter(2, Boolean.TRUE);
                query.executeUpdate();
                em.getTransaction().commit();
                return null;
            }
        });
    }
    
    private void updateResourceStutas(){
        JpaTemplate template = createJpaTemplate();
        template.execute(new JpaCallback<Object>() {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
                em.getTransaction().begin();
                String hql = "Update Resource o Set o.status= ?1 Where o.status = ?2";
                Query query = em.createQuery(hql);
                query.setParameter(1, Resource.Status.NORMAL);
                query.setParameter(2, Resource.Status.RELEASED);
                query.executeUpdate();
                em.getTransaction().commit();
                return null;
            }
        });
    }
    
    private void updateArticleStutas(){
        JpaTemplate template = createJpaTemplate();
        template.execute(new JpaCallback<Object>() {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
                em.getTransaction().begin();
                String hql = "Update Article o Set o.status=?1 Where o.status = ?2";
                Query query = em.createQuery(hql);
                query.setParameter(1, Article.Status.PRERELEASE);
                query.setParameter(2, Article.Status.RELEASE);
                query.executeUpdate();
                em.getTransaction().commit();
                return null;
            }
        });
    }
    
    private void writerDepender(FileWriter writer,Taskable task,int level)throws IOException{
        String space = SPACE.substring(0,level*2);
        if(task.getProgress() != 100){
            String info = String.format("%s%s，完成 %d",space,task.getDescription(),task.getProgress());
            writer.append(info).append("\n");
            writer.flush();    
        }
        List<Taskable> children = task.getDependences();
        level = level + 1;
        for(Taskable child : children){
            writerDepender(writer,child,level);
        }
        level=level -1;
    }
    
    public void runPublishSite()throws Exception{
//        updateTemplateSourceStutas();
//        updateResourceStutas();
        updateArticleStutas();
        
        PublishServiceable publishService = getPublishService();
        publishService.publishSite(-2, true, "admin");
        
        int seq = 0;
        File file = new File("/tmp/progress/");
        FileUtils.deleteQuietly(file);
        file.mkdir();
        while(true){
            FileWriter  writer = new FileWriter("/tmp/progress/"+String.valueOf(seq)+".txt");
            List<Taskable> tasks = publishService.getSitePublishTasks(-2);
            if(tasks.isEmpty()){
                publishService.closeSitePublish(-2);
            }
            for(Taskable task : tasks){
                writerDepender(writer,task,0);
            }
            writer.flush();
            writer.close();
            seq++;
            Thread.sleep(1000*30);
        }
    }

    public static void main(String[] args){
        try{
            PublishIntegratedTest test = new PublishIntegratedTest();
            test.runPublishSite();
        }catch(Exception e){
            logger.error(e.getMessage());
        }
    }
    
    @Test
    public void testPublish(){
        logger.info("测试程序使用了多线程，请使用main运行方式。");
    }
}
