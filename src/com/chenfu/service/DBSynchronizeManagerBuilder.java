package com.chenfu.service;

import com.chenfu.domain.DataBase;
import com.chenfu.domain.SynchronizeParamInfo;
import com.chenfu.utils.Dom4jUtil;

public class DBSynchronizeManagerBuilder {

	private SynchronizeParamInfo synchronizeParamInfo;

	private DBSynchronizeManager dbSynchronizeManager;

	public SynchronizeParamInfo getSynchronizeParamInfo() {
		return synchronizeParamInfo;
	}

	public void initParam(SynchronizeParamInfo synchronizeParamInfo) {
		synchronizeParamInfo = Dom4jUtil.getBean("synchronizeParamInfo",
				SynchronizeParamInfo.class);
	}

	public void buildLife() {
		DBSynchronizeManagerFactory dbSynchronizeManagerFactory = new DBSynchronizeManagerFactory();
		dbSynchronizeManager = dbSynchronizeManagerFactory
				.getDBSynchronizeManager();
	}

	public void buildDataBase() {
		DataBase srcDB = Dom4jUtil.getBean("srcDB", DataBase.class);
		DataBase ctrlDB = Dom4jUtil.getBean("ctrlDB", DataBase.class);
		DataBase deskDB = Dom4jUtil.getBean("deskDB", DataBase.class);
		dbSynchronizeManager.setCtrlDB(ctrlDB);
		dbSynchronizeManager.setDeskDB(deskDB);
		dbSynchronizeManager.setSrcDB(srcDB);
	}

	public void buildSynchronizer() {
		OracleViewDBSynchronizer oracleViewDBSynchronizer = Dom4jUtil.getBean(
				"oracleViewDBSynchronizer", OracleViewDBSynchronizer.class);
		OracleTableDBSynchronizer oracleTableDBSynchronizer = Dom4jUtil
				.getBean("oracleTableDBSynchronizer",
						OracleTableDBSynchronizer.class);
		dbSynchronizeManager.addSynchronizer(oracleTableDBSynchronizer);
		dbSynchronizeManager.addSynchronizer(oracleViewDBSynchronizer);
	}

	public void buildDataTableConfigs() {

	}

	public DBSynchronizeManager retrieveResult() {

		return dbSynchronizeManager;
	}

}
