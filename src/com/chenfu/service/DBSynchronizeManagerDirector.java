package com.chenfu.service;

import com.chenfu.domain.SynchronizeParamInfo;
import com.chenfu.utils.Dom4jUtil;

public class DBSynchronizeManagerDirector {
	
	private DBSynchronizeManagerBuilder dbSynchronizeManagerBuilder = Dom4jUtil.getBean("dbSynchronizeManagerBuilder");
	
	public DBSynchronizeManager construct(SynchronizeParamInfo synchronizeParamInfo) {
	
		dbSynchronizeManagerBuilder.initParam(synchronizeParamInfo);
		dbSynchronizeManagerBuilder.buildLife();
		dbSynchronizeManagerBuilder.buildDataBase();
		dbSynchronizeManagerBuilder.buildSynchronizer();
		dbSynchronizeManagerBuilder.buildDataTableConfigs();
		return dbSynchronizeManagerBuilder.retrieveResult();
	}
}
