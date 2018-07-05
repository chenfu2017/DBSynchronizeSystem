package com.chenfu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chenfu.domain.DataBase;
import com.chenfu.domain.synDataTableConfig;


public abstract class DBSynchronizeManager {

	protected List<DBObjectSynchronizer> synchronizers = new ArrayList<DBObjectSynchronizer>();

	protected DataBase srcDB;
	
	protected DataBase ctrlDB;
	
	protected DataBase deskDB;
	
	private Map<String,synDataTableConfig> synDataTableConfig = new HashMap<String,synDataTableConfig>();

	public Map<String, synDataTableConfig> getSynDataTableConfig() {
		return synDataTableConfig;
	}

	public void setSynDataTableConfig(
			Map<String, synDataTableConfig> synDataTableConfig) {
		this.synDataTableConfig = synDataTableConfig;
	}

	public DataBase getDeskDB() {
		return deskDB;
	}

	public void setDeskDB(DataBase deskDB) {
		this.deskDB = deskDB;
	}

	public DataBase getSrcDB() {
		return srcDB;
	}

	public void setSrcDB(DataBase srcDB) {
		this.srcDB = srcDB;
	}

	public DataBase getCtrlDB() {
		return ctrlDB;
	}

	public void setCtrlDB(DataBase ctrlDB) {
		this.ctrlDB = ctrlDB;
	}
	
	public void addSynchronizer(DBObjectSynchronizer dbObjectSynchronizer){
		synchronizers.add(dbObjectSynchronizer);
	}
	
	public void removeSynchronizer(DBObjectSynchronizer dbObjectSynchronizer){
		synchronizers.remove(dbObjectSynchronizer);
	}
	
	public abstract void executeSyn();
	
}
