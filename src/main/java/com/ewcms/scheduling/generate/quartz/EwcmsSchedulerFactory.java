package com.ewcms.scheduling.generate.quartz;

import java.util.Properties;

import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author wuzhijun
 *
 */
public class EwcmsSchedulerFactory extends StdSchedulerFactory {

	private Properties initProps;

	public void initialize(Properties props) throws SchedulerException {
		this.initProps = props;
		
		super.initialize(props);
	}
	
	public Properties getInitProps() {
		return initProps;
	}
	
	public void reinit() throws SchedulerException {
		super.initialize(initProps);
	}
	
}
