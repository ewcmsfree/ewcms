/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.plugin.leadingwindow.generator;

import com.ewcms.plugin.leadingwindow.model.LeaderChannel;
import com.ewcms.generator.directive.page.PageParam;
import freemarker.template.Configuration;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import com.ewcms.core.site.model.Channel;
import com.ewcms.generator.release.ReleaseException;
import com.ewcms.generator.dao.GeneratorDAOable;
import com.ewcms.plugin.leadingwindow.dao.PositionDAO;
import com.ewcms.plugin.leadingwindow.model.Leader;
import com.ewcms.plugin.leadingwindow.model.Position;
import com.ewcms.util.EmptyUtil;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.ewcms.util.FileUtil.createDirs;

/**
 *
 * @author wangwei
 */
@Service
public class LeadingRelease implements LeadingReleaseable {

    private static final String DEFAULT_ENCODER = "utf-8";
    private static final int DEFAULT_LIST_MAX_SIZE = 15;
    private String charsetName = DEFAULT_ENCODER;
    private int row = DEFAULT_LIST_MAX_SIZE;
    private boolean home = false;

    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName;
    }

    public void setRow(int row) {
        this.row = row;
    }
    @Autowired
    private PositionDAO positionDAO;

    public void setPositionDAO(PositionDAO dao) {
        this.positionDAO = dao;
    }
    @Autowired
    private GeneratorDAOable generatorDAO;

    public void setGeneratorDAO(GeneratorDAOable dao) {
        this.generatorDAO = dao;
    }
    @Autowired
    private Configuration cfg;

    public void setCfg(Configuration cfg) {
        this.cfg = cfg;
    }

    @Override
    public void release(int channelId) throws ReleaseException {
        release(channelId, -1);
    }

    @Override
    public void release(int channelId, int leaderId) throws ReleaseException {
        Channel channel = generatorDAO.getChannel(channelId);

        String dir = getDir(channel);
        mkdir(dir);

        Position root = getRootPosition(channelId);

        LeadingMenu rootMenu = new LeadingMenu(0, "root");
        initMenu(root, rootMenu, channel.getAbsUrl());
        home = true;
        releaseLeading(root, rootMenu, dir, channel, leaderId);
    }

    private String getDir(final Channel channel) {
        String rootDir = channel.getSite().getServerDir();
        if (rootDir.endsWith("/")) {
            return String.format("%s%s", rootDir, channel.getPubPath());
        } else {
            return String.format("%s/%s", rootDir, channel.getPubPath());
        }
    }

    private void initMenu(final Position position, final LeadingMenu menu, final String parentUrl) {
        int id = getLeadingId(position.getId(), 0);
        menu.setId(id);
        menu.setTitle(position.getName());
        if (EmptyUtil.isCollectionNotEmpty(position.getLeaders())) {
            for (Leader leader : position.getLeaders()) {
                id = getLeadingId(position.getId(), leader.getId());
                String url = getUrl(parentUrl, String.valueOf(id));
                menu.addChild(new LeadingMenu(id, leader.getName(), url));
            }
        }
        for (Position child : position.getChildren()) {
            id = getLeadingId(child.getId(), 0);
            LeadingMenu menuChild = new LeadingMenu(id, child.getName());
            menu.addChild(menuChild);
            initMenu(child, menuChild, parentUrl);
        }
    }

    private int getLeadingId(int positionId, int leaderId) {
        return positionId * 10000 + leaderId;
    }

    private Position getRootPosition(final int channelId) {
        return positionDAO.findPositionParent(channelId);
    }

    private void releaseLeading(final Position position, final LeadingMenu rootMenu, final String dir, final Channel channel, final int leaderId) throws ReleaseException {
        if (EmptyUtil.isCollectionNotEmpty(position.getLeaders())) {
            for (Leader leader : position.getLeaders()) {
                if (!isHtml(leader, leaderId)) {
                    continue;
                }

                int id = getLeadingId(position.getId(), leader.getId());
                menuFocus(rootMenu, id);

                Leader clone = cloneLeader(leader,id);
                if (home) {
                    homeHtml(clone, rootMenu, dir, channel);
                    home = false;
                }
                leadingHtml(clone, rootMenu, dir, channel, id);
            }
        }
        for (Position child : position.getChildren()) {
            releaseLeading(child, rootMenu, dir, channel, leaderId);
        }
    }

    private Leader cloneLeader(final Leader leader,final Integer id) {
        Leader vo = new Leader();
        vo.setChannelId(leader.getChannelId());
        vo.setChargeWork(leader.getChargeWork());
        vo.setContact(leader.getContact());
        vo.setDuties(leader.getDuties());
        vo.setEmail(leader.getEmail());
        vo.setId(id);
        vo.setImage(leader.getImage());
        vo.setMobile(leader.getMobile());
        vo.setName(leader.getName());
        vo.setOfficeAddress(leader.getOfficeAddress());
        vo.setResume(leader.getResume());
        vo.setLeaderChannels(leader.getLeaderChannels());
        
        return vo;
    }

    private boolean isHtml(Leader leader, int leaderId) {
        return leaderId == -1 || leader.getId() == leaderId;
    }

    boolean menuFocus(final LeadingMenu menu, final int id) {

        if (menu.getId() == id) {
            menu.setFocus(true);
            return true;
        }

        boolean current = false;
        for (LeadingMenu child : menu.getChildren()) {
            current = current || menuFocus(child, id);
        }

        menu.setFocus(current);
        return current;
    }

    private void homeHtml(final Leader leader, final LeadingMenu rootMenu, final String dir, final Channel channel) throws ReleaseException {
        String filename = getFilename(dir, "index");
        html(leader, rootMenu, channel, filename);
    }

    private void leadingHtml(final Leader leader, final LeadingMenu rootMenu, final String dir, final Channel channel, final int id) throws ReleaseException {
        String filename = getFilename(dir, String.valueOf(id));
        html(leader, rootMenu, channel, filename);
        leadingChannelHtml(leader, rootMenu, dir, channel, id);
    }

    private void html(final Leader leader, final LeadingMenu rootMenu, final Channel channel, final String filename) throws ReleaseException {
        try {
            Writer writer = createWriter(filename);
            LeadingHtml html = new LeadingHtml(rootMenu, leader);
            html.process(cfg, writer, channel, null);
            writer.flush();
            writer.close();
        } catch (IOException e) {
        }
    }

    private void leadingChannelHtml(final Leader leader, final LeadingMenu rootMenu, final String dir, final Channel channel, final int id) throws ReleaseException {
        List<LeaderChannel> leaderChannels = leader.getLeaderChannels();
        for (LeaderChannel leaderChannel : leaderChannels) {
            String detailDir = getDetailDir(dir, id, leaderChannel.getId());
            mkdir(detailDir);
            int count = leaderChannel.getArticleRmcs().size();
            int pageCount = (count + row - 1) / row;
            String urlPattern = getDetailUrlPattern(channel.getAbsUrl(), id, leaderChannel.getId());
            String filePattern = getDetailFilePattern(dir, id, leaderChannel.getId());
            PageParam pageParam = constructPageParam(pageCount, row, urlPattern);
            LeadingDetailHtml detailHtml = new LeadingDetailHtml(rootMenu, leader, leaderChannel);
            for (int i = 0; i < pageCount; ++i) {
                try {
                    pageParam.setPage(i);
                    if (i == 0) {
                        String path = getDetailIndexFile(dir, id, leaderChannel.getId());
                        Writer writer = createWriter(path);
                        detailHtml.process(cfg, writer, channel, pageParam);
                        writer.flush();
                        writer.close();
                    }
                    String path = String.format(filePattern, i);
                    Writer writer = createWriter(path);
                    detailHtml.process(cfg, writer, channel, pageParam);
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    //TODO错误
                    throw new ReleaseException(e);
                }
            }
        }
    }

    private String getDetailUrlPattern(final String url, final int id, final int leaderChannelId) {
        return String.format("%s/%d/%d/%s.html", url, id, leaderChannelId, "%d");
    }

    private String getDetailDir(final String dir, final int leaderId, final int channelId) {
        return String.format("%s/%d/%d", dir, leaderId, channelId);
    }

    private String getDetailFilePattern(final String dir, final int id, final int leaderChannelId) {
        return String.format("%s/%d/%d/%s.html", dir, id, leaderChannelId, "%d");
    }

    private String getDetailIndexFile(final String dir, final int id, final int leaderChannelId) {
        return String.format("%s/%d/%d/index.html", dir, id, leaderChannelId);
    }

    private Writer createWriter(String fileName) throws IOException {
        OutputStream stream = new FileOutputStream(fileName);
        return new OutputStreamWriter(stream, this.charsetName);
    }

    private void mkdir(final String dir) throws ReleaseException {
        try {
            createDirs(dir, true);
        } catch (IOException e) {
            //TODO错误信息
            throw new ReleaseException(e);
        }
    }

    String getUrl(final String url, final String name) {
        return String.format("%s/%s.html", url, name);
    }

    String getFilename(final String dir, final String name) {
        return String.format("%s/%s.html", dir, name);
    }

    private PageParam constructPageParam(final int count, final int row, final String urlPattern) {
        PageParam pageParam = new PageParam();
        pageParam.setCount(count);
        pageParam.setUrlPattern(urlPattern);
        pageParam.setRow(row);

        return pageParam;
    }
}
