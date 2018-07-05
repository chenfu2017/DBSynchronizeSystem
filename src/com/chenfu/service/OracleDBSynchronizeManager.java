package com.chenfu.service;


public class OracleDBSynchronizeManager extends DBSynchronizeManager {

	public void executeSyn() {
		synDBObject();
		compileDBObject();
	}
	
	protected void synDBObject(){
		for (DBObjectSynchronizer dbSynchronizer : synchronizers) {
			try {
				dbSynchronizer.processSyn(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}	
	
	protected void compileDBObject(){
		for (DBObjectSynchronizer dbSynchronizer : synchronizers) {
			try {
				dbSynchronizer.compile(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String toString() {
		return "OracleDBSynchronizeManager [synchronizers=" + synchronizers + ", srcDB=" + srcDB + ", ctrlDB=" + ctrlDB
				+ ", deskDB=" + deskDB + "]";
	}
	
	
}
