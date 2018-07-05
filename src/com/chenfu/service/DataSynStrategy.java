package com.chenfu.service;

import com.chenfu.domain.DataBase;

public abstract class DataSynStrategy {
	
	protected String tableName;
	
	protected DataBase srcDB;
	
	protected DataBase destDB;
	

	public DataSynStrategy(String tableName, DataBase srcDB, DataBase destDB) {
		this.tableName = tableName;
		this.srcDB = srcDB;
		this.destDB = destDB;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public DataBase getSrcDB() {
		return srcDB;
	}

	public void setSrcDB(DataBase srcDB) {
		this.srcDB = srcDB;
	}

	public DataBase getDestDB() {
		return destDB;
	}

	public void setDestDB(DataBase destDB) {
		this.destDB = destDB;
	}

	public abstract String processSyn();
	

}
