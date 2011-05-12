/**
 * 
 */
package com.ewcms.web.action.site.channel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.core.site.ChannelNode;
import com.ewcms.core.site.SiteFac;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.ChannelEntity;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateEntity;
import com.ewcms.util.JSONUtil;
import com.ewcms.util.Struts2Util;
import com.ewcms.web.action.CrudBaseAction;
import com.ewcms.web.util.TreeNode;
import com.ewcms.web.util.TreeNodeConvert;

/**
 * @author 周冬初
 * 
 */
public class ChannelAction extends CrudBaseAction<Channel, Integer> {
	private static final long serialVersionUID = 1L;
	@Autowired
	private SiteFac siteFac;
	private Integer id;
	private File iconFile;

	public File getIconFile() {
		return iconFile;
	}

	public void setIconFile(File iconFile) {
		this.iconFile = iconFile;
	}

	public Channel getChannelVo() {
		return super.getVo();
	}

	public void setChannelVo(Channel channelVo) {
		super.setVo(channelVo);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	protected Integer getPK(Channel vo) {
		return vo.getId();
	}

	@Override
	protected Channel getOperator(Integer pk) {
		return siteFac.getChannel(pk);
	}

	@Override
	protected void deleteOperator(Integer pk) {
	}

	@Override
	protected Integer saveOperator(Channel vo, boolean isUpdate) {
		return null;
	}

	@Override
	protected Channel createEmptyVo() {
		return new Channel();
	}

	/**
	 * 获取站点已发布专栏树.
	 */
	public void channelTree() throws Exception {
		constructTree(true);
	}

	/**
	 * 获取站点所有专栏树.
	 */
	public void channelTreePub() throws Exception {
		constructTree(false);
	}

	private void constructTree(Boolean isPub) {
		try {
			if (id == null) {
				ChannelNode rootVo = siteFac.channelNodeRoot();
				if (isPub && !rootVo.isPublicable())
					return;
				TreeNode treeFile = new TreeNode();
				treeFile.setId(rootVo.getId().toString());
				treeFile.setText(rootVo.getName());
				treeFile.setState("open");
				Map<String,String> attributes = new HashMap<String,String>();
				int max = TreeNodeConvert.treeNodePermission(attributes,rootVo.getPermissions());
				treeFile.setAttributes(attributes);
				switch(max){
					case 1:treeFile.setIconCls("icon-table-refresh");break;
					case 2:treeFile.setIconCls("icon-table-edit");break;
					case 4:treeFile.setIconCls("icon-table-pub");break;
					case 8:treeFile.setIconCls("icon-note-add");break;
					case 16:treeFile.setIconCls("icon-note-edit");break;
					case 32:treeFile.setIconCls("icon-note-delete");break;
					case 64:treeFile.setIconCls("icon-note");break;
					default:treeFile.setIconCls("icon-note-error");
				}
				treeFile.setChildren(TreeNodeConvert.channelNodeConvert(siteFac.getChannelChildren(rootVo.getId(), isPub)));
				Struts2Util.renderJson(JSONUtil.toJSON(new TreeNode[] { treeFile }));
				return;
			}
			List<TreeNode> tnList = TreeNodeConvert.channelNodeConvert(siteFac.getChannelChildren(id, isPub));
			if (tnList.isEmpty()) {
				Struts2Util.renderJson("{}");
			} else {
				Struts2Util.renderJson(JSONUtil.toJSON(tnList));
			}
		} catch (Exception e) {
			outputInfo(e.toString());
		}
	}

	public void addChannel() {
		try {
			Integer id = siteFac.addChannel(vo.getId(), vo.getName());
			Struts2Util.renderJson(JSONUtil.toJSON(id));
		} catch (Exception e) {
			outputInfo(e.toString());
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 删除站点专栏.
	 */
	public void delChannel() {
		try {
			siteFac.delChannel(vo.getId());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 重命名专栏.
	 */
	public void renameChannel() {
		try {
			Channel vo = siteFac.getChannel(getChannelVo().getId());
			vo.setName(getChannelVo().getName());
			siteFac.updChannel(vo);
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 移动专栏.
	 */
	public void movetoChannel() {
		try {
			Channel vo = siteFac.getChannel(getChannelVo().getId());
			Channel parentVo = siteFac.getChannel(getChannelVo().getParent()
					.getId());
			vo.setParent(parentVo);
			siteFac.updChannel(vo);
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	/**
	 * 编辑专栏.
	 */
	public String editChannel() {
		if (getChannelVo() == null || getChannelVo().getId() == null) {
			setChannelVo(siteFac.getChannelRoot());
		} else {
			setChannelVo(siteFac.getChannel(vo.getId()));
		}
		return INPUT;
	}

	/**
	 * 保存专栏.
	 */
	public String saveInfo() {
		try {
			Channel vo = siteFac.getChannel(getChannelVo().getId());
			Template detailTPL = getChannelVo().getDetailTPL();
			Template listTPL = getChannelVo().getListTPL();
			Template homeTPL = getChannelVo().getHomeTPL();
			if (vo.getParent() != null) {
				if (detailTPL.getId() == null) {
					vo.setDetailTPL(null);
				} else {
					if (vo.getDetailTPL() == null
							|| detailTPL.getId().intValue() != vo
									.getDetailTPL().getId()) {
						detailTPL.setId(createChannelTPL(detailTPL.getId()));
						vo.setDetailTPL(detailTPL);
					}
				}

				if (listTPL.getId() == null) {
					vo.setListTPL(null);
				} else {
					if (vo.getListTPL() == null
							|| listTPL.getId().intValue() != vo.getListTPL()
									.getId()) {
						listTPL.setId(createChannelTPL(listTPL.getId()));
						vo.setListTPL(listTPL);
					}
				}
				vo.setDir(getChannelVo().getDir());
				vo.setListSize(getChannelVo().getListSize());
				vo.setUrl(getChannelVo().getUrl());
				vo.setMaxSize(getChannelVo().getMaxSize());
				vo.setDescribe(getChannelVo().getDescribe());
			}
			if (homeTPL.getId() == null) {
				vo.setHomeTPL(null);
			} else {
				if (vo.getHomeTPL() == null
						|| homeTPL.getId().intValue() != vo.getHomeTPL()
								.getId()) {
					homeTPL.setId(createChannelTPL(homeTPL.getId()));
					vo.setHomeTPL(homeTPL);
				}
			}
			vo.setPublicenable(getChannelVo().getPublicenable());
			if (iconFile != null) {
				ChannelEntity entityVo = vo.getChannelEntity();
				if(entityVo==null)
				entityVo = new ChannelEntity();
				byte[] buffer = new byte[Integer.parseInt(String
						.valueOf(iconFile.length()))];
				InputStream in = new BufferedInputStream(new FileInputStream(
						iconFile), Integer.parseInt(String.valueOf(iconFile
						.length())));
				in.read(buffer);
				entityVo.setIconEntity(buffer);
				vo.setChannelEntity(entityVo);
			}
			siteFac.updChannel(vo);
			addActionMessage("数据保存成功！");
		} catch (Exception e) {
			addActionMessage("数据保存失败！");
		}
		setChannelVo(siteFac.getChannel(getChannelVo().getId()));
		return INPUT;
	}

	private Integer createChannelTPL(Integer id) {
		try {
			Template template = siteFac.getTemplate(id);
			if (template.getChannelId() != null)
				return template.getId();

			Template vo = new Template();
			vo.setSite(getCurrentSite());
			vo.setName(template.getName());
			vo.setSize(template.getSize());
			TemplateEntity tplEntity = new TemplateEntity();
			tplEntity.setTplEntity(template.getTemplateEntity().getTplEntity());
			vo.setTemplateEntity(tplEntity);
			vo.setParent(siteFac.channelTemplate(
					getChannelVo().getId().toString()));
			vo.setPath(siteFac.getTemplate(vo.getParent().getId()).getPath() + "/"
					+ template.getName());
			vo.setChannelId(getChannelVo().getId());
			return siteFac.addTemplate(vo);
		} catch (Exception e) {
		}
		return null;
	}
}
