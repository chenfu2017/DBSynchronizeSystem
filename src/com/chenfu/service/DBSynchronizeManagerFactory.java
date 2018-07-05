package com.chenfu.service;

import com.chenfu.utils.Dom4jUtil;

public class DBSynchronizeManagerFactory {
	
	public DBSynchronizeManager getDBSynchronizeManager(){
		DBSynchronizeManager dbSynchronizeManager = Dom4jUtil.getBean("dbSynchronizeManager");
		return dbSynchronizeManager;	
	}
}
