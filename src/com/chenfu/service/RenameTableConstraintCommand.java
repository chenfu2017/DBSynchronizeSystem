package com.chenfu.service;

import com.chenfu.domain.DataBase;

public class RenameTableConstraintCommand extends TempTableSynCommand {

	
	private DataSynHelper dataSynHelper;
	
	private DataBase dataBase;
	
	private String TableName;
	
	private  String oldConstraintNames[];
	
	private String newConstraintNames[];
	
	private String constraintNamePrefix;
	
	

	public RenameTableConstraintCommand(DataSynHelper dataSynHelper,
			DataBase dataBase, String tableName) {
		this.dataSynHelper = dataSynHelper;
		this.dataBase = dataBase;
		TableName = tableName;
	}

	
	private String[] queryConstraintNames(String tableName,DataBase dataBase){
		
		return null;
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}
	

}
