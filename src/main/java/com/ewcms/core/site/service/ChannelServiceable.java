/**
 * 
 */
package com.ewcms.core.site.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.Permission;
import com.ewcms.core.site.ChannelNode;
import com.ewcms.core.site.model.Channel;
import com.ewcms.publication.service.ChannelPublishServiceable;

/**
 * @author 周冬初
 *
 */
public interface ChannelServiceable extends ChannelPublishServiceable {
	  public Set<Permission> getPermissionsById(int id);
	    
	    public Acl findAclOfChannel(final Channel channel);	

	    public void updatePermissionOfChannel(final Integer id,final Map<String,Integer> sidNamePermissionMasks,boolean inherit);
	    
	    /**
	     * 获取当前频道的子频道
	     * 
	     * TODO 说明原因
	     * 
	     * @param id 频道编号 
	     * @param publicenable 是否发布(true:只显示发布的子频道,false:显示所有子频道）
	     * @return 子频道集合
	     */
	    public List<ChannelNode> getChannelChildren(Integer id,Boolean publicenable);
	  
	    
	    /**
	     * 得到顶级站点专栏（根站点专栏）
	     * 
	     * 顶级站点专栏不存在，则创建顶级站点专栏。
	     * 
	     * @return channel
	     */
	    public ChannelNode channelNodeRoot();
	    
	    public Channel getChannelRoot();
	    /**
	     * 创建站点专栏.
	     * 
	     * @param parentId 父栏目编号.
	     * @param name 栏目名称.
	     * 
	     * @return 频道编号
	     */
	    public Integer addChannel(Integer parentId, String name);

	    /**
	     * 重命名站点专栏.
	     */
	    public void renameChannel(Integer id, String name);

	    /**
	     * 修改站点专栏.
	     */
	    public Integer updChannel(final Channel channel);


	    public void delChannel(Integer id);

	    public Channel getChannel(Integer id);
}
