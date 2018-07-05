package com.chenfu.service;

import com.chenfu.domain.DataBase;

public interface DataSynHelper {
	
	public void renameTable(String fromTableName, String toTableName, DataBase dataBase);
	
	public void renameConstraintNames(String tableName, String oldconstraintNames[], String newconstraintNames[], DataBase dataBase);
	
	public void synTableData(String fromTableName, String toTableName, DataBase srcDB, DataBase deskDB);
	
	public void dropTable(String fromTableName, DataBase dataBase);
	
	
}
