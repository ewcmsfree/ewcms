package com.ewcms.comm.jpa.query;

import java.sql.Date;
import java.util.Calendar;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Certificate.class)
public abstract class Certificate_ {

	public static volatile SingularAttribute<Certificate, Integer> limit;
	public static volatile SingularAttribute<Certificate, String> id;
	public static volatile SingularAttribute<Certificate, String> name;
	public static volatile SingularAttribute<Certificate, Date> brithdate;
	public static volatile SingularAttribute<Certificate, Calendar> brithCalendar;

}

