/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.content.resource.web.image;

import com.ewcms.content.resource.model.Resource;
import com.ewcms.content.resource.model.ResourceType;
import com.ewcms.content.resource.web.ResourceQueryAction;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wangwei
 */
public class ImageQueryAction extends ResourceQueryAction {

    private int col;

    public void setCol(int col) {
        this.col = col;
    }

    protected List constructRows(final List data) {
        return rows(data);
    }

    private List<List> rows(final List data) {
        int size = data.size();
        if ((size % col) != 0) {
            int count = col - (size % col);
            size = size + count;
            for (int i = 0; i < count; ++i) {
                data.add(new Resource());
            }
        }

        int rowCount = (size) / col;
        List<List> rowData = new ArrayList<List>();
        for (int i = 0; i < rowCount; ++i) {
            int start = i * col;
            int end = (i + 1) * col;
            rowData.add(data.subList(start, end));
        }

        return rowData;
    }

    @Override
    protected ResourceType resourceType() {
        return ResourceType.IMAGE;
    }
}
