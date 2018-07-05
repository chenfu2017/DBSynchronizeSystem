package com.chenfu.service;

import com.chenfu.domain.DataBase;

public class RenameTableCommand extends TempTableSynCommand implements DataSynHelper{

	private DataSynHelper dataSynHelper;
	
	private DataBase dataBase;
	
	private String fromTableName;
	
	private String toTableName;
	
	public RenameTableCommand(DataSynHelper dataSynHelper, DataBase dataBase,
			String fromTableName, String toTableName) {
		this.dataSynHelper = dataSynHelper;
		this.dataBase = dataBase;
		this.fromTableName = fromTableName;
		this.toTableName = toTableName;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renameTable(String fromTableName, String toTableName,
			DataBase dataBase) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renameConstraintNames(String tableName,
			String[] oldconstraintNames, String[] newconstraintNames,
			DataBase dataBase) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void synTableData(String fromTableName, String toTableName,
			DataBase srcDB, DataBase deskDB) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dropTable(String fromTableName, DataBase dataBase) {
		// TODO Auto-generated method stub
		
	}

}
