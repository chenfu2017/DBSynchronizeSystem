package com.chenfu.service;


public class OracleViewDBSynchronizer extends DefaultOracleObjectSynchronizer{

	@Override
	public void processSyn(DBSynchronizeManager dbSynchronizeManager) {
		System.out.println("OracleViewDBSynchronizer");
	}
}
