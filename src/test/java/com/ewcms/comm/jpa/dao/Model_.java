package com.ewcms.comm.jpa.dao;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Model.class)
public abstract class Model_ {

	public static volatile SingularAttribute<Model, Integer> id;
	public static volatile SingularAttribute<Model, String> title;

}

