package com.ewcms.plugin.report.manager.util;

import com.ewcms.plugin.report.model.Parameter;
import com.ewcms.plugin.report.model.ParametersType;
import com.ewcms.plugin.report.model.data.SqlData;
import com.ewcms.plugin.report.model.data.StaticData;
import com.ewcms.plugin.report.model.view.BooleanView;
import com.ewcms.plugin.report.model.view.CheckView;
import com.ewcms.plugin.report.model.view.DateView;
import com.ewcms.plugin.report.model.view.ListView;
import com.ewcms.plugin.report.model.view.SessionView;
import com.ewcms.plugin.report.model.view.TextView;

public class ParameterSetValueUtil {
	/**
	 * 把页面传入的对象转换成参数对象
	 * 
	 * @param parameter
	 *            参数对象
	 * @param pagesParam
	 *            页面参数对象
	 * @return
	 */
	public static Parameter setParametersValue(Parameter parameter) {
		if (parameter.getType() == ParametersType.TEXT) {
			StaticData sd = new StaticData();
			sd.setValue(parameter.getValue());
			TextView tv = new TextView();
			parameter.setData(sd);
			parameter.setComponentView(tv);
		} else if (parameter.getType() == ParametersType.BOOLEAN) {
			BooleanView bv = new BooleanView();
			parameter.setData(null);
			parameter.setComponentView(bv);
		} else if (parameter.getType() == ParametersType.CHECK) {
			StaticData sd = new StaticData();
			sd.setValue(parameter.getValue());
			CheckView cv = new CheckView();
			parameter.setData(sd);
			parameter.setComponentView(cv);
		} else if (parameter.getType() == ParametersType.DATE) {
			DateView dv = new DateView();
			parameter.setData(null);
			parameter.setComponentView(dv);
		} else if (parameter.getType() == ParametersType.LIST) {
			StaticData sd = new StaticData();
			sd.setValue(parameter.getValue());
			ListView lv = new ListView();
			parameter.setData(sd);
			parameter.setComponentView(lv);
		} else if (parameter.getType() == ParametersType.SESSION) {
			SessionView sv = new SessionView();
			sv.setName(parameter.getValue());
			parameter.setData(null);
			parameter.setComponentView(sv);
		} else if (parameter.getType() == ParametersType.SQL) {
			SqlData sd = new SqlData();
			sd.setSqlQuery(parameter.getValue());
			parameter.setData(sd);
			parameter.setComponentView(null);
		}
		return parameter;
	}

}
