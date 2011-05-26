/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query.jpa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ewcms.common.query.model.Certificate;
import com.ewcms.common.query.model.LimitLog;
import com.ewcms.common.query.model.Sex;

@Repository
@Transactional
public class QueryInit extends JpaDaoSupport{

    
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD");
    
    @Autowired
    protected EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void init() {
        super.setEntityManagerFactory(entityManagerFactory);
    }
    
    public void initDatabase()throws IOException{
        if(isAlreadyInit()){
            return;
        }
        insert();
    }
    
    public void resetInitDatabase()throws IOException{
        clean();
        initDatabase();
    }
    
    private boolean isAlreadyInit(){
        return getJpaTemplate().execute(new JpaCallback<Boolean>(){
            @Override
            public Boolean doInJpa(EntityManager em) throws PersistenceException {
                String hql = "Select count(o.id) From Certificate o";
                int count = em.createQuery(hql,Long.class).getSingleResult().intValue();
                return count > 0;
            }
            
        });
    }
    
    private void clean(){
        getJpaTemplate().execute(new JpaCallback<Object>(){
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
                String hql = "Delete From LimitLog";
                em.createQuery(hql).executeUpdate();
                hql="Delete From Certificate";
                em.createQuery(hql).executeUpdate();
                hql="Delete From Sex";
                em.createQuery(hql).executeUpdate();
                return null;
            }
        });
    }
    
    private void insert()throws IOException{
        insertSex();
        insertCertificate();
        insertLimitLog();
    }
        
    private void insertSex()throws IOException{
        Insert<Sex> insert = new Insert<Sex>("sex.csv",getJpaTemplate());
        insert.insert(new InsertCallback<Sex>(){    
            @Override
            public Sex mapping(String line) {
                String[] array = line.split(",");
                Sex sex = new Sex();
                sex.setId(Integer.valueOf(array[0]));
                sex.setName(array[1]);
                
                return sex;
            }
        });
    }
    
    private void insertCertificate()throws IOException{
        Insert<Certificate> insert = new Insert<Certificate>("certificate.csv",getJpaTemplate());
        final JpaTemplate jpaTemplate = this.getJpaTemplate();
        insert.insert(new InsertCallback<Certificate>(){
            @Override
            public Certificate mapping(String line) {
                String[] array = line.split(",");
                Certificate c = new Certificate();
                c.setId(array[0]);
                c.setName(array[1]);
                c.setLimit(Float.valueOf(array[4]).intValue());
                c.setSex(jpaTemplate.getReference(Sex.class, Integer.valueOf(array[2])));
                try {
                    c.setBrithdate(format.parse(array[3]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return c;
            }
        });
    }
    
    private void insertLimitLog()throws IOException{
        Insert<LimitLog> insert = new Insert<LimitLog>("limitlog.csv",getJpaTemplate());
        final JpaTemplate jpaTemplate = this.getJpaTemplate();
        insert.insert(new InsertCallback<LimitLog>(){
            @Override
            public LimitLog mapping(String line) {
                String[] array = line.split(",");
                LimitLog log = new LimitLog();
                log.setId(Integer.valueOf(array[0]));
                log.setCertificate(jpaTemplate.getReference(Certificate.class, array[1]));
                log.setMoney(Float.valueOf(array[2]).intValue());
                try {
                    log.setDate(format.parse(array[3]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return log;
            }
        });
    }
    
    class Insert<T>{
   
        private String name;
        private JpaTemplate jpaTemplate;
        
        Insert(String name,JpaTemplate jpaTemplate){
            this.name = name;
            this.jpaTemplate = jpaTemplate;
        }
        
        void insert(InsertCallback<T> callback)throws IOException{
            BufferedReader reader =new BufferedReader(
                    new InputStreamReader(this.getClass().getResourceAsStream(name)));
            List<T> list = new ArrayList<T>();
            for(String line = reader.readLine();line != null ; line = reader.readLine()){
                if(StringUtils.isBlank(line)){
                    continue;
                }
                T t = callback.mapping(line);
                list.add(t);
            }
            
            persist(list); 
        }  
        
        private void persist(List<?> data){
            for(Object entity :data){
                jpaTemplate.persist(entity);
            }
        }
    }
    
    interface InsertCallback<T>{
        T mapping(String line);
    }
}
