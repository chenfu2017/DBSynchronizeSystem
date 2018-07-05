package com.chenfu.service;

import com.chenfu.domain.DataBase;

public class OracleTableDBSynchronizer extends DefaultOracleObjectSynchronizer{

	@Override
	public void processSyn(DBSynchronizeManager dbSynchronizeManager) {
		System.out.println("OracleTableDBSynchronizer");
	}
	
	protected String[] findAllTables(DataBase srcDB){
		
		return new String[]{};
	}

	protected void  synsingleTable(String tableName,DBSynchronizeManager dbSynchronizeManager){
		DataBase srcDB = dbSynchronizeManager.getSrcDB();
		DataBase deskDB = dbSynchronizeManager.getDeskDB();
		DataSynStrategy dataSynStrategy= new IncSynStrategy(tableName, srcDB, deskDB);
		dbSynchronizeManager.executeSyn();		
	}
}
