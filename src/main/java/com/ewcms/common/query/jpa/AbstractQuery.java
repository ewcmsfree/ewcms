package com.ewcms.common.query.jpa;

import java.util.ArrayList;
import java.util.List;

import com.ewcms.common.query.Queryable;
import com.ewcms.common.query.Result;
import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.cache.CacheResult;
import com.ewcms.common.query.cache.CacheResultable;
import com.ewcms.common.query.cache.NullCacheResult;
import com.ewcms.common.query.cache.ResultCacheable;

public abstract class AbstractQuery implements Queryable {

    protected int getCount(QueryTemplateable template) {
        Long c = (Long)template.getResultSingle();
        
        return c.intValue();
    }

    protected List<Object> getExtList(QueryTemplateable template) {
        if(template != null){
            return template.getResultList();
        }else{
            return new ArrayList<Object>();
        }
    }

    protected List<Object> getResultList(QueryTemplateable template,int row,int page) {
         return template
            .setFirstResult(row * page)
            .setMaxResults(row)
            .getResultList();
    }
    
    protected Result getResult(QueryTemplateable cTemplate,QueryTemplateable lTemplate,QueryTemplateable eTemplate,int row,int page){
        List<Object> resultList = this.getResultList(lTemplate, row, page);
        Result result = new Result(row,getCount(cTemplate),resultList,getExtList(eTemplate));
        return result;
    }

    protected void appendResultList(QueryTemplateable template,int newsResult,CacheResult result) {
        
        if(result.isLoaded()){
            return ;
        }
        
        int startPosition = result.getStartPosition();
        List<Object> list= template
            .setFirstResult(startPosition)
            .setMaxResults(newsResult)
            .getResultList();
        
        if(list.isEmpty()){
            return ;
        }else{
            result.appendResultList(list);
        }
        
        appendResultList(template,newsResult,result);
    }

    protected int getCacheCount(ResultCacheable cache,int count) {
        return (cache.getMaxResult() == -1 || cache.getMaxResult() > count) ? 
                count  : cache.getMaxResult();
    }
    
    protected CacheResultable getCacheResult(ResultCacheable cache,String cacheKey,
            QueryTemplateable cTemplate,QueryTemplateable lTemplate,QueryTemplateable eTemplate,
            int row,int page) {
        
        if(cache == null){
            Resultable result = queryResult();
            return new NullCacheResult(cacheKey,result);
        } 
        
        CacheResult cacheResult =(CacheResult)cache.getResultFromCache(cacheKey);
        if(cacheResult == null){
          int count = getCacheCount(cache,getCount(cTemplate));
          cacheResult = new CacheResult(cacheKey,count,getExtList(eTemplate));
        } 
        cacheResult.setPage(page);
        cacheResult.setRow(row);
        appendResultList(lTemplate,cache.getNewsResult(),cacheResult);
        
        return cache.putResultInCache(cacheResult);
    }
}
